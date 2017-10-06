package ru.snake_game.util;

public class Location {
    private int x, y;

    public Location(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Location Moved(Vector move)
    {
        return new Location(x + move.getX(), y + move.getY());
    }
}
