package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.items.PinView;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class PinsLayer extends Pane {

    private GameView gw;

    private HashMap<Integer, PinView> pins;

    public PinsLayer(GameView gw) {
        super();
        this.gw = gw;
        pins = new HashMap<>();
    }

    /**
     * Crée un nouveau pin en retournant son ID
     * @param at
     * @return
     */
    public int addPin(Vector2 at,int nb) {
        PinView pv = new PinView(gw,nb);
        int id = pins.size();
        pins.put(id,pv);
        this.getChildren().add(pv);

        pv.translateXProperty().bind(gw.getCellSizeX().multiply(at.getX()));
        pv.translateYProperty().bind(gw.getCellSizeY().multiply(at.getY()));

        pv.setViewOrder(-100*at.getY()+(10*at.getX()));

        return id;
    }

    public void removePin(int id) {
        PinView pv = pins.get(id);
        if(pv!=null) {
            this.getChildren().remove(pv);
            this.pins.remove(id);
        }
    }

    public void reset() {
        this.getChildren().clear();
        this.pins.clear();
    }

    public int getNbOf(int id) {
        int res = -1;
        PinView pv = this.pins.get(id);
        if(pv!=null)
            res = pv.getNb();
        return res;
    }

    public boolean doesPinExists(int pinID) {
        return this.pins.get(pinID)!=null;
    }


}
