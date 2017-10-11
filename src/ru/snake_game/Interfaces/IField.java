package ru.snake_game.Interfaces;

import ru.snake_game.FieldObjects.AbstractFieldObject;
import ru.snake_game.util.Location;

public interface IField {
    AbstractFieldObject fieldObjectAt(Location location);
}