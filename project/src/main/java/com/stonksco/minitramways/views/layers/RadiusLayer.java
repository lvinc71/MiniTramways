package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.ColorEnum;
import com.stonksco.minitramways.views.GameView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.HashMap;

public class RadiusLayer extends Pane {

    private final GameView gw;

    private final HashMap<Vector2,Circle> circles;

    public RadiusLayer(GameView gw) {
        super();
        this.gw = gw;
        circles = new HashMap<>();
    }

    public void showRadiusAt(Vector2 at) {
        if(circles.get(at) == null) {
            Circle c = new Circle();
            c.setRadius(3.5d*gw.getCellSizeX().get());
            c.translateXProperty().bind(gw.CellToPixelsX(at));
            c.translateYProperty().bind(gw.CellToPixelsY(at));

            c.setFill(gw.getColor(ColorEnum.GRID_DOT));
            this.getChildren().add(c);
            circles.put(at,c);
        }
    }

    public void hideRadiusAt(Vector2 at) {
        Circle c = circles.get(at);
        if(c!=null) {
            this.getChildren().remove(c);
            circles.remove(at,c);
        }
    }





}
