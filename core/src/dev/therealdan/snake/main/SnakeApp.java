package dev.therealdan.snake.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.therealdan.snake.game.Snake;
import dev.therealdan.snake.main.scoreapi.ScoreAPI;
import dev.therealdan.snake.screens.MainMenuScreen;

public class SnakeApp extends Game {

    public Preferences preferences;
    public ScoreAPI scoreAPI;
    public FontManager font;
    public SoundManager sound;
    public ColorManager color;

    public ShapeRenderer shapeRenderer;
    public SpriteBatch batch;

    public String username;

    @Override
    public void create() {
        preferences = Gdx.app.getPreferences("snake");
        scoreAPI = new ScoreAPI(preferences);
        font = new FontManager();
        sound = new SoundManager();
        color = new ColorManager(preferences);

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        username = preferences.getString("Username", "");

        setScreen(new MainMenuScreen(this, new Snake(0, 165, 6)));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        font.dispose();
        sound.dispose();

        shapeRenderer.dispose();
        batch.dispose();

        preferences.putString("Username", username);
        preferences.putBoolean("UseDarkTheme", color.isDarkTheme());
        preferences.flush();
    }

    @Override
    public void resize(int width, int height) {
        getScreen().resize(width, height);
    }
}
