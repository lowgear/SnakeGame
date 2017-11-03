package ru.snake_game.view.util;

import javafx.animation.Animation;
import javafx.scene.Node;

public class NodeAndAnimation {
    public final Animation animation;
    public final Node node;

    public NodeAndAnimation(Node node, Animation animation) {
        this.node = node;
        this.animation = animation;
    }
}
