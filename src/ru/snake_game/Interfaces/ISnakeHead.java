package ru.snake_game.Interfaces;

import ru.snake_game.util.Vector;

public interface ISnakeHead extends IFieldObject {
    Vector getDirection();

    void setDirection(Vector direction);

    void kill();

    void grow();

    void move();

    int length();

    boolean isAlive();
}
