package ru.snake_game.FieldObjects;

import ru.snake_game.Interfaces.IFieldObject;
import ru.snake_game.util.Location;

abstract public class AbstractFieldObject implements IFieldObject {
    private Location location;

    public Location getLocation() {
        return location;
    }

    protected void setLocation(Location location){
        if (location == null)
            throw new IllegalArgumentException("Location can'n be null.");
        this.location = location;
    }

    public AbstractFieldObject(Location location)
    {
        if (location == null)
            throw new IllegalArgumentException("location is null.");
        this.setLocation(location);
    }
}
