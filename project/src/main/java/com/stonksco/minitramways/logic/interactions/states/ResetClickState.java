package com.stonksco.minitramways.logic.interactions.states;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.interactions.ClickStateMachine;

/**
 * État final de l'automate, efface les données et retourne à l'état initial
 */
public class ResetClickState extends AbstractClickState{

    public ResetClickState(ClickStateMachine machine) {
        super(machine);
    }

    @Override
    public AbstractClickState naturalTransition() {
        action();
        return new StartState(sm);
    }

    @Override
    public void action() {
        sm.getData().clear();
    }
}
