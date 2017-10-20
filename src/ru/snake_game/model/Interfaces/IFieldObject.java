package ru.snake_game.model.Interfaces;

import ru.snake_game.model.util.Location;

public interface IFieldObject
{
    Location getLocation();

    void snakeInteract(ISnakeHead snake);

    void act();
}