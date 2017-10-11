package ru.snake_game;

import ru.snake_game.FieldObjects.*;
import ru.snake_game.Interfaces.IField;
import ru.snake_game.util.Location;
import ru.snake_game.util.Vector;

import java.util.ArrayList;

public class Field implements IField {
    public ArrayList<AbstractFieldObject> field;
    public int height;
    public int width;

    Field(int height, int width) throws Exception
    {
        if (height < 0 || width < 0)
            throw new Exception("Field can`t to be built, incorrect parameters");
        this.height = height;
        this.width = width;
        field =  new ArrayList<>(height*width);
    }

    public void CreateField()
    {
        for (int i = 0; i < field.size(); i+=width)
        {
            field.set(i, new Wall(this.GetLocation(i)));
        }
        for (int i = width-1; i < field.size(); i+=width)
        {
            field.set(i, new Wall(this.GetLocation(i)));
        }
        for (int i = 1; i < width - 1; i++)
        {
            field.set(i, new Wall(this.GetLocation(i)));
        }
        for (int i = field.size() - (width - 1); i < field.size() - 1 ; i++)
        {
            field.set(i, new Wall(this.GetLocation(i)));
        }
        field.set((width + 1), new SnakeHead(new Location(1,1), null, new Vector(1,0)));
    }

    public AbstractFieldObject FieldObjectAt(Location location)
    {
        int index = location.getY() * width + location.getX();
        return field.get(index);
    }

    public ArrayList<SnakePart> GetSnake()
    {
        ArrayList<SnakePart> snake = new ArrayList<>();
        for (int i = 0; i < field.size(); i++)
        {
            if (field.get(i) instanceof SnakeHead)
                snake.add((SnakeHead)field.get(i));
            if (field.get(i) instanceof SnakeBody)
                snake.add((SnakeBody)field.get(i));
        }
        return snake;
    }

    public SnakeHead GetSnakeHead()
    {
        SnakeHead snakeHead = null;
        for (int i = 0; i < field.size(); i++)
            if (field.get(i) instanceof SnakeHead)
                snakeHead = (SnakeHead)field.get(i);
        return snakeHead;
    }

    public Location GetLocation(int indexInField)
    {
        int x = indexInField % width;
        int y = indexInField / width;
        return new Location(x, y);
    }

    public int GetIndexInField(Location location)
    {
        return location.getY() * width + location.getX();
    }

    public void Refresh()
    {
        ArrayList<SnakePart> snake = this.GetSnake();
        for (int i = 0; i < snake.size(); i++)
        {
            SnakePart snakePart = snake.get(i);
            int newIndex = this.GetIndexInField(snakePart.getLocation());
            int oldIndex = field.indexOf(snakePart);
            if(newIndex != oldIndex)
            {
                field.add(newIndex, snakePart);
                field.add(oldIndex, new Empty(this.GetLocation(oldIndex)));
            }
        }
    }
}