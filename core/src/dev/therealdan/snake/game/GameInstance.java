package dev.therealdan.snake.game;

import com.badlogic.gdx.Gdx;
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

    private long slowMotionActivated = 0;
    private long slowMotionTransition = 1000;
    private long slowMotionDuration = 5000;
    private float slowMotionSpeed = 0.1f;

    public GameInstance(SoundManager sound, Snake snake) {
        random = new Random();
        this.sound = sound;

        this.snake = snake;
    }

    public void loop(float delta) {
        if (gameover) return;
        delta *= getGameSpeed();

        snake.handleMovementControls(delta);
        snake.handleMovement(delta);
        if (snake.handleWorldLooping(worldWidth, worldHeight)) sound.playWorldLoop();
        snake.handleConnectedBody(delta);

        if (snake.overlapsSelf(worldWidth, worldHeight)) {
            gameover = true;
            sound.playGameOver();
        }

        handleConsumeApples();
        handleAppleSpawning();
    }

    public void resize(float width, float height) {
        worldWidth = width;
        worldHeight = height;
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
}
