package ru.snake_game.FieldObjects;

import ru.snake_game.Interfaces.ISnakeHead;
import ru.snake_game.util.Location;

public class SnakeBody extends SnakePart {
    private final ISnakeHead head;
    protected SnakePart next;

    public SnakeBody(Location location, ISnakeHead head, SnakeBody prev, SnakePart next)
    {
        super(location, prev, next.getField());
        this.next = next;
        this.head = head;
    }

    @Override
    protected void move() {
        moveChild();
        setLocation(next.getLocation());
        getField().setObjectAt(getLocation(), this);
    }

    @Override
    protected ISnakeHead getHead() {
        return head;
    }
}
