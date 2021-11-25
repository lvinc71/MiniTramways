package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.layers.cells.CellView;
import com.stonksco.minitramways.views.layers.cells.StationView;

import java.util.HashMap;

public class StationsLayer extends GridView{

    private HashMap<Vector2, StationView> stations;

    public StationsLayer(GameView gw, Vector2 size) {
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
        StationView s = new StationView(gw,at);
        s.enable();
        stations.remove(this.getCellAt(at));
        stations.put(at,s);
        this.add(s,(int)at.getX(),(int)at.getY());
        System.out.println("STATIONS LIST : "+stations);
    }

    public void updateStations() {
        this.stations.clear();
        this.getChildren().clear();
        this.getColumnConstraints().clear();
        this.getRowConstraints().clear();
        this.fill(CellView.class);
        for(Vector2 v : Game.get().getStations()) {
            addStationAt(v.round());
        }
    }

    public void showRadiusOf(Vector2 cell) {
        StationView sv = stations.get(cell);
        if(sv != null)
            sv.showRadius();
        System.out.println("Temp : STATION VIEW  = "+sv+cell);
    }

    public void hideRadiusOf(Vector2 cell) {
        StationView sv = stations.get(cell);
        if(sv != null)
            sv.hideRadius();
    }

}
