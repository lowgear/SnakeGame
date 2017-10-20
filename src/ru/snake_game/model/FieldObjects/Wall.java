package ru.snake_game.model.FieldObjects;

import ru.snake_game.model.Interfaces.IField;
import ru.snake_game.model.util.Location;

public class Wall extends AbstractSolidFieldObject {
    public Wall(Location location, IField field) {
        super(location);
    }
}
