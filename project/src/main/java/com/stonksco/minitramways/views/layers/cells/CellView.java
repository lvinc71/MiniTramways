package com.stonksco.minitramways.views.layers.cells;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import javafx.scene.layout.StackPane;

/**
 * Gère l'affichage d'une cellule de la grille et de son contenu
 */
public class CellView extends StackPane {

    protected Vector2 gridPos;
    protected GameView gw;

    public CellView(GameView gw, Vector2 gridPos) {
        this.gw = gw;
        this.gridPos = gridPos;
    }

    public Vector2 getGridPos() {
        return gridPos.clone();
    }
}
