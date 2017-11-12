package ru.snake_game.view.util;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.collections.ObservableList;
import javafx.scene.Group;
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
import ru.snake_game.model.util.Vector;

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

    private Node makeSnakeEye(){
        Node eye = makeCircle(Color.LIGHTBLUE);
        ((Circle)eye).setRadius(CELL_SIZE / 4);
        return eye;
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
                }
                else{
                    node.setTranslateX(snakePart.getLocation().getX() * CELL_SIZE);
                    node.setTranslateY(snakePart.getLocation().getY() * CELL_SIZE);
                }
            }
        };
    }


    private static Transition getSnakeHeadTickAnimation(SnakeHead snakeHead, Node node) {
        return new Transition() {
            private Location from = snakeHead.getLocation();
            private Location to = snakeHead.getLocation();

            {
                setCycleDuration(Duration.seconds(1));
            }

            @Override
            protected void interpolate(double frac) {
                Vector direction = snakeHead.getDirection();
                int kleftX = direction.getX() == 1 ? 1 : -1;
                int kleftY = direction.getY() == 1 ? 1 : -1;
                int krightX = direction.getX() == -1 ? -1 : 1;
                int krightY = direction.getY() == -1 ? -1 : 1;

                ObservableList<Node> subTree = ((Group) node).getChildren();
                if (snakeHead.isAlive()) {
                    if (!snakeHead.getLocation().equals(to)) {
                        from = to;
                        to = snakeHead.getLocation();
                    }

                    double nX = (from.getX() + (to.getX() - from.getX()) * frac) * CELL_SIZE;
                    double nY = (from.getY() + (to.getY() - from.getY()) * frac) * CELL_SIZE;

                    subTree.get(0).setTranslateX(nX);
                    subTree.get(0).setTranslateY(nY);

                    subTree.get(1).setTranslateX(nX + kleftX * CELL_SIZE / 4);
                    subTree.get(1).setTranslateY(nY + kleftY * CELL_SIZE / 4);

                    subTree.get(2).setTranslateX(nX + krightX * CELL_SIZE / 4);
                    subTree.get(2).setTranslateY(nY + krightY * CELL_SIZE / 4);
                }
                else {
                    subTree.get(0).setTranslateX(snakeHead.getLocation().getX() * CELL_SIZE);
                    subTree.get(0).setTranslateY(snakeHead.getLocation().getY() * CELL_SIZE);

                    subTree.get(1).setTranslateX(snakeHead.getLocation().getX() * CELL_SIZE + kleftX * CELL_SIZE / 4);
                    subTree.get(1).setTranslateY(snakeHead.getLocation().getY() * CELL_SIZE + kleftY * CELL_SIZE / 4);

                    subTree.get(2).setTranslateX(snakeHead.getLocation().getX() * CELL_SIZE + krightX * CELL_SIZE / 4);
                    subTree.get(2).setTranslateY(snakeHead.getLocation().getY() * CELL_SIZE + krightY * CELL_SIZE / 4);
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
        Group group = null;

        if (object instanceof ISnakeHead) {

            Vector direction = ((SnakeHead)object).getDirection();
            int kleftX = direction.getX() == 1 ? 1 : -1;
            int kleftY = direction.getY() == 1 ? 1 : -1;
            int krightX = direction.getX() == -1 ? -1 : 1;
            int krightY = direction.getY() == -1 ? -1 : 1;

            Node leftEye = makeSnakeEye();
            leftEye.setTranslateX((object.getLocation().getX() * CELL_SIZE) + kleftX * CELL_SIZE / 4);
            leftEye.setTranslateY((object.getLocation().getY() * CELL_SIZE) + kleftY * CELL_SIZE / 4);

            Node rightEye = makeSnakeEye();
            rightEye.setTranslateX((object.getLocation().getX() * CELL_SIZE) + krightX * CELL_SIZE / 4);
            rightEye.setTranslateY((object.getLocation().getY() * CELL_SIZE) + krightY * CELL_SIZE / 4);

            group = new Group();
            group.getChildren().add(node);
            group.getChildren().add(leftEye);
            group.getChildren().add(rightEye);

            animation = new Transition() {
                {
                    setCycleDuration(Duration.seconds(2));
                    setCycleCount(INDEFINITE);
                }

                @Override
                protected void interpolate(double frac) {
                    if (((ISnakeHead) object).isAlive()) {
                        if (Math.round(frac) == 1){
                            ((Circle)leftEye).setFill(Color.WHITE);
                            ((Circle)rightEye).setFill(Color.WHITE);
                        }
                        else {
                            ((Circle)leftEye).setFill(Color.GREEN);
                            ((Circle)rightEye).setFill(Color.GREEN);
                        }
                    }
                    else{
                        ((Circle)leftEye).setFill(Color.GREEN);
                        ((Circle)rightEye).setFill(Color.GREEN);
                    }
                }
            };
        }

        if (object instanceof SnakeHead)
            tickAnimation = getSnakeHeadTickAnimation((SnakeHead) object, group);
        else if (object instanceof SnakePart)
            tickAnimation = getSnakePartTickAnimation((SnakePart) object, node);

        if (group == null)
            return new NodeAndAnimation(node, animation, tickAnimation);
        else
            return new NodeAndAnimation(group, animation,tickAnimation);
    }
}
