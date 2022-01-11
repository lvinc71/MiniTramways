package com.stonksco.minitramways.logic.interactions.states;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.interactions.ClickStateMachine;
import com.stonksco.minitramways.logic.interactions.InteractionException;
import com.stonksco.minitramways.logic.map.lines.LinePart;

import java.util.HashMap;

public class LineExtensionState extends AbstractClickState{

    public LineExtensionState(ClickStateMachine machine) {
        super(machine);
        stateName = "Line extension";
        HashMap<String,Object> d = sm.getData();
        if(d.containsKey("secondcell")) {
            d.remove("firstcell");
            d.put("firstcell", d.get("secondcell"));
            d.remove("secondcell");
        }

        if(Game.get().isAtExtremity((Vector2)d.get("firstcell"))) {
            canExtend=true;

            d.put("linetoextend",Game.get().lineFromExtremity((Vector2)d.get("firstcell")));

        } else {
            Game.Debug(1,"Selected station is not at an extremity of its line. Extension will abort, and a new line will be created.");
        }
    }

    private boolean canExtend = false;

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
    public AbstractClickState naturalTransition() {
        AbstractClickState res = this;
        if(!canExtend)
            res=new LineCreationState(sm);
        return res;
    }

    @Override
    public void action() throws InteractionException {
        Vector2 v1 = (Vector2) sm.getData().get("firstcell");
        Vector2 v2 = (Vector2) sm.getData().get("secondcell");

        Integer creationCost = (35)+(int)(Vector2.AbstractDistance(v1,v2)/7);
        if(Game.get().getMoney()<creationCost) {
            throw new InteractionException("money");
        } else {
            Integer res = Game.get().ExtendLine(v1, v2, (Integer) sm.getData().get("linetoextend"));
            if(res!=null && res>-1) {
                Game.get().addMoney(-1*creationCost);
            } else {
                throw new InteractionException("unknown");
            }
        }
    }
}
