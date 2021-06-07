package dev.therealdan.snake.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.therealdan.snake.main.SnakeApp;

public class MainMenuScreen implements Screen {

    final SnakeApp app;

    private ScreenViewport viewport;
    private OrthographicCamera camera;

    public MainMenuScreen(final SnakeApp app) {
        this.app = app;

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0.15f, 0.2f, 1);

        camera.update();
        app.batch.setProjectionMatrix(camera.combined);

        app.batch.begin();
        app.font.center(app.batch, "Click to Start", 0, 0, 16);
        app.batch.end();

        if (Gdx.input.isTouched()) {
            app.setScreen(new GameScreen(app));
            dispose();
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
