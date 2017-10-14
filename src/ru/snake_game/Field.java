package ru.snake_game;

import ru.snake_game.FieldObjects.SnakeBody;
import ru.snake_game.FieldObjects.SnakeHead;
import ru.snake_game.FieldObjects.SnakePart;
import ru.snake_game.Interfaces.IField;
import ru.snake_game.Interfaces.IFieldObject;
import ru.snake_game.Interfaces.ISnakeHead;
import ru.snake_game.util.Location;

import java.util.ArrayList;
import java.util.Iterator;

public class Field implements IField, Iterable<IFieldObject> {
    private ArrayList<IFieldObject> field;
    private int height;
    private int width;

    Field(int height, int width) throws Exception {
        if (height < 1 || width < 1)
            throw new Exception("Field can`t to be built, incorrect parameters");
        this.height = height;
        this.width = width;
        int n = width * height;
        field = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            field.add(null);
    }

    public IFieldObject getObjectAt(int x, int y) {
        int index = y * width + x;
        return field.get(index);
    }

    public IFieldObject getObjectAt(Location location) {
        return getObjectAt(location.getX(), location.getY());
    }

    public void setObjectAt(int x, int y, IFieldObject object) {
        field.set(getIndexInField(x, y), object);
    }

    public void setObjectAt(Location location, IFieldObject object) {
        setObjectAt(location.getX(), location.getY(), object);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ISnakeHead getSnakeHead() {
        SnakeHead snakeHead = null;
        for (IFieldObject fieldObject : field)
            if (fieldObject instanceof SnakeHead)
                snakeHead = (SnakeHead) fieldObject;
        return snakeHead;
    }

    public ArrayList<SnakePart> getSnake() {
        ArrayList<SnakePart> snake = new ArrayList<>();
        for (IFieldObject fieldObject : field) {
            if (fieldObject instanceof SnakeHead)
                snake.add((SnakeHead) fieldObject);
            if (fieldObject instanceof SnakeBody)
                snake.add((SnakeBody) fieldObject);
        }
        return snake;
    }

    private Location getLocation(int indexInField) {
        int x = indexInField % width;
        int y = indexInField / width;
        return new Location(x, y);
    }

    private int getIndexInField(Location location) {
        return getIndexInField(location.getX(), location.getY());
    }

    private int getIndexInField(int x, int y) {
        return y * width + x;
    }

    @Override
    public Iterator<IFieldObject> iterator() {
        return field.iterator();
    }
}