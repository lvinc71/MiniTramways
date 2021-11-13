package com.stonksco.minitramways.views.items.lines;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.layers.LinesView;
import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.util.HashMap;

/**
 * Représente une ligne et les trams associés
 */
public class LineView extends Group {

    private GameView gw;
    private LinesView layer;
    // Liste des trams, stockés avec leur identifiant (1..n)
    private HashMap<Integer,TramView> trams;


    private Color c;

    // Stocke les différents tronçons de la ligne.
    // Pour chaque tronçon, le vecteur associé correspond aux positions de début (x) et fin (y) dans la ligne
    private HashMap<Vector2, LinePart> parts;

    public LineView(GameView gw, LinesView layer, Vector2 start, Vector2 end, Color c) {
        super();
        this.gw = gw;
        this.layer = layer;
        parts = new HashMap<>();
        trams = new HashMap<>();
        parts.put(new Vector2(0,100), new LinePart(gw,layer,start,end,c));
        gw.addStationAt(start);
        gw.addStationAt(end);
        addTram();
        layer.getChildren().add(this);
    }


    public void addTram() {
        if(trams.isEmpty()) {
            TramView tv = new TramView(this,parts.get(new Vector2(0,100)),gw);
            trams.put(0,tv);
            this.getChildren().add(tv);
        }

    }

}
