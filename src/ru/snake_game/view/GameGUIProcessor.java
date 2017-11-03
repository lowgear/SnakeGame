package ru.snake_game.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import ru.snake_game.model.FieldGenerators;
import ru.snake_game.model.FieldObjects.Apple;
import ru.snake_game.model.FieldObjects.SnakeHead;
import ru.snake_game.model.Game;
import ru.snake_game.model.Interfaces.IField;
import ru.snake_game.model.Interfaces.IFieldObject;
import ru.snake_game.model.util.Location;
import ru.snake_game.model.util.Vector;
import ru.snake_game.view.util.NodeAndAnimation;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class GameGUIProcessor {
    private static final double cellSize = 10;
    SubScene gameArea;
    private Duration tickDuration = new Duration(100);
    private SnakeHead snake;
    private Game game;
    private Timeline tickLine;
    private Group gameObjectsToDraw;
    private HashMap<IFieldObject, Node> drawnObjects;
    private Function<IFieldObject, NodeAndAnimation> objectPainter;


    private void initGameScene() {
        tickLine = new Timeline();
        tickLine.setOnFinished(event -> {
            game.tick();
            handle(game.getField());
            arrangeTickLineAndDrawnObjects();
            tickLine.play();
        });


        gameObjectsToDraw = new Group();
        gameArea = new SubScene(gameObjectsToDraw, , );

        HashMap<KeyCode, Runnable> keyPressActions = new HashMap<>();
        keyPressActions.put(KeyCode.ESCAPE, this::pauseGame);
        keyPressActions.put(KeyCode.UP, () -> snake.setDirection(Vector.UP));
        keyPressActions.put(KeyCode.DOWN, () -> snake.setDirection(Vector.DOWN));
        keyPressActions.put(KeyCode.LEFT, () -> snake.setDirection(Vector.LEFT));
        keyPressActions.put(KeyCode.RIGHT, () -> snake.setDirection(Vector.RIGHT));

        gameScene.setOnKeyPressed(event -> {
            Runnable toDo = keyPressActions.get(event.getCode());
            if (toDo != null)
                toDo.run();
        });
    }

    private void arrangeTickLineAndDrawnObjects() {
        tickLine.getKeyFrames().clear();
        gameObjectsToDraw.getChildren().clear();

        IField field = game.getField();
        for (IFieldObject object : field) {
            Node node;
            Location loc = object.getLocation();
            if (drawnObjects.containsKey(object)) {
                node = drawnObjects.get(object);
                tickLine.getKeyFrames().add(new KeyFrame(tickDuration,
                        new KeyValue(node.translateXProperty(),
                                ((double) loc.getX()) / field.getWidth() * gameArea.getWidth()),
                        new KeyValue(node.translateYProperty(),
                                ((double) loc.getY()) / field.getHeight() * gameArea.getHeight())));
            } else {
                node = howToPaint.get(object.getClass()).run();
                drawnObjects.put(object, node);
                node.translateXProperty().setValue(((double) loc.getX()) / field.getWidth() * gameArea.getWidth());
                node.translateYProperty().setValue(((double) loc.getY()) / field.getHeight() * gameArea.getHeight());
            }
            gameObjectsToDraw.getChildren().add(node);
        }
    }

    private void handle(IField field) {
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
    }

    private void startGame() {
        @SuppressWarnings("MagicNumber") IField field = FieldGenerators.genBoardedField(20, 15);
        if (field.getWidth() / field.getHeight() > WINDOW_WIDTH / WINDOW_HEIGHT) {
            gameArea.setWidth(WINDOW_WIDTH);
            gameArea.setHeight(WINDOW_WIDTH / field.getWidth() * field.getHeight());
        } else {
            gameArea.setWidth(WINDOW_HEIGHT / field.getHeight() * field.getWidth());
            gameArea.setHeight(WINDOW_HEIGHT);
        }
        cellSize = gameArea.getHeight() / field.getHeight();

        snake = new SnakeHead(new Location(4, 4), null, Vector.RIGHT, field);
        field.addObject(snake);
        game = new Game(field);
        drawnObjects = new HashMap<>();
        cellSize = Double.min(WINDOW_HEIGHT, WINDOW_WIDTH) / game.getField().getWidth();
        strokeWidth = cellSize / 10;
        arrangeTickLineAndDrawnObjects();
        tickLine.play();
        primaryStage.setScene(gameScene);
    }

    public SubScene getScene() {
        //todo
    }

    public void addObject(IFieldObject object, Node node, Animation animation) {
        //todo
    }

    public void setOnPause(Runnable handler) {
        //todo
    }

    public void play() {
        //todo
    }

    public EventHandler<? super KeyEvent> getKeyPressHandler() {
        //todo
    }


    public void setObjectPainter(Function<IFieldObject, NodeAndAnimation> objectPainter) {
        this.objectPainter = objectPainter;
    }
}
