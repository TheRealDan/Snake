package dev.therealdan.snake.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class Apple extends Circle {

    private Color color;

    public Apple(float x, float y) {
        color = Color.RED;
        radius = 8;
        this.x = x;
        this.y = y;
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(x, y, radius);
    }
}