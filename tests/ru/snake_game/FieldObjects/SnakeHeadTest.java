package ru.snake_game.FieldObjects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.snake_game.util.Location;
import ru.snake_game.util.Vector;

import java.lang.reflect.Array;

import static org.junit.Assert.*;

public class SnakeHeadTest {
    private SnakeHead snake;
    Apple apple = new Apple(new Location(-1, -1));

    @Before
    public void setUp() throws Exception {
        snake = new SnakeHead(new Location(0, 0), null, new Vector(0, 1));
    }

    @After
    public void tearDown() throws Exception {
        snake = null;
    }

    @Test
    public void kill() throws Exception {
        assertTrue(snake.IsAlive());
        snake.Kill();
        assertFalse(snake.IsAlive());
        snake.Kill();
        assertFalse(snake.IsAlive());
    }

    @Test
    public void isAlive() throws Exception {
        assertTrue(snake.IsAlive());
        assertTrue(snake.IsAlive());
        snake.Kill();
        assertFalse(snake.IsAlive());
        assertFalse(snake.IsAlive());
    }

    @Test
    public void eat() throws Exception {
        assertEquals(1, snake.Length());
        assertEquals(1, snake.Length());
        snake.Eat(new Apple(new Location(0, 1)));
        assertEquals(2, snake.Length());
        assertEquals(2, snake.Length());
        snake.Eat(new Apple(new Location(0, 1)));
        assertEquals(3, snake.Length());
        assertEquals(3, snake.Length());
        snake.Eat(new Apple(new Location(0, 1)));
        assertEquals(4, snake.Length());
        assertEquals(4, snake.Length());
        snake.Eat(new Apple(new Location(0, 1)));
        assertEquals(5, snake.Length());
        assertEquals(5, snake.Length());
    }

    @Test
    public void move() throws Exception {
        assertEquals(new Location(0, 0), snake.location);
        snake.Eat(apple);
        snake.Move();
        assertEquals(new Location(0, 1), snake.location);
        assertEquals(new Location(0, 0), snake.prev.location);
        snake.SetDirection(new Vector(1, 0));
        snake.Eat(apple);
        snake.Move();
        assertEquals(new Location(1, 1), snake.location);
        assertEquals(new Location(0, 1), snake.prev.location);
        assertEquals(new Location(0, 0), snake.prev.prev.location);
    }

    @Test
    public void getDirection() throws Exception {
        assertEquals(new Vector(0, 1), snake.GetDirection());
        snake.SetDirection(new Vector(0, -1));
        assertEquals(new Vector(0, -1), snake.GetDirection());
    }

    @Test
    public void length() throws Exception
    {
        assertEquals(1, snake.Length());
        snake.Eat(apple);
        assertEquals(2, snake.Length());
        snake.Eat(apple);
        assertEquals(3, snake.Length());
        snake.Eat(apple);
        assertEquals(4, snake.Length());
    }
}