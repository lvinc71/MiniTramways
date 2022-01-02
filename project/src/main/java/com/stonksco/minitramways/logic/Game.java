package com.stonksco.minitramways.logic;

import com.stonksco.minitramways.logic.interactions.ClickStateMachine;
import com.stonksco.minitramways.logic.interactions.states.AbstractClickState;
import com.stonksco.minitramways.logic.map.Area;
import com.stonksco.minitramways.logic.map.GameMap;
import com.stonksco.minitramways.logic.map.lines.Line;
import com.stonksco.minitramways.logic.map.lines.Tramway;
import com.stonksco.minitramways.logic.map.buildings.BuildingEnum;
import com.stonksco.minitramways.logic.people.People;
import com.stonksco.minitramways.views.Clock;

import java.util.*;

public class Game {

    private int debug = 0;
    private int satisfaction = 0; // Moyenne de la satisfaction entre 0 et 100
    private LinkedHashMap<People,Integer> satisfactions;
    private ClickStateMachine clicksm;

    private static final Game g = new Game();

    private Game() {
        satisfactions = new LinkedHashMap<People, Integer>() {
            @Override
            protected boolean removeEldestEntry(final Map.Entry eldest) {
                return size() > 40;
            }
        };
        computeSatisfaction();
        clicksm = new ClickStateMachine();
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
    private Player player;

    private boolean needPinsUpdate = false;

    public GameMap getMap() {
        return map;
    }

    public static void Debug(int level,String msg) {
        if(level <= get().debug)
            System.out.println("[DEBUG] : "+msg);
    }

    public void initGame() {
        map = new GameMap();
        player = new Player();
        map.init();
        satisfactions.put(null,50);
    }

    public int getDebug() {
        return this.debug;
    }

    public Integer CreateLine(Vector2 start, Vector2 end) {
        Integer res = null;

        // Vérifier argent, si c'est ok on exécute
        res = map.CreateLine(start, end).getID();

        return res;
    }

    public Vector2 getMapSize() {
        return map.getGridSize().clone();
    }

    public HashMap<Integer, Area> getAreas(){

        return getMap().getAreas();

    }

    public HashMap<BuildingEnum,ArrayList<Vector2>> getBuildings(){
        return this.getMap().getBuildings();
    }
    public List<Map.Entry<Vector2,Vector2>> getPartsVectorsOf(int lineID) {
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

    public void Update() {
        map.Update();
        for(People p : People.getAll()) {
            p.Update();
        }
        computeSatisfaction();
    }

    public int getFirstIndexOf(int lineID) {
        return map.getFirstIndexOf(lineID);
    }

    public int getLastIndexIOf(int lineID) {
        return map.getLastIndexOf(lineID);
    }

    public ArrayList<Tramway> getTramsOf(int lineID) {
        return map.getTramsOf(lineID);
    }

    /**
     * Retourne le nombre de personnes à un endroit donné
     * @param pos l'endroit en question
     * @return nombre de personnes
     */
    public int getAmountOf(Vector2 pos) {
        return map.getAmountOf(pos);
    }

    /**
     * Retourne toutes les personnes se trouvant sur une case donnée
     * @param at
     * @return
     */
    public ArrayList<People> getPeopleAt(Vector2 at) {
        ArrayList<People> res = new ArrayList<>();
        for(People p : People.getAll()) {
            if(p.getCurrentPlace().getCoordinates().equals(at))
                res.add(p);
        }
        return res;
    }

    public boolean needPinsUpdate() {
        boolean res = needPinsUpdate;
        needPinsUpdate = false;
        return res;
    }

    public void requestPinsUpdate() {
        needPinsUpdate = true;
    }

    public void addMoney(int nb) {
            player.addMoney(5);
    }

    public int getMoney() {
        return player.getMoney();
    }

    public Integer[] getLinesID() {
        return map.getLinesID();
    }

    public void sendSatisfaction(People people, int val) {
        satisfactions.put(people,val);
    }

    private long lastSatisfactionUpdate = 0;

    private void computeSatisfaction() {
        if(lastSatisfactionUpdate>5000000000l) {
            double s = 0;
            for(Map.Entry e : satisfactions.entrySet()) {
                s+=(Integer)e.getValue();
            }
            satisfaction = (int) (s/satisfactions.size());
            Game.Debug(2,"Satisfaction updated to "+satisfaction);
            lastSatisfactionUpdate=0;
        } else {
            lastSatisfactionUpdate+= Clock.get().DeltaTime();
        }
    }

    public int getSatisfaction() {
        return satisfaction;
    }

    public AbstractClickState sendLeftClick(Vector2 at) {
        return clicksm.sendLeftClick(at);
    }

    public AbstractClickState sendRightClick(Vector2 at) {
        return clicksm.sendRightClick(at);
    }

    public AbstractClickState getCurrentClickState() {
        return clicksm.getCurrentState();
    }

    public Integer ExtendLine(Vector2 v1, Vector2 v2, Integer id) {
        Line l = map.ExtendLine(v1,v2,id);
        int res=-1;
        if(l!=null)
            res = l.getID();
        return res;
    }

    public void destroyStation(Vector2 stationtodestroy) {
        ArrayList<Integer> res = map.destroyStation(stationtodestroy);
        if(res!=null) {
            if(res.size()>0) {
                for(People p : People.getAll()) {
                    p.destroyed(stationtodestroy);
                }
            }
        }
    }
}
