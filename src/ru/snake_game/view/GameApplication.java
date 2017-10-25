package ru.snake_game.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.snake_game.controller.IGameController;

public class GameApplication extends Application {
    private Stage primaryStage;

    private Scene mainMenuScene;
    private Scene gameScene;
    private Node pauseMenuScene;

    @Override
    public void init() {
        initMainMenuScene();
        initGameScene();
        initPauseMenuScene();
    }

    private void initPauseMenuScene() {
        Group root = new Group();

        scene = new Scene(mainMenuScene);
        IGameController controller; //TODO: init


        Button[] buttons = new Button[]{
                new Button("Play"),
                new Button("Exit")
        };
        buttons[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO start game
            }
        });
        buttons[1].setOnAction(event -> primaryStage.close());

        for (int i = 0; i < 2; i++) {
            GridPane.setConstraints(buttons[i], 0, i);
        }
        mainMenuScene.getChildren().addAll(buttons);

        mainMenuScene = new Scene(root);
    }

    private void initGameScene() {
        //todo
    }

    private void initMainMenuScene() {
        //todo
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }

    private double desiredHeight() {
        //TODO
        return 480;
    }

    private double desiredWidth() {
        //TODO
        return 640;
    }
}
