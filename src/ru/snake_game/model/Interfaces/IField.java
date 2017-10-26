package ru.snake_game.model.Interfaces;

import ru.snake_game.model.util.Location;

public interface IField extends Iterable<IFieldObject> {
    int getWidth();

    int getHeight();

    IFieldObject getObjectAt(Location location);

    IFieldObject getObjectAt(int x, int y);

    void addObject(IFieldObject object);

    ISnakeHead getSnakeHead();

    void eraseAt(Location location);
}