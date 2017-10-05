package FieldObjects;

import Interfaces.IFieldObject;

public abstract class SnakePart extends AbstractFieldObject {
    public SnakePart(int x, int y) {
        super(x, y);
    }

    protected abstract void Move();
}
