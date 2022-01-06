package com.stonksco.minitramways.logic.interactions;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.interactions.states.AbstractClickState;
import com.stonksco.minitramways.logic.interactions.states.StartState;
import com.stonksco.minitramways.logic.map.buildings.Building;
import com.stonksco.minitramways.logic.map.buildings.Station;

import java.util.HashMap;

/**
 * Gère les interactions clics gauche et droit
 */
public class ClickStateMachine {

    private AbstractClickState currentState;
    private HashMap<String,Object> data;

    public ClickStateMachine() {
        data = new HashMap<>();
        currentState = new StartState(this);
    };

    public AbstractClickState sendLeftClick(Vector2 at) {
        if(at != null) {
            Building bAt = Game.get().getMap().getBuildingAt((Vector2)at);
            if(bAt instanceof Station) {
                // Si on a clic gauche sur une station
                currentState = currentState.leftStationTransition((Vector2)at);
            } else if (bAt==null) {
                // Si on a clic gauche sur une case vide
                currentState = currentState.leftTransition((Vector2)at);
            }

            naturalTransition();

            Game.Debug(1,"Click state machine updated : "+currentState);

        }
        return currentState;
    }

    public AbstractClickState sendRightClick(Object at) {
        if(at instanceof Vector2) {

            Building bAt = Game.get().getMap().getBuildingAt((Vector2)at);
            if(bAt instanceof Station) {
                // Si on a clic droit sur une station
                currentState = currentState.rightStationTransition((Vector2)at);
            } else {
                // Si on a clic droit sur une case "vide"
                currentState = currentState.rightTransition((Vector2)at);
            }
        }

        naturalTransition();

        Game.Debug(1,"Click state machine updated : "+currentState);

        return currentState;
    }

    private void naturalTransition() {
        // On effectue les transitions naturelles tant qu'elles donnent un autre état que l'état courant
        boolean exit = false;
        AbstractClickState precState = currentState;
        while(!exit) {
            currentState = currentState.naturalTransition();
            if(precState==currentState)
                exit=true;
            precState = currentState;
        }
    }

    public HashMap<String,Object> getData() {
        return data;
    }

    public AbstractClickState getCurrentState() {
        return currentState;
    }
}
