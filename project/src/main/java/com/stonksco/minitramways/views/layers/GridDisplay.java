package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.layers.cells.GridDisplayCell;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Ellipse;

public class GridDisplay extends GridView {



    public GridDisplay(GameView gw, Vector2 size) {
        super(gw,size);

        fill(GridDisplayCell.class);



    }




}
