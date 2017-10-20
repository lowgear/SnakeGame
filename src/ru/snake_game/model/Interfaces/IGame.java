package ru.snake_game.model.Interfaces;

public interface IGame{
    void tick() throws  Exception;

    IField getField();
}
