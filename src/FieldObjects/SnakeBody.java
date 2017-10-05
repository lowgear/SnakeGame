package FieldObjects;

public class SnakeBody extends SnakePart {
    private SnakePart prev;
    private SnakePart next;

    public SnakeBody(int x, int y, SnakePart prev, SnakePart next)
    {
        super(x, y);
        this.next = next;
        this.prev = prev;
    }

    @Override
    protected void Move() {
        prev.Move();
        location = next.location;
    }
}
