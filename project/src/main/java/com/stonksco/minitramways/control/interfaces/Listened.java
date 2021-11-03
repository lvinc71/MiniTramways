package com.stonksco.minitramways.control.interfaces;

import java.util.ArrayList;

public abstract class Listened {
    private ArrayList<Listener> listeners = new ArrayList<>();

    public void add(Listener listener) {
        this.listeners.add(listener);
    }

    public void Notify(String msg) {
        for(Listener o : listeners) {
            o.Notify(msg);
        }
    }

}
