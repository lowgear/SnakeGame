package ru.snake_game.model;

import org.jetbrains.annotations.NotNull;
import ru.snake_game.model.FieldObjects.SnakeHead;
import ru.snake_game.model.Interfaces.IField;
import ru.snake_game.model.Interfaces.IFieldObject;
import ru.snake_game.model.Interfaces.ISnakeHead;
import ru.snake_game.model.util.Location;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Field implements IField, Iterable<IFieldObject> {
    private ArrayList<IFieldObject> field;
    private int height;
    private int width;

    public Field(int height, int width) {
        if (height < 1 || width < 1)
            throw new IllegalArgumentException("Field can`t to be built, incorrect parameters");
        this.height = height;
        this.width = width;
        int n = width * height;
        field = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            field.add(null);
    }

    @Override
    public IFieldObject getObjectAt(int x, int y) {
        int index = getIndexInField(x, y);
        return field.get(index);
    }

    @Override
    public IFieldObject getObjectAt(Location location) {
        return getObjectAt(location.getX(), location.getY());
    }

    @Override
    public void addObject(IFieldObject object) {
        Location loc = object.getLocation();
        field.set(getIndexInField(loc.getX(), loc.getY()), object);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public ISnakeHead getSnakeHead() {
        SnakeHead snakeHead = null;
        for (IFieldObject fieldObject : field)
            if (fieldObject instanceof SnakeHead)
                snakeHead = (SnakeHead) fieldObject;
        return snakeHead;
    }

    @Override
    public void eraseAt(Location location) {
        field.set(getIndexInField(location.getX(), location.getY()), null);
    }

    private int getIndexInField(int x, int y) {
        return y * width + x;
    }

    @NotNull
    @Override
    public Iterator<IFieldObject> iterator() {
        return field.stream().filter(Objects::nonNull).iterator();
    }
}