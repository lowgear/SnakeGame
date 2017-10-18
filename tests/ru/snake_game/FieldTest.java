package ru.snake_game;

import javafx.util.converter.LocalDateStringConverter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.snake_game.FieldObjects.AbstractFieldObject;
import ru.snake_game.FieldObjects.SnakeHead;
import ru.snake_game.FieldObjects.Wall;
import ru.snake_game.Interfaces.IFieldObject;
import ru.snake_game.util.Location;
import ru.snake_game.util.Vector;

import java.util.Locale;

import static org.junit.Assert.*;

public class FieldTest {
    Field field;

    @Before
    public void setUp() throws Exception {
        field = new Field(4,4);
        field.setObjectAt(0,0, new Wall(new Location(0,0), field));
        field.setObjectAt(1,1, new Wall(new Location(1,1), field));
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
        field.setObjectAt(location, new Wall(location, field));
        assertTrue(field.getObjectAt(2,2) instanceof  Wall);
    }

    @Test
    public void setObjectAt1() throws Exception {
        Location location = new Location(3,3);
        field.setObjectAt(location, new Wall(location, field));
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
    public void size() throws Exception {
       assertEquals(4 * 4, field.size());
    }

    @Test
    public void getSnakeHead() throws Exception {
        Location location = new Location(0,1);
        field.setObjectAt(location, new SnakeHead(location, null, new Vector(0,1), field));
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