package com.stonksco.minitramways.logic;

import com.stonksco.minitramways.logic.map.Cell;
import com.stonksco.minitramways.logic.map.GameMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    private GameMap map;

    public GameMap getMap() {
        return map;
    }

    public static void Debug(int level,String msg) {
        if(level <= get().debug)
            System.out.println("[DEBUG] : "+msg);
    }

    public void initGame() {
        map = new GameMap();
        map.init();
    }

    public int getDebug() {
        return this.debug;
    }

    public ArrayList<Integer> CreateLine(Vector2 start, Vector2 end) {
        ArrayList<Integer> res = null;

        // Vérifier argent, si c'est ok on exécute
        res = map.CreateLine(start, end);

        return res;
    }

    public Vector2 getMapSize() {
        return map.getGridSize().clone();
    }

    public HashMap<Integer, HashMap<Vector2, Cell>> getAreas(){

        return getMap().getAreas();

    }

    public Set<Map.Entry<Vector2,Vector2>> getPartsVectorsOf(int lineID) {
        return map.getPartsVectorsOf(lineID);
    }

    public ArrayList<Vector2> getStations() {
        return map.getStations();
    }

    /**
     * Retourne true si la position passée se trouve à une extrémité de ligne
     * @param pos
     * @return
     */
    public boolean isAtExtremity(Vector2 pos) {
        return map.isAtExtremity(pos);
    }




}
