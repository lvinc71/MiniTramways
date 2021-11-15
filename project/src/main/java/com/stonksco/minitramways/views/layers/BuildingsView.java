package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.layers.cells.BuildingView;
import com.stonksco.minitramways.views.layers.cells.CellView;

import java.util.HashMap;

public class BuildingsView extends GridView {

    private HashMap<Vector2, BuildingView> Building;

    public BuildingsView(GameView gw, Vector2 size) {
        super(gw, size);
        fill(CellView.class);
        Building = new HashMap<>();
    }

    public void addBuildingsAt(Vector2 at) {
        BuildingView b  = new BuildingView(gw,at);
        this.getChildren().remove(this.getCellAt(at));
        Building.put(at,b);
        this.add(b,(int)at.getX(),(int)at.getY());
    }
}
