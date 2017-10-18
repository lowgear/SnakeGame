package ru.snake_game.FieldObjects;

import ru.snake_game.Interfaces.IField;
import ru.snake_game.Interfaces.IFieldObject;
import ru.snake_game.Interfaces.ISnakeHead;
import ru.snake_game.util.Location;
import ru.snake_game.util.Vector;

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
            field.setObjectAt(partLocation, tail.prev);
            tail = tail.prev;
        }
    }

    public void kill() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean willGrow() {
        return lengthToGrow > 0;
    }

    public void eat(int growValue) throws IllegalStateException {
        if (!isAlive())
            throw new IllegalStateException("Dead snake can't eat.");
        if (growValue < 1)
            throw new IllegalArgumentException("growValue should be positive.");

        if (lengthToGrow == 0)
            justAte = true;
        lengthToGrow += growValue;
    }

    protected void move() {
        if (!isAlive())
            return;
        if (lengthToGrow > 0 && !justAte) {
            SnakePart tail = getTail();

            Location t = tail.getLocation();
            moveChild();
            tail.prev = new SnakeBody(t, this, null, tail);
            lengthToGrow--;

            getField().setObjectAt(t, tail.prev);
        } else
            moveChild();

        setLocation(getLocation().moved(direction));
        getField().setObjectAt(getLocation(), this);
        justAte = false;
    }

    @Override
    protected ISnakeHead getHead() {
        return this;
    }

    public Vector getDirection() {
        if (!isAlive())
            return Vector.ZERO;
        return direction;
    }

    public void setDirection(Vector direction) {
        if (direction.getY() * direction.getX() != 0 || Math.abs(direction.getY() + direction.getX()) != 1)
            throw new IllegalArgumentException("Direction module is not 1.");

        if (prev == null || prev.getLocation() != getLocation().moved(direction))
            this.direction = direction;
    }

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
        IFieldObject directionObject = getField().getObjectAt(getLocation().moved(direction));
        if (directionObject != null)
            directionObject.snakeInteract(this);
        move();
    }
}