package dev.therealdan.snake.game;

import com.badlogic.gdx.math.Circle;

public class SnakeBody extends Circle {

    public SnakeBody(float x, float y) {
        radius = 12;
        this.x = x;
        this.y = y;
    }
}