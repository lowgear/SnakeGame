package ru.snake_game.Interfaces;

public interface IGame{
    void tick() throws  Exception;

    IField getField();
}
