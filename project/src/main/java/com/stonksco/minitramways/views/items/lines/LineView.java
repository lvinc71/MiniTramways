package com.stonksco.minitramways.views.items.lines;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.LinePart;
import com.stonksco.minitramways.logic.map.Tramway;
import com.stonksco.minitramways.views.Clock;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.layers.LinesLayer;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.util.*;

/**
 * Représente une ligne et les trams associés
 */
public class LineView extends Group {

    private GameView gw;
    private LinesLayer layer;
    // Liste des trams, stockés avec leur identifiant (1..n)
    private HashMap<Integer,TramView> trams;

    private Color color;
    private int colorId;
    private int lineID;

    // Stocke les différents tronçons de la ligne.
    // Pour chaque tronçon, le vecteur associé correspond aux positions de début (x) et fin (y) dans la ligne
    private HashMap<Vector2, LinePartView> parts;

    public LineView(GameView gw, LinesLayer layer, int lineID) {
        super();
        this.gw = gw;
        this.layer = layer;
        parts = new HashMap<>();
        this.lineID = lineID;

        this.color = layer.getColorFor(lineID);
        this.colorId = layer.getColorId(lineID);

        List<Map.Entry<Vector2,Vector2>> partsVectors = Game.get().getPartsVectorsOf(lineID);


        int pos = Game.get().getFirstIndexOf(lineID);
        for(Map.Entry v : partsVectors) {

            Vector2 posV = new Vector2(pos,pos+100);
            LinePartView lp = addPart(posV,(Vector2)v.getKey(),(Vector2)v.getValue());
            Game.Debug(2,"VIEW : Drawn line part "+(Vector2)v.getKey()+"-----"+(Vector2)v.getValue()+"  at indexes "+posV);
            pos+=100;
        }

        layer.getChildren().add(this);

    }

    private void addTram(double at) {
        if(trams==null)
            trams = new HashMap<>();

        TramView tv = new TramView(this,at,gw,colorId);
        trams.put(trams.size(),tv);
        this.getChildren().add(tv);

    }

    private LinePartView addPart(Vector2 position, Vector2 from, Vector2 to) {
        LinePartView lp = new LinePartView(gw,this,from,to,(int)position.getX(),(int)position.getY(),color);
        this.parts.put(position,lp);
        return lp;
    }

    public void remove() {
        for(LinePartView lp : parts.values()) {
            lp.getChildren().clear();
        }
        this.getChildren().clear();
        this.parts.clear();
    }

    public ReadOnlyDoubleProperty getPosXAt(double at) {
        return getPartAt(at).getPosXAt(at);
    }

    public ReadOnlyDoubleProperty getPoxYAt(double at) {
        return getPartAt(at).getPosYAt(at);
    }

    public LinePartView getPartAt(double at) {
        LinePartView res = null;
        for(Map.Entry e : parts.entrySet()) {
            if(((Vector2)e.getKey()).getX() <=at && ((Vector2)e.getKey()).getY()>at) {
                res=(LinePartView)e.getValue();
                break;
            }
        }
        return res;
    }

    private double timeSinceLastTramUpdate = 0;

    public void UpdateTrams() {
        if(timeSinceLastTramUpdate > 0.04) {
            if (trams == null)
                trams = new HashMap<>();

            for (TramView tv : trams.values()) {
                this.getChildren().remove(tv);
            }
            this.trams.clear();

            for (Tramway t : Game.get().getTramsOf(this.lineID)) {
                addTram(t.getLinePos());
            }
            timeSinceLastTramUpdate = 0;
        } else {
            timeSinceLastTramUpdate+= Clock.get().GameDeltaTime();
        }



    }




}
