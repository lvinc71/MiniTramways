package com.stonksco.minitramways.views.layers.cells;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.ColorEnum;
import com.stonksco.minitramways.views.GameView;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Ellipse;

public class GridDisplayCell extends CellView{

    private Ellipse e;

    public GridDisplayCell(GameView gw, Vector2 gridPos) {
        super(gw,gridPos);

        int i = (int)gridPos.getY();
        int j = (int)gridPos.getX();

        if(i==0 || j==0) {
            e = new Ellipse(0,0,3,3);
            e.setTranslateX(-3);
            e.setTranslateY(-3);
            e.setFill(gw.getColor(ColorEnum.GRID_DOT));
            StackPane.setAlignment(e, Pos.TOP_LEFT);
            this.getChildren().add(e);
        }

            e = new Ellipse(0,0,3,3);
            e.setTranslateX(3);
            e.setTranslateY(3);
            e.setFill(gw.getColor(ColorEnum.GRID_DOT));
            StackPane.setAlignment(e, Pos.BOTTOM_RIGHT);
            this.getChildren().add(e);

    }

    public ReadOnlyDoubleProperty getPixelsX() {
        SimpleDoubleProperty res = new SimpleDoubleProperty(0);
        res.bind(this.layoutXProperty().add(gw.getCellSizeX().divide(2)));

        return res;

    }
    public ReadOnlyDoubleProperty getPixelsY() {
        SimpleDoubleProperty res = new SimpleDoubleProperty(0);
        res.bind(this.layoutYProperty().add(gw.getCellSizeY().divide(2)));

        return res;

    }
}
