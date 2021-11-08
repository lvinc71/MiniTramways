package com.stonksco.minitramways.views;

import com.stonksco.minitramways.logic.map.building.BuildingEnum;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class CellView extends StackPane {

    private BuildingEnum building = null;

    public void setBuilding(BuildingEnum b) {
        if(building==null) {

        }

        this.building = b;
    }

    public BuildingEnum getBuilding() {
        return this.building;
    }

    /**
     * Retourne l'image image correspondant au tybe de bâtiment donné
     * @param b
     */
    private Image getBuildingImage(BuildingEnum b) {
        return null;
    }


}
