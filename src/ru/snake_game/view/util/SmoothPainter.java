package ru.snake_game.view.util;

import javafx.animation.Animation;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import ru.snake_game.model.FieldObjects.Apple;
import ru.snake_game.model.FieldObjects.SnakeBody;
import ru.snake_game.model.FieldObjects.SnakeHead;
import ru.snake_game.model.FieldObjects.Wall;
import ru.snake_game.model.Interfaces.IFieldObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    @Override
    public NodeAndAnimation paint(IFieldObject object) {
        Node node = HOW_TO_PAINT.get(object.getClass()).emit();
        Animation animation, tickAnimation;

        //todo
        throw new NotImplementedException();
    }
}
