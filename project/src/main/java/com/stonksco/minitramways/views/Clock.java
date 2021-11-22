package com.stonksco.minitramways.views;

import com.stonksco.minitramways.logic.Game;
import javafx.animation.AnimationTimer;

/**
 * Gère le déroulement du temps du jeu
 */
public class Clock extends AnimationTimer {

    private float gameTimeSpeedFactor = 1;
    private long lastUpdate = 0;
    private long now = 0;

    private Clock() {

    }

    private static Clock instance = new Clock();

    public static Clock get() {
        return instance;
    }

    @Override
    public void handle(long l) {
        now = l;
        Game.get().Update();
        GameView.FrameUpdate();
        lastUpdate = now;
    }

    public long DeltaTime() {
        return now-lastUpdate;
    }

    /**
     * Retourne le delta en secondes
     * @return
     */
    public double GameDeltaTime() {
        return (double)GameDeltaTimeNs()/Math.pow(10,9);
    }

    /**
     * Retourne le delta en nanosecondes
     * @return
     */
    public long GameDeltaTimeNs() {
        return (long)((now-lastUpdate)*gameTimeSpeedFactor);
    }

}
