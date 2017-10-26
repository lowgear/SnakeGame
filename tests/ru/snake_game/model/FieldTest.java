package ru.snake_game.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.snake_game.model.FieldObjects.SnakeHead;
import ru.snake_game.model.FieldObjects.Wall;
import ru.snake_game.model.Interfaces.IFieldObject;
import ru.snake_game.model.util.Location;
import ru.snake_game.model.util.Vector;

import static org.junit.Assert.*;

public class FieldTest {
    Field field;

    @Before
    public void setUp() throws Exception {
        field = new Field(4,4);
        field.addObject(new Wall(new Location(0, 0)));
        field.addObject(new Wall(new Location(1, 1)));
    }

    @After
    public void tearDown() throws Exception {
        field = null;
    }

    @Test
    public void getObjectAt() throws Exception {
        assertNull(field.getObjectAt(0,1));
        assertTrue(field.getObjectAt(0,0) instanceof Wall);
    }

    @Test
    public void getObjectAt1() throws Exception {
        assertTrue(field.getObjectAt(new Location(1,1)) instanceof Wall);
        assertNull(field.getObjectAt(new Location(3,3)));
    }

    @Test
    public void setObjectAt() throws Exception {
        Location location = new Location(2,2);
        field.addObject(new Wall(location));
        assertTrue(field.getObjectAt(2,2) instanceof  Wall);
    }

    @Test
    public void setObjectAt1() throws Exception {
        Location location = new Location(3,3);
        field.addObject(new Wall(location));
        assertTrue(field.getObjectAt(location) instanceof Wall);
    }

    @Test
    public void getWidth() throws Exception {
        assertEquals(4, field.getWidth());
    }

    @Test
    public void getHeight() throws Exception {
        assertEquals(4, field.getHeight());
    }

    @Test
    public void getSnakeHead() throws Exception {
        Location location = new Location(0,1);
        field.addObject(new SnakeHead(location, null, new Vector(0, 1), field));
        assertEquals(location, field.getSnakeHead().getLocation());
    }

    @Test
    public void iterator() throws Exception {
        int size = 0;
        for (IFieldObject object : field){
            size += 1;
        }
        assertEquals(4 * 4, size);
    }
}