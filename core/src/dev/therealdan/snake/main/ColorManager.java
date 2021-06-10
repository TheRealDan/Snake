package dev.therealdan.snake.main;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;

public class ColorManager {

    private ColorTheme lightTheme;
    private ColorTheme darkTheme;

    private boolean useDarkTheme;

    public ColorManager(Preferences preferences) {
        lightTheme = new ColorTheme(
                new Color(0.14509805f, 0.49411765f, 0.5019608f, 1),
                new Color(0.05882353f, 0.44313726f, 0.4509804f, 1),
                new Color(0.73333335f, 0.78431374f, 0.7921569f, 1),
                Color.BLACK
        );

        darkTheme = new ColorTheme(
                new Color(0.11725491f, 0.1564706f, 0.18784314f, 1),
                new Color(0.050980393f, 0.09411765f, 0.12941177f, 1),
                new Color(0.73333335f, 0.78431374f, 0.7921569f, 1),
                Color.BLACK
        );

        this.useDarkTheme = preferences.getBoolean("UseDarkTheme", false);
    }

    public void switchTheme() {
        useDarkTheme = !useDarkTheme;
    }

    public void useLightTheme() {
        useDarkTheme = false;
    }

    public void useDarkTheme() {
        useDarkTheme = true;
    }

    public boolean isDarkTheme() {
        return useDarkTheme;
    }

    public ColorTheme getTheme() {
        return useDarkTheme ? darkTheme : lightTheme;
    }
}