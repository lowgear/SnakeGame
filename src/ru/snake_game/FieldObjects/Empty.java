package ru.snake_game.FieldObjects;

import ru.snake_game.Interfaces.IWalkableFieldObject;
import ru.snake_game.util.Location;

public class Empty extends AbstractFieldObject implements IWalkableFieldObject {
    public Empty(Location location) {
        super(location);
    }
}
