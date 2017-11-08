package ru.snake_game.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import ru.snake_game.model.Game;
import ru.snake_game.model.Interfaces.IField;
import ru.snake_game.model.Interfaces.IFieldObject;
import ru.snake_game.model.Interfaces.ISnakeHead;
import ru.snake_game.model.util.Vector;
import ru.snake_game.view.util.IFieldObjectPainter;
import ru.snake_game.view.util.NodeAndAnimation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static ru.snake_game.view.util.NodeAndAnimation.CELL_SIZE;

public class GameGUIProcessor implements IGameGUIProcessor {
    private static final Function<IField, Object> NO_LOGIC = (field) -> null;
    private final SubScene gameArea;
    private final ISnakeHead snake;
    private final Game game;
    private final Group objectsGroup = new Group();
    private final HashMap<IFieldObject, NodeAndAnimation> drawings = new HashMap<>();
    private final ParallelTransition tickScheduler = new ParallelTransition();
    private final IFieldObjectPainter objectPainter;
    private final boolean inited;
    private Duration tickDuration = Duration.seconds(1);
    private Runnable onPauseAction;
    private Function<IField, Object> gameLogic = NO_LOGIC;
    private boolean isPlaying = false;

    public GameGUIProcessor(IField field, IFieldObjectPainter objectPainter) {
        gameArea = new SubScene(
                objectsGroup,
                CELL_SIZE * field.getWidth(),
                CELL_SIZE * field.getHeight());
        gameArea.setFill(Color.LIGHTGRAY);

        snake = findSnake(field);
        game = new Game(field);

        this.objectPainter = objectPainter;

        setUpAnimations();
        setUpControls();
        setUpGraphics();

        inited = true;
    }

    private static ISnakeHead findSnake(IField field) {
        ISnakeHead res = null;
        for (IFieldObject object : field)
            if (object instanceof ISnakeHead) {
                if (res == null)
                    res = (ISnakeHead) object;
                else
                    throw new IllegalStateException();
            }
        if (res != null)
            return res;
        throw new IllegalStateException();
    }

    private void setUpControls() {
        if (inited)
            throw new IllegalStateException();

        HashMap<KeyCode, Runnable> keyPressActions = new HashMap<>();
        keyPressActions.put(KeyCode.ESCAPE, this::pause);
        keyPressActions.put(KeyCode.UP, () -> snake.setDirection(Vector.UP));
        keyPressActions.put(KeyCode.DOWN, () -> snake.setDirection(Vector.DOWN));
        keyPressActions.put(KeyCode.LEFT, () -> snake.setDirection(Vector.LEFT));
        keyPressActions.put(KeyCode.RIGHT, () -> snake.setDirection(Vector.RIGHT));

        gameArea.setOnKeyPressed(event -> {
            Runnable toDo = keyPressActions.get(event.getCode());
            if (toDo != null)
                toDo.run();
            event.consume();
        });
    }

    private void setUpAnimations() {
        if (inited)
            throw new IllegalStateException();

        tickScheduler.setOnFinished((event) -> {
            game.tick();
            gameLogic.apply(game.getField());
            setUpGraphics();

            playAnimations();
            tickScheduler.play();
        });
        tickScheduler.setRate(1);
//        tickScheduler.setCycleCount(Animation.INDEFINITE);
        Timeline tl = new Timeline(new KeyFrame(tickDuration));
        tickScheduler.getChildren().add(tl);
    }

    private void playAnimations() {
        for (NodeAndAnimation nodeAndAnimation : drawings.values()) {
            if (nodeAndAnimation.animation != null)
                nodeAndAnimation.animation.play();
//            if (nodeAndAnimation.tickAnimation != null)
//                nodeAndAnimation.tickAnimation.play();
        }
    }

    private void setUpGraphics() {
//        if (inited)
//            throw new IllegalStateException();


        removeNotPresentObjects();
        addNewObjects();
        objectsGroup.getChildren().clear();
        for (NodeAndAnimation nodeAndAnimation : drawings.values()) {
            objectsGroup.getChildren().add(nodeAndAnimation.node);
        }
    }

    private void addNewObjects() {
        for (IFieldObject object : game.getField())
            if (!drawings.containsKey(object)) {
                NodeAndAnimation nodeAndAnimation = objectPainter.paint(object);
                normalizeAnimationToTick(nodeAndAnimation.tickAnimation);
                objectsGroup.getChildren().add(nodeAndAnimation.node);
                drawings.put(object, nodeAndAnimation);
                if (isPlaying)
                    if (nodeAndAnimation.animation != null)
                        nodeAndAnimation.animation.play();
                if (nodeAndAnimation.tickAnimation != null)
                    tickScheduler.getChildren().add(nodeAndAnimation.tickAnimation);
//                    nodeAndAnimation.tickAnimation.stop();
            }
    }

    private void removeNotPresentObjects() {
        HashSet<IFieldObject> presentObjects = new HashSet<>();
        game.getField().forEach(presentObjects::add);
        List<IFieldObject> drawnObjects = new LinkedList<>(drawings.keySet());
        tickScheduler.getChildren().clear();
        //todo fix bug with deleted base timeline
        for (IFieldObject object : drawnObjects) {
            NodeAndAnimation nodeAndAnimation = drawings.get(object);
            if (!presentObjects.contains(object)) {
                if (nodeAndAnimation.animation != null)
                    nodeAndAnimation.animation.stop();
                drawings.remove(object);
            } else if (nodeAndAnimation.tickAnimation != null)
                tickScheduler.getChildren().add(nodeAndAnimation.tickAnimation);
        }
    }

    private void normalizeAnimationToTick(Animation animation) {
        if (animation == null)
            return;
        double rate = tickDuration.toMillis() / animation.getCycleDuration().toMillis();
        animation.setRate(rate);
    }

    @Override
    public SubScene getScene() {
        return gameArea;
    }

    @Override
    public void setOnPause(Runnable action) {
        onPauseAction = action;
    }

    @Override
    public void play() {
        isPlaying = true;
        playAnimations();
        tickScheduler.play();
    }

    @Override
    public void pause() {
        isPlaying = false;
        for (NodeAndAnimation nodeAndAnimation : drawings.values()) {
            if (nodeAndAnimation.animation != null)
                nodeAndAnimation.animation.pause();
//            if (nodeAndAnimation.tickAnimation != null)
//                nodeAndAnimation.tickAnimation.pause();
        }
        tickScheduler.pause();

        onPauseAction.run();
    }

    @Override
    public void setGameLogic(Function<IField, Object> gameLogic) {
        if (gameLogic == null)
            gameLogic = NO_LOGIC;
        this.gameLogic = gameLogic;
    }

    @Override
    public DoubleProperty rateProperty() {
        return tickScheduler.rateProperty();
    }
}
