package dev.therealdan.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dev.therealdan.snake.main.SoundManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameInstance {

    private Random random;
    private SoundManager sound;

    public Snake snake;
    public List<Apple> apples = new ArrayList<>();

    public float worldWidth;
    public float worldHeight;

    private long lastAppleSpawn = System.currentTimeMillis();
    private long appleSpawnInterval = 2500;
    public boolean gameover = false;
    public long gameoverAt = System.currentTimeMillis();

    private long slowMotionActivated = 0;
    private long slowMotionTransition = 1000;
    private long slowMotionDuration = 5000;
    private float slowMotionSpeed = 0.1f;

    private long lastMove = System.currentTimeMillis();

    public GameInstance(SoundManager sound, Snake snake) {
        random = new Random();
        this.sound = sound;

        this.snake = snake;
    }

    public void loop(float delta) {
        if (gameover) return;
        delta *= getGameSpeed();

        handleMovementControls(snake, delta);
        snake.handleMovement(delta);
        snake.handleConnectedBody(delta);
        if (snake.handleWorldLooping(worldWidth, worldHeight)) sound.playWorldLoop();

        if (snake.overlapsSelf(worldWidth, worldHeight)) {
            gameover = true;
            gameoverAt = System.currentTimeMillis();
            sound.playGameOver();
        }

        handleConsumeApples();
        handleAppleSpawning();
    }

    public void resize(float width, float height) {
        worldWidth = width;
        worldHeight = height;
    }

    public void handleMovementControls(Snake snake, float delta) {
        float maxVelocity = 280;

        if (Gdx.input.isKeyPressed(Input.Keys.W) && (snake.yVelocity >= Math.abs(snake.xVelocity) || Math.abs(snake.xVelocity) > maxVelocity / 2f)) {
            lastMove = System.currentTimeMillis();
            snake.yVelocity += 1000 * delta;
            if (snake.yVelocity > maxVelocity) snake.xVelocity += -snake.xVelocity * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && (snake.yVelocity <= Math.abs(snake.xVelocity) || Math.abs(snake.xVelocity) > maxVelocity / 2f)) {
            lastMove = System.currentTimeMillis();
            snake.yVelocity -= 1000 * delta;
            if (snake.yVelocity < maxVelocity) snake.xVelocity += -snake.xVelocity * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && (snake.xVelocity <= Math.abs(snake.yVelocity) || Math.abs(snake.yVelocity) > maxVelocity / 2f)) {
            lastMove = System.currentTimeMillis();
            snake.xVelocity -= 1000 * delta;
            if (snake.xVelocity < maxVelocity) snake.yVelocity += -snake.yVelocity * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && (snake.xVelocity >= Math.abs(snake.yVelocity) || Math.abs(snake.yVelocity) > maxVelocity / 2f)) {
            lastMove = System.currentTimeMillis();
            snake.xVelocity += 1000 * delta;
            if (snake.xVelocity > maxVelocity) snake.yVelocity += -snake.yVelocity * delta;
        }

        if (snake.xVelocity > maxVelocity) snake.xVelocity = maxVelocity;
        if (snake.xVelocity < -maxVelocity) snake.xVelocity = -maxVelocity;
        if (snake.yVelocity > maxVelocity) snake.yVelocity = maxVelocity;
        if (snake.yVelocity < -maxVelocity) snake.yVelocity = -maxVelocity;
    }

    private void handleConsumeApples() {
        for (Apple apple : new ArrayList<>(apples)) {
            if (snake.overlaps(apple, worldWidth, worldHeight)) {
                apples.remove(apple);
                snake.addBody();
                sound.playConsumeApple();
            }
        }
    }

    private void handleAppleSpawning() {
        if (System.currentTimeMillis() - lastAppleSpawn < appleSpawnInterval) return;
        if (System.currentTimeMillis() - lastMove > 5000) return;
        lastAppleSpawn = System.currentTimeMillis();
        apples.add(new Apple((random.nextBoolean() ? 1 : -1) * random.nextInt(Gdx.graphics.getWidth() / 2), (random.nextBoolean() ? 1 : -1) * random.nextInt(Gdx.graphics.getHeight() / 2)));
    }

    public int getScore() {
        return snake.getLength();
    }

    public boolean activateSlowMotion() {
        if (System.currentTimeMillis() - slowMotionActivated < slowMotionDuration) return false;
        slowMotionActivated = System.currentTimeMillis();
        return true;
    }

    public float getGameSpeed() {
        float timepassed = System.currentTimeMillis() - slowMotionActivated;
        if (timepassed > slowMotionDuration) return 1f;
        if (timepassed < slowMotionTransition) return Math.max(slowMotionSpeed, 1f - (timepassed / slowMotionTransition));
        if (timepassed > slowMotionDuration - slowMotionTransition) return slowMotionSpeed + ((timepassed - slowMotionDuration + slowMotionTransition) / slowMotionTransition);
        return slowMotionSpeed;
    }

    public Apple getClosestApple(float x, float y) {
        Apple closest = null;
        float distance, closestDistance = 0;
        for (Apple apple : apples) {
            distance = apple.distanceTo(x, y);
            if (distance < closestDistance || closest == null) {
                closest = apple;
                closestDistance = distance;
            }
        }

        return closest;
    }
}
