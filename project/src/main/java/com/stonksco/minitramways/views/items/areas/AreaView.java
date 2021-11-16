package com.stonksco.minitramways.views.items.areas;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.map.AreaTypes;
import com.stonksco.minitramways.views.ColorEnum;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.layers.AreasView;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

public class AreaView extends Region {

    private GameView gw;

    private AreaTypes type;

    private Pane svgContainer;

    public AreaView(GameView gw, AreaTypes type, AreasView layer) {

        super();
        this.gw = gw;
        this.type = type;

        SVGPath e = new SVGPath();
        this.getChildren().add(e);

        switch(type) {
            case office:
                e.setContent("M180.09,300.14a240.2,240.2,0,0,1-74.47-11.82l-.28-.09-69.22-5.52h0a51.55,51.55,0,0,1-15.33-8.31A46.37,46.37,0,0,1,3.44,242.58l-.8-7.9a27.32,27.32,0,0,1,1.91-13.17c5.06-12.18,7.86-40.2,7.32-49.57s-2.24-34-2.41-36.31v-.17l0-.17c-.05-.22-.09-.45-.12-.67A95.15,95.15,0,0,1,30.17,58.43l9-10.83a124.81,124.81,0,0,1,96.2-45.1H227V300.1l-46.9,0Z");
                e.setFill(gw.getColor(ColorEnum.OFFICE_BACKGROUND));
                e.setStroke(gw.getColor(ColorEnum.OFFICE_BORDER));
                e.strokeWidthProperty().bind(gw.getCellSizeX().divide(6));

                e.scaleXProperty().bind(gw.getCellSizeX().divide(30));
                e.scaleYProperty().bind(gw.getCellSizeY().divide(30));
                e.layoutYProperty().bind(layer.layoutYProperty().add(gw.getCellSizeY().multiply(15)));
                e.layoutXProperty().bind(layer.layoutXProperty().add(gw.getCellSizeX().multiply(15)));
                AnchorPane.setRightAnchor(e,0d);


                break;
            case residential:
                e.setContent("M2.5,2.5H116.73a128.33,128.33,0,0,1,89.2,35.84l16.39,15.71a163.08,163.08,0,0,1,50,107.76l1.15,18.3a149.16,149.16,0,0,1-7,55.73l-1.37,4.25a148.77,148.77,0,0,0-5.66,67L264,338.22a187.16,187.16,0,0,0,9.51,37.5l28.15,76.64L2.5,452.41Z");
                e.setFill(gw.getColor(ColorEnum.RESIDENTIAL_BACKGROUND));
                e.setStroke(gw.getColor(ColorEnum.RESIDENTIAL_BORDER));
                e.strokeWidthProperty().bind(gw.getCellSizeX().divide(6));

                e.scaleXProperty().bind(gw.getCellSizeX().divide(Game.get().getMapSize().getX()));
                e.scaleYProperty().bind(gw.getCellSizeY().divide(Game.get().getMapSize().getY()));
                e.translateXProperty().bind(gw.gridPosX().add(gw.getCellSizeX().multiply(2)));
                e.translateYProperty().bind(gw.gridPosY().add(gw.getCellSizeY().multiply(0)));

                break;
            case shopping:
                e.setContent("M339.48,166.83c-5.94-.22-23.45-9.89-35.08-16.32-4.55-2.51-8.48-4.68-11.34-6.12-11.64-5.82-124.84-18.11-137.64-18.11a12.68,12.68,0,0,0-1.56.07,380.14,380.14,0,0,0-44.59,8.26,60.33,60.33,0,0,1-71.43-38L3.57,2.5H320.08L342,47.59a131,131,0,0,1,6.42,99.82C342.83,163.74,340.15,166.4,339.48,166.83Z");
                e.setFill(gw.getColor(ColorEnum.COMMERCIAL_BACKGROUND));
                e.setStroke(gw.getColor(ColorEnum.COMMERCIAL_BORDER));
                e.strokeWidthProperty().bind(gw.getCellSizeX().divide(6));

                e.scaleXProperty().bind(gw.getCellSizeX().divide(Game.get().getMapSize().getX()));
                e.scaleYProperty().bind(gw.getCellSizeY().divide(Game.get().getMapSize().getY()));
                e.translateXProperty().bind(gw.gridPosX().add(gw.getCellSizeX().multiply(8)));
                e.translateYProperty().bind(gw.gridPosY().add(gw.getCellSizeY().multiply(0)));
                break;
        }


    }
}
