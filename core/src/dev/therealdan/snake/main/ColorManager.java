package dev.therealdan.snake.main;

import com.badlogic.gdx.graphics.Color;

public class ColorManager {

    private ColorTheme lightTheme;
    private ColorTheme darkTheme;

    private boolean useDark = true;

    public ColorManager() {
        lightTheme = new ColorTheme(new Color(0.14509805f, 0.49411765f, 0.5019608f, 1), new Color(0.05882353f, 0.44313726f, 0.4509804f, 1), Color.WHITE);
        darkTheme = new ColorTheme(new Color(0.11725491f, 0.1564706f, 0.18784314f, 1), new Color(0.050980393f, 0.09411765f, 0.12941177f, 1), Color.WHITE);
    }

    public void switchTheme() {
        useDark = !useDark;
    }

    public void useLightTheme() {
        useDark = false;
    }

    public void useDarkTheme() {
        useDark = true;
    }

    public boolean isDarkTheme() {
        return useDark;
    }

    public ColorTheme getTheme() {
        return useDark ? darkTheme : lightTheme;
    }
}