package ru.snake_game.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.snake_game.controller.IGameController;

public class GameApplication extends Application {
    private Stage primaryStage;

    private Scene mainMenuScene;
    private Scene gameScene;
    private Scene pauseMenuScene;

    @Override
    public void init() {
        initMainMenuScene();
        initGameScene();
        initPauseMenuScene();
    }

    private void initPauseMenuScene() {
        Button[] buttons = new Button[]{
                new Button("Resume"),
                new Button("Restart"),
                new Button("Quit to Main Menu")
        };

        buttons[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(gameScene);
            }
        });

        buttons[1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                initGameScene();
                primaryStage.setScene(gameScene);
            }
        });

        buttons[2].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(mainMenuScene);
            }
        });

        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setVgap(20);

        Text scenetitle = new Text();
        scenetitle.setText("PAUSE");
        scenetitle.setFont(Font.font("verdana", FontWeight.BOLD, 50));
        root.add(scenetitle, 0, 0);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setMinSize(180, 40);
            GridPane.setConstraints(buttons[i], 0, i + 1);
        }
        root.getChildren().addAll(buttons);

        pauseMenuScene = new Scene(root, desiredWidth(), desiredHeight());
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
