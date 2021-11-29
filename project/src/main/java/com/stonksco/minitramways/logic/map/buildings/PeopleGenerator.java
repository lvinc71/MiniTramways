package com.stonksco.minitramways.logic.map.buildings;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.map.Area;
import com.stonksco.minitramways.logic.map.GameMap;
import com.stonksco.minitramways.views.Clock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PeopleGenerator {

    private GameMap gm;


    /**
     * Durée restante avant la prochaine génération en secondes
     */
    private double remainingTimeUntilGen = 0;


    public PeopleGenerator(GameMap gm) {
        this.gm = gm;
    }


    /**
     * Appelée à chaque frame, cette fonction gère la génération des personnes
     */
    public void peopleGeneration() {
        if(remainingTimeUntilGen<=0) {
            // On génère un nombre aléatoire de personnes dans des maisons aléatoires

            int nbToGen = 2+((int)(Math.random()*2d));
            for(int i = 0; i<nbToGen; i++) {
                gm.getRandomHouse().spawn();
            }

            remainingTimeUntilGen = 8 + (Math.random()*10d);
            Game.Debug(1,"New people spawned.");
        } else {
            // Cas où on ne génère pas
            remainingTimeUntilGen -= Clock.get().GameDeltaTime();
        }

    }

}
