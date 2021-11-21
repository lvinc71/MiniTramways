package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.map.Area;
import com.stonksco.minitramways.logic.map.AreaTypes;
import com.stonksco.minitramways.logic.map.building.Shop;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.items.areas.AreaView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class AreasLayer extends Pane {

    private GameView gw;

    private ArrayList<AreaView> areas;

    private AreaView area;

    public AreasLayer(GameView gw) {
        super();
        this.gw = gw;
        areas = new ArrayList<AreaView>();

        for(Area a : Game.get().getAreas().values()) {
            addArea(a);
        }

        if(Game.get().getDebug()>2)
            this.setBorder(new Border(new BorderStroke(Color.PURPLE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


    }

    public void addArea(Area a){
        area = new AreaView(gw,this,a);
        this.getChildren().add(area);
        areas.add(area);
    }

}
