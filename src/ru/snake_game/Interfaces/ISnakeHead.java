package ru.snake_game.Interfaces;

import ru.snake_game.FieldObjects.SnakeBody;
import ru.snake_game.util.Vector;

public interface ISnakeHead {
    Vector getDirection();

    void kill();

    SnakeBody grow();

    void move();

    int length();
}
