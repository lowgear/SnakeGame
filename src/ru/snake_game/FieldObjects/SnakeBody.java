package ru.snake_game.FieldObjects;

import ru.snake_game.util.Location;

public class SnakeBody extends SnakePart {
    private SnakePart next;

    public SnakeBody(Location location, SnakePart prev, SnakePart next)
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
