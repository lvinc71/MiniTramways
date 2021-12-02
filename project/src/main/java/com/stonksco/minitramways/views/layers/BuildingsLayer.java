package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.buildings.BuildingEnum;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.layers.cells.BuildingView;
import com.stonksco.minitramways.views.layers.cells.CellView;

import java.util.ArrayList;
import java.util.HashMap;

public class BuildingsLayer extends GridView {

    private final HashMap<Vector2, BuildingView> buildings;


    public BuildingsLayer(GameView gw, Vector2 size) {
        super(gw, size);
        fill(CellView.class);
        buildings = new HashMap<>();
    }

    public void addBuildingAt(Vector2 at, BuildingEnum type) {
        BuildingView b  = new BuildingView(gw,at,type);
        this.getChildren().remove(this.getCellAt(at));
        buildings.put(at,b);
        this.add(b,(int)at.getX(),(int)at.getY());
    }

    /**
     * Appelée à chaque frame. Met à jour les bâtiments si leur nombre a changé.
     */
    public void updateBuildings(){
        HashMap<BuildingEnum, ArrayList<Vector2>> buildings = Game.get().getBuildings();

        // On calcule le nombre de bâtiments
        int buildingsCount = 0;
        for(ArrayList<Vector2> list : buildings.values()) {
            buildingsCount += list.size();
        }

        // Si de nouveaux bâtiments ont été ajoutés
        if(buildingsCount > this.buildings.size()) {

            Game.Debug(2,"VIEW : Buildings refreshed.");

            ArrayList<Vector2> pos;
            pos = buildings.get(BuildingEnum.HOUSE);
            if (pos != null) {
                for (int i = 0; i < pos.size(); i++) {
                    addBuildingAt(pos.get(i), BuildingEnum.HOUSE);
                }
            }
            pos = buildings.get(BuildingEnum.SHOP);
            if (pos != null) {
                for (int i = 0; i < pos.size(); i++) {
                    addBuildingAt(pos.get(i), BuildingEnum.SHOP);
                }
            }
            pos = buildings.get(BuildingEnum.OFFICE);
            if (pos != null) {
                for (int i = 0; i < pos.size(); i++) {
                    addBuildingAt(pos.get(i), BuildingEnum.OFFICE);
                }
            }
        }

    }

    /**
     * Met à jour les pins affichant le nombre de personnes dans chaque bâtiment
     */
    public void updateBuildingsPins() {
        for(Vector2 v : buildings.keySet()) {
            int nb = Game.get().getAmountOf(v);
            buildings.get(v).setAmount(nb);
        }
    }
}
