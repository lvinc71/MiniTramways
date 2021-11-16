package com.stonksco.minitramways.views.items.lines;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.ColorEnum;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.layers.LinesView;
import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Représente une ligne et les trams associés
 */
public class LineView extends Group {

    private GameView gw;
    private LinesView layer;
    // Liste des trams, stockés avec leur identifiant (1..n)
    private HashMap<Integer,TramView> trams;

    private Color color;
    private int colorId;

    // Stocke les différents tronçons de la ligne.
    // Pour chaque tronçon, le vecteur associé correspond aux positions de début (x) et fin (y) dans la ligne
    private HashMap<Vector2, LinePartView> parts;

    public LineView(GameView gw, LinesView layer, int lineID) {
        super();
        this.gw = gw;
        this.layer = layer;
        parts = new HashMap<>();

        this.color = layer.getColorFor(lineID);
        this.colorId = layer.getColorId(lineID);

        Set<Map.Entry<Vector2,Vector2>> partsVectors = Game.get().getPartsVectorsOf(lineID);

        int pos = 0;
        for(Map.Entry v : partsVectors) {
            Vector2 posV = new Vector2(pos,pos+100);
            LinePartView lp = addPart(posV,(Vector2)v.getKey(),(Vector2)v.getValue());

            // Si ce LinePart est le linePart central
            if(posV.equals(new Vector2(0,100))) {
                addTram(lp,25);
            }

            pos+=100;
        }

        layer.getChildren().add(this);

    }

    public void addTram(LinePartView lp, int pos) {
        if(trams==null)
            trams = new HashMap<>();
        if(trams.isEmpty()) {
            TramView tv = new TramView(this,lp,pos,gw,colorId);
            trams.put(0,tv);
            this.getChildren().add(tv);
        }
    }

    private LinePartView addPart(Vector2 position, Vector2 from, Vector2 to) {
        LinePartView lp = new LinePartView(gw,this,from,to,color);
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

}
