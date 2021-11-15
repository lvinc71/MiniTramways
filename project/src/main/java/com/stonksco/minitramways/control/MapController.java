package com.stonksco.minitramways.control;

import com.stonksco.minitramways.control.interfaces.Controler;
import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.GameMap;
import com.stonksco.minitramways.views.GameView;

import java.util.ArrayList;

public class MapController implements Controler {

    private GameMap map;
    private GameView gameview;

    public MapController(GameMap map, GameView view) {
        this.map = map;
        this.gameview = view;
    }

    @Override
    public void Warn() {

    }

   public boolean createLine(Vector2 start, Vector2 end)
   {
       boolean res = true;
       ArrayList<Integer> listeDeLignes =Game.get().CreateLine(start,end);
       if(listeDeLignes!=null)
           gameview.updateLines(listeDeLignes);
       else
           res = false;

       return res;
   }

}
