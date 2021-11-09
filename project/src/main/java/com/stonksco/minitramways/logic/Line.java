package com.stonksco.minitramways.logic;

import com.stonksco.minitramways.logic.map.building.Station;

import java.util.HashMap;

public class Line {

    /**
     * Stockage des stations selon leur position sur la ligne
     */
    private HashMap<Integer, Station> stations;

    public Line(Vector2 start, Vector2 end) {
        stations = new HashMap<>();
        this.stations.put(0,new Station());
        this.stations.put(100,new Station());
    }

    /**
     * Retourne la prochaine station sur le trajet depuis celle-ci, selon la direction dans laquelle le tram parcourt la ligne
     * @param comingFrom Station visitée avant la station courante, pour déduire la direction
     * @return La prochaine station sur le trajet
     */
    public Station getNextStation(Station comingFrom) {
        return null;
    }
}
