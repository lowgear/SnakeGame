package ru.snake_game.view;

import javafx.beans.property.DoubleProperty;
import javafx.scene.SubScene;
import ru.snake_game.model.Interfaces.IField;

import java.util.function.Function;

public interface IGameGUIProcessor {
    SubScene getScene();

    void setOnPause(Runnable action);

    void play();

    void pause();

    void setGameLogic(Function<IField, Object> gameLogic);

    boolean isPlaying();

    DoubleProperty rateProperty();
}
