package ru.snake_game.FieldObjects;

import ru.snake_game.util.Location;

public class SnakeBody extends SnakePart {
    protected SnakePart next;

    public SnakeBody(Location location, SnakeBody prev, SnakePart next)
    {
        super(location, prev);
        this.next = next;
    }

    @Override
    protected void Move() {
        MoveChild();
        location = next.location;
    }
}
