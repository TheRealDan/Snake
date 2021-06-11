package dev.therealdan.snake.game;

import com.badlogic.gdx.math.Circle;

public class CircleEntity extends Circle {

    public boolean overlaps(float x, float y, float radius) {
        float radiusSum = this.radius + radius;
        return distanceTo(x, y) < radiusSum * radiusSum;
    }

    public float getBoundX(float worldWidth) {
        float x = this.x;
        while (x < -(worldWidth / 2f)) x += worldWidth;
        while (x > worldWidth / 2f) x -= worldWidth;
        return x;
    }

    public float getBoundY(float worldHeight) {
        float y = this.y;
        while (y < -(worldHeight / 2f)) y += worldHeight;
        while (y > worldHeight / 2f) y -= worldHeight;
        return y;
    }

    public float distanceTo(float x, float y) {
        float dx = this.x - x;
        float dy = this.y - y;
        return dx * dx + dy * dy;
    }

    public float distanceToX(float x) {
        float dx = this.x - x;
        return dx * dx;
    }

    public float distanceToY(float y) {
        float dy = this.y - y;
        return dy * dy;
    }
}