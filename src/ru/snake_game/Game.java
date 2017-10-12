package ru.snake_game;

import ru.snake_game.Interfaces.IField;
import ru.snake_game.Interfaces.IFieldObject;
import ru.snake_game.Interfaces.IGame;
import ru.snake_game.Interfaces.ISnakeHead;
import ru.snake_game.util.Location;
import ru.snake_game.util.Vector;

public class Game implements IGame {
    private IField field;

    Game(IField field) throws Exception
    {
        this.field = field;
    }

    public void tick() throws IllegalStateException
    {
        ISnakeHead snakeHead = field.getSnakeHead();

        Vector direction = snakeHead.getDirection();
        Location objectLocation = snakeHead.getLocation().Moved(direction);
        IFieldObject directionObject = field.getObjectAt(objectLocation);
        if (directionObject != null)
            directionObject.snakeInteract(snakeHead);
        snakeHead.move();
    }

    public IField getField() {
        return field;
    }
}