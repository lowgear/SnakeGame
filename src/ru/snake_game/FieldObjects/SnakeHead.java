package ru.snake_game.FieldObjects;

import ru.snake_game.Interfaces.ISnakeHead;
import ru.snake_game.util.Location;
import ru.snake_game.util.Vector;

public class SnakeHead extends SnakePart implements ISnakeHead {
    private Vector direction;

    private boolean ateRecently = false;

    private boolean isAlive = true;
    private final static Vector noDirection = new Vector(0, 0);

    public SnakeHead(Location location, SnakeBody prev, Vector direction) {
        super(location, prev);

        SetDirection(direction);
    }

    public void Kill()
    {
        isAlive = false;
    }

    public boolean IsAlive()
    {
        return isAlive;
    }

    public SnakeBody Eat(Apple apple) throws NullPointerException, IllegalStateException
    {
        if (apple == null)
            throw new NullPointerException("Apple cant be null.");
        if (!IsAlive())
            throw new IllegalStateException("Dead snake can't eat.");
        SnakeBody t = new SnakeBody(location, prev, this);
        if (prev != null)
            prev.next = t;
        prev = t;
        ateRecently = true;
        return t;
    }

    public void Move() {
        if (!IsAlive())
            return;
        if (!ateRecently)
            MoveChild();
        else
            ateRecently = false;
        location = location.Moved(direction);
    }

    public Vector GetDirection() {
        if (!IsAlive())
            return noDirection;
        return direction;
    }

    public void SetDirection(Vector direction)
    {
        if (direction.getY() * direction.getX() != 0 || Math.abs(direction.getY() + direction.getX()) != 1)
            throw new IllegalArgumentException("Direction module is not 1.");

        this.direction = direction;
    }

    public int Length() {
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