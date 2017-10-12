package ru.snake_game;

import ru.snake_game.FieldObjects.SnakeBody;
import ru.snake_game.FieldObjects.SnakeHead;
import ru.snake_game.FieldObjects.SnakePart;
import ru.snake_game.FieldObjects.Wall;
import ru.snake_game.Interfaces.IField;
import ru.snake_game.Interfaces.IFieldObject;
import ru.snake_game.Interfaces.ISnakeHead;
import ru.snake_game.util.Location;
import ru.snake_game.util.Vector;

import java.util.ArrayList;

public class Field implements IField {
    private ArrayList<IFieldObject> field;
    private int height;
    private int width;

    Field(int height, int width) throws Exception {
        if (height < 1 || width < 1)
            throw new Exception("Field can`t to be built, incorrect parameters");
        this.height = height;
        this.width = width;
        field = new ArrayList<>(height * width);
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

    public void refresh() {
        ArrayList<SnakePart> snake = this.getSnake();
        for (SnakePart snakePart : snake) {
            int newIndex = this.getIndexInField(snakePart.getLocation());
            int oldIndex = field.indexOf(snakePart);
            if (newIndex != oldIndex) {
                field.add(newIndex, snakePart);
                field.add(oldIndex, null);
            }
        }
    }

    public void createField() {
        for (int i = 0; i < field.size(); i += width) {
            field.set(i, new Wall(this.getLocation(i)));
        }
        for (int i = width - 1; i < field.size(); i += width) {
            field.set(i, new Wall(this.getLocation(i)));
        }
        for (int i = 1; i < width - 1; i++) {
            field.set(i, new Wall(this.getLocation(i)));
        }
        for (int i = field.size() - (width - 1); i < field.size() - 1; i++) {
            field.set(i, new Wall(this.getLocation(i)));
        }
        field.set((width + 1), new SnakeHead(new Location(1, 1), null, new Vector(1, 0)));
    }
}