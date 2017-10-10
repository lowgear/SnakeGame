package ru.snake_game;

import ru.snake_game.FieldObjects.AbstractFieldObject;
import ru.snake_game.FieldObjects.Apple;
import ru.snake_game.FieldObjects.SnakeHead;
import ru.snake_game.Interfaces.IGame;
import ru.snake_game.Interfaces.IWalkableFieldObject;
import ru.snake_game.util.Location;
import ru.snake_game.util.Vector;

public class Game implements IGame {
    Field field;

    Game(int height, int width) throws Exception
    {
        field = new Field(height, width);
        field.CreateField();
    }

    public void Tick() throws Exception
    {
        SnakeHead snakeHead = null;
        for (int i = 0; i < field.field.size(); i++)
        {
            AbstractFieldObject object = field.field.get(i);
            if (object instanceof SnakeHead)
                snakeHead = (SnakeHead)object;
        }

        if (snakeHead == null)
            throw new Exception("Snake not found");

        Vector direction = snakeHead.GetDirection();
        AbstractFieldObject directionObject = field.GetTypeFieldObject(new Location(direction.getX(), direction.getY()));
        if (directionObject instanceof  IWalkableFieldObject)
        {
            snakeHead.Move();
            if (directionObject instanceof Apple)
                snakeHead.Eat(new Location(direction.getX(), direction.getY()));
        }
        else
        {
            /*I think we should inform that snake can`t go*/
        }
    }
}