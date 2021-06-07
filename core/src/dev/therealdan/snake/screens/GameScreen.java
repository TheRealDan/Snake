package dev.therealdan.snake.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.therealdan.snake.game.Apple;
import dev.therealdan.snake.game.GameInstance;
import dev.therealdan.snake.main.SnakeApp;

public class GameScreen implements Screen {

    final SnakeApp app;

    private ScreenViewport viewport;
    private OrthographicCamera camera;

    private GameInstance instance;

    public GameScreen(SnakeApp app) {
        this.app = app;

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);

        instance = new GameInstance();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        app.shapeRenderer.setProjectionMatrix(camera.combined);
        app.batch.setProjectionMatrix(camera.combined);

        app.shapeRenderer.setAutoShapeType(true);
        app.shapeRenderer.begin();
        for (Apple apple : instance.apples)
            apple.render(app.shapeRenderer);
        instance.snake.render(app.shapeRenderer, instance.worldWidth, instance.worldHeight);
        app.shapeRenderer.end();

        app.batch.begin();
        app.font.draw(app.batch, "Score: " + instance.snake.getLength(), -(Gdx.graphics.getWidth() / 2f) + 25, Gdx.graphics.getHeight() / 2f - 25, 16);
        app.batch.end();

        instance.loop(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        instance.resize(width, height);
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