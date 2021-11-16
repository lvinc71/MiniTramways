package com.stonksco.minitramways.logic.map;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.building.Building;
import com.stonksco.minitramways.logic.map.building.BuildingEnum;
import com.stonksco.minitramways.logic.map.building.Station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameMap {

    // Grille
    private HashMap<Vector2, Cell> grid = null;
    private Vector2 gridSize = null;

    // Elements de la map
    private HashMap<Integer,Line> lines;
    private HashMap<Integer, Area> areas;
    private HashMap<Vector2, Station> stations;


    public GameMap() {

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
                Game.Debug(3,"Added cell at "+ v);
            }
        }
        Game.Debug(1,"Grid initialized with "+grid.size()+" cells.");
    }

    /**
     * Initialise tous les composants de la map
     */
    public void init() {
        initGrid(36,23);
        lines = new HashMap<>();
        initAreas();
        initBuildings();

    }

    public Cell getCellAt(Vector2 v) {
        return grid.get(v);
    }

    public Vector2 getGridSize() {
        return gridSize.clone();
    }

    /**
     * Crée une ligne et retourne les éventuelles intersections engendrées
     * @param start
     * @param end
     * @return ArrayList vide si aucune ligne n'a été affectée ; Sinon liste des ID des lignes affectées
     */
    public ArrayList<Integer> CreateLine(Vector2 start, Vector2 end) {
        ArrayList<Integer> updatedLines = new ArrayList<>();

        Game.Debug(2,"Line creation initiated from "+start+" to "+end);
        int creationMode = 0; // Mode de création selon le contexte :
        // 0 = impossible de créer ;
        // 1 = création d'une nouvelle ligne complète OU Création d'une nouvelle ligne avec la deuxième station existante;
        // 2 = Extension d'une ligne existante depuis la première station OU Extension d'une ligne existante, mais la deuxième station existe déjà également

        if(getBuildingAt(start)==null) {
            // Création d'une nouvelle ligne
            if(getBuildingAt(end) instanceof Station) {
                // On crée une nouvelle ligne qu'on associe à la station de la deuxième case
                creationMode = 1;
            } else if(getBuildingAt(end) == null) {
                // On crée une nouvelle ligne complète
                creationMode = 1;
            }
        } else if(getBuildingAt(start) instanceof Station) {
            // On étend une ligne existante
            if(getBuildingAt(end) != null) {
                // Il existe un bâtiment sur la deuxième case
                if(getBuildingAt(end) instanceof Station) {
                    // On étend la ligne en ne créant aucune station
                    creationMode = 2;
                }
            } else {
                // Aucun bâtiment n'est sur la deuxième case
                creationMode = 2;
            }

        }

        switch(creationMode) {
            case 1 : // On crée une ligne complète, à partir de rien OU on
                // On crée la ligne, qui créera elle-même les stations nécessaires
                Line l = new Line(start,end,lines.size());
                lines.put(lines.size(),l);
                // On génère les intersections si nécessaire
                updatedLines.add(l.getID());
                HashMap<LinePart,Vector2> intersections =  checkIntersections(l.getParts().get(0));
                processIntersections(intersections,l.getParts().get(0));
                // Pour chaque intersection, on ajoute la ligne concernée à la liste des lignes mises à jour
                for(Map.Entry e : intersections.entrySet()) {
                    updatedLines.add(getIdOf(((LinePart)e.getKey()).getLine()));
                }
                break;
            case 2 : // On étend une ligne existante, la station end n'existe pas OU elle existe
                Line l2 = ((Station)getCellAt(start).getBuilding()).getLines()[0];
                LinePart lp2 = l2.extend(start,end);
                // On génère les intersections si nécessaire
                updatedLines.add(l2.getID());
                HashMap<LinePart,Vector2> intersections2 =  checkIntersections(lp2);
                processIntersections(intersections2,lp2);
                // Pour chaque intersection, on ajoute la ligne concernée à la liste des lignes mises à jour
                for(Map.Entry e : intersections2.entrySet()) {
                    updatedLines.add(getIdOf(((LinePart)e.getKey()).getLine()));
                }
                break;
            default: // Autres cas, ne rien créer
        }

        Game.Debug(1,"Line created from "+start+" to "+end);
        return updatedLines;
    }


    private void processIntersections(HashMap<LinePart,Vector2> intersections, LinePart with) {
        // Pour chaque ligne
        for(Line l : lines.values()) {
            // On lui donne chaque intersection, pour que le linepart concerné se divise
            for(Map.Entry e : intersections.entrySet()) {
                if(l.divide(((LinePart)e.getKey()).getStartPos(),((LinePart)e.getKey()).getEndPos(),(Vector2)e.getValue())) {
                    with.divide(with.getStartPos(),with.getEndPos(),(Vector2)e.getValue());
                    Station s = addStation((Vector2)e.getValue());
                    s.addLine(l);
                    s.addLine(with.getLine());
                    Game.Debug(2,"Intersection processed : Station créée en "+(Vector2)e.getValue());
                }
            }
        }

    }

    public Station addStation(Vector2 at) {
        if(stations==null)
            stations = new HashMap<>();
        Station s = new Station(getCellAt(at));
        stations.put(at,s);
        return s;
    }


    private ArrayList<LinePart> alreadyChecked;

    /**
     * Vérifie si des intersections entre des tronçons doivent être générées
     * @param newPart
     * @return
     */
    public HashMap<LinePart,Vector2> checkIntersections(LinePart newPart) {
        if(alreadyChecked==null)
            alreadyChecked=new ArrayList<>();

        HashMap<LinePart,Vector2> intersections = new HashMap<>();
        for(Line l : lines.values()) {
            for(LinePart lp : l.getParts()) {
                Vector2 v = Vector2.getIntersectionOf(newPart.getStartPos(),newPart.getEndPos(),lp.getStartPos(),lp.getEndPos());
                if(v!=null)
                    intersections.put(lp,v);
            }
        }
        alreadyChecked.add(newPart);
        return intersections;
    }

    public Building getBuildingAt(Vector2 cell) {
        return getCellAt(cell).getBuilding();
    }


    public HashMap<Integer, HashMap<Vector2, com.stonksco.minitramways.logic.map.Cell>> getAreas() {

        return null;
    }

    public void initAreas() {
        areas = new HashMap<>();

        ArrayList<Cell> list1 = new ArrayList<>();
        list1.add(Game.get().getMap().grid.get(new Vector2(8.0,1.0)));//
        list1.add(Game.get().getMap().grid.get(new Vector2(1.0,0.0)));//
        list1.add(Game.get().getMap().grid.get(new Vector2(2.0,0.0)));//
        list1.add(Game.get().getMap().grid.get(new Vector2(3.0,0.0)));
        Area shop = new Area(list1, AreaTypes.shopping);

        ArrayList<Cell> list2 = new ArrayList<>();
        list2.add(Game.get().getMap().grid.get(new Vector2(0.0,19.0)));//
        list2.add(Game.get().getMap().grid.get(new Vector2(0.0,20.0)));//
        list2.add(Game.get().getMap().grid.get(new Vector2(0.0,21.0)));//
        list2.add(Game.get().getMap().grid.get(new Vector2(0.0,22.0)));
        Area office = new Area(list2,AreaTypes.office);

        ArrayList<Cell> list3 = new ArrayList<>();
        list3.add(Game.get().getMap().grid.get(new Vector2(14.0,19.0)));
        list3.add(Game.get().getMap().grid.get(new Vector2(13.0,20.0)));
        list3.add(Game.get().getMap().grid.get(new Vector2(10.0,21.0)));
        list3.add(Game.get().getMap().grid.get(new Vector2(12.0,22.0)));
        Area residence = new Area(list3,AreaTypes.residential);

        areas.put(0,shop);
        areas.put(1,office);
        areas.put(2,residence);

    }

    public void initBuildings() {
        for(Area a : areas.values()) {
            boolean exit=false;
            while (a.getDensity()<0.4d && !exit) {
                if(!a.generateBuilding())
                    exit=true;
            }
            Game.Debug(1,"Generated "+a.getBuildings().size()+" buildings in "+a.getType()+" area.");
        }
    }


    public Area getAreaOf(Vector2 pos) {
        Area res = null;
        for (Area a : areas.values()) {
            if(a.isIn(pos)){
                res=a;
                break;
            }

        }
        return res;
    }

    /**
     * Retourne l'ID de la ligne passée en paramètre
     * @param l
     * @return
     */
    public int getIdOf(Line l) {
        int res = -1;
        for(Map.Entry e : lines.entrySet()) {
            if(e.getValue()==l)
                res = (Integer)e.getKey();
            break;
        }
        return res;
    }

    public Area getAreas(int i ){
        return areas.get(i);
    }

    public int getNombreArea(){
        return areas.size();

    }

    public HashMap<BuildingEnum,ArrayList<Vector2>> getBuildings(){
        HashMap<BuildingEnum,ArrayList<Vector2>> hm = new HashMap<>();
        ArrayList<Cell> tempC = new ArrayList<>();
        ArrayList<Vector2> tempV = new ArrayList<>();

        for(Area a : areas.values()) {
            switch(a.getType()) {
                case residential:
                    tempC = a.getCells();
                    for(int b=0; b<tempC.size();b++){
                        if(tempC.get(b).getBuilding()!=null) {
                            tempV.add(tempC.get(b).getCoordinates());

                        }
                    }
                    hm.put(BuildingEnum.HOUSE,tempV);
                    break;

                case office:
                    tempC = a.getCells();
                    for(int b=0; b<tempC.size();b++){
                        if(tempC.get(b).getBuilding()!=null) {
                            tempV.add(tempC.get(b).getCoordinates());

                        }
                    }
                    hm.put(BuildingEnum.OFFICE,tempV);
                    break;

                case shopping:
                    tempC = a.getCells();
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
