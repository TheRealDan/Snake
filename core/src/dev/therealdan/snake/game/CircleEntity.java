package dev.therealdan.snake.game;

import com.badlogic.gdx.math.Circle;

public class CircleEntity extends Circle {

    public boolean overlaps(float x, float y, float radius) {
        float dx = this.x - x;
        float dy = this.y - y;
        float distance = dx * dx + dy * dy;
        float radiusSum = this.radius + radius;
        return distance < radiusSum * radiusSum;
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
}