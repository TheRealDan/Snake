package dev.therealdan.snake.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class SnakeBody extends Circle {

    public SnakeBody(float x, float y) {
        radius = 12;
        this.x = x;
        this.y = y;
    }

    public void render(ShapeRenderer shapeRenderer, float x, float y, Color color, boolean head) {
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.circle(x, y, radius);

        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(x, y, radius);

        if (head) {
            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(x + (radius / 3f), y + (radius / 6f), radius / 2f);

            shapeRenderer.set(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.circle(x + (radius / 3f), y + (radius / 6f), radius / 2f);

            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.circle(x + (radius / 3f), y + (radius / 6f), radius / 4f);

            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(x - (radius / 3f), y + (radius / 6f), radius / 2f);

            shapeRenderer.set(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.circle(x - (radius / 3f), y + (radius / 6f), radius / 2f);

            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.circle(x - (radius / 3f), y + (radius / 6f), radius / 4f);
        }
    }
}