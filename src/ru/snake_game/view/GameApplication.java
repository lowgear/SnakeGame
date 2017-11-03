package ru.snake_game.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.snake_game.model.FieldObjects.Apple;
import ru.snake_game.model.FieldObjects.SnakeBody;
import ru.snake_game.model.FieldObjects.SnakeHead;
import ru.snake_game.model.FieldObjects.Wall;
import ru.snake_game.model.Interfaces.IFieldObject;
import ru.snake_game.view.util.NodeAndAnimation;

import java.util.HashMap;

public class GameApplication extends Application {
    private Stage primaryStage;

    public static final double WINDOW_WIDTH = 800;
    public static final double WINDOW_HEIGHT = 600;

    private Scene mainMenuScene;
    private Scene gameScene;
    private Scene pauseMenuScene;

    private GameGUIProcessor gameProcessor;

    private Circle makeCircle(Paint fill) {
        double r = cellSize / 2;
        Circle res = new Circle(r, r, r);
        res.setStrokeType(StrokeType.INSIDE);
        res.setStroke(Color.BLACK);
        res.setStrokeWidth(strokeWidth);
        res.setFill(fill);
        return res;
    }

    private Rectangle makeSquare(Paint fill) {
        Rectangle res = new Rectangle(
                cellSize,
                cellSize);
        res.setFill(fill);
        res.setStrokeType(StrokeType.INSIDE);
        res.setStroke(Color.BLACK);
        res.setStrokeWidth(strokeWidth);
        return res;
    }

    @Override
    public void init() {
        initMainMenuScene();
        initPauseMenuScene();

        howToPaint = new HashMap<>();
        howToPaint.put(Wall.class, () -> makeSquare(Color.GRAY));
        howToPaint.put(SnakeHead.class, () -> makeCircle(Color.LIGHTGREEN));
        howToPaint.put(SnakeBody.class, () -> makeCircle(Color.GREEN));
        howToPaint.put(Apple.class, () -> makeCircle(Color.RED));
    }

    private void initGameScene(SubScene gameArea) {
        Group root = new Group(gameArea);
        gameScene = new Scene(root);
        gameArea.setFocusTraversable(true);
    }

    private NodeAndAnimation paintFieldObject(IFieldObject object) {
        //todo
    }

    private void startGame() {
        gameProcessor = new GameGUIProcessor();

        gameProcessor.setOnPause(this::pauseGame);
        gameProcessor.setObjectPainter(this::paintFieldObject);

        initGameScene(gameProcessor.getScene());

        primaryStage.setScene(gameScene);
        gameProcessor.play();
    }

    private void pauseGame() {
        primaryStage.setScene(pauseMenuScene);
        WritableImage image = new WritableImage((int) WINDOW_WIDTH, (int) WINDOW_HEIGHT);
        gameScene.snapshot(image);
        ((GridPane) (pauseMenuScene.getRoot()))
                .setBackground(new Background(new BackgroundImage(
                        image,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        BackgroundSize.DEFAULT)));
    }

    private void resumeGame() {
        primaryStage.setScene(gameScene);
        gameProcessor.play();
    }


    private void initPauseMenuScene() {
        Button[] buttons = new Button[]{
                new Button("Resume"),
                new Button("Restart"),
                new Button("Quit to Main Menu")
        };

        buttons[0].setOnAction(event -> resumeGame());
        buttons[1].setOnAction(event -> startGame());
        buttons[2].setOnAction(event -> {
            gameProcessor = null;
            gameScene = null;
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
