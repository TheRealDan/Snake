package dev.therealdan.snake.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.therealdan.snake.game.Apple;
import dev.therealdan.snake.game.GameInstance;
import dev.therealdan.snake.main.SnakeGame;

public class GameScreen implements Screen {

    final SnakeGame game;

    private ScreenViewport viewport;
    private OrthographicCamera camera;

    private GameInstance instance;

    public GameScreen(SnakeGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);

        instance = new GameInstance();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.shapeRenderer.setProjectionMatrix(camera.combined);
        game.batch.setProjectionMatrix(camera.combined);

        game.shapeRenderer.setAutoShapeType(true);
        game.shapeRenderer.begin();
        for (Apple apple : instance.apples)
            apple.render(game.shapeRenderer);
        instance.snake.render(game.shapeRenderer);
        game.shapeRenderer.end();

        instance.loop(delta);
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