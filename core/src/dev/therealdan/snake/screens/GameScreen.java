package dev.therealdan.snake.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.therealdan.snake.game.Apple;
import dev.therealdan.snake.game.Snake;
import dev.therealdan.snake.main.SnakeGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreen implements Screen {

    final SnakeGame game;

    private Random random;
    private ScreenViewport viewport;
    private OrthographicCamera camera;

    private Snake snake;
    private List<Apple> apples = new ArrayList<>();

    private long lastAppleSpawn = System.currentTimeMillis();
    private long appleSpawnInterval = 2500;

    public GameScreen(SnakeGame game) {
        this.game = game;

        random = new Random();
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);

        snake = new Snake(Color.GREEN, 4);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.shapeRenderer.setProjectionMatrix(camera.combined);
        game.batch.setProjectionMatrix(camera.combined);

        game.shapeRenderer.setAutoShapeType(true);
        game.shapeRenderer.begin();
        for (Apple apple : apples)
            apple.render(game.shapeRenderer);
        snake.render(game.shapeRenderer);
        game.shapeRenderer.end();

        snake.handleMovementControls(delta);
        snake.handleConnectedBody(delta);

        if (System.currentTimeMillis() - lastAppleSpawn > appleSpawnInterval) {
            lastAppleSpawn = System.currentTimeMillis();
            apples.add(new Apple((random.nextBoolean() ? 1 : -1) * random.nextInt(Gdx.graphics.getWidth() / 2), (random.nextBoolean() ? 1 : -1) * random.nextInt(Gdx.graphics.getHeight() / 2)));
        }

        for (Apple apple : apples) {
            if (snake.overlaps(apple)) {
                apples.remove(apple);
                snake.addBody();
                break;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}