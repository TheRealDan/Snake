package dev.therealdan.snake.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Apple extends CircleEntity {

    private Color red;
    private Color green;

    public Apple(float x, float y) {
        this.x = x;
        this.y = y;
        radius = 8;
        red = new Color(1.0f, 0.13333334f, 0.047058824f, 1);
        green = new Color(0.6901961f, 0.85882354f, 0.2627451f, 1);
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(red);
        shapeRenderer.circle(x, y, radius);

        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(x, y, radius);

        shapeRenderer.line(x, y + radius - 1, x, y + radius + 3);

        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(green);
        shapeRenderer.ellipse(x, y + radius - 1, radius, radius / 2f);
    }

    public void teleport(float x, float y) {
        this.x = x;
        this.y = y;
    }
}