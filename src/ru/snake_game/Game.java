package ru.snake_game;

import ru.snake_game.Interfaces.IField;
import ru.snake_game.Interfaces.IFieldObject;
import ru.snake_game.Interfaces.IGame;

import java.util.HashSet;

public class Game implements IGame {
    private IField field;

    Game(IField field) throws Exception
    {
        this.field = field;
    }

    public void tick() throws IllegalStateException
    {
        HashSet<IFieldObject> objectsWhichActed = new HashSet<>();
        for (IFieldObject object : field) {
            if (object != null && !objectsWhichActed.contains(object)) {
                objectsWhichActed.add(object);
                object.act();
            }
        }
    }

    public IField getField() {
        return field;
    }
}