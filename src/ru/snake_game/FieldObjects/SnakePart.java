package ru.snake_game.FieldObjects;

import ru.snake_game.util.Location;

public abstract class SnakePart extends AbstractFieldObject {
    private SnakePart prev;

    public SnakePart(Location location, SnakePart prev) {
        super(location);
        this.prev = prev;
    }

    protected abstract void Move();

    protected void MoveChild()
    {
        if (prev != null)
            prev.Move();
    }
}
