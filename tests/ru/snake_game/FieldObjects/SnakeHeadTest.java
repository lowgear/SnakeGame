package ru.snake_game.FieldObjects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.snake_game.util.Location;
import ru.snake_game.util.Vector;

import static org.junit.Assert.*;

public class SnakeHeadTest {
    private SnakeHead snake;
    //private Apple apple = new Apple(new Location(-1, -1));

    @Before
    public void setUp() throws Exception {
        //  snake = new SnakeHead(new Location(0, 0), null, new Vector(0, 1));
    }

    @After
    public void tearDown() throws Exception {
        snake = null;
    }

    @Test
    public void kill() throws Exception {
        assertTrue(snake.isAlive());
        snake.kill();
        assertFalse(snake.isAlive());
        snake.kill();
        assertFalse(snake.isAlive());
    }

    @Test
    public void isAlive() throws Exception {
        assertTrue(snake.isAlive());
        assertTrue(snake.isAlive());
        snake.kill();
        assertFalse(snake.isAlive());
        assertFalse(snake.isAlive());
    }

    @Test
    public void lengthAfterGrow() throws Exception {
        snake.grow();
        assertEquals(2, snake.length());
        assertEquals(2, snake.length());
    }

    @Test
    public void moveEat() throws Exception {
        snake.grow();
        snake.move();
        assertEquals(new Location(0, 1), snake.getLocation());
        assertEquals(new Location(0, 0), snake.prev.getLocation());
    }

    @Test
    public void moveEatTurn() throws Exception {
        snake.grow();
        snake.move();
        snake.setDirection(new Vector(1, 0));
        snake.grow();
        snake.move();
        assertEquals(new Location(1, 1), snake.getLocation());
        assertEquals(new Location(0, 1), snake.prev.getLocation());
        assertEquals(new Location(0, 0), snake.prev.prev.getLocation());
    }

    @Test
    public void setDirection() throws Exception {
        snake.setDirection(new Vector(0, -1));
        assertEquals(new Vector(0, -1), snake.getDirection());
    }

    @Test
    public void initialLength() throws Exception {
        assertEquals(snake.length(), 1);
    }

    @Test
    public void initialLocation() throws Exception {
        assertEquals(snake.getLocation(), new Location(0, 0));
    }

    @Test
    public void initialDirection() throws Exception {
        assertEquals(snake.getDirection(), new Vector(0, 1));
    }

    @Test
    public void initiallyAlive() throws Exception {
        assertTrue(snake.isAlive());
    }


    /*
    TODO:
    wall kill
    snake kill
    cycle alive
     */
}