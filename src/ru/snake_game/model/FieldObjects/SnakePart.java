package ru.snake_game.model.FieldObjects;

import com.sun.istack.internal.NotNull;
import ru.snake_game.model.Interfaces.IField;
import ru.snake_game.model.Interfaces.ISnakeHead;
import ru.snake_game.model.util.Location;

public abstract class SnakePart extends AbstractSolidFieldObject {
    protected SnakeBody prev;
    protected IField field;

    protected SnakePart(@NotNull Location location, @NotNull SnakeBody prev, @NotNull IField field) {
        super(location);

        if (field == null)
            throw new IllegalArgumentException("field can't be null.");

        this.prev = prev;
        this.field = field;
    }

    protected abstract void move();

    protected void moveChild()
    {
        if (prev != null)
            prev.move();
        else
            field.eraseAt(getLocation());
    }

    @Override
    public void snakeInteract(ISnakeHead snakeHead) {
        if (prev != null || getHead().willGrow())
            snakeHead.kill();
    }


    public abstract ISnakeHead getHead();
}
