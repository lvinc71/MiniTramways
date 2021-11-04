package com.stonksco.minitramways.control;

import com.stonksco.minitramways.control.interfaces.Controler;
import com.stonksco.minitramways.logic.map.Map;
import com.stonksco.minitramways.views.GameView;

public class MapController implements Controler {

    private Map map;
    private GameView gameview;

    public MapController(Map map, GameView view) {
        this.map = map;
        this.gameview = view;
    }

    @Override
    public void Warn() {

    }
}
