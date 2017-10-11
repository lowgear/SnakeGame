package ru.snake_game.FieldObjects;

import ru.snake_game.Interfaces.IFieldObject;
import ru.snake_game.Interfaces.ISnakeHead;
import ru.snake_game.util.Location;

import java.util.Collections;

public abstract class AbstractSolidFieldObject extends AbstractFieldObject {
    public AbstractSolidFieldObject(Location location) {
        super(location);
    }

    @Override
    public Iterable<IFieldObject> snakeInteract(ISnakeHead snake) {
        snake.kill();
        return Collections.emptyList();
    }
}
