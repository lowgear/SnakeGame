package FieldObjects;

import util.Location;

abstract public class AbstractFieldObject {
    protected Location location;

    public AbstractFieldObject(int x, int y)
    {
        this(new Location(x, y));
    }

    public AbstractFieldObject(Location location)
    {
        this.location = location;
    }

    public void Act()
    {
    }
}
