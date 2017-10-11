package ru.snake_game;

import ru.snake_game.FieldObjects.AbstractFieldObject;
import ru.snake_game.FieldObjects.Apple;
import ru.snake_game.FieldObjects.SnakeHead;
import ru.snake_game.FieldObjects.SnakeBody;
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

    public void tick() throws IllegalStateException
    {
        SnakeHead snakeHead = field.GetSnakeHead();

        if (snakeHead == null)
            throw new IllegalStateException("Snake not found");

        Vector direction = snakeHead.getDirection();
        Location objectLocation = snakeHead.getLocation().Moved(direction);
        AbstractFieldObject directionObject = field.fieldObjectAt(objectLocation);
        if (directionObject instanceof  IWalkableFieldObject)
        {
            if (directionObject instanceof Apple)
            {
                SnakeBody newPart = snakeHead.grow();
                field.field.add(field.GetIndexInField(objectLocation), snakeHead);
                field.field.add(field.GetIndexInField(snakeHead.getLocation()), newPart);
            }
            snakeHead.move();
            field.Refresh();
        }
        else
        {
            snakeHead.kill();
        }
    }
}