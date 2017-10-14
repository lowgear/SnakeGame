package ru.snake_game.FieldObjects;

import ru.snake_game.Interfaces.IField;
import ru.snake_game.Interfaces.ISnakeHead;
import ru.snake_game.util.Location;

public class Apple extends AbstractFieldObject {
    private final int foodValue;

    public Apple(Location location, IField field, int foodValue) {
        super(location, field);
        if (foodValue < 1)
            throw new IllegalArgumentException("foodValue should be positive.");
        this.foodValue = foodValue;
    }

    @Override
    public void snakeInteract(ISnakeHead snake) {
        snake.eat(foodValue);
        getField().setObjectAt(getLocation(), null);
    }
}
