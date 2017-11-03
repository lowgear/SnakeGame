package ru.snake_game.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.input.KeyCode;
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
import java.util.function.Function;

import static ru.snake_game.view.util.NodeAndAnimation.CELL_SIZE;

public class GameGUIProcessor implements IGameGUIProcessor {
    private static final Function<IField, Object> NO_LOGIC = (field) -> null;
    private final SubScene gameArea;
    private final ISnakeHead snake;
    private final Game game;
    private final Group objectsGroup = new Group();
    private final HashMap<IFieldObject, NodeAndAnimation> drawings = new HashMap<>();
    private final Timeline tickScheduler = new Timeline();
    private final IFieldObjectPainter objectPainter;
    private final boolean inited;
    private Duration tickDuration = Duration.seconds(1);
    private Runnable onPauseAction;
    private Function<IField, Object> gameLogic = NO_LOGIC;

    public GameGUIProcessor(IField field, IFieldObjectPainter objectPainter) {
        gameArea = new SubScene(
                objectsGroup,
                CELL_SIZE * field.getWidth(),
                CELL_SIZE * field.getHeight());

        snake = findSnake(field);
        game = new Game(field);

        this.objectPainter = objectPainter;

        setUpTickScheduler();
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
        });
    }

    private void setUpTickScheduler() {
        if (inited)
            throw new IllegalStateException();

        tickScheduler.setOnFinished((event) -> {
            game.tick();
            gameLogic.apply(game.getField());
            setUpGraphics();
            tickScheduler.play();
        });
        tickScheduler.getKeyFrames().add(new KeyFrame(tickDuration));
    }

    private void setUpGraphics() {
        if (inited)
            throw new IllegalStateException();

        removeNotPresentObjects();
        addNewObjects();
    }

    private void addNewObjects() {
        for (IFieldObject object : game.getField())
            if (!drawings.containsKey(object)) {
                NodeAndAnimation nodeAndAnimation = objectPainter.paint(object);
                normalizeAnimationToTick(nodeAndAnimation.tickAnimation);
                objectsGroup.getChildren().add(nodeAndAnimation.node);
                drawings.put(object, nodeAndAnimation);
            }
    }

    private void removeNotPresentObjects() {
        HashSet<IFieldObject> presentObjects = new HashSet<>();
        game.getField().forEach((presentObjects::add));
        for (IFieldObject object : drawings.keySet())
            if (!presentObjects.contains(object))
                drawings.remove(object);
    }

    private void normalizeAnimationToTick(Animation animation) {
        double rate = animation.getCycleDuration().toMillis() / tickDuration.toMillis();
        animation.setRate(rate);
    }


    /*private void initGameScene() {
        tickLine = new Timeline();
        tickLine.setOnFinished(event -> {
            game.tick();
            emit(game.getField());
            arrangeTickLineAndDrawnObjects();
            tickLine.play();
        });


        objectsGroup = new Group();
        gameArea = new SubScene(objectsGroup, , );
    }*/

    /*private void arrangeTickLineAndDrawnObjects() {
        tickLine.getKeyFrames().clear();
        objectsGroup.getChildren().clear();

        IField field = game.getField();
        for (IFieldObject object : field) {
            Node node;
            Location loc = object.getLocation();
            if (drawings.containsKey(object)) {
                node = drawings.get(object);
                tickLine.getKeyFrames().add(new KeyFrame(tickDuration,
                        new KeyValue(node.translateXProperty(),
                                ((double) loc.getX()) / field.getWidth() * gameArea.getWidth()),
                        new KeyValue(node.translateYProperty(),
                                ((double) loc.getY()) / field.getHeight() * gameArea.getHeight())));
            } else {
                node = howToPaint.get(object.getClass()).run();
                drawings.put(object, node);
                node.translateXProperty().setValue(((double) loc.getX()) / field.getWidth() * gameArea.getWidth());
                node.translateYProperty().setValue(((double) loc.getY()) / field.getHeight() * gameArea.getHeight());
            }
            objectsGroup.getChildren().add(node);
        }
    }*/

    /*private void emit(IField field) {
        boolean hasApple = false;
        for (IFieldObject object :
                field) {
            if (object instanceof Apple) {
                hasApple = true;
                break;
            }
        }
        if (!hasApple) {
            int x, y;
            do {
                x = ThreadLocalRandom.current().nextInt(game.getField().getWidth());
                y = ThreadLocalRandom.current().nextInt(game.getField().getHeight());
            } while (game.getField().getObjectAt(x, y) != null);

            //noinspection MagicNumber
            game.getField().addObject(
                    new Apple(
                            new Location(x, y),
                            game.getField(),
                            ThreadLocalRandom.current().nextInt(1, 3)
                    )
            );
        }
    }*/

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
        for (NodeAndAnimation nodeAndAnimation : drawings.values()) {
            if (nodeAndAnimation.animation != null)
                nodeAndAnimation.animation.play();
            if (nodeAndAnimation.tickAnimation != null)
                nodeAndAnimation.tickAnimation.play();
        }
        tickScheduler.play();
    }

    @Override
    public void pause() {
        for (NodeAndAnimation nodeAndAnimation : drawings.values()) {
            if (nodeAndAnimation.animation != null)
                nodeAndAnimation.animation.pause();
            if (nodeAndAnimation.tickAnimation != null)
                nodeAndAnimation.tickAnimation.pause();
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
