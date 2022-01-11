package com.stonksco.minitramways.logic.interactions.states;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.interactions.ClickStateMachine;

public class StartState extends AbstractClickState {

    public StartState(ClickStateMachine machine) {
        super(machine);
        stateName="Initial state";
    }


    @Override
    public AbstractClickState leftTransition(Vector2 clicked) {
        sm.getData().put("firstcell",clicked);
        return new LineCreationState(sm);
    }

    @Override
    public AbstractClickState leftStationTransition(Vector2 clicked) {
        sm.getData().put("firstcell",clicked);
        if(Game.get().isAtExtremity(clicked))
            return new LineExtensionState(sm);
        else
            return new LineCreationState(sm);
    }

    @Override
    public AbstractClickState rightStationTransition(Vector2 clicked) {
        sm.getData().put("stationtodestroy",clicked);
        return new StationDestroyState(sm);
    }

    public AbstractClickState rightPartTransition(Vector2 clicked) {
        return this;
    }


    @Override
    public void action() {}

}
