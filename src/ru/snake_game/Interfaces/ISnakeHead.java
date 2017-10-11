package ru.snake_game.Interfaces;

import ru.snake_game.FieldObjects.Apple;
import ru.snake_game.FieldObjects.SnakeBody;
import ru.snake_game.util.Vector;

public interface ISnakeHead {
    Vector GetDirection();

    void Kill();

    SnakeBody Eat(Apple apple);

    void Move();
}
