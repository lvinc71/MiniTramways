package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.ColorEnum;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.items.lines.LineView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LinesLayer extends Pane {

    private GameView gw;

    private HashMap<Integer,LineView> lines;

    public LinesLayer(GameView gw) {
        super();
        this.gw = gw;
        lines = new HashMap<>();
    }


    public Color getColorFor(int id){
        Color c = gw.getColor(ColorEnum.values()[getColorId(id)]);
        return c;
    }
    public int getColorId(int id) {
        int res = id%8;
        Game.Debug(3,"Found color "+res+" for line "+id);
        return res;
    }

    public boolean addLine(int lineID) {
        boolean res = false;
        List<Map.Entry<Vector2,Vector2>> parts = Game.get().getPartsVectorsOf(lineID);
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

    /**
     * Appelée  à chaque frame
     */
    public void Update() {
        for(LineView l : lines.values()) {
            l.UpdateTrams();
        }
    }

}
