package ru.snake_game.FieldObjects;

import ru.snake_game.Interfaces.IWalkableFieldObject;
import ru.snake_game.util.Location;

public class Apple extends AbstractFieldObject implements IWalkableFieldObject {
    public Apple(Location location) {
        super(location);
    }
}
