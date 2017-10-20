package ru.snake_game.model.Interfaces;

import ru.snake_game.model.util.Location;

public interface IField extends Iterable<IFieldObject> {
    int getWidth();

    int getHeight();

    IFieldObject getObjectAt(Location location);

    IFieldObject getObjectAt(int x, int y);

    void setObjectAt(Location location, IFieldObject object);

    void setObjectAt(int x, int y, IFieldObject object);

    ISnakeHead getSnakeHead();
}