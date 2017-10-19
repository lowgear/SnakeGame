package ru.snake_game.FieldObjects;

import ru.snake_game.Interfaces.IField;
import ru.snake_game.util.Location;

public class Wall extends AbstractSolidFieldObject {
    public Wall(Location location, IField field) {
        super(location);
    }
}
