package dev.therealdan.snake.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

public class SoundManager implements Disposable {

    private Sound consumeapple;
    private Sound gameover;
    private Sound slowmotion;
    private Sound worldloop;

    private float volume;

    public SoundManager() {
        consumeapple = Gdx.audio.newSound(Gdx.files.internal("sounds/consumeapple.wav"));
        gameover = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover.wav"));
        slowmotion = Gdx.audio.newSound(Gdx.files.internal("sounds/slowmotion.wav"));
        worldloop = Gdx.audio.newSound(Gdx.files.internal("sounds/worldloop.wav"));

        volume = 1;
    }

    public void changeVolume() {
        if (getVolume() >= 0.6f) {
            setVolume(0.4f);
        } else if (getVolume() >= 0.4f) {
            setVolume(0.25f);
        } else if (getVolume() >= 0.25f) {
            setVolume(0.1f);
        } else if (getVolume() >= 0.1f) {
            setVolume(0f);
        } else {
            setVolume(0.6f);
        }
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getVolume() {
        return volume;
    }

    public boolean isMuted() {
        return getVolume() == 0f;
    }

    public void playConsumeApple() {
        consumeapple.play(volume * 0.7f);
    }

    public void playWorldLoop() {
        worldloop.play(volume * 0.5f);
    }

    public void playSlowMotion() {
        slowmotion.play(volume * 1.35f);
    }

    public void playGameOver() {
        gameover.play(volume);
    }

    @Override
    public void dispose() {
        consumeapple.dispose();
        gameover.dispose();
        slowmotion.dispose();
        worldloop.dispose();
    }
}