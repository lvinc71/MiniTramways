package com.stonksco.minitramways.logic.map.generation;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.map.Area;
import com.stonksco.minitramways.logic.map.GameMap;
import com.stonksco.minitramways.views.Clock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.SortedMap;

public class BuildingsGenerator {

    private GameMap gm;


    /**
     * Durée restante avant la prochaine génération en secondes
     * Cette durée est de plus en plus courte à chaque tour
     */
    private double remainingTimeUntilGen = 0;


    public BuildingsGenerator(GameMap gm) {
        this.gm = gm;
    }


    /**
     * Appelée à chaque frame, cette fonction gère la génération progressive des bâtiments
     */
    public void buildingsGeneration() {
        if(remainingTimeUntilGen<=0) {
            // On génère des bâtiments dans la zone la moins dense

            // On classe les zones par densité
            ArrayList<Area> areas = new ArrayList<>(gm.getAreas().values());


            Collections.sort(areas, new Comparator<Area>() {
                @Override
                public int compare(Area o1, Area o2) {
                    return Double.compare(o1.getDensity(),o2.getDensity());
                }
            });

            boolean res = false;
            for(Area a : areas) {
                // Première itération = zone la moins dense
                // On demande la génération
                res = a.generateBuilding();
                // Si la génération réussit, on sort de la boucle
                if(res)
                    break;
                // Si la génération échoue, on passe à la prochaine zone et on réessaye
            }

            // Si on n'a pas réussi à générer de bâtiment dans aucune zone, alors on considère que les zones sont pleines
            // On bloque donc la génération pour éviter les calculs inutiles
            if(!res) {
                remainingTimeUntilGen = Double.POSITIVE_INFINITY;
                Game.Debug(1,"Cannot generate more buildings. Generation ended.");
            }
            else {
                // On calcule le nouveau countdown
                remainingTimeUntilGen = 20 + (Math.random()*10d);
                Game.Debug(1,"New buildings generated.");
            }
        } else {
            // Cas où on ne génère pas
            remainingTimeUntilGen -= Clock.get().GameDeltaTime();
        }

    }

    /**
     * Génère les bâtiments initiaux
     */
    public void initBuildings() {
        for(Area a : gm.getAreas().values()) {
            a.generateBuilding();
        }
    }

}
