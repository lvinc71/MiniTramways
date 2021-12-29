package com.stonksco.minitramways.control;

import com.stonksco.minitramways.control.utils.Listened;
import com.stonksco.minitramways.control.utils.Notification;
import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.interactions.states.AbstractClickState;
import com.stonksco.minitramways.logic.interactions.states.LineCreationState;
import com.stonksco.minitramways.logic.interactions.states.LineExtensionState;

import java.util.ArrayList;

public class MapController extends Listened {

    public MapController() {
    }






   public boolean createLine(Vector2 start, Vector2 end)
   {
       boolean res = false;
       Integer lineID = Game.get().CreateLine(start,end);
       if(lineID!=null) {
           res=true;
       }

       Notification notif = new Notification("updatelines");
       notif.setData(Game.get().getMap().getLinesToUpdate());
       Notify(notif);
       return res;
   }

    public void sendLeftClick(Vector2 at) {
        AbstractClickState oldState = Game.get().getCurrentClickState();
        AbstractClickState newState = Game.get().sendLeftClick(at);

        if(newState instanceof LineExtensionState) {
            Notification notif = new Notification("updatelines");
            notif.setData(Game.get().getMap().getLinesToUpdate());
            Notify(notif);
        }

    }

    public void sendRightClick(Vector2 at) {
        AbstractClickState oldState = Game.get().getCurrentClickState();
        AbstractClickState newState = Game.get().sendRightClick(at);


    }
}
