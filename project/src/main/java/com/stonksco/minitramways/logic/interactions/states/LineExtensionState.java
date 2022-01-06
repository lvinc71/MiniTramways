package com.stonksco.minitramways.logic.interactions.states;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.interactions.ClickStateMachine;
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
        } else {
            Game.Debug(1,"Selected station is not at an extremity of its line. Extension will abort, and a new line will be created.");
        }
    }

    private boolean canExtend = false;

    @Override
    public AbstractClickState leftTransition(Vector2 clicked) {
        sm.getData().put("secondcell",clicked);
        action();
        return new LineExtensionState(sm);
    }

    @Override
    public AbstractClickState leftStationTransition(Vector2 clicked) {
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
    public void action() {
        Vector2 v1 = (Vector2) sm.getData().get("firstcell");
        Vector2 v2 = (Vector2) sm.getData().get("secondcell");
        Game.get().ExtendLine(v1,v2, (Integer) sm.getData().get("linetoextend"));
    }
}
