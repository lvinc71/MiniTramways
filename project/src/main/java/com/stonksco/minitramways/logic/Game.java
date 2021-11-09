package com.stonksco.minitramways.logic;

import com.stonksco.minitramways.logic.map.Map;

public class Game {

    private int debug = 0;

    private static final Game g = new Game();

    private Game() {

    }

    public static Game get() {
        return g;
    }

    public void setDebug(int debug) {
        this.debug = debug;
        if(debug>0)
            Debug(debug,"DEBUG ENABLED WITH A LEVEL OF "+debug);
    }

    private Map map;

    public Map getMap() {
        return map;
    }

    public static void Debug(int level,String msg) {
        if(level <= get().debug)
            System.out.println("[DEBUG] : "+msg);
    }

    public void initGame() {
        map = new Map();
    }

    public int getDebug() {
        return this.debug;
    }

    public boolean CreateLine(Vector2 start, Vector2 end) {
        boolean canCreate = true;

        if(!map.CreateLine(start,end))
            canCreate = false;

        // ICI, AJOUTER CONTROLE ARGENT/DISPO

        return canCreate;
    }

}
