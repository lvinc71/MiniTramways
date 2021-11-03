package com.stonksco.minitramways.logic.map;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;

import java.util.HashMap;

public class Map {

    private HashMap<Vector2,Cell> grid = null;

    private Vector2 windowSize = null;
    private Vector2 gridSize = null;

    public Map() {
        init();
    }

    /**
     * Construit la grille de la map
     * @param width nombre de cases en largeur
     * @param height nombre de cases en hauteur
     */
    private void initGrid(int width, int height) {

        Game.Debug(1,"Initializing grid...");

        gridSize = new Vector2(width,height);
        grid = new HashMap<>();

        for(int i =0; i<height; i++) {
            for(int j = 0; j<width; j++) {
                Vector2 v = new Vector2(j,i);
                grid.put(v,new Cell(v));
                Game.Debug(3,"Added cell at "+v.toString());
            }
        }
        Game.Debug(1,"Grid initialized with "+grid.size()+" cells.");
    }

    /**
     * Initialise tous les composants de la map
     */
    private void init() {
        initGrid(23,36);
    }

    /**
     *
     * @param pixels
     */
    public Vector2 PixelsToGrid(Vector2 pixels) {
        return null;
    }

    /**
     *
     * @param grid
     * @param mode
     */
    public Vector2 GridToPixels(Vector2 grid,int mode) {
        return null;
    }

    public HashMap<Vector2,Cell> GetGrid() {
        return grid;
    }

    public Vector2 getGridSize() {
        return gridSize.clone();
    }
}
