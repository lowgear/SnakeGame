package ru.snake_game.FieldObjects;

import ru.snake_game.Interfaces.IFieldObject;
import ru.snake_game.util.Location;

abstract public class AbstractFieldObject implements IFieldObject {
    protected Location location;

    public Location getLocation() {
        return location;
    }

    public AbstractFieldObject(Location location)
    {
        this.location = location;
    }
}
