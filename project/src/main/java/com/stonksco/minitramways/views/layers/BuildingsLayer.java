package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.building.BuildingEnum;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.layers.cells.BuildingView;
import com.stonksco.minitramways.views.layers.cells.CellView;

import java.util.HashMap;

public class BuildingsLayer extends GridView {

    private HashMap<Vector2, BuildingView> Building;


    public BuildingsLayer(GameView gw, Vector2 size) {
        super(gw, size);
        fill(CellView.class);
        Building = new HashMap<>();
    }

    public void addBuildingAt(Vector2 at, BuildingEnum type) {
        BuildingView b  = new BuildingView(gw,at,type);
        this.getChildren().remove(this.getCellAt(at));
        Building.put(at,b);
        this.add(b,(int)at.getX(),(int)at.getY());
    }
}
