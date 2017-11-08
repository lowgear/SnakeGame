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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.snake_game.model.FieldGenerators;
import ru.snake_game.model.FieldObjects.Apple;
import ru.snake_game.model.Interfaces.IField;
import ru.snake_game.model.Interfaces.IFieldObject;
import ru.snake_game.model.util.Location;

import java.util.HashSet;

import static ru.snake_game.view.util.Extensions.getRandomItem;
import static ru.snake_game.view.util.SmoothPainter.SMOOTH_PAINTER;

public class GameApplication extends Application {
    private static final int FIELD_HEIGHT = 15;
    private static final int FIELD_WIDTH = 15;
    private Stage primaryStage;

    public static final double WINDOW_WIDTH = 800;
    public static final double WINDOW_HEIGHT = 600;

    private Scene mainMenuScene;
    private Scene gameScene;
    private Scene pauseMenuScene;

    private GameGUIProcessor gameProcessor;

    @Override
    public void init() {
        initMainMenuScene();
        initPauseMenuScene();
    }

    private void initGameScene(SubScene gameArea) {
        Group root = new Group(gameArea);
        gameScene = new Scene(root);
        gameArea.setFocusTraversable(true);
    }

    private static Object gameLogic(IField field) {
        HashSet<Location> freeLocations = new HashSet<>();
        for (int x = 0; x < field.getWidth(); x++)
            for (int y = 0; y < field.getHeight(); y++)
                freeLocations.add(new Location(x, y));
        for (IFieldObject object :
                field) {
            freeLocations.remove(object.getLocation());
        }

        if (freeLocations.isEmpty())
            return null;

        Location appleLocation = getRandomItem(freeLocations);
        Apple apple = new Apple(appleLocation, field, 1);
        field.addObject(apple);

        return null;
    }

    private void startGame() {
        gameProcessor = new GameGUIProcessor(FieldGenerators.genBoardedField(FIELD_HEIGHT, FIELD_WIDTH), SMOOTH_PAINTER);

        gameProcessor.setOnPause(this::pauseGame);
        gameProcessor.setGameLogic(GameApplication::gameLogic);

        initGameScene(gameProcessor.getScene());
        fitGameArea(gameProcessor.getScene());

        primaryStage.setScene(gameScene);
        gameProcessor.play();
    }

    private void fitGameArea(SubScene gameArea) {
        double ratio;
        if (gameArea.getHeight() * WINDOW_WIDTH > WINDOW_HEIGHT * gameArea.getWidth())
            ratio = gameArea.getHeight() / WINDOW_HEIGHT;
        else
            ratio = gameArea.getWidth() / WINDOW_WIDTH;

        gameArea.setScaleX(ratio);
        gameArea.setScaleY(ratio);

        gameArea.setTranslateX(-gameArea.getWidth() / 2);
        gameArea.setTranslateY(-gameArea.getHeight() / 2);

        gameArea.setLayoutX(WINDOW_WIDTH / 2);
        gameArea.setLayoutY(WINDOW_HEIGHT / 2);
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

        setUpPrimaryStageSize();
    }

    private void setUpPrimaryStageSize() {
        Scene scene = primaryStage.getScene();
        primaryStage.setWidth(WINDOW_WIDTH
                + scene.getWindow().getWidth()
                - scene.getWidth());
        primaryStage.setHeight(WINDOW_HEIGHT
                + scene.getWindow().getHeight()
                - scene.getHeight());

//        primaryStage.setWidth(WINDOW_WIDTH
//                + mainMenuScene.getWindow().getWidth()
//                - mainMenuScene.getWidth());
//        primaryStage.setHeight(WINDOW_HEIGHT
//                + mainMenuScene.getWindow().getHeight()
//                - mainMenuScene.getHeight());
    }
}
