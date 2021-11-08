package com.stonksco.minitramways.views;

import com.stonksco.minitramways.logic.map.building.BuildingEnum;
import javafx.scene.layout.StackPane;

public class CellView extends StackPane {

    private BuildingEnum building = null;

    public void setBuilding(BuildingEnum b) {
        this.building = b;
    }

    public BuildingEnum getBuilding() {
        return this.building;
    }

    

}
