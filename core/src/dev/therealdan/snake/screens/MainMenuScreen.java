package dev.therealdan.snake.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.therealdan.snake.game.Apple;
import dev.therealdan.snake.game.Snake;
import dev.therealdan.snake.main.SnakeApp;

public class MainMenuScreen implements Screen {

    final SnakeApp app;

    private ScreenViewport viewport;
    private OrthographicCamera camera;

    private Snake snake;
    private Apple apple;

    public MainMenuScreen(final SnakeApp app) {
        this.app = app;

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);

        snake = new Snake(Color.GREEN, 6);
        apple = new Apple(0, 0);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(app.color.getTheme().dark.cpy());

        camera.update();
        app.shapeRenderer.setProjectionMatrix(camera.combined);
        app.batch.setProjectionMatrix(camera.combined);

        app.shapeRenderer.setAutoShapeType(true);
        app.shapeRenderer.begin();
        snake.render(app.shapeRenderer, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        apple.render(app.shapeRenderer);
        app.shapeRenderer.end();

        app.batch.begin();
        app.batch.setColor(app.color.getTheme().text);
        app.font.center(app.batch, "Use WASD to move", 0, 200, 16);
        app.font.center(app.batch, "You can use the screen's edges to loop around", 0, 140, 16);
        app.font.center(app.batch, "Eat apples to grow longer", 0, 50, 16);
        app.font.center(app.batch, "Any part of the Snake can collect Apples", 0, 10, 16);
        app.font.center(app.batch, "Don't crash into your body", 0, -90, 16);
        app.font.center(app.batch, "Press the Spacebar to Start", 0, -180, 16);
        app.batch.end();

        snake.handleMovement(delta);
        snake.handleWorldLooping(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        snake.handleConnectedBody(delta);

        apple.teleport(130, 43);

        if (snake.getHead().y > 165) snake.getHead().y--;
        if (snake.getHead().y < 165) snake.getHead().y++;

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
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
