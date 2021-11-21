package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.Game;
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

        this.addArea(AreaTypes.residential);
        this.addArea(AreaTypes.office);
        this.addArea(AreaTypes.shopping);

        if(Game.get().getDebug()>2)
            this.setBorder(new Border(new BorderStroke(Color.PURPLE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    public void addArea(AreaTypes type){
        area = new AreaView(gw,type,this);
        this.getChildren().add(area);
        areas.add(area);
    }

}
