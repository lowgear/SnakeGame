package ru.snake_game.FieldObjects;

import ru.snake_game.Interfaces.ISnakeHead;
import ru.snake_game.util.Location;

public class SnakeBody extends SnakePart {
    protected SnakePart next;

    public SnakeBody(Location location, SnakeBody prev, SnakePart next)
    {
        super(location, prev, next.getField());
        this.next = next;
    }

    @Override
    protected void move() {
        moveChild();
        setLocation(next.getLocation());
    }

    @Override
    public void snakeInteract(ISnakeHead snakeHead) {
        if (prev != null)
            super.snakeInteract(snakeHead);
    }
}
