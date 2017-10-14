package ru.snake_game;

import ru.snake_game.Interfaces.IField;
import ru.snake_game.Interfaces.IFieldObject;
import ru.snake_game.Interfaces.IGame;

public class Game implements IGame {
    private IField field;

    Game(IField field) throws Exception
    {
        this.field = field;
    }

    public void tick() throws IllegalStateException
    {
        for (IFieldObject object : field) {
            object.act();
        }
    }

    public IField getField() {
        return field;
    }
}