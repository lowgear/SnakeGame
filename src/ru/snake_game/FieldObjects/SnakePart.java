package ru.snake_game.FieldObjects;

import ru.snake_game.Interfaces.IField;
import ru.snake_game.Interfaces.ISnakeHead;
import ru.snake_game.util.Location;

public abstract class SnakePart extends AbstractSolidFieldObject {
    protected SnakeBody prev;

    protected SnakePart(Location location, SnakeBody prev, IField field) {
        super(location, field);
        this.prev = prev;
    }

    protected abstract void move();

    protected void moveChild()
    {
        if (prev != null)
            prev.move();
        else
            this.getField().setObjectAt(this.getLocation(), null);
    }

    @Override
    public void snakeInteract(ISnakeHead snakeHead) {
        if (prev != null || getHead().willGrow())
            snakeHead.kill();
    }

    protected abstract ISnakeHead getHead();
}
