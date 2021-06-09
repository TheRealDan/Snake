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

    private float xVelocity = 150;
    private float yVelocity = 0;

    public Snake(Color color, float x, float y, int length) {
        this.color = color;

        for (int i = 0; i < length; i++)
            addBody(x, y);
    }

    public void render(ShapeRenderer shapeRenderer, float worldWidth, float worldHeight) {
        boolean head = true;
        for (SnakeBody snakeBody : snakeBodies) {
            snakeBody.render(shapeRenderer, snakeBody.x, snakeBody.y, color, head);
            snakeBody.render(shapeRenderer, snakeBody.x + worldWidth, snakeBody.y, color, head);
            snakeBody.render(shapeRenderer, snakeBody.x - worldWidth, snakeBody.y, color, head);
            snakeBody.render(shapeRenderer, snakeBody.x, snakeBody.y + worldHeight, color, head);
            snakeBody.render(shapeRenderer, snakeBody.x, snakeBody.y - worldHeight, color, head);
            snakeBody.render(shapeRenderer, snakeBody.x + worldWidth, snakeBody.y + worldHeight, color, head);
            snakeBody.render(shapeRenderer, snakeBody.x - worldWidth, snakeBody.y - worldHeight, color, head);
            snakeBody.render(shapeRenderer, snakeBody.x + worldWidth, snakeBody.y - worldHeight, color, head);
            snakeBody.render(shapeRenderer, snakeBody.x - worldWidth, snakeBody.y + worldHeight, color, head);
            head = false;
        }
    }

    public void handleMovementControls(float delta) {
        float maxVelocity = 300;

        if (Gdx.input.isKeyPressed(Input.Keys.W) && (yVelocity >= Math.abs(xVelocity) || Math.abs(xVelocity) > maxVelocity / 2f)) {
            yVelocity += 1000 * delta;
            if (yVelocity > maxVelocity) xVelocity += -xVelocity * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && (yVelocity <= Math.abs(xVelocity) || Math.abs(xVelocity) > maxVelocity / 2f)) {
            yVelocity -= 1000 * delta;
            if (yVelocity < maxVelocity) xVelocity += -xVelocity * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && (xVelocity <= Math.abs(yVelocity) || Math.abs(yVelocity) > maxVelocity / 2f)) {
            xVelocity -= 1000 * delta;
            if (xVelocity < maxVelocity) yVelocity += -yVelocity * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && (xVelocity >= Math.abs(yVelocity) || Math.abs(yVelocity) > maxVelocity / 2f)) {
            xVelocity += 1000 * delta;
            if (xVelocity > maxVelocity) yVelocity += -yVelocity * delta;
        }

        if (xVelocity > maxVelocity) xVelocity = maxVelocity;
        if (xVelocity < -maxVelocity) xVelocity = -maxVelocity;
        if (yVelocity > maxVelocity) yVelocity = maxVelocity;
        if (yVelocity < -maxVelocity) yVelocity = -maxVelocity;
    }

    public void handleMovement(float delta) {
        getHead().x += xVelocity * delta;
        getHead().y += yVelocity * delta;
    }

    public boolean handleWorldLooping(float worldWidth, float worldHeight) {
        boolean didTeleport = false;

        SnakeBody head = getHead();
        if (head.x > worldWidth / 2f) {
            teleport(head.x - worldWidth, head.y);
            didTeleport = true;
        } else if (head.x < -(worldWidth / 2f)) {
            teleport(head.x + worldWidth, head.y);
            didTeleport = true;
        }

        if (head.y > worldHeight / 2f) {
            teleport(head.x, head.y - worldHeight);
            didTeleport = true;
        } else if (head.y < -(worldHeight / 2f)) {
            teleport(head.x, head.y + worldHeight);
            didTeleport = true;
        }

        return didTeleport;
    }

    public void handleConnectedBody(float delta) {
        SnakeBody head = getHead();
        SnakeBody toFollow = head;
        for (SnakeBody snakeBody : snakeBodies) {
            if (snakeBody.equals(head)) continue;

            if (!snakeBody.overlaps(toFollow)) {
                snakeBody.x += (snakeBody.x > toFollow.x ? -1 : 1) * Math.abs(snakeBody.x - toFollow.x) * delta * 10;
                snakeBody.y += (snakeBody.y > toFollow.y ? -1 : 1) * Math.abs(snakeBody.y - toFollow.y) * delta * 10;
            }

            toFollow = snakeBody;
        }
    }

    public void teleport(float x, float y) {
        SnakeBody head = getHead();
        float xOffset = head.x - x;
        float yOffset = head.y - y;

        head.x = x;
        head.y = y;

        for (SnakeBody snakeBody : snakeBodies) {
            if (snakeBody.equals(head)) continue;
            snakeBody.x -= xOffset;
            snakeBody.y -= yOffset;
        }
    }

    public void addBody() {
        snakeBodies.add(new SnakeBody(getTail().x, getTail().y));
    }

    public void addBody(float x, float y) {
        snakeBodies.add(new SnakeBody(x, y));
    }

    public void trim(int amount) {
        for (int i = 0; i < amount; i++)
            snakeBodies.remove(getLength() - 1);
    }

    public boolean overlapsSelf() {
        if (getLength() < 2) return false;

        SnakeBody head = getHead();
        SnakeBody neck = getNeck();
        for (SnakeBody snakeBody : snakeBodies) {
            if (snakeBody.equals(head) || snakeBody.equals(neck)) continue;
            if (head.overlaps(snakeBody)) return true;
        }

        return false;
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

    public int getLength() {
        return snakeBodies.size();
    }

    public SnakeBody getHead() {
        return snakeBodies.get(0);
    }

    public SnakeBody getNeck() {
        return snakeBodies.get(1);
    }

    public SnakeBody getTail() {
        return snakeBodies.get(snakeBodies.size() - 1);
    }
}