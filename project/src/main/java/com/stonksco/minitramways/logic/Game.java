package com.stonksco.minitramways.logic;

import com.stonksco.minitramways.logic.map.AreaTypes;
import com.stonksco.minitramways.logic.map.Cell;
import com.stonksco.minitramways.logic.map.GameMap;
import com.stonksco.minitramways.logic.map.building.Building;
import com.stonksco.minitramways.logic.map.building.BuildingEnum;

import java.util.ArrayList;
import java.util.HashMap;

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

    public HashMap<BuildingEnum,ArrayList<Vector2>> getBuildings(){
        HashMap<BuildingEnum,ArrayList<Vector2>> hm = new HashMap<>();
        ArrayList<Cell> tempC = new ArrayList<>();
        ArrayList<Vector2> tempV = new ArrayList<>();

        for(int i = 0; i<this.getMap().getNombreArea(); i++){
            switch (this.getMap().getAreas(i).getType()){
                case residential:
                   tempC = Game.get().getMap().getAreas(i).getCells();
                   for(int b=0; b<tempC.size();b++){
                       if(tempC.get(b).getBuilding()!=null) {
                           tempV.add(tempC.get(b).getCoordinates());

                       }
                   }
                   hm.put(BuildingEnum.HOUSE,tempV);
                    break;

                case office:
                    tempC = Game.get().getMap().getAreas(i).getCells();
                    for(int b=0; b<tempC.size();b++){
                        if(tempC.get(b).getBuilding()!=null) {
                            tempV.add(tempC.get(b).getCoordinates());

                        }
                    }
                    hm.put(BuildingEnum.OFFICE,tempV);
                    break;

                case shopping:
                    tempC = Game.get().getMap().getAreas(i).getCells();
                    for(int b=0; b<tempC.size();b++){
                        if(tempC.get(b).getBuilding()!=null) {
                            tempV.add(tempC.get(b).getCoordinates());

                        }
                    }
                    hm.put(BuildingEnum.SHOP,tempV);
                    break;

            }
            tempC=null;
        }
        return hm;
    }


}
