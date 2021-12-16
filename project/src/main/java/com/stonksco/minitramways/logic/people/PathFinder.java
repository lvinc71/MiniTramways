package com.stonksco.minitramways.logic.people;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.Vector2Couple;
import com.stonksco.minitramways.logic.map.GameMap;
import com.stonksco.minitramways.logic.map.buildings.Station;

import java.util.*;

/**
 * Gère les calculs d'itinéraires
 */
public class PathFinder {

    private GameMap gm;

    public PathFinder(GameMap gm) {
        this.gm = gm;
        updateGraph();
        stationsOfPath=new ArrayList<>();
    }

    // Données de départ
    private Vector2 from; // point de départ
    private Vector2 to;  // Point d'arrivée

    private ArrayList<Vector2> vertices;
    private HashMap<Vector2Couple,Integer> lineParts; // Tronçons associés à l'ID de leur ligne

    // Données calculées ou utilisées pour le calcul
    private HashMap<Vector2, Double> distances;
    private HashMap<Vector2,Vector2> parents;
    private ArrayList<Vector2> open;
    private ArrayList<Vector2> closed;
    private HashMap<Vector2,Double> fvals;
    private boolean fromHasStation = false; // Si au moins une station déssert le point de départ
    private boolean toHasStation = false; // Si au moins une station déssert le point d'arrivée

    // Données produites
    private ArrayList<Vector2> stationsOfPath; // Stations formant le chemin


    /**
     * Retourne les nœuds adjacents associés au numéro de la ligne permettant de s'y rendre
     * @param of station source
     */
    private ArrayList<Vector2> getAdjacentVertices(Vector2 of) {
        ArrayList<Vector2> res = new ArrayList<>();

        for(Vector2Couple part : lineParts.keySet()) {
            if(part.getV1().equals(of))
                res.add(part.getV2());
            else if(part.getV2().equals(of))
                res.add(part.getV1());
        }
        return res;
    }

    /**
     * Actualise toutes les informations sur le graphe (stations, lignes) en cas de modifications
     */
    public void updateGraph() {
        // On récupère toutes les stations
        vertices = gm.getStations();
        // Puis tous les tronçons qui les relient
        lineParts = new HashMap<>();
        for(int i = 0; i<gm.linesCount(); i++) {
            for(Map.Entry<Vector2,Vector2> part : gm.getPartsVectorsOf(i)) {
                lineParts.put(new Vector2Couple(part.getKey(),part.getValue()),i);
            }
        }
        // Puis on crée des "faux tronçons" pour lier les points de départ et d'arrivée aux stations qui leur sont accessibles
        // Si l'une de ces étapes échoue, alors le chemin sera toujours nul
        for(Vector2 s : vertices) {
            if(Game.get().getMap().getBuildingAt(s) instanceof Station) {
                if(from!=null)
                    if(Vector2.Distance(s,from)< ((Station)Game.get().getMap().getBuildingAt(s)).radius()) {
                        lineParts.put(new Vector2Couple(s,from),-1);
                        fromHasStation=true;
                    }
                if(to!=null)
                    if(Vector2.Distance(s,to)< ((Station)Game.get().getMap().getBuildingAt(s)).radius()) {
                        lineParts.put(new Vector2Couple(s,to),-1);
                        toHasStation=true;
                    }
            }

        }

        vertices.add(from);
        vertices.add(to);

        stationsOfPath = new ArrayList<>(); // On réinitialise le chemin si le graphe change
    }

    public void changeTarget(Vector2 newTarget) {
        this.to = newTarget;
        stationsOfPath = new ArrayList<>(); // On réinitialise le chemin si on change un point de départ/arrivée
        toHasStation=false;
        updateGraph();
    }

    public void changeStart(Vector2 newStart) {
        this.from = newStart;
        stationsOfPath = new ArrayList<>(); // On réinitialise le chemin si on change un point de départ/arrivée
        fromHasStation=false;
        updateGraph();
    }



    /**
     * Retourne les stations formant le plus court chemin (en distance) de from à to
     * @return
     */
    public ArrayList<Vector2> getPath() {
        if(stationsOfPath.size()==0 && fromHasStation && toHasStation) {
            ArrayList<Vector2> path = new ArrayList<>();

            open=new ArrayList<>();
            closed=new ArrayList<>();
            fvals = new HashMap<>();
            parents = new HashMap<>();
            distances = new HashMap<>();

            Vector2 current = from;
            double h = Vector2.AbstractDistance(current,to);
            fvals.put(current,h+0);
            distances.put(current,0d);

            while(!current.equals(to)) {
                for(Vector2 a : getAdjacentVertices(current)) {
                    if(!open.contains(a) && !closed.contains(a)) {
                        open.add(a);
                        double g = distances.get(current)+Vector2.AbstractDistance(current,a);
                        distances.put(a,g);
                        h = Vector2.AbstractDistance(a,to);
                        double f = g+h;
                        Double currentfval = fvals.get(a);
                        if(currentfval==null || currentfval<f) {
                            fvals.put(a,f);
                            parents.put(a,current);
                        }
                    }
                }
                closed.add(current);
                Vector2 lowest = getLowestFfromOpen();
                open.remove(lowest);
                current=lowest;
                if(current==null)
                    break;
            }

            // On construit le chemin en "remontant les parents" depuis l'arrivée
            current = to;
            while(!current.equals(from)) {
                path.add(current);
                current=parents.get(current);
                if(current==null) {
                    path.clear();
                    break;
                }
            }
            Collections.reverse(path);
            stationsOfPath=path;
            stationsOfPath.remove(from);
            stationsOfPath.remove(to);
        }
        return stationsOfPath;
    }

    private Vector2 getLowestFfromOpen() {
        Vector2 res = null;
        double min = Double.POSITIVE_INFINITY;
        for(Vector2 v : open) {
            if(fvals.get(v)<min) {
                res=v;
                min=fvals.get(v);
            }
        }
        return res;
    }


}
