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

    public void sendLeftClick(Vector2 at) {
        AbstractClickState oldState = Game.get().getCurrentClickState();
        AbstractClickState newState = Game.get().sendLeftClick(at);

        if(newState instanceof LineExtensionState) {
            Notification notif = new Notification("updatelines");
            notif.setData(Game.get().getMap().getLinesToUpdate());
            Notify(notif);
        }

        Notify(new Notification("updateinteractions"));
    }

    public void sendRightClick(Vector2 at) {
        AbstractClickState oldState = Game.get().getCurrentClickState();
        AbstractClickState newState = Game.get().sendRightClick(at);

        ArrayList<Integer> toUpdate = Game.get().getMap().getLinesToUpdate();
        if(toUpdate!=null && toUpdate.size()>0) {
            Notification notif = new Notification("updatelines");
            notif.setData(toUpdate);
            Notify(notif);
        }

        Notify(new Notification("updateinteractions"));
    }
}
