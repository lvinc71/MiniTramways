package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.map.AreaTypes;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.items.areas.AreaView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;

public class AreasView extends Pane {

    private GameView gw;

    private ArrayList<AreaView> areas;

    public AreasView(GameView gw) {
        super();
        this.gw = gw;

    }
}
