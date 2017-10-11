package ru.snake_game.FieldObjects;

import ru.snake_game.util.Location;

public abstract class SnakePart extends AbstractSolidFieldObject {
    protected SnakeBody prev;

    public SnakePart(Location location, SnakeBody prev) {
        super(location);
        this.prev = prev;
    }

    protected abstract void move();

    protected void MoveChild()
    {
        if (prev != null)
            prev.move();
    }
}
