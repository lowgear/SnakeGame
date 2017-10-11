package ru.snake_game.FieldObjects;

import ru.snake_game.Interfaces.IFieldObject;
import ru.snake_game.Interfaces.ISnakeHead;
import ru.snake_game.util.Location;

import java.util.ArrayList;

public class Apple extends AbstractFieldObject {
    public Apple(Location location) {
        super(location);
    }

    @Override
    public Iterable<IFieldObject> snakeInteract(ISnakeHead snake) {
        ArrayList<IFieldObject> res = new ArrayList<IFieldObject>();
        res.add(snake.grow());
        return res;
    }
}
