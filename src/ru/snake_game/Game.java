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

    public void Tick() throws Exception
    {
        SnakeHead snakeHead = field.GetSnakeHead();

        if (snakeHead == null)
            throw new Exception("Snake not found");

        Vector direction = snakeHead.GetDirection();
        Location objectLocation = snakeHead.getLocation().Moved(direction);
        AbstractFieldObject directionObject = field.FieldObjectAt(objectLocation);
        if (directionObject instanceof  IWalkableFieldObject)
        {
            if (directionObject instanceof Apple)
            {
                SnakeBody newPart = snakeHead.Eat((Apple)directionObject);
                field.field.add(field.GetIndexInField(objectLocation), snakeHead);
                field.field.add(field.GetIndexInField(snakeHead.getLocation()), newPart);
            }
            snakeHead.Move();
            field.Refresh();
        }
        else
        {
            snakeHead.Kill();
        }
    }
}