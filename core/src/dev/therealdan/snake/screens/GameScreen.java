package dev.therealdan.snake.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.therealdan.snake.game.Apple;
import dev.therealdan.snake.game.GameInstance;
import dev.therealdan.snake.game.Snake;
import dev.therealdan.snake.main.SnakeApp;
import dev.therealdan.snake.main.scoreapi.Score;

public class GameScreen implements Screen, InputProcessor {

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

        app.scoreAPI.retrieveScores();
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

        if (!instance.gameover) {
            app.batch.begin();
            app.batch.setColor(app.color.getTheme().text);
            app.font.center(app.batch, "Score: " + instance.getScore(), 0, Gdx.graphics.getHeight() / 2f - 25, 16);
            float yOffset = 25;
            for (Score score : app.scoreAPI.getScores()) {
                app.font.draw(app.batch, score.Name + ": " + score.Score, -(Gdx.graphics.getWidth() / 2f) + 25, Gdx.graphics.getHeight() / 2f - yOffset, 16);
                yOffset += 25;
            }
            app.batch.end();
        } else {
            app.shapeRenderer.begin();
            app.shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            app.shapeRenderer.setColor(app.color.getTheme().interfaceBackground);
            app.shapeRenderer.rect(-200, -100, 400, 225);
            app.shapeRenderer.end();

            app.batch.begin();
            app.batch.setColor(app.color.getTheme().text);
            app.font.center(app.batch, "Game Over!", 0, 90, 32);
            app.font.center(app.batch, "Your Score: " + instance.getScore(), 0, 40, 24);
            app.font.draw(app.batch, "Name", -175, -20, 16);
            app.font.center(app.batch, app.name + (System.currentTimeMillis() % 1000 > 500 ? "|" : ""), 0, -20, 16);
            app.font.center(app.batch, "Submit", -100, -60, 16);
            app.font.center(app.batch, "Retry", 100, -60, 16);
            app.batch.end();
        }

        instance.loop(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        instance.resize(width, height);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
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

    @Override
    public boolean keyDown(int keycode) {
        if (Input.Keys.ESCAPE == keycode) {
            app.setScreen(new MainMenuScreen(app, instance.snake));
            dispose();
            return true;
        }

        if (!instance.gameover) {
            if (Input.Keys.E == keycode) {
                if (instance.activateSlowMotion()) app.sound.playSlowMotion();
                return true;
            }
        } else {
            String key = Input.Keys.toString(keycode);
            if ("ABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(key)) {
                if (app.name.length() < 16) app.name += key;
                return true;
            } else if (Input.Keys.BACKSPACE == keycode) {
                switch (app.name.length()) {
                    case 0:
                        break;
                    case 1:
                        app.name = "";
                        break;
                    default:
                        app.name = app.name.substring(0, app.name.length() - 1);
                        break;
                }
                return true;
            } else if (Input.Keys.ENTER == keycode) {
                app.scoreAPI.postScore(app.name, instance.getScore());
                app.setScreen(new MainMenuScreen(app, instance.snake));
                dispose();
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}