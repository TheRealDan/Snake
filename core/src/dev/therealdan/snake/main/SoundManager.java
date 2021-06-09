package dev.therealdan.snake.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    private Sound consumeapple;
    private Sound gameover;
    private Sound worldloop;

    private float volume;

    public SoundManager() {
        consumeapple = Gdx.audio.newSound(Gdx.files.internal("sounds/consumeapple.wav"));
        gameover = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover.wav"));
        worldloop = Gdx.audio.newSound(Gdx.files.internal("sounds/worldloop.wav"));

        volume = 1;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getVolume() {
        return volume;
    }

    public void playConsumeApple() {
        consumeapple.play(volume);
    }

    public void playWorldLoop() {
        worldloop.play(volume);
    }

    public void playGameOver() {
        gameover.play(volume);
    }
}