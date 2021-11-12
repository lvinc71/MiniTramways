package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.layers.cells.CellView;

public class BuildingsView extends GridView {

    public BuildingsView(GameView gw, Vector2 size) {
        super(gw, size);
        fill(CellView.class);
    }
}
