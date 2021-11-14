package com.stonksco.minitramways.views.items.areas;

import com.stonksco.minitramways.logic.map.AreaTypes;
import com.stonksco.minitramways.views.GameView;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;

public class AreaView extends Group {

    private GameView gw;

    private AreaTypes type;

    private SVGPath path;

    public AreaView(GameView gw, AreaTypes type) {

        super();
        this.gw = gw;
        this.type = type;
        Color WorkColor = Color.web("#D8171F");
        /*SVGPath e = new SVGPath();
        e.setContent("M40,60 c40,30 440,300 50,70 c60,90 40,10 10,90 ");
        e.setFill(WorkColor);*/
        Rectangle e = new Rectangle();
        e.setHeight(90);
        e.setWidth(150);
        e.setFill(WorkColor);
        e.setViewOrder(100);
        LinearGradient Lg = LinearGradient.valueOf("linear-gradient(to left, #00FA9A  50% , #006400 100%)");
        e.setStroke(Lg);                    //mPaint.setStrokeCap(Paint.Cap.ROUND);https://docs.oracle.com/javase/8/javafx/api/index.html?javafx/scene/layout/BorderStroke.html
        e.setStrokeWidth(3);
        e.setArcHeight(20);
        e.setArcWidth(20);

        this.getChildren().add(e);
    }
}
