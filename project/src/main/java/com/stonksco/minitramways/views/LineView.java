package com.stonksco.minitramways.views;

import com.stonksco.minitramways.logic.Vector2;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.HashMap;

public class LineView extends Group {

    private GameView gw;


    // Stocke les différents tronçons de la ligne.
    // Pour chaque tronçon, le vecteur associé correspond aux positions de début (x) et fin (y) dans la ligne
    private HashMap<Vector2, LinePart> parts;

    public LineView(GameView gw, Vector2 start, Vector2 end) {
        super();
        this.gw = gw;
        parts = new HashMap<>();
        parts.put(new Vector2(0,100), new LinePart(gw,gw.GetCellAt(start),gw.GetCellAt(end)));
    }

}
