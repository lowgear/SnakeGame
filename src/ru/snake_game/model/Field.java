package ru.snake_game.model;

import ru.snake_game.model.FieldObjects.SnakeHead;
import ru.snake_game.model.Interfaces.IField;
import ru.snake_game.model.Interfaces.IFieldObject;
import ru.snake_game.model.Interfaces.ISnakeHead;
import ru.snake_game.model.util.Location;

import java.util.ArrayList;
import java.util.Iterator;

public class Field implements IField, Iterable<IFieldObject> {
    private ArrayList<IFieldObject> field;
    private int height;
    private int width;

    public Field(int height, int width) {
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
    public void setObjectAt(int x, int y, IFieldObject object) {
        field.set(getIndexInField(x, y), object);
    }

    @Override
    public void setObjectAt(Location location, IFieldObject object) {
        setObjectAt(location.getX(), location.getY(), object);
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

    private int getIndexInField(int x, int y) {
        return y * width + x;
    }

    @Override
    public Iterator<IFieldObject> iterator() {
        return field.iterator();
    }
}