package dev.therealdan.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private Color color;
    private List<SnakeBody> snakeBodies = new ArrayList<>();

    public Snake(Color color, int length) {
        this.color = color;

        for (int i = 0; i < length; i++)
            addBody();
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        for (SnakeBody snakeBody : snakeBodies)
            shapeRenderer.circle(snakeBody.x, snakeBody.y, snakeBody.radius);
    }

    public void handleMovementControls(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) getHead().y += 100 * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) getHead().y -= 100 * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) getHead().x -= 100 * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) getHead().x += 100 * delta;
    }

    public void handleConnectedBody(float delta) {
        SnakeBody head = getHead();
        SnakeBody toFollow = head;
        for (SnakeBody snakeBody : snakeBodies) {
            if (snakeBody.equals(head)) continue;

            if (!snakeBody.overlaps(toFollow)) {
                snakeBody.x += (snakeBody.x > toFollow.x ? -1 : 1) * Math.abs(snakeBody.x - toFollow.x) * delta * 5;
                snakeBody.y += (snakeBody.y > toFollow.y ? -1 : 1) * Math.abs(snakeBody.y - toFollow.y) * delta * 5;
            }

            toFollow = snakeBody;
        }
    }

    public void addBody() {
        snakeBodies.add(new SnakeBody());
    }

    public boolean contains(Vector2 point) {
        for (SnakeBody snakeBody : snakeBodies)
            if (snakeBody.contains(point))
                return true;

        return false;
    }

    public boolean contains(Rectangle rectangle) {
        for (SnakeBody snakeBody : snakeBodies)
            if (rectangle.contains(snakeBody))
                return true;

        return false;
    }

    public boolean overlaps(Circle circle) {
        for (SnakeBody snakeBody : snakeBodies)
            if (snakeBody.overlaps(circle))
                return true;

        return false;
    }

    public SnakeBody getHead() {
        return snakeBodies.get(0);
    }
}