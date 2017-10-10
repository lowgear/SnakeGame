package ru.snake_game;

import ru.snake_game.FieldObjects.SnakeHead;
import ru.snake_game.FieldObjects.Wall;
import ru.snake_game.Interfaces.IField;
import ru.snake_game.FieldObjects.AbstractFieldObject;
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
            field.set(i, new Wall(new Location(0, i % height)));
        }
        for (int i = width-1; i < field.size(); i+=width)
        {
            field.set(i, new Wall(new Location(width - 1, (i+1) % height)));
        }
        for (int i = 1; i < width - 1; i++)
        {
            field.set(i, new Wall(new Location(i, 0)));
        }
        for (int i = field.size() - (width - 1); i < field.size() - 1 ; i++)
        {
            field.set(i, new Wall(new Location(i % width, height - 1)));
        }
        field.set((width + 1), new SnakeHead(new Location(1,1), null, new Vector(2,1)));
    }

    public AbstractFieldObject GetTypeFieldObject(Location location) {
        int index = location.getY() * width + location.getX();
        return field.get(index);
    }
}