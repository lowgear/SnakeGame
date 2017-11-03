package ru.snake_game.view.util;

import javafx.animation.Animation;
import javafx.scene.Node;

public class NodeAndAnimation {
    public static final double CELL_SIZE = 10;

    public final Animation animation;
    public final Animation tickAnimation;
    public final Node node;

    public NodeAndAnimation(Node node, Animation animation, Animation tickAnimation) {
        this.node = node;
        this.animation = animation;
        this.tickAnimation = tickAnimation;
    }
}
