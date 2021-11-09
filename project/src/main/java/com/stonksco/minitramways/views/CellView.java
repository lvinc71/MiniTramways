package com.stonksco.minitramways.views;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.building.BuildingEnum;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class CellView extends StackPane {

    private Vector2 gridPos;

    public void setGridPos(Vector2 pos) {
        this.gridPos = pos;
    }

    public Vector2 getGridPos() {
        return gridPos;
    }
}
