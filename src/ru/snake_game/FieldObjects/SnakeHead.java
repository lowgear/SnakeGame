package ru.snake_game.FieldObjects;

import ru.snake_game.Interfaces.ISnakeHead;
import ru.snake_game.util.Location;
import ru.snake_game.util.Vector;

public class SnakeHead extends SnakePart implements ISnakeHead {
    private Vector direction;

    private boolean ateRecently = false;

    public boolean isAlive = true;

    public SnakeHead(Location location, SnakeBody prev, Vector direction) {
        super(location, prev);

        if (direction.getY() * direction.getX() != 0 || Math.abs(direction.getY() + direction.getX()) != 1)
            throw new IllegalArgumentException("Direction module is not 1.");

        this.direction = direction;
    }

    public void Kill()
    {
        isAlive = false;
    }

    public SnakeBody Eat(Location apple)
    {
        SnakeBody t = new SnakeBody(location, prev, this);
        prev.next = t;
        prev = t;
        ateRecently = true;
        return t;
    }

    public void Move() {
        if (!ateRecently)
            MoveChild();
        else
            ateRecently = false;
        location = location.Moved(direction);
    }

    public Vector GetDirection() {
        return direction;
    }
}