package ru.snake_game.model.util;

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

    public Location moved(Vector move)
    {
        return new Location(x + move.getX(), y + move.getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Location)
        {
            Location loc = (Location)obj;
            return loc.x == x && loc.y == y;
        }
        return false;
    }
}
