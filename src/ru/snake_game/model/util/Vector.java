package ru.snake_game.model.util;

@SuppressWarnings("InstanceVariableNamingConvention")
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

    public final static Vector ZERO = new Vector(0, 0);
    public final static Vector UP = new Vector(0, -1);
    public final static Vector DOWN = new Vector(0, 1);
    public final static Vector LEFT = new Vector(-1, 0);
    public final static Vector RIGHT = new Vector(1, 0);
}
