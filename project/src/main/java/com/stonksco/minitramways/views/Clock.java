package com.stonksco.minitramways.views;

import com.stonksco.minitramways.logic.Game;
import javafx.animation.AnimationTimer;

/**
 * Gère le déroulement du temps du jeu
 */
public class Clock extends AnimationTimer {

    private final float gameTimeSpeedFactor = 10;
    private long lastUpdate = 0;
    private long now = 0;
    private long elapsed = 0;
    private long gameElapsed = 0;

    private Clock() {

    }

    private static final Clock instance = new Clock();

    public static Clock get() {
        return instance;
    }

    @Override
    public void handle(long l) {
        now = l;
        Game.get().Update();
        GameView.FrameUpdate();
        lastUpdate = now;
        elapsed += DeltaTime();
        gameElapsed += GameDeltaTimeNs();
    }

    public long DeltaTime() {
        return now-lastUpdate;
    }

    /**
     * Retourne le delta in-game en secondes
     * @return
     */
    public double GameDeltaTime() {
        return (double)GameDeltaTimeNs()/Math.pow(10,9);
    }

    /**
     * Retourne le delta in-game en nanosecondes
     * @return
     */
    public long GameDeltaTimeNs() {
        return (long)((now-lastUpdate)*gameTimeSpeedFactor);
    }

    /**
     * Retourne le temps in-game écoulé depuis le début de la partie en ns
     * @return
     */
    public long GameElapsed() {
        return gameElapsed;
    }

}
