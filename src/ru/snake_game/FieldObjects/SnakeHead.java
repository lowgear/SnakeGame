package ru.snake_game.FieldObjects;

import ru.snake_game.Interfaces.IField;
import ru.snake_game.Interfaces.ISnakeHead;
import ru.snake_game.util.Location;
import ru.snake_game.util.Vector;

public class SnakeHead extends SnakePart implements ISnakeHead {
    private Vector direction;

    private boolean ateRecently = false;

    private boolean alive = true;

    public SnakeHead(Location location, SnakeBody prev, Vector direction, IField field) {
        super(location, prev, field);

        setDirection(direction);
    }

    public void kill()
    {
        alive = false;
    }

    public boolean isAlive()
    {
        return alive;
    }

    public void grow() throws IllegalStateException
    {
        if (!isAlive())
            throw new IllegalStateException("Dead snake can't grow.");

        ateRecently = true;
    }

    public void move() {
        if (!isAlive())
            return;
        if (ateRecently) {
            SnakePart tail = getTail();
            //remember tail's location before movement
            SnakeBody t = new SnakeBody(tail.getLocation(), null, tail);
            ateRecently = false;
            moveChild();
            tail.prev = t;
            //place new segment where the tail was
            getField().setObjectAt(t.getLocation(), t);
        } else
            moveChild();

        setLocation(getLocation().Moved(direction));
    }

    public Vector getDirection() {
        if (!isAlive())
            return Vector.ZERO;
        return direction;
    }

    public void setDirection(Vector direction)
    {
        if (direction.getY() * direction.getX() != 0 || Math.abs(direction.getY() + direction.getX()) != 1)
            throw new IllegalArgumentException("Direction module is not 1.");

        this.direction = direction;
    }

    public int length() {
        int result = 1;
        SnakePart cur = this.prev;
        while (cur != null)
        {
            cur = cur.prev;
            result++;
        }
        return result;
    }

    private SnakePart getTail() {
        SnakePart cur = this;
        while (cur.prev != null)
            cur = cur.prev;
        return cur;
    }
}