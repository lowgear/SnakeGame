package ru.snake_game.FieldObjects;

import ru.snake_game.util.Location;

public class SnakeBody extends SnakePart {
    protected SnakePart next;

    public SnakeBody(Location location, SnakeBody prev, SnakePart next)
    {
        super(location, prev);
        if (next == null)
            throw new NullPointerException("Next SnakePart can't be null.");
        this.next = next;
    }

    @Override
    protected void move() {
        MoveChild();
        setLocation(next.getLocation());
    }
}
