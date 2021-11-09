package com.stonksco.minitramways.logic.map;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.building.BuildingEnum;

public class Cell {

    private Vector2 pos = null;
    private BuildingEnum building;

    public Cell(Vector2 pos) {
        this.pos = pos;
    }

    public void setBuilding(BuildingEnum b) {
        this.building = b;
    }

    public BuildingEnum getBuilding() {
        return building;
    }
}
