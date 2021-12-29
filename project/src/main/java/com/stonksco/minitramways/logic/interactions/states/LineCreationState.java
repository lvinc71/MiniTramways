package com.stonksco.minitramways.logic.interactions.states;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.interactions.ClickStateMachine;
import com.stonksco.minitramways.logic.map.lines.LinePart;

public class LineCreationState extends AbstractClickState {


    public LineCreationState(ClickStateMachine machine) {
        super(machine);
        stateName = "Line creation";
    }

    @Override
    public AbstractClickState leftTransition(Vector2 clicked) {
        sm.getData().put("secondcell",clicked);
        action();
        return new LineExtensionState(sm);
    }

    @Override
    public AbstractClickState rightTransition(Vector2 clicked) {
        return new ResetClickState(sm);
    }

    @Override
    public AbstractClickState rightStationTransition(Vector2 clicked) {
        return new ResetClickState(sm);
    }

    @Override
    public AbstractClickState rightPartTransition(LinePart clicked) {
        return new ResetClickState(sm);
    }

    @Override
    public void action() {
        Vector2 v1 = (Vector2) sm.getData().get("firstcell");
        Vector2 v2 = (Vector2) sm.getData().get("secondcell");
        Integer createdID = Game.get().CreateLine(v1,v2);
        sm.getData().put("createdid",createdID);
    }

}
