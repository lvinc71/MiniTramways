package com.stonksco.minitramways.views.ui.elements.selections;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.ColorEnum;
import com.stonksco.minitramways.views.GameView;
import eu.hansolo.tilesfx.tools.GradientLookup;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CellSelection extends Group {

    private final GameView gw;
    private final Vector2 cellPos;

    public CellSelection(GameView gw, Vector2 pos) {
        this.gw = gw;
        cellPos=pos;

        show();
    }

    public void show() {
        Circle c = new Circle();
        c.radiusProperty().bind(gw.getCellSizeX().multiply(0.6d));
        c.setFill(gw.getColor(ColorEnum.CELL_SELECTION));
        c.centerYProperty().bind(gw.CellToPixelsY(cellPos).add(gw.gridPosY()));
        c.centerXProperty().bind(gw.CellToPixelsX(cellPos).add(gw.gridPosX()));
        this.getChildren().add(c);
    }

    public void hide() {
        this.getChildren().clear();
    }





}
