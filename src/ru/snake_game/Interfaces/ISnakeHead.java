package ru.snake_game.Interfaces;

import ru.snake_game.util.Location;
import ru.snake_game.util.Vector;

public interface ISnakeHead {
    Vector GetDirection();

    void Eat(Location apple);

    void Move();
}
