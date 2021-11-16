package com.stonksco.minitramways.views.items.areas;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.map.AreaTypes;
import com.stonksco.minitramways.views.ColorEnum;
import com.stonksco.minitramways.views.GameView;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

public class AreaView extends Group {

    private GameView gw;

    private AreaTypes type;

    private Pane svgContainer;

    public AreaView(GameView gw, AreaTypes type) {

        super();
        this.gw = gw;
        this.type = type;

        SVGPath e = new SVGPath();
        Region svgShape = new Region();
        svgShape.setShape(e);

        switch(type) {
            case office:
                e.setContent("M550.41,600.28a243,243,0,0,1-75.29-11.94l-69.2-5.52-.29-.09A54.31,54.31,0,0,1,389.5,574a48.69,48.69,0,0,1-18.28-33.54l-.79-7.89a29.75,29.75,0,0,1,2.08-14.37c4.76-11.45,7.67-39.17,7.14-48.48s-2.24-33.88-2.41-36.27q-.09-.42-.15-.84a97.63,97.63,0,0,1,21.43-78.14l9-10.83a127.66,127.66,0,0,1,98.28-46h94v302.6Z");
                e.setFill(gw.getColor(ColorEnum.OFFICE_BACKGROUND));
                e.setStroke(gw.getColor(ColorEnum.OFFICE_BORDER));
                e.strokeWidthProperty().bind(gw.getCellSizeX().divide(6));

                e.scaleXProperty().bind(gw.getCellSizeX().divide(Game.get().getMapSize().getX()));
                e.scaleYProperty().bind(gw.getCellSizeY().divide(Game.get().getMapSize().getY()));
                e.translateXProperty().bind(gw.gridPosX().add(gw.getCellSizeX().multiply(15)));
                e.translateYProperty().bind(gw.gridPosY().add(gw.getCellSizeY().multiply(5)));
                break;
            case residential:
                e.setContent("M2,597.78V142.87H118.73a131.39,131.39,0,0,1,90.93,36.54l16.39,15.71a166,166,0,0,1,50.8,109.4l1.15,18.3a152.44,152.44,0,0,1-7.14,56.66l-1.37,4.25a145.51,145.51,0,0,0-5.57,65.85l4.53,31.15a184.77,184.77,0,0,0,9.39,37l29.38,80Z");
                e.setFill(gw.getColor(ColorEnum.RESIDENTIAL_BACKGROUND));
                e.setStroke(gw.getColor(ColorEnum.RESIDENTIAL_BORDER));
                e.strokeWidthProperty().bind(gw.getCellSizeX().divide(6));

                e.scaleXProperty().bind(gw.getCellSizeX().divide(Game.get().getMapSize().getX()));
                e.scaleYProperty().bind(gw.getCellSizeY().divide(Game.get().getMapSize().getY()));
                e.translateXProperty().bind(gw.gridPosX().add(gw.getCellSizeX().multiply(2)));
                e.translateYProperty().bind(gw.gridPosY().add(gw.getCellSizeY().multiply(0)));

                break;
            case shopping:
                e.setContent("M614.78,2.5H293.13L328.62,100A62.9,62.9,0,0,0,403,139.54a376.74,376.74,0,0,1,44.33-8.21c7.38-1,126.79,12.32,137.74,17.8s39.13,22.7,47.74,22.7c3,0,7-9.13,11.08-21.12A134.33,134.33,0,0,0,637.35,49Z");
                e.setFill(gw.getColor(ColorEnum.COMMERCIAL_BACKGROUND));
                e.setStroke(gw.getColor(ColorEnum.COMMERCIAL_BORDER));
                e.strokeWidthProperty().bind(gw.getCellSizeX().divide(6));

                e.scaleXProperty().bind(gw.getCellSizeX().divide(Game.get().getMapSize().getX()));
                e.scaleYProperty().bind(gw.getCellSizeY().divide(Game.get().getMapSize().getY()));
                e.translateXProperty().bind(gw.gridPosX().add(gw.getCellSizeX().multiply(8)));
                e.translateYProperty().bind(gw.gridPosY().add(gw.getCellSizeY().multiply(0)));
                break;
        }

        this.getChildren().add(e);
    }
}
