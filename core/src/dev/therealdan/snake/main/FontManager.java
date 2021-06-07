package dev.therealdan.snake.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;

public class FontManager implements Disposable {

    private FreeTypeFontGenerator freeTypeFontGenerator;

    private HashMap<Integer, BitmapFont> fonts = new HashMap<>();

    public FontManager() {
        freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Rubik-Medium.ttf"));
    }

    public void draw(SpriteBatch batch, String text, float x, float y) {
        draw(batch, text, x, y, 16);
    }

    public void draw(SpriteBatch batch, String text, float x, float y, int fontSize) {
        if (!fonts.containsKey(fontSize)) generateFont(fontSize);
        fonts.get(fontSize).draw(batch, text, x, y);
    }

    @Override
    public void dispose() {
        freeTypeFontGenerator.dispose();
    }

    private void generateFont(int fontSize) {
        FreeTypeFontGenerator.FreeTypeFontParameter freeTypeFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        freeTypeFontParameter.size = fontSize;
        fonts.put(fontSize, freeTypeFontGenerator.generateFont(freeTypeFontParameter));
    }
}