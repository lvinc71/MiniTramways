package com.stonksco.minitramways.control;

import com.stonksco.minitramways.control.interfaces.Controler;
import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.GameMap;
import com.stonksco.minitramways.views.GameView;

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
       if(Game.get().CreateLine(start,end)!=null)
           gameview.CreateLine(start,end);
       else
           res = false;

       return res;
   }

}
