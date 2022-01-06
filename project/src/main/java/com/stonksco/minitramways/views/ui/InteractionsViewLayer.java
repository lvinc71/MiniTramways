package com.stonksco.minitramways.views.ui;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.interactions.states.AbstractClickState;
import com.stonksco.minitramways.logic.interactions.states.LineCreationState;
import com.stonksco.minitramways.logic.interactions.states.LineExtensionState;
import com.stonksco.minitramways.logic.interactions.states.StartState;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.ui.elements.controls.MouseControls;
import com.stonksco.minitramways.views.ui.elements.selections.CellSelection;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class InteractionsViewLayer extends Pane {

    private GameView gw;
    private ArrayList<CellSelection> selected = new ArrayList<>();
    private MouseControls mousectrls;
    private AbstractClickState state;

    public InteractionsViewLayer(GameView gw) {
        this.gw = gw;
        mousectrls = new MouseControls();
        changeState(Game.get().getCurrentClickState());
    }

    public void changeState(AbstractClickState state) {
        this.state = state;
        update();
    }

    private void update() {
        this.getChildren().clear();
        selected.clear();
        if(state instanceof StartState) {

        } else if(state instanceof LineCreationState) {
            selected.add(new CellSelection(gw, (Vector2) state.getData().get("firstcell")));

        } else if(state instanceof LineExtensionState) {
            selected.add(new CellSelection(gw, (Vector2) state.getData().get("firstcell")));

        }

        for(CellSelection cs : selected) {
            this.getChildren().add(cs);
        }
    }









}
