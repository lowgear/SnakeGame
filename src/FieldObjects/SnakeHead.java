package FieldObjects;

import util.Vector;

public class SnakeHead extends SnakePart {
    private SnakeBody prev;
    private Vector direction;

    public SnakeHead(int x, int y, SnakeBody prev, Vector direction) {
        super(x, y);

        if (direction.getY() * direction.getX() != 0 || Math.abs(direction.getY() + direction.getX()) != 1)
            throw new IllegalArgumentException("Direction module is not 1.");

        this.prev = prev;
        this.direction = direction;
    }

    @Override
    public void Act()
    {
        prev.Move();
        Move();
    }

    @Override
    protected void Move() {
        location = location.Moved(direction);
    }
}
