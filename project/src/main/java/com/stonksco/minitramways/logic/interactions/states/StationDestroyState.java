package com.stonksco.minitramways.logic.interactions.states;

import com.stonksco.minitramways.logic.interactions.ClickStateMachine;

public class StationDestroyState extends AbstractClickState{

    public StationDestroyState(ClickStateMachine machine) {
        super(machine);
        stateName="Station Destroy";
    }

    @Override
    public AbstractClickState naturalTransition() {
        return new ResetClickState(sm);
    }

    @Override
    public void action() {

    }
}
