package ru.snake_game.Interfaces;

import ru.snake_game.util.Location;

public interface IFieldObject
{
    Location getLocation();

    void snakeInteract(ISnakeHead snake);

    void act();
}