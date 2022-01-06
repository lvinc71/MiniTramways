package com.stonksco.minitramways.control.utils;

import java.util.ArrayList;

public abstract class Listened {
    private final ArrayList<Listener> listeners = new ArrayList<>();

    public void register(Listener listener) {
        this.listeners.add(listener);
    }

    public void Notify(Notification msg) {
        for(Listener o : listeners) {
            o.Notify(msg);
        }
    }

}
