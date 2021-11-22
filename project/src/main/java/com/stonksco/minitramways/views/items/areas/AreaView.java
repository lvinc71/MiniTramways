package com.stonksco.minitramways.views.items.areas;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.Area;
import com.stonksco.minitramways.logic.map.AreaTypes;
import com.stonksco.minitramways.views.ColorEnum;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.layers.AreasLayer;
import javafx.scene.layout.*;
import javafx.scene.shape.*;

import java.util.ArrayList;

public class AreaView extends Pane {

    private GameView gw;
    private AreaTypes type;

    private Path areaShape;

    public AreaView(GameView gw, AreasLayer layer, Area a) {

        super();
        this.gw = gw;
        this.type = a.getType();

        areaShape = new Path();


        ArrayList<Vector2> edgeCells = new ArrayList<Vector2>();

        switch(type) {
            case office:
                areaShape.setFill(gw.getColor(ColorEnum.OFFICE_BACKGROUND));
                areaShape.setStroke(gw.getColor(ColorEnum.OFFICE_BORDER));


                break;
            case residential:
                areaShape.setFill(gw.getColor(ColorEnum.RESIDENTIAL_BACKGROUND));
                areaShape.setStroke(gw.getColor(ColorEnum.RESIDENTIAL_BORDER));

                edgeCells.add(new Vector2(0,5));
                edgeCells.add(new Vector2(1,5));
                edgeCells.add(new Vector2(2,5));
                edgeCells.add(new Vector2(3,6));
                edgeCells.add(new Vector2(4,6));
                edgeCells.add(new Vector2(5,6));
                edgeCells.add(new Vector2(6,7));
                edgeCells.add(new Vector2(6,8));
                edgeCells.add(new Vector2(6,9));
                edgeCells.add(new Vector2(6,10));
                edgeCells.add(new Vector2(5,11));
                edgeCells.add(new Vector2(5,12));
                edgeCells.add(new Vector2(5,13));
                edgeCells.add(new Vector2(5,14));
                edgeCells.add(new Vector2(5,15));
                edgeCells.add(new Vector2(5,17));
                edgeCells.add(new Vector2(6,18));
                edgeCells.add(new Vector2(6,19));
                edgeCells.add(new Vector2(6,20));
                edgeCells.add(new Vector2(7,21));
                edgeCells.add(new Vector2(7,22));
                edgeCells.add(new Vector2(0,22));

                break;
            case shopping:
                areaShape.setFill(gw.getColor(ColorEnum.COMMERCIAL_BACKGROUND));
                areaShape.setStroke(gw.getColor(ColorEnum.COMMERCIAL_BORDER));
                break;
        }


        for(int i = 1; i<edgeCells.size(); i++) {
            MoveTo from = new MoveTo();
            from.xProperty().bind(gw.CellToPixelsX(edgeCells.get(i-1)));
            from.yProperty().bind(gw.CellToPixelsY(edgeCells.get(i-1)));
            LineTo to = new LineTo();
            to.xProperty().bind(gw.CellToPixelsX(edgeCells.get(i)));
            to.yProperty().bind(gw.CellToPixelsY(edgeCells.get(i)));

            areaShape.getElements().add(from);
            areaShape.getElements().add(to);
        }

        areaShape.getElements().add(new ClosePath());
        areaShape.fillRuleProperty().setValue(FillRule.NON_ZERO);

        this.getChildren().add(areaShape);


    }
}
