package com.stonksco.minitramways.logic.interactions.states;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.interactions.ClickStateMachine;

public class StationDestroyState extends AbstractClickState{

    public StationDestroyState(ClickStateMachine machine) {
        super(machine);
        stateName="Station Destroy";
    }

    @Override
    public AbstractClickState naturalTransition() {
        action();
        return new ResetClickState(sm);
    }

    @Override
    public void action() {
        Game.get().destroyStation((Vector2)sm.getData().get("stationtodestroy"));
    }
}
