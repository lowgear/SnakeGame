package ru.snake_game.FieldObjects;

import ru.snake_game.Interfaces.IField;
import ru.snake_game.Interfaces.ISnakeHead;
import ru.snake_game.util.Location;

public abstract class AbstractSolidFieldObject extends AbstractFieldObject {
    public AbstractSolidFieldObject(Location location, IField field) {
        super(location, field);
    }

    @Override
    public void snakeInteract(ISnakeHead snake) {
        snake.kill();
    }
}
