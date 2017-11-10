package ru.snake_game.view.util;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;
import ru.snake_game.model.FieldObjects.*;
import ru.snake_game.model.Interfaces.IFieldObject;
import ru.snake_game.model.Interfaces.ISnakeHead;
import ru.snake_game.model.util.Location;

import java.util.HashMap;

import static ru.snake_game.view.util.NodeAndAnimation.CELL_SIZE;

public class SmoothPainter implements IFieldObjectPainter {
    public static final SmoothPainter SMOOTH_PAINTER = new SmoothPainter();
    private static final HashMap<Class<? extends IFieldObject>, IEmitter<Node>> HOW_TO_PAINT = new HashMap<>();
    private static final double STROKE_WIDTH = CELL_SIZE / 10;

    static {
        HOW_TO_PAINT.put(Wall.class, () -> makeSquare(Color.GRAY));
        HOW_TO_PAINT.put(SnakeHead.class, () -> makeCircle(Color.LIGHTGREEN));
        HOW_TO_PAINT.put(SnakeBody.class, () -> makeCircle(Color.GREEN));
        HOW_TO_PAINT.put(Apple.class, () -> makeCircle(Color.RED));
    }

    private SmoothPainter() {
    }

    private static Circle makeCircle(Paint fill) {
        double r = CELL_SIZE / 2;
        Circle res = new Circle(r, r, r);
        res.setStrokeType(StrokeType.INSIDE);
        res.setStroke(Color.BLACK);
        res.setStrokeWidth(STROKE_WIDTH);
        res.setFill(fill);
        return res;
    }

    private static Rectangle makeSquare(Paint fill) {
        Rectangle res = new Rectangle(
                CELL_SIZE,
                CELL_SIZE);
        res.setFill(fill);
        res.setStrokeType(StrokeType.INSIDE);
        res.setStroke(Color.BLACK);
        res.setStrokeWidth(STROKE_WIDTH);
        return res;
    }

    private static Transition getSnakePartTickAnimation(SnakePart snakePart, Node node) {
        return new Transition() {
            private Location from = snakePart.getLocation();
            private Location to = snakePart.getLocation();

            {
                setCycleDuration(Duration.seconds(1));
            }

            @Override
            protected void interpolate(double frac) {
                if (snakePart.getHead().isAlive()) {
                    if (!snakePart.getLocation().equals(to)) {
                        from = to;
                        to = snakePart.getLocation();
                    }
                    double nX = (from.getX() + (to.getX() - from.getX()) * frac) * CELL_SIZE;
                    double nY = (from.getY() + (to.getY() - from.getY()) * frac) * CELL_SIZE;
                    node.setTranslateX(nX);
                    node.setTranslateY(nY);
                } else {
                    node.setTranslateX(snakePart.getLocation().getX() * CELL_SIZE);
                    node.setTranslateY(snakePart.getLocation().getY() * CELL_SIZE);
                }
            }
        };
    }

    @Override
    public NodeAndAnimation paint(IFieldObject object) {
        Node node = HOW_TO_PAINT.get(object.getClass()).emit();
        node.setTranslateX(object.getLocation().getX() * CELL_SIZE);
        node.setTranslateY(object.getLocation().getY() * CELL_SIZE);

        Animation animation = null, tickAnimation = null;

        //todo implement eye blinking
        if (object instanceof ISnakeHead)
            animation = new Transition() {
                {
                    setCycleDuration(Duration.seconds(2));
                }

                @Override
                protected void interpolate(double frac) {
                    if (((ISnakeHead) object).isAlive())
                        ((Circle) node).setFill(Color.LIGHTGREEN);
                    else
                        ((Circle) node).setFill(Color.RED);
                }
            };

        if (object instanceof SnakePart) {
            tickAnimation = getSnakePartTickAnimation((SnakePart) object, node);
        }

        return new NodeAndAnimation(node, animation, tickAnimation);
    }
}
