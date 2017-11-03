package ru.snake_game.view.util;

@FunctionalInterface
public interface IEmitter<T> {
    T emit();
}
