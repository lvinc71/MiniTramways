package com.stonksco.minitramways.views.items.areas;

import com.stonksco.minitramways.logic.map.AreaTypes;
import com.stonksco.minitramways.views.GameView;
import javafx.scene.Group;
import javafx.scene.shape.SVGPath;

public class AreaView extends Group {

    private GameView gw;

    private AreaTypes type;

    private SVGPath path;

    public AreaView(GameView gw, AreaTypes type) {
        super();
        this.gw = gw;
        this.type = type;
    }
}
