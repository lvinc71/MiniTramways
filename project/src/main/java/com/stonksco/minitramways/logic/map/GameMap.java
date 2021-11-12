package com.stonksco.minitramways.logic.map;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Line;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.building.BuildingEnum;

import java.util.ArrayList;
import java.util.HashMap;

public class GameMap {

    // Grille
    private HashMap<Vector2,Cell> grid = null;
    private Vector2 gridSize = null;

    // Elements de la map
    private ArrayList<Line> lines;


    public GameMap() {
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
        initGrid(36,23);
        lines = new ArrayList<>();
    }

    public HashMap<Vector2,Cell> GetGrid() {
        return grid;
    }

    public Vector2 getGridSize() {
        return gridSize.clone();
    }

    public boolean CreateLine(Vector2 start, Vector2 end) {
        Game.Debug(2,"Line creation initiated from "+start+" to "+end);
        boolean canCreate = true;
        if(grid.get(start).getBuilding() != null)
            canCreate=false;
        else if(grid.get(end).getBuilding() != null)
            canCreate=false;

        if(canCreate) {
            Line l = new Line(start,end);
            lines.add(l);
        }

        Game.Debug(1,"Line created from "+start+" to "+end);
        return canCreate;
    }

    public boolean isEmpty(Vector2 cell) {
        boolean res=false;
        try {
            res = (this.grid.get(cell).getBuilding() == null);
        } catch (Exception e) {}
        return res;
    }

    public boolean addBuildingTo(BuildingEnum b, Vector2 to) {
        return false;
    }

}
