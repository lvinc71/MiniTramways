package com.stonksco.minitramways.logic.interactions.states;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.interactions.ClickStateMachine;
import com.stonksco.minitramways.logic.interactions.InteractionException;
import com.stonksco.minitramways.logic.map.lines.LinePart;

import java.util.HashMap;

public abstract class AbstractClickState {

    protected ClickStateMachine sm;
    protected String stateName="Undefined state";

    public AbstractClickState(ClickStateMachine machine) {
        sm = machine;
    }

    /**
     * Retourne l'état après clic gauche sur une case "vide"
     * @return
     */
    public AbstractClickState leftTransition(Vector2 clicked) throws InteractionException {
        return this;
    }

    /**
     * Retourne l'état après clic gauche sur une station
     * @return
     */
    public AbstractClickState leftStationTransition(Vector2 clicked) throws InteractionException {
        return this;
    }

    /**
     * Retourne l'état après clic droit sur une case "vide"
     * @return
     */
    public AbstractClickState rightTransition(Vector2 clicked) throws InteractionException {
        return this;
    }

    /**
     * Retourne l'état après clic droit sur une station
     * @return
     */
    public AbstractClickState rightStationTransition(Vector2 clicked) throws InteractionException {
        return this;
    }

    /**
     * Retourne l'état après clic droit sur un tronçon
     * @return
     */
    public AbstractClickState rightPartTransition(LinePart clicked) throws InteractionException {
        return this;
    }


    /**
     * Retourne l'état après avoir effectué l'action
     * @return
     */
    public AbstractClickState naturalTransition() {
        return this;
    }

    /**
     * Actions à effectuer selon l'interaction
     */
    public abstract void action() throws InteractionException;

    @Override
    public String toString() {
        return "[ ("+stateName+"):"+sm.getData()+" ]";
    }

    public HashMap<String,Object> getData() {
        return (HashMap<String, Object>) sm.getData().clone();
    }

}
