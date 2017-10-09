package ru.snake_game;

import ru.snake_game.FieldObjects.Wall;
import ru.snake_game.Interfaces.IField;
import ru.snake_game.FieldObjects.AbstractFieldObject;
import ru.snake_game.util.Location;
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
        /*for (int i = 0; i < width; i++)
        {
            field.set(i, new Wall(new Location(i, 0)));
        }
        for (int i = width; i < field.size(); i*=2)
        {
            field.set(i, new Wall(new Location(0, i % width)));
        }
        for (int i = width-1; i < field.size(); i+=width)
        {
            field.set(i, new Wall(new Location(width - 1, i % width)));
        }
        for (int i = field.size() - width + 1; i < width - 1 ; i++)
        {
            field.set(i, new Wall(new Location(i, 0)));
        }*/
        //...
    }

    public AbstractFieldObject GetTypeFieldObject(Location location) {
        int index = location.getY() * width + location.getX();
        return field.get(index);
    }
}