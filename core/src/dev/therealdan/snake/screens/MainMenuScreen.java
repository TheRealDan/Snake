package dev.therealdan.snake.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.therealdan.snake.game.Apple;
import dev.therealdan.snake.game.Snake;
import dev.therealdan.snake.main.SnakeApp;

public class MainMenuScreen implements Screen {

    final SnakeApp app;

    private ScreenViewport viewport;
    private OrthographicCamera camera;

    private Rectangle darkModeButton;
    private Texture lightningWhite, lightningBlack;
    private Rectangle volumeButton;
    private Texture speakerBlack, speakerWhite, muteBlack, muteWhite;

    private Snake snake;
    private Apple apple;

    public MainMenuScreen(final SnakeApp app, Snake snake) {
        this.app = app;

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);

        volumeButton = new Rectangle(0, 0, 35, 35);
        speakerBlack = new Texture("icons/speaker-black.png");
        speakerWhite = new Texture("icons/speaker-white.png");

        darkModeButton = new Rectangle(0, 0, 35, 35);
        lightningWhite = new Texture("icons/lightning-white.png");
        lightningBlack = new Texture("icons/lightning-black.png");
        muteBlack = new Texture("icons/mute-black.png");
        muteWhite = new Texture("icons/mute-white.png");

        snake.setLength(6);
        this.snake = snake;
        apple = new Apple(0, 0);

        if (app.username.length() > 0) app.scoreAPI.retrieveScores();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(app.color.getTheme().dark.cpy());

        camera.update();
        app.shapeRenderer.setProjectionMatrix(camera.combined);
        app.batch.setProjectionMatrix(camera.combined);

        darkModeButton.setPosition(-(Gdx.graphics.getWidth() / 2f), Gdx.graphics.getHeight() / 2f - volumeButton.height);
        volumeButton.setPosition(Gdx.graphics.getWidth() / 2f - volumeButton.width, Gdx.graphics.getHeight() / 2f - volumeButton.height);

        app.shapeRenderer.setAutoShapeType(true);
        app.shapeRenderer.begin();
        snake.render(app.shapeRenderer, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), apple);
        apple.render(app.shapeRenderer);
        app.shapeRenderer.end();

        app.batch.begin();
        app.batch.draw(app.color.isDarkTheme() ? lightningWhite : lightningBlack, darkModeButton.x, darkModeButton.y, darkModeButton.width, darkModeButton.height);
        app.batch.draw(app.color.isDarkTheme() ? app.sound.isMuted() ? muteWhite : speakerWhite : app.sound.isMuted() ? muteBlack : speakerBlack, volumeButton.x, volumeButton.y, volumeButton.width, volumeButton.height);
        app.batch.setColor(app.color.getTheme().text);
        app.font.center(app.batch, "Use WASD to move", 0, 210, 16);
        app.font.center(app.batch, "You can use the screen's edges to loop around", 0, 150, 16);
        app.font.center(app.batch, "Eat apples to grow longer", 0, 60, 16);
        app.font.center(app.batch, "Any part of the Snake can collect Apples", 0, 20, 16);
        app.font.center(app.batch, "Don't crash into your body", 0, -70, 16);
        app.font.center(app.batch, "Tap E to slow down time", 0, -110, 16);
        app.font.center(app.batch, "Press the Spacebar to Start", 0, -190, 16);
        app.batch.end();

        snake.handleMovement(delta);
        if (snake.handleWorldLooping(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())) app.sound.playWorldLoop();
        snake.handleConnectedBody(delta);

        apple.teleport(130, 51);

        if (snake.getHead().y > 175) snake.getHead().y--;
        if (snake.getHead().y < 175) snake.getHead().y++;
        if (snake.yVelocity > 0) snake.yVelocity--;
        if (snake.yVelocity < 0) snake.yVelocity++;

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            app.setScreen(new GameScreen(app, snake));
            dispose();
        } else if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector3 position = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(position);
            if (darkModeButton.contains(position.x, position.y)) {
                app.sound.playConsumeApple();
                app.color.switchTheme();
            } else if (volumeButton.contains(position.x, position.y)) {
                app.sound.changeVolume();
                app.sound.playConsumeApple();
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
