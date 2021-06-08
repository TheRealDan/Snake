package dev.therealdan.snake.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    public Sound consumeapple = Gdx.audio.newSound(Gdx.files.internal("sounds/consumeapple.wav"));
    public Sound gameover = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover.wav"));
    public Sound worldloop = Gdx.audio.newSound(Gdx.files.internal("sounds/worldloop.wav"));
}