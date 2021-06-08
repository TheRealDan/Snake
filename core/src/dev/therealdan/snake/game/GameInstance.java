package dev.therealdan.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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

    public GameInstance(SoundManager sound) {
        random = new Random();
        this.sound = sound;

        snake = new Snake(Color.GREEN, 4);
    }

    public void loop(float delta) {
        if (gameover) return;

        snake.handleMovementControls(delta);
        snake.handleMovement(delta);
        if (snake.handleWorldLooping(worldWidth, worldHeight)) sound.worldloop.play();
        snake.handleConnectedBody(delta);

        if (snake.overlapsSelf()) {
            gameover = true;
            sound.gameover.play();
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
            if (snake.overlaps(apple)) {
                apples.remove(apple);
                snake.addBody();
                sound.consumeapple.play();
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
}
