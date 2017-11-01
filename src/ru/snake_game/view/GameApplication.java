package ru.snake_game.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.snake_game.model.FieldGenerators;
import ru.snake_game.model.FieldObjects.Apple;
import ru.snake_game.model.FieldObjects.SnakeBody;
import ru.snake_game.model.FieldObjects.SnakeHead;
import ru.snake_game.model.FieldObjects.Wall;
import ru.snake_game.model.Game;
import ru.snake_game.model.Interfaces.IField;
import ru.snake_game.model.Interfaces.IFieldObject;
import ru.snake_game.model.util.Location;
import ru.snake_game.model.util.Vector;
import ru.snake_game.view.util.INoArgFunction;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class GameApplication extends Application {
    private Stage primaryStage;

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;

    private Scene mainMenuScene;
    private Scene gameScene;
    private Scene pauseMenuScene;

    SubScene gameArea;

    private Duration tickDuration = new Duration(200);
    private double cellSize;
    private double strokeWidth;
    private SnakeHead snake;
    private Game game;
    private Timeline tickLine;
    private Group gameObjectsToDraw;
    private HashMap<IFieldObject, Node> drawnObjects;
    private HashMap<Class, INoArgFunction<Node>> howToPaint;

    @Override
    public void init() {
        initMainMenuScene();
        initGameScene();
        initPauseMenuScene();

        howToPaint = new HashMap<>();
        howToPaint.put(Wall.class, () -> {
            Rectangle res = new Rectangle(
                    cellSize,
                    cellSize);
            res.setFill(Color.GRAY);
            res.setStrokeType(StrokeType.INSIDE);
            res.setStroke(Color.BLACK);
            res.setStrokeWidth(strokeWidth);
            return res;
        });
        howToPaint.put(SnakeHead.class, () -> {
            double r = cellSize / 2;
            Circle res = new Circle(r, r, r);
            res.setStrokeType(StrokeType.INSIDE);
            res.setStroke(Color.BLACK);
            res.setStrokeWidth(strokeWidth);
            res.setFill(Color.LIGHTGREEN);
            return res;
        });
        howToPaint.put(SnakeBody.class, () -> {
            double r = cellSize / 2;
            Circle res = new Circle(r, r, r);
            res.setStrokeType(StrokeType.INSIDE);
            res.setStroke(Color.BLACK);
            res.setStrokeWidth(strokeWidth);
            res.setFill(Color.GREEN);
            return res;
        });
        howToPaint.put(Apple.class, () -> {
            double r = cellSize / 2;
            Circle res = new Circle(r, r, r);
            res.setStrokeType(StrokeType.INSIDE);
            res.setStroke(Color.BLACK);
            res.setStrokeWidth(strokeWidth);
            res.setFill(Color.RED);
            return res;
        });
    }

    private void initGameScene() {
        tickLine = new Timeline();
        tickLine.setOnFinished(event -> {
            game.tick();
            handle(game.getField());
            arrangeTickLineAndDrawnObjects();
            tickLine.play();
        });

        Group root = new Group();
        gameScene = new Scene(root);

        gameObjectsToDraw = new Group();
        //noinspection SuspiciousNameCombination
        gameArea = new SubScene(gameObjectsToDraw, WINDOW_HEIGHT, WINDOW_HEIGHT);
        gameArea.setFill(Color.LIGHTGRAY);
        root.getChildren().add(gameArea);

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

    private void startGame() {
        @SuppressWarnings("MagicNumber") IField field = FieldGenerators.genBoardedField(15, 15);
        snake = new SnakeHead(new Location(4, 4), null, Vector.RIGHT, field);
        field.addObject(snake);
        game = new Game(field);
        drawnObjects = new HashMap<>();
        cellSize = ((double) Integer.min(WINDOW_HEIGHT, WINDOW_WIDTH)) / game.getField().getWidth();
        strokeWidth = cellSize / 20;
        arrangeTickLineAndDrawnObjects();
        tickLine.play();
        primaryStage.setScene(gameScene);
    }

    private void pauseGame() {
        primaryStage.setScene(pauseMenuScene);
        tickLine.pause();
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
                                ((double) loc.getX()) / field.getWidth() * WINDOW_HEIGHT),
                        new KeyValue(node.translateYProperty(),
                                ((double) loc.getY()) / field.getHeight() * WINDOW_HEIGHT)));
            } else {
                node = howToPaint.get(object.getClass()).run();
                drawnObjects.put(object, node);
                node.translateXProperty().setValue(((double) loc.getX()) / field.getWidth() * WINDOW_HEIGHT);
                node.translateYProperty().setValue(((double) loc.getY()) / field.getHeight() * WINDOW_HEIGHT);
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

    private void initPauseMenuScene() {
        Button[] buttons = new Button[]{
                new Button("Resume"),
                new Button("Restart"),
                new Button("Quit to Main Menu")
        };

        buttons[0].setOnAction(event -> {
            primaryStage.setScene(gameScene);
            tickLine.play();
        });

        buttons[1].setOnAction(event -> startGame());

        buttons[2].setOnAction(event -> {
            tickLine.stop();
            primaryStage.setScene(mainMenuScene);
        });

        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setVgap(20);

        Text sceneTitle = new Text();
        sceneTitle.setText("PAUSE");
        sceneTitle.setFont(Font.font("verdana", FontWeight.BOLD, 50));
        root.add(sceneTitle, 0, 0);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setMinSize(180, 40);
            GridPane.setConstraints(buttons[i], 0, i + 1);
        }
        root.getChildren().addAll(buttons);
        pauseMenuScene = new Scene(root);
    }

    private void initMainMenuScene() {
        Group root = new Group();
        mainMenuScene = new Scene(root);
        mainMenuScene.fillProperty().setValue(Color.BLACK);

        GridPane buttonList = new GridPane();
        root.getChildren().add(buttonList);

        //noinspection MagicNumber
        buttonList.layoutXProperty().setValue(20);
        //noinspection MagicNumber
        buttonList.layoutYProperty()
                .bind(mainMenuScene.heightProperty().subtract(buttonList.heightProperty().add(20)));

        buttonList.setVgap(5);

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> primaryStage.close());

        Button playButton = new Button("Play");
        playButton.setOnAction(event -> startGame());

        Button[] buttons = new Button[]{
                playButton,
                new Button("Options"),
                new Button("Credits"),
                exitButton
        };

        for (int i = 0; i < buttons.length; i++) {
            GridPane.setConstraints(buttons[i], 0, i);
        }
        buttonList.getChildren().addAll(buttons);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.resizableProperty().setValue(false);

        primaryStage.setScene(mainMenuScene);
        primaryStage.show();

        primaryStage.setWidth(WINDOW_WIDTH
                + mainMenuScene.getWindow().getWidth()
                - mainMenuScene.getWidth());
        primaryStage.setHeight(WINDOW_HEIGHT
                + mainMenuScene.getWindow().getHeight()
                - mainMenuScene.getHeight());
    }
}
