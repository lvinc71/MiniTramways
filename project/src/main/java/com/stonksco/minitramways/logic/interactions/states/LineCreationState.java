package com.stonksco.minitramways.logic.interactions.states;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.interactions.ClickStateMachine;
import com.stonksco.minitramways.logic.interactions.InteractionException;
import com.stonksco.minitramways.logic.map.lines.LinePart;

/**
 * Création de ligne en cours
 * @Data firstcell
 */
public class LineCreationState extends AbstractClickState {


    public LineCreationState(ClickStateMachine machine) {
        super(machine);
        stateName = "Line creation";
    }

    @Override
    public AbstractClickState leftTransition(Vector2 clicked) throws InteractionException {
        sm.getData().put("secondcell",clicked);
        action();
        return new LineExtensionState(sm);
    }

    @Override
    public AbstractClickState leftStationTransition(Vector2 clicked) throws InteractionException {
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
    public void action() throws InteractionException {
        Vector2 v1 = (Vector2) sm.getData().get("firstcell");
        Vector2 v2 = (Vector2) sm.getData().get("secondcell");
        Integer creationCost = (2*35)+(int)(Vector2.AbstractDistance(v1,v2)/7);
        if(Game.get().getMoney()<creationCost) {
            throw new InteractionException("money");
        } else {
            Integer createdID = Game.get().CreateLine(v1,v2);
            if(createdID!=null) {
                sm.getData().put("createdid",createdID);
                Game.get().addMoney(-1*creationCost);
            } else {
                throw new InteractionException("obstructed");
            }

        }


    }

}
