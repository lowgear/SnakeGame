package ru.snake_game.FieldObjects;

import ru.snake_game.Interfaces.IField;
import ru.snake_game.Interfaces.IFieldObject;
import ru.snake_game.util.Location;

abstract public class AbstractFieldObject implements IFieldObject {
    private Location location;
    private IField field;

    public Location getLocation() {
        return location;
    }

    protected AbstractFieldObject(Location location, IField field) {
        if (field == null)
            throw new IllegalArgumentException("field is null.");
        if (location == null)
            throw new IllegalArgumentException("location is null.");
        this.field = field;
        this.setLocation(location);
    }

    protected void setLocation(Location location){
        if (location == null)
            throw new IllegalArgumentException("Location can'n be null.");
        this.location = location;
    }

    @Override
    public IField getField() {
        return field;
    }
}
