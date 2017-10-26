package ru.snake_game.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.snake_game.model.FieldObjects.Apple;
import ru.snake_game.model.FieldObjects.SnakeHead;
import ru.snake_game.model.FieldObjects.Wall;
import ru.snake_game.model.util.Location;
import ru.snake_game.model.util.Vector;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;
    private Field field;

    @Before
    public void setUp() throws Exception {
        field = new Field(6,6);
        int width = field.getWidth();
        int height = field.getHeight();
        for (int y = 0; y < height; y += 1) {
            Location location = new Location(0, y);
            field.addObject(new Wall(location));
            location = new Location(width - 1, y);
            field.addObject(new Wall(location));
        }
        for (int x = 1; x < width - 1; x++) {
            Location location = new Location(x, 0);
            field.addObject(new Wall(location));
            location = new Location(x, height - 1);
            field.addObject(new Wall(location));
        }

        game = new Game(field);
    }

    @After
    public void tearDown() throws Exception {
        field = null;
        game = null;
    }

    @Test
    public void tick() throws Exception {
        Location location = new Location(1,1);
        field.addObject(new SnakeHead(location, null, new Vector(1, 0), field));
        location = new Location(3, 2);
        field.addObject(new Apple(location, field, 2));
        SnakeHead snakeHead = (SnakeHead) field.getSnakeHead();

        game.tick();
        game.tick();
        snakeHead.setDirection(new Vector(0, 1));
        game.tick();
        game.tick();
        game.tick();
        snakeHead.setDirection(new Vector(-1,0));
        game.tick();
        game.tick();
        game.tick();

        assertFalse(snakeHead.isAlive());
        assertTrue(field.getObjectAt(new Location(0,4)) instanceof Wall);
        assertNull(field.getObjectAt(new Location(3, 2)));
        assertEquals(new Location(1,4), snakeHead.getLocation());
        assertEquals(3, snakeHead.length());
    }

    @Test
    public void cycleTick() throws Exception{
        Location location = new Location(1,1);
        SnakeHead snakeHead = new SnakeHead(location, null, new Vector(1,0), field);
        field.addObject(snakeHead);
        location  = new Location(2,1);
        field.addObject(new Apple(location, field, 11));

        ArrayList<Vector> vectors = new ArrayList<>();
        vectors.add(new Vector(0,1));
        vectors.add(new Vector(-1,0));
        vectors.add(new Vector(0,-1));
        vectors.add(new Vector(1,0));

        for (int j = 0; j < 5; j++) {
            for (Vector vector : vectors) {
                for (int i = 0; i < 3; i++)
                    game.tick();
                snakeHead.setDirection(vector);
            }
        }

        assertTrue(snakeHead.isAlive());
        assertEquals(12, snakeHead.length());
    }

}