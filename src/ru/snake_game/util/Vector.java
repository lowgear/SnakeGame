package ru.snake_game.util;

public class Vector {
    private int x, y;
    public Vector(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector)
        {
            Vector vec = (Vector) obj;
            return vec.x == x && vec.y == y;
        }
        return false;
    }
}
