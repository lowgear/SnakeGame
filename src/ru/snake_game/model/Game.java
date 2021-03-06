package ru.snake_game.model;

import ru.snake_game.model.Interfaces.IField;
import ru.snake_game.model.Interfaces.IFieldObject;
import ru.snake_game.model.Interfaces.IGame;

import java.util.HashSet;

public class Game implements IGame {
    private IField field;

    public Game(IField field)
    {
        this.field = field;
    }

    @Override
    public void tick()
    {
        HashSet<IFieldObject> objectsWhichActed = new HashSet<>();
        for (IFieldObject object : field) {
            if (object != null && !objectsWhichActed.contains(object)) {
                objectsWhichActed.add(object);
                object.act();
            }
        }
    }

    @Override
    public IField getField() {
        return field;
    }
}