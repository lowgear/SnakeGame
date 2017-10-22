package ru.snake_game.model;

import org.junit.Before;
import org.junit.Test;
import ru.snake_game.model.FieldObjects.Apple;
import ru.snake_game.model.FieldObjects.SnakeBody;
import ru.snake_game.model.FieldObjects.SnakeHead;
import ru.snake_game.model.FieldObjects.Wall;
import ru.snake_game.model.util.Location;
import ru.snake_game.model.util.Vector;

import java.util.ArrayList;
import java.util.Iterator;

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
            field.setObjectAt(location, new Wall(location, field));
            location = new Location(width - 1, y);
            field.setObjectAt(location, new Wall(location, field));
        }
        for (int x = 1; x < width - 1; x++) {
            Location location = new Location(x, 0);
            field.setObjectAt(location, new Wall(location, field));
            location = new Location(x, height - 1);
            field.setObjectAt(location, new Wall(location, field));
        }

        game = new Game(field);
    }

    @Test
    public void eatAndGo() throws Exception {
        Location location = new Location(1,1);
        SnakeHead snakeHead = new SnakeHead(location, null, new Vector(1,0), field);
        field.setObjectAt(location, snakeHead);
        location = new Location(2, 1);
        field.setObjectAt(location, new Apple(location, field, 2));

        game.tick();
        game.tick();
        game.tick();

        assertFalse(field.getObjectAt(new Location(2,1)) instanceof Apple);
        assertEquals(3, snakeHead.length());
    }

    @Test
    public void killSnake() throws  Exception{
        Location location = new Location(1,1);
        SnakeHead snakeHead = new SnakeHead(location, null, new Vector(1,0), field);
        field.setObjectAt(location, snakeHead);

        for (int i = 0; i < field.getWidth() - 2; i++)
            game.tick();

        assertFalse(snakeHead.isAlive());
        assertTrue(field.getObjectAt(new Location(5,1)) instanceof Wall);
        assertEquals(new Location(4, 1), snakeHead.getLocation());
    }

    @Test
    public void snakeEatSelf() throws Exception{
        Location location = new Location(2,2);

        ArrayList<Location> bodyLocations = new ArrayList<>();
        bodyLocations.add(new Location(3,2));
        bodyLocations.add(new Location(3,1));
        bodyLocations.add(new Location(2,1));
        bodyLocations.add(new Location(1,1));

        SnakeHead snakeHead = new SnakeHead(location, new Vector(0,-1), field, bodyLocations);
        field.setObjectAt(location, snakeHead);

        game.tick();

        assertFalse(snakeHead.isAlive());
        assertEquals(location, snakeHead.getLocation());
        for (Location bodyLocation : bodyLocations){
            assertTrue(field.getObjectAt(bodyLocation) instanceof SnakeBody);
        }
    }

    @Test
    public void snakeCycle() throws Exception{
        Location location = new Location(1,1);
        SnakeHead snakeHead = new SnakeHead(location, null, new Vector(1,0), field);
        field.setObjectAt(location, snakeHead);
        location  = new Location(2,1);
        field.setObjectAt(location, new Apple(location, field, 11));

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