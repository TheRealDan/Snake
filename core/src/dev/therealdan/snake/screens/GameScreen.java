package dev.therealdan.snake.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.therealdan.snake.game.Apple;
import dev.therealdan.snake.game.GameInstance;
import dev.therealdan.snake.game.Snake;
import dev.therealdan.snake.main.SnakeApp;

public class GameScreen implements Screen {

    final SnakeApp app;

    private ScreenViewport viewport;
    private OrthographicCamera camera;

    private GameInstance instance;

    private Color background;
    private long backgroundColorInterval = 10000;

    public GameScreen(SnakeApp app, Snake snake) {
        this.app = app;

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);

        snake.setLength(6);
        instance = new GameInstance(app.sound, snake);

        background = app.color.getTheme().dark.cpy();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(background);
        background.lerp(System.currentTimeMillis() % backgroundColorInterval > backgroundColorInterval / 2 ? app.color.getTheme().light : app.color.getTheme().dark, 0.005f);

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
        app.batch.setColor(app.color.getTheme().text);
        if (!instance.gameover) {
            app.font.draw(app.batch, "Score: " + instance.getScore(), -(Gdx.graphics.getWidth() / 2f) + 25, Gdx.graphics.getHeight() / 2f - 25, 16);
        } else {
            app.font.center(app.batch, "Game Over!", 0, 50, 32);
            app.font.center(app.batch, "Your Score: " + instance.getScore(), 0, 0, 24);
        }
        app.batch.end();

        instance.loop(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            app.setScreen(new MainMenuScreen(app, instance.snake));
            dispose();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            instance.activateSlowMotion();
        }
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