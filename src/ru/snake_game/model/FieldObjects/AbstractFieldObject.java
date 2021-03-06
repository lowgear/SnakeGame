package ru.snake_game.model.FieldObjects;

import ru.snake_game.model.Interfaces.IFieldObject;
import ru.snake_game.model.util.Location;

abstract public class AbstractFieldObject implements IFieldObject {
    private Location location;

    protected AbstractFieldObject(Location location) {
        if (location == null)
            throw new IllegalArgumentException("location is null.");
        this.setLocation(location);
    }

    @Override
    public Location getLocation() {
        return location;
    }

    protected void setLocation(Location location) {
        if (location == null)
            throw new IllegalArgumentException("Location can'n be null.");
        this.location = location;
    }

    @Override
    public void act() {
    }
}
