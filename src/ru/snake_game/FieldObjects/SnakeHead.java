package ru.snake_game.FieldObjects;

import ru.snake_game.Interfaces.ISnakeHead;
import ru.snake_game.util.Location;
import ru.snake_game.util.Vector;

public class SnakeHead extends SnakePart implements ISnakeHead {
    private Vector direction;

    private boolean ateRecently = false;

    private boolean alive = true;

    public SnakeHead(Location location, SnakeBody prev, Vector direction) {
        super(location, prev);

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

    public SnakeBody grow() throws IllegalStateException
    {
        if (!isAlive())
            throw new IllegalStateException("Dead snake can't grow.");
        SnakeBody t = new SnakeBody(getLocation(), prev, this);
        if (prev != null)
            prev.next = t;
        prev = t;
        ateRecently = true;
        return t;
    }

    public void move() {
        if (!isAlive())
            return;
        if (!ateRecently)
            MoveChild();
        else
            ateRecently = false;
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
}