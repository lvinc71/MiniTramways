package com.stonksco.minitramways.logic.people;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.Vector2Couple;
import com.stonksco.minitramways.logic.map.GameMap;

import java.util.*;

/**
 * Gère les calculs d'itinéraires
 */
public class PathFinder {

    private GameMap gm;

    public PathFinder(GameMap gm) {
        this.gm = gm;
        distances = new HashMap<>();
        preds = new HashMap<>();
        visited = new HashMap<>();
        updateGraph();
    }

    // Données de départ
    private Vector2 from; // point de départ
    private Vector2 to;  // Point d'arrivée

    private ArrayList<Vector2> stations;
    private HashMap<Vector2Couple,Integer> lineParts; // Tronçons associés à l'ID de leur ligne

    // Données calculées ou utilisées pour le calcul
    private HashMap<Vector2, Double> distances; // Distances de chaque point au point de départ
    private HashMap<Vector2,Boolean> visited; // Stations visitées
    private HashMap<Vector2,Vector2> preds; // Prédécesseurs

    // Données produites
    private ArrayList<Vector2> stationsOfPath; // Stations formant le chemin


    /**
     * Retourne les stations adjacentes associées au numéro de la ligne permettant de s'y rendre
     * @param of station source
     */
    private HashMap<Vector2,Integer> getAdjacentStations(Vector2 of) {
        HashMap<Vector2,Integer> res = new HashMap<>();

        for(Vector2Couple part : lineParts.keySet()) {
            if(part.getV1().equals(of))
                res.put(part.getV2(),lineParts.get(part));
            else if(part.getV2().equals(of))
                res.put(part.getV1(),lineParts.get(part));
        }
        return res;
    }

    /**
     * Actualise toutes les informations sur le graphe (stations, lignes) en cas de modifications
     */
    public void updateGraph() {
        stations = gm.getStations();
        lineParts = new HashMap<>();
        for(int i = 0; i<gm.linesCount(); i++) {
            for(Map.Entry<Vector2,Vector2> part : gm.getPartsVectorsOf(i)) {
                lineParts.put(new Vector2Couple(part.getKey(),part.getValue()),i);
            }
        }

        stationsOfPath = new ArrayList<>(); // On réinitialise le chemin si le graphe change
        distances.clear();
    }

    public void changeTarget(Vector2 newTarget) {
        this.to = newTarget;
        stationsOfPath = new ArrayList<>(); // On réinitialise le chemin si on change un point de départ/arrivée
    }

    public void changeStart(Vector2 newStart) {
        this.from = newStart;
        resetDistances();
        stationsOfPath = new ArrayList<>(); // On réinitialise le chemin si on change un point de départ/arrivée
    }

    private void resetDistances() {
         distances = new HashMap<>();
        // On initialise toutes les distances à +infini
        for(Vector2 v : stations) {
            distances.put(v,Double.POSITIVE_INFINITY);
        }

        // On définit la distance du départ à 0
        distances.remove(from);
        distances.put(from,0d);

        computeDistances();
    }

    /**
     * Calcule la distance de chaque station au point de départ
     */
    private void computeDistances() {
        ArrayList<Vector2> q = (ArrayList<Vector2>)stations.clone();

        while(q.size()>0) {
            Vector2 v1 = getMinDistance(q);
            q.remove(v1);
            for(Vector2 v2 : getAdjacentStations(v1).keySet()) {
                updateDistance(v1,v2);
            }
        }
    }

    /**
     * Retourne la station de distance minimale parmi la liste fournie
     * @return
     */
    private Vector2 getMinDistance(ArrayList<Vector2> stations) {
        double min = Double.POSITIVE_INFINITY;
        Vector2 res = null;
        for(Vector2 v : stations) {
            if(distances.get(v)<min) {
                min = distances.get(v);
                res = v;
            }
        }
        return res;
    }

    private void updateDistance(Vector2 v1,Vector2 v2) {
        if(distances.get(v2) > distances.get(v1) + Vector2.Distance(v1,v2)) {
            distances.put(v2,distances.get(v1) + Vector2.Distance(v1,v2));
            preds.put(v2,v1);
        }
    }

    /**
     * Retourne les stations formant le plus court chemin (en distance) de from à to
     * @return
     */
    public ArrayList<Vector2> getPath() {
        if(distances.size()==0)
            resetDistances();

        ArrayList<Vector2> res = null;
        if(stationsOfPath.size() == 0) { // Si le chemin n'est pas déjà calculé
            Vector2 v = to;
            while(!v.equals(from)) {
                stationsOfPath.add(v);
                v = preds.get(v);
            }
            stationsOfPath.add(from);
        }

        Collections.reverse(stationsOfPath);
        res = (ArrayList<Vector2>)stationsOfPath.clone();

        return res;
    }


}
