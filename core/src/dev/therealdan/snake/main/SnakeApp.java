package dev.therealdan.snake.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.therealdan.snake.game.Snake;
import dev.therealdan.snake.main.scoreapi.ScoreAPI;
import dev.therealdan.snake.screens.MainMenuScreen;

public class SnakeApp extends Game {

    public ShapeRenderer shapeRenderer;
    public SpriteBatch batch;
    public FontManager font;
    public SoundManager sound;
    public ColorManager color;
    public ScoreAPI scoreAPI;

    public String name = "";

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new FontManager();
        sound = new SoundManager();
        color = new ColorManager();
        scoreAPI = new ScoreAPI();

        setScreen(new MainMenuScreen(this, new Snake(Color.GREEN, 0, 165, 6)));
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
