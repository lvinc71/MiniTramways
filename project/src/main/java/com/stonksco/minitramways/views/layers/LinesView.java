package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.ColorEnum;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.items.lines.LineView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class LinesView extends Pane {

    private GameView gw;

    private HashMap<Integer,LineView> lines;

    public LinesView(GameView gw) {
        super();
        this.gw = gw;
        lines = new HashMap<>();
    }

    public boolean createLine(Vector2 start, Vector2 end) {
        this.lines.put(lines.size(),new LineView(gw,this,start,end));
        return true;
    }

    public Color getColor(){
        return gw.getColor(ColorEnum.values()[lines.size()%7]);
    }
}
