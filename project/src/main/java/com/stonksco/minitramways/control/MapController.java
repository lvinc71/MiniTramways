package com.stonksco.minitramways.control;

import com.stonksco.minitramways.control.interfaces.Controler;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.Map;
import com.stonksco.minitramways.views.GameView;
import javafx.scene.Node;

public class MapController implements Controler {

    private Map map;
    private GameView gameview;

    public MapController(Map map, GameView view) {
        this.map = map;
        this.gameview = view;
    }

    @Override
    public void Warn() {

    }

   public void createLine(Vector2 start, Vector2 end)
   {
        //j'ai pas le droit de la coder
   }
}
