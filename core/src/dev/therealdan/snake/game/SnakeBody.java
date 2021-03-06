package dev.therealdan.snake.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SnakeBody extends CircleEntity {

    public SnakeBody(float x, float y) {
        radius = 12;
        this.x = x;
        this.y = y;
    }

    public void render(ShapeRenderer shapeRenderer, float x, float y, Color color, boolean head, Apple apple) {
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.circle(x, y, radius);

        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(x, y, radius);

        if (head) {
            float xOffset = 0;
            float yOffset = 0;
            if (apple != null) {
                if (apple.distanceToX(x) > 50) {
                    if (x < apple.x) xOffset += radius / 4f;
                    if (x > apple.x) xOffset -= radius / 4f;
                }
                if (apple.distanceToY(y) > 50) {
                    if (y < apple.y) yOffset += radius / 4f;
                    if (y > apple.y) yOffset -= radius / 4f;
                }
            }

            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(x + (radius / 3f), y + (radius / 6f), radius / 2f);

            shapeRenderer.set(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.circle(x + (radius / 3f), y + (radius / 6f), radius / 2f);

            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.circle(x + (radius / 3f) + xOffset, y + (radius / 6f) + yOffset, radius / 4f);

            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(x - (radius / 3f), y + (radius / 6f), radius / 2f);

            shapeRenderer.set(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.circle(x - (radius / 3f), y + (radius / 6f), radius / 2f);

            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.circle(x - (radius / 3f) + xOffset, y + (radius / 6f) + yOffset, radius / 4f);
        }
    }

    public boolean touchesOutsideOf(float x, float y, float width, float height) {
        return this.x - radius < x || this.x + radius > x + width || this.y - radius < y || this.y + radius > y + height;
    }

    public boolean nearBorderOf(float x, float y, float width, float height) {
        return Math.abs(this.x - x) < radius || Math.abs(this.x - (x + width)) < radius || Math.abs(this.y - y) < radius || Math.abs(this.y - (y + height)) < radius;
    }
}