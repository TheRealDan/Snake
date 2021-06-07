package dev.therealdan.snake.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.therealdan.snake.screens.MainMenuScreen;

public class SnakeGame extends Game {

    public ShapeRenderer shapeRenderer;
    public SpriteBatch batch;
    public FontManager font;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new FontManager();

        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
    }

    @Override
    public void resize(int width, int height) {
        getScreen().resize(width, height);
    }
}
