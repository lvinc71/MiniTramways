package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.layers.cells.CellView;
import com.stonksco.minitramways.views.layers.cells.StationView;

import java.util.HashMap;

public class StationsView extends GridView{

    private HashMap<Vector2, StationView> stations;

    public StationsView(GameView gw, Vector2 size) {
        super(gw, size);
        stations = new HashMap<>();
        fill(CellView.class);

        if(Game.get().getDebug()>2)
            this.setGridLinesVisible(true);

    }

    /**
     * Ajoute une station aux coordonnées passées en paramètres
     * @param at
     */
    public void addStationAt(Vector2 at) {
        CellView cell = gw.getGridDisplay().getCellAt(at);
        stations.remove(this.getCellAt(at));
        StationView s = new StationView(gw,at);
        cell.getChildren().add(s);
        stations.put(at,s);
        this.add(s,(int)at.getX(),(int)at.getY());

    }

    public void updateStations() {
        this.stations.clear();
        this.getChildren().clear();
        this.getColumnConstraints().clear();
        this.getRowConstraints().clear();
        this.fill(CellView.class);
        for(Vector2 v : Game.get().getStations()) {
            addStationAt(v);
        }
    }

}
