package ru.snake_game.model.FieldObjects;

import ru.snake_game.model.Interfaces.IField;
import ru.snake_game.model.Interfaces.IFieldObject;
import ru.snake_game.model.Interfaces.ISnakeHead;
import ru.snake_game.model.util.Location;
import ru.snake_game.model.util.Vector;

public class SnakeHead extends SnakePart implements ISnakeHead {
    private Vector direction;

    private int lengthToGrow = 0;

    private boolean alive = true;
    private boolean justAte;

    public SnakeHead(Location location, SnakeBody prev, Vector direction, IField field) {
        super(location, prev, field);

        setDirection(direction);
    }

    public SnakeHead(Location location, Vector direction, IField field, Iterable<Location> bodyLocation) {
        super(location, null, field);
        setDirection(direction);

        SnakePart tail = this;
        for (Location partLocation : bodyLocation) {
            tail.prev = new SnakeBody(partLocation, this, null, tail);
            field.addObject(tail.prev);
            tail = tail.prev;
        }
    }

    @Override
    public void kill() {
        alive = false;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public boolean willGrow() {
        return lengthToGrow > 0;
    }

    @Override
    public void eat(int growValue) throws IllegalStateException {
        if (!isAlive())
            throw new IllegalStateException("Dead snake can't eat.");
        if (growValue < 1)
            throw new IllegalArgumentException("growValue should be positive.");

        if (lengthToGrow == 0)
            justAte = true;
        lengthToGrow += growValue;
    }

    @Override
    protected void move() {
        if (!isAlive())
            return;
        if (lengthToGrow > 0 && !justAte) {
            SnakePart tail = getTail();

            Location t = tail.getLocation();
            moveChild();
            tail.prev = new SnakeBody(t, this, null, tail);
            lengthToGrow--;

            field.addObject(tail.prev);
        } else
            moveChild();

        setLocation(getLocation().moved(direction));
        field.addObject(this);
        justAte = false;
    }

    @Override
    public ISnakeHead getHead() {
        return this;
    }

    @Override
    public Vector getDirection() {
        if (!isAlive())
            return Vector.ZERO;
        return direction;
    }

    @Override
    public void setDirection(Vector direction) {
        if (direction.getY() * direction.getX() != 0 || Math.abs(direction.getY() + direction.getX()) != 1)
            throw new IllegalArgumentException("Direction module is not 1.");

        if (prev == null || !prev.getLocation().equals(getLocation().moved(direction)))
            this.direction = direction;
    }

    @Override
    public int length() {
        int result = 1;
        SnakePart cur = this.prev;
        while (cur != null) {
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

    @Override
    public void act() {
        if (!isAlive())
            return;
        IFieldObject directionObject = field.getObjectAt(getLocation().moved(direction));
        if (directionObject != null)
            directionObject.snakeInteract(this);
        move();
    }
}