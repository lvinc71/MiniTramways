package com.stonksco.minitramways.views.layers.cells;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import javafx.scene.image.ImageView;

/**
 * Représente l'affichage d'un bâtiment
 */
public class BuildingView extends CellView {

    private ImageView sprite;

    private int people = 0;

    public BuildingView(GameView gw, Vector2 gridPos) {
        super(gw,gridPos);
    }
}