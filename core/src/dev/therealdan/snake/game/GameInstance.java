package dev.therealdan.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameInstance {

    private Random random;

    public Snake snake;
    public List<Apple> apples = new ArrayList<>();

    public float worldWidth;
    public float worldHeight;

    private long lastAppleSpawn = System.currentTimeMillis();
    private long appleSpawnInterval = 2500;
    private boolean gameover = false;

    public GameInstance() {
        random = new Random();

        snake = new Snake(Color.GREEN, 4);
    }

    public void loop(float delta) {
        if (gameover) return;

        snake.handleMovementControls(delta);
        snake.handleMovement(delta);
        snake.handleWorldLooping(worldWidth, worldHeight);
        snake.handleConnectedBody(delta);

        if (snake.overlapsSelf()) gameover = true;

        handleConsumeApples();
        handleAppleSpawning();
    }

    public void resize(float width, float height) {
        worldWidth = width;
        worldHeight = height;
    }

    private void handleConsumeApples() {
        for (Apple apple : new ArrayList<>(apples)) {
            if (snake.overlaps(apple)) {
                apples.remove(apple);
                snake.addBody();
            }
        }
    }

    private void handleAppleSpawning() {
        if (System.currentTimeMillis() - lastAppleSpawn < appleSpawnInterval) return;
        lastAppleSpawn = System.currentTimeMillis();
        apples.add(new Apple((random.nextBoolean() ? 1 : -1) * random.nextInt(Gdx.graphics.getWidth() / 2), (random.nextBoolean() ? 1 : -1) * random.nextInt(Gdx.graphics.getHeight() / 2)));
    }
}
