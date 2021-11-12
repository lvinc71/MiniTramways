package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.map.AreaTypes;
import com.stonksco.minitramways.logic.map.building.Shop;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.items.areas.AreaView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;

public class AreasView extends Pane {

    private GameView gw;

    private ArrayList<AreaView> areas;

    private AreaView area;

    public AreasView(GameView gw) {
        super();
        this.gw = gw;
        areas = new ArrayList<AreaView>();
    }

    public void AddArea(){
        area = new AreaView(gw,AreaTypes.Work);
        this.getChildren().add(area);
        areas.add(area);
    }

}
