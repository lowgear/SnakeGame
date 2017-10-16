package ru.snake_game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.snake_game.FieldObjects.*;
import ru.snake_game.util.Location;
import ru.snake_game.util.Vector;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;
    private Field field;

    @Before
    public void setUp() throws Exception {
        field = new Field(6,6);
        int width = field.getWidth();
        for (int i = 0; i < field.size(); i+=width) {
            Location location = field.getLocation(i);
            field.setObjectAt(location, new Wall(location, field));
        }
        for (int i = width-1; i < field.size(); i+=width) {
            Location location = field.getLocation(i);
            field.setObjectAt(location, new Wall(location, field));
        }
        for (int i = 1; i < width - 1; i++) {
            Location location = field.getLocation(i);
            field.setObjectAt(location, new Wall(location, field));
        }
        for (int i = field.size() - (width - 1); i < field.size() - 1 ; i++) {
            Location location = field.getLocation(i);
            field.setObjectAt(location, new Wall(location,field));
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
        int width = field.getWidth();
        Location location = field.getLocation(width + 1);
        field.setObjectAt(location, new SnakeHead(location, null, new Vector(1,0), field));
        location = field.getLocation(2 * width + 3);
        field.setObjectAt(location, new Apple(location, field, 2));
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
        assertTrue(field.getObjectAt(field.getLocation(4 * width)) instanceof Wall);
        assertNull(field.getObjectAt(field.getLocation(2 * width + 3)));
        assertEquals(field.getLocation(4 * width + 1), snakeHead.getLocation());
        assertEquals(3, snakeHead.length());
    }

    @Test
    public void cycleTick() throws Exception{
        int width = field.getWidth();
        Location location = field.getLocation(width + 1);
        SnakeHead snakeHead = new SnakeHead(location, null, new Vector(1,0), field);
        field.setObjectAt(location, snakeHead);
        field.setObjectAt(field.getLocation(width + 2), new Apple(location, field, 11));

        ArrayList<Vector> vectors = new ArrayList<>();
        vectors.add(new Vector(0,1));
        vectors.add(new Vector(-1,0));
        vectors.add(new Vector(0,-1));
        vectors.add(new Vector(1,0));

        for (int c = 0; c < 5; c++){
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