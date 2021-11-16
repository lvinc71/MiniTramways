package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.ColorEnum;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.items.lines.LineView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LinesView extends Pane {

    private GameView gw;

    private HashMap<Integer,LineView> lines;

    public LinesView(GameView gw) {
        super();
        this.gw = gw;
        lines = new HashMap<>();
    }


    public Color getColorFor(int id){
        return gw.getColor(ColorEnum.values()[id]);
    }
    public int getColorId(int id) {
        return id%8;
    }

    public boolean addLine(int lineID) {
        boolean res = false;
        Set<Map.Entry<Vector2,Vector2>> parts = Game.get().getPartsVectorsOf(lineID);
        if(parts.size()>0) {
            this.lines.put(lineID,new LineView(gw,this,lineID));
            res=true;
        } else {
            Game.Debug(1,"VIEW : Error when retrieving line parts of line "+lineID+" from Game !");
        }
        return res;
    }

    public void removeLine(int lineID) {
        if(lines.get(lineID)!=null) {
            lines.get(lineID).remove();
            lines.remove(lineID);
        }
    }
}
