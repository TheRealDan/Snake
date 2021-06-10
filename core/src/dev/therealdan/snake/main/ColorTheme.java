package dev.therealdan.snake.main;

import com.badlogic.gdx.graphics.Color;

public class ColorTheme {

    public Color light;
    public Color dark;
    public Color text;
    public Color interfaceBackground;
    public Color interfaceOutline;

    public ColorTheme(Color light, Color dark, Color text, Color interfaceBackground, Color interfaceOutline) {
        this.light = light;
        this.dark = dark;
        this.text = text;
        this.interfaceBackground = interfaceBackground;
        this.interfaceOutline = interfaceOutline;
    }
}