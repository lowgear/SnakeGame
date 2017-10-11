package ru.snake_game.FieldObjects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.snake_game.util.Location;
import ru.snake_game.util.Vector;

import java.lang.reflect.Array;

import static org.junit.Assert.*;

public class SnakeHeadTest {
    private SnakeHead snake;

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
        snake.Eat()
    }

    @Test
    public void move() throws Exception {

    }

    @Test
    public void getDirection() throws Exception {
    }

}