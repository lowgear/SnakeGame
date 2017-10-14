package ru.snake_game.Interfaces;

import ru.snake_game.util.Location;

public interface IField extends Iterable<IFieldObject> {
    int getWidth();

    int getHeight();

    IFieldObject getObjectAt(Location location);

    IFieldObject getObjectAt(int x, int y);

    void setObjectAt(Location location, IFieldObject object);

    void setObjectAt(int x, int y, IFieldObject object);

    ISnakeHead getSnakeHead();
}