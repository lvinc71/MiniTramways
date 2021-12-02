package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.Vector2Couple;
import com.stonksco.minitramways.logic.people.People;
import com.stonksco.minitramways.views.ColorEnum;
import com.stonksco.minitramways.views.GameView;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TargetsLayer extends Pane {

    private GameView gw;

    public TargetsLayer(GameView gw) {
        super();
        this.gw = gw;
    }

    public void Enter(Vector2 cell) {
        HashMap<Vector2,Integer> nbTargets = new HashMap<>();

        ArrayList<People> ppl = Game.get().getPeopleAt(cell);
        for(People p : ppl) {
            if(nbTargets.get(p.getTargetPos())==null)
                nbTargets.put(p.getTargetPos(),0);
            nbTargets.put(p.getTargetPos(),nbTargets.get(p.getTargetPos())+1);
        }

        for(Map.Entry<Vector2,Integer> e : nbTargets.entrySet()) {
            StackPane n = createTargetDisplay(e.getValue());
            n.translateXProperty().bind(gw.CellToPixelsX(e.getKey()).add(n.widthProperty().divide(-2)));
            n.translateYProperty().bind(gw.CellToPixelsY(e.getKey()).add(n.heightProperty().divide(-2)));
            this.getChildren().add(n);
        }
    }

    public void Exit(Vector2 cell) {
        this.getChildren().clear();
    }

    private StackPane createTargetDisplay(int nbOfPeople) {
        StackPane res = new StackPane();
        Circle c1 = new Circle();
        c1.radiusProperty().bind(gw.getCellSizeX().multiply(1.2d+(0.2d*nbOfPeople)));
        c1.setFill(gw.getColor(ColorEnum.TARGET_COLOR));
        c1.setStroke(gw.getColor(ColorEnum.TARGET_OUTLINE_COLOR));
        c1.strokeWidthProperty().bind(gw.getCellSizeX().multiply(0.15d+(0.15d*nbOfPeople)));
        c1.strokeTypeProperty().setValue(StrokeType.INSIDE);


        res.getChildren().add(c1);
        return res;
    }
}
