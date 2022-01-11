package com.stonksco.minitramways.logic.map;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.buildings.*;
import com.stonksco.minitramways.logic.map.generation.BuildingsGenerator;
import com.stonksco.minitramways.logic.map.generation.PeopleGenerator;
import com.stonksco.minitramways.logic.map.lines.Line;
import com.stonksco.minitramways.logic.map.lines.LinePart;
import com.stonksco.minitramways.logic.map.lines.Tramway;
import com.stonksco.minitramways.logic.people.People;

import java.util.*;
import java.util.function.ToDoubleFunction;

public class GameMap {

    // Grille
    private HashMap<Vector2, Cell> grid = null;
    private Vector2 gridSize = null;

    // Elements de la map
    private HashMap<Integer, Line> lines;
    private HashMap<Integer, Area> areas;
    private HashMap<Vector2, Station> stations;

    // générateurs
    private BuildingsGenerator bg;
    private PeopleGenerator pg;


    public GameMap() {
        bg = new BuildingsGenerator(this);
        pg = new PeopleGenerator(this);
        lines = new HashMap<>();
        areas = new HashMap<>();
        stations = new HashMap<>();
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
        bg.initBuildings();

    }

    public Cell getCellAt(Vector2 v) {
        return grid.get(v);
    }

    public Vector2 getGridSize() {
        return gridSize.clone();
    }


    private ArrayList<Integer> linesToUpdate;

    /**
     * Crée une ligne et retourne son id
     * @param start
     * @param end
     * @return ArrayList vide si aucune ligne n'a été affectée ; Sinon liste des ID des lignes affectées
     */
    public Line CreateLine(Vector2 start, Vector2 end) {
        linesToUpdate = new ArrayList<>();
        Line createdLine = null;

        Game.Debug(2,"Line modification initiated from "+start+" to "+end);
        int creationMode = 0; // Mode de création selon le contexte :
        // 0 = impossible de créer ;
        // 1 = création d'une nouvelle ligne complète OU Création d'une nouvelle ligne avec la deuxième station existante

        if(getBuildingAt(start)==null || getBuildingAt(start) instanceof Station) {
            // Création d'une nouvelle ligne
            if(getBuildingAt(end) instanceof Station) {
                    // On crée une nouvelle ligne qu'on associe à la station de la deuxième case
                    creationMode = 1;

            } else if(getBuildingAt(end) == null) {
                // On crée une nouvelle ligne complète
                creationMode = 1;
            }
        }

        if(creationMode==1) { // On crée une ligne complète, à partir de rien
                // On crée la ligne, qui créera elle-même les stations nécessaires
                Line l = new Line(start,end,lines.size());
                lines.put(lines.size(),l);
                // On génère les intersections si nécessaire
                linesToUpdate.add(l.getID());
                HashMap<LinePart,Vector2> intersections =  checkIntersections(l.getFirstPart());
                processIntersections(intersections,l.getFirstPart());
                // Pour chaque intersection, on ajoute la ligne concernée à la liste des lignes mises à jour
                for(Map.Entry e : intersections.entrySet()) {
                    linesToUpdate.add(getIdOf(((LinePart)e.getKey()).getLine()));
                }
                Game.Debug(1,"Line "+l.getID()+" created from "+start+" to "+end);
                createdLine = l;
        }

        Game.Debug(2,"Updated lines : "+linesToUpdate);

        if(linesToUpdate.size()>0)
            People.UpdateGraph();

        return createdLine;
    }

    /**
     * Étend une ligne existante depuis une extrémité et retourne son ID
     * @param start
     * @param end
     * @return ArrayList vide si aucune ligne n'a été affectée ; Sinon liste des ID des lignes affectées
     */
    public Line ExtendLine(Vector2 start, Vector2 end, Integer lineID) {
        linesToUpdate = new ArrayList<>();
        Line extendedLine = null;

        Line l2 = null;
        if(lineID!=null) {
            l2 = lines.get(lineID);
        } else {
            l2 = ((Station)getCellAt(start).getBuilding()).getLines()[0];
        }


        Game.Debug(2,"Line modification initiated from "+start+" to "+end);
        int creationMode = 0; // Mode de création selon le contexte :
        // 0 = impossible de créer ;
        // 2 = Extension d'une ligne existante depuis la première station OU Extension d'une ligne existante, mais la deuxième station existe déjà également

        if(getBuildingAt(start) instanceof Station) {
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

        if(creationMode==2) {
            // On étend une ligne existante, la station end n'existe pas OU elle existe

            LinePart lp2 = l2.extend(start,end);
            if(lp2!=null) {
                // On génère les intersections si nécessaire
                linesToUpdate.add(l2.getID());
                HashMap<LinePart,Vector2> intersections2 =  checkIntersections(lp2);
                processIntersections(intersections2,lp2);
                // Pour chaque intersection, on ajoute la ligne concernée à la liste des lignes mises à jour
                for(Map.Entry e : intersections2.entrySet()) {
                    linesToUpdate.add(getIdOf(((LinePart)e.getKey()).getLine()));
                }
                Game.Debug(1,"Line "+l2.getID()+" extended from "+start+" to "+end);
                extendedLine=l2;
            }
        }

        Game.Debug(2,"Updated lines : "+linesToUpdate);

        if(linesToUpdate.size()>0)
            People.UpdateGraph();

        return extendedLine;
    }


    public ArrayList<Integer> getLinesToUpdate() {
        if(linesToUpdate==null)
            linesToUpdate=new ArrayList<>();

        ArrayList<Integer> temp = (ArrayList<Integer>) linesToUpdate.clone();
        linesToUpdate.clear();
        return temp;
    }


    private void processIntersections(HashMap<LinePart,Vector2> intersections, LinePart with) {

        // Pour chaque ligne
        for(Line l : lines.values()) {
            // On lui donne chaque intersection, pour que le linepart concerné se divise
            for(Map.Entry e : intersections.entrySet()) {
                if(getBuildingAt((Vector2)e.getValue())==null) {
                    if (l.divide(((LinePart) e.getKey()).getStartPos(), ((LinePart) e.getKey()).getEndPos(), (Vector2) e.getValue()) != null) {
                        //with.divide(with.getStartPos(),with.getEndPos(),(Vector2)e.getValue());
                        Station s = addStation((Vector2) e.getValue());
                        s.addLine(l);
                        Game.Debug(2, "Intersection processed : Station created at " + e.getValue());
                    }
                }
            }
        }


        // On crée une liste de toutes les coordonnées d'intersections puis on les ordonne de la plus proche à la plus éloignée du point de départ
        List<Vector2> divisionsByDistance = new ArrayList<>();
        for(Vector2 v : intersections.values()) {
            if(!divisionsByDistance.contains(v))
                divisionsByDistance.add(v);
        }

        Collections.sort(divisionsByDistance, Comparator.comparingDouble(new ToDoubleFunction<Vector2>() {
            @Override
            public double applyAsDouble(Vector2 v) {
                return Vector2.Distance(v, with.getStartPos());
            }
        }));


        if(divisionsByDistance.size()>0)
            Game.Debug(1,"Divisions needed for "+with+" : "+divisionsByDistance);
        else
            Game.Debug(1,"No divisions needed for "+with);


        LinePart partToDivide = with;
        for(Vector2 v : divisionsByDistance) {
            partToDivide = partToDivide.divide(partToDivide.getStartPos(),partToDivide.getEndPos(),v);
            if(partToDivide!=null) {
                ((Station) getBuildingAt(v)).addLine(with.getLine());
                for (People p : People.getAll()) {
                    p.addIntersectionBetween(v, partToDivide.getStartPos(), partToDivide.getEndPos());
                }
            }
        }

        People.UpdateGraph();


    }

    /**
     * Boucle 'en spirale' autour du point donné, jusqu'à trouver une case vide
     * @return position de la case vide la plus proche
     */
    public Vector2 getClosestEmptyOrStation(Vector2 from) {
        Game.Debug(2,"Searching for closest linkable cell from "+from+"...");
        Vector2 res = null;
        from = from.round();

        // (di, dj) is a vector - direction in which we move right now
        int dx = 1;
        int dy = 0;
        // length of current segment
        int segment_length = 1;

        // current position (i, j) and how much of current segment we passed
        int x = (int)from.getX();
        int y = (int)from.getY();
        int segment_passed = 0;
        for (int k = 0; k < 15; ++k) {

            Cell checkedCell = getCellAt(new Vector2(x,y));
            if(checkedCell!=null) {
                if(checkedCell.getBuilding()==null || checkedCell.getBuilding() instanceof Station)
                    res = checkedCell.getCoordinates();
            }

            if(res!=null)
                break;

            // make a step, add 'direction' vector (di, dj) to current position (i, j)
            x += dx;
            y += dy;

            ++segment_passed;

            if (segment_passed == segment_length) {
                // done with current segment
                segment_passed = 0;

                // 'rotate' directions
                int buffer = dx;
                dx = -dy;
                dy = buffer;

                // increase segment length if necessary
                if (dy == 0) {
                    ++segment_length;
                }
            }
        }
        Game.Debug(2,"Found "+res);
        return res;
    }

    public Station addStation(Vector2 at) {
        if(stations==null)
            stations = new HashMap<>();
        Station s = new Station(getCellAt(at));
        getCellAt(at).setBuilding(s);
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
        if(newPart!=null) {
            for (Line l : lines.values()) {
                for (LinePart lp : l.getParts()) {
                    if (newPart != null && newPart != lp &&
                            !newPart.getStartPos().equals(lp.getEndPos()) &&
                            !newPart.getStartPos().equals(lp.getStartPos()) &&
                            !newPart.getEndPos().equals(lp.getStartPos()) &&
                            !newPart.getEndPos().equals(lp.getEndPos())) {
                        Vector2 v = Vector2.getIntersectionOf(newPart.getStartPos(), newPart.getEndPos(), lp.getStartPos(), lp.getEndPos());
                        if (v != null)
                            intersections.put(lp, getClosestEmptyOrStation(v.round()));
                    }
                }
                alreadyChecked.add(newPart);
            }
        }
        Game.Debug(1,"Found intersections for part "+newPart+" : "+intersections.values());
        return intersections;
    }

    public Building getBuildingAt(Vector2 cell) {
        return getCellAt(cell).getBuilding();
    }


    public HashMap<Integer, Area>  getAreas() {
        return areas;
    }

    public void initAreas() {
        areas = new HashMap<>();

        ArrayList<Cell> list1 = new ArrayList<>();
        list1.add(Game.get().getMap().grid.get(new Vector2(13,0)));
        list1.add(Game.get().getMap().grid.get(new Vector2(14,0)));
        list1.add(Game.get().getMap().grid.get(new Vector2(15,0)));
        list1.add(Game.get().getMap().grid.get(new Vector2(16,0)));
        list1.add(Game.get().getMap().grid.get(new Vector2(17,0)));
        list1.add(Game.get().getMap().grid.get(new Vector2(18,0)));
        list1.add(Game.get().getMap().grid.get(new Vector2(19,0)));
        list1.add(Game.get().getMap().grid.get(new Vector2(20,0)));
        list1.add(Game.get().getMap().grid.get(new Vector2(21,0)));
        list1.add(Game.get().getMap().grid.get(new Vector2(22,0)));
        list1.add(Game.get().getMap().grid.get(new Vector2(23,0)));
        list1.add(Game.get().getMap().grid.get(new Vector2(13,1)));
        list1.add(Game.get().getMap().grid.get(new Vector2(14,1)));
        list1.add(Game.get().getMap().grid.get(new Vector2(15,1)));
        list1.add(Game.get().getMap().grid.get(new Vector2(16,1)));
        list1.add(Game.get().getMap().grid.get(new Vector2(17,1)));
        list1.add(Game.get().getMap().grid.get(new Vector2(18,1)));
        list1.add(Game.get().getMap().grid.get(new Vector2(19,1)));
        list1.add(Game.get().getMap().grid.get(new Vector2(20,1)));
        list1.add(Game.get().getMap().grid.get(new Vector2(21,1)));
        list1.add(Game.get().getMap().grid.get(new Vector2(22,1)));
        list1.add(Game.get().getMap().grid.get(new Vector2(23,1)));
        list1.add(Game.get().getMap().grid.get(new Vector2(14,2)));
        list1.add(Game.get().getMap().grid.get(new Vector2(15,2)));
        list1.add(Game.get().getMap().grid.get(new Vector2(16,2)));
        list1.add(Game.get().getMap().grid.get(new Vector2(17,2)));
        list1.add(Game.get().getMap().grid.get(new Vector2(18,2)));
        list1.add(Game.get().getMap().grid.get(new Vector2(19,2)));
        list1.add(Game.get().getMap().grid.get(new Vector2(20,2)));
        list1.add(Game.get().getMap().grid.get(new Vector2(21,2)));
        list1.add(Game.get().getMap().grid.get(new Vector2(22,2)));
        list1.add(Game.get().getMap().grid.get(new Vector2(23,2)));
        list1.add(Game.get().getMap().grid.get(new Vector2(24,2)));
        list1.add(Game.get().getMap().grid.get(new Vector2(14,3)));
        list1.add(Game.get().getMap().grid.get(new Vector2(15,3)));
        list1.add(Game.get().getMap().grid.get(new Vector2(16,3)));
        list1.add(Game.get().getMap().grid.get(new Vector2(17,3)));
        list1.add(Game.get().getMap().grid.get(new Vector2(18,3)));
        list1.add(Game.get().getMap().grid.get(new Vector2(19,3)));
        list1.add(Game.get().getMap().grid.get(new Vector2(20,3)));
        list1.add(Game.get().getMap().grid.get(new Vector2(21,3)));
        list1.add(Game.get().getMap().grid.get(new Vector2(22,3)));
        list1.add(Game.get().getMap().grid.get(new Vector2(23,3)));
        list1.add(Game.get().getMap().grid.get(new Vector2(24,3)));
        list1.add(Game.get().getMap().grid.get(new Vector2(14,4)));
        list1.add(Game.get().getMap().grid.get(new Vector2(15,4)));
        list1.add(Game.get().getMap().grid.get(new Vector2(16,4)));
        list1.add(Game.get().getMap().grid.get(new Vector2(17,4)));
        list1.add(Game.get().getMap().grid.get(new Vector2(18,4)));
        list1.add(Game.get().getMap().grid.get(new Vector2(19,4)));
        list1.add(Game.get().getMap().grid.get(new Vector2(20,4)));
        list1.add(Game.get().getMap().grid.get(new Vector2(21,4)));
        list1.add(Game.get().getMap().grid.get(new Vector2(22,4)));
        list1.add(Game.get().getMap().grid.get(new Vector2(23,4)));
        list1.add(Game.get().getMap().grid.get(new Vector2(24,4)));
        list1.add(Game.get().getMap().grid.get(new Vector2(15,5)));
        list1.add(Game.get().getMap().grid.get(new Vector2(22,5)));
        list1.add(Game.get().getMap().grid.get(new Vector2(23,5)));

        Area shop = new Area(list1, AreaTypes.shopping);

        ArrayList<Cell> list2 = new ArrayList<>();
        list2.add(Game.get().getMap().grid.get(new Vector2(29,6)));
        list2.add(Game.get().getMap().grid.get(new Vector2(30,6)));
        list2.add(Game.get().getMap().grid.get(new Vector2(31,6)));
        list2.add(Game.get().getMap().grid.get(new Vector2(32,6)));
        list2.add(Game.get().getMap().grid.get(new Vector2(33,6)));
        list2.add(Game.get().getMap().grid.get(new Vector2(34,6)));
        list2.add(Game.get().getMap().grid.get(new Vector2(35,6)));
        list2.add(Game.get().getMap().grid.get(new Vector2(29,7)));
        list2.add(Game.get().getMap().grid.get(new Vector2(30,7)));
        list2.add(Game.get().getMap().grid.get(new Vector2(31,7)));
        list2.add(Game.get().getMap().grid.get(new Vector2(32,7)));
        list2.add(Game.get().getMap().grid.get(new Vector2(33,7)));
        list2.add(Game.get().getMap().grid.get(new Vector2(34,7)));
        list2.add(Game.get().getMap().grid.get(new Vector2(35,7)));
        list2.add(Game.get().getMap().grid.get(new Vector2(29,8)));
        list2.add(Game.get().getMap().grid.get(new Vector2(30,8)));
        list2.add(Game.get().getMap().grid.get(new Vector2(31,8)));
        list2.add(Game.get().getMap().grid.get(new Vector2(32,8)));
        list2.add(Game.get().getMap().grid.get(new Vector2(33,8)));
        list2.add(Game.get().getMap().grid.get(new Vector2(34,8)));
        list2.add(Game.get().getMap().grid.get(new Vector2(35,8)));
        list2.add(Game.get().getMap().grid.get(new Vector2(29,9)));
        list2.add(Game.get().getMap().grid.get(new Vector2(30,9)));
        list2.add(Game.get().getMap().grid.get(new Vector2(31,9)));
        list2.add(Game.get().getMap().grid.get(new Vector2(32,9)));
        list2.add(Game.get().getMap().grid.get(new Vector2(33,9)));
        list2.add(Game.get().getMap().grid.get(new Vector2(34,9)));
        list2.add(Game.get().getMap().grid.get(new Vector2(35,9)));
        list2.add(Game.get().getMap().grid.get(new Vector2(29,10)));
        list2.add(Game.get().getMap().grid.get(new Vector2(30,10)));
        list2.add(Game.get().getMap().grid.get(new Vector2(31,10)));
        list2.add(Game.get().getMap().grid.get(new Vector2(32,10)));
        list2.add(Game.get().getMap().grid.get(new Vector2(33,10)));
        list2.add(Game.get().getMap().grid.get(new Vector2(34,10)));
        list2.add(Game.get().getMap().grid.get(new Vector2(35,10)));
        list2.add(Game.get().getMap().grid.get(new Vector2(29,11)));
        list2.add(Game.get().getMap().grid.get(new Vector2(30,11)));
        list2.add(Game.get().getMap().grid.get(new Vector2(31,11)));
        list2.add(Game.get().getMap().grid.get(new Vector2(32,11)));
        list2.add(Game.get().getMap().grid.get(new Vector2(33,11)));
        list2.add(Game.get().getMap().grid.get(new Vector2(34,11)));
        list2.add(Game.get().getMap().grid.get(new Vector2(35,11)));
        list2.add(Game.get().getMap().grid.get(new Vector2(29,12)));
        list2.add(Game.get().getMap().grid.get(new Vector2(30,12)));
        list2.add(Game.get().getMap().grid.get(new Vector2(31,12)));
        list2.add(Game.get().getMap().grid.get(new Vector2(32,12)));
        list2.add(Game.get().getMap().grid.get(new Vector2(33,12)));
        list2.add(Game.get().getMap().grid.get(new Vector2(34,12)));
        list2.add(Game.get().getMap().grid.get(new Vector2(35,12)));
        list2.add(Game.get().getMap().grid.get(new Vector2(29,13)));
        list2.add(Game.get().getMap().grid.get(new Vector2(30,13)));
        list2.add(Game.get().getMap().grid.get(new Vector2(31,13)));
        list2.add(Game.get().getMap().grid.get(new Vector2(32,13)));
        list2.add(Game.get().getMap().grid.get(new Vector2(33,13)));
        list2.add(Game.get().getMap().grid.get(new Vector2(34,13)));
        list2.add(Game.get().getMap().grid.get(new Vector2(35,13)));
        list2.add(Game.get().getMap().grid.get(new Vector2(29,14)));
        list2.add(Game.get().getMap().grid.get(new Vector2(30,14)));
        list2.add(Game.get().getMap().grid.get(new Vector2(31,14)));
        list2.add(Game.get().getMap().grid.get(new Vector2(32,14)));
        list2.add(Game.get().getMap().grid.get(new Vector2(33,14)));
        list2.add(Game.get().getMap().grid.get(new Vector2(34,14)));
        list2.add(Game.get().getMap().grid.get(new Vector2(35,14)));
        list2.add(Game.get().getMap().grid.get(new Vector2(29,15)));
        list2.add(Game.get().getMap().grid.get(new Vector2(30,15)));
        list2.add(Game.get().getMap().grid.get(new Vector2(31,15)));
        list2.add(Game.get().getMap().grid.get(new Vector2(32,15)));
        list2.add(Game.get().getMap().grid.get(new Vector2(33,15)));
        list2.add(Game.get().getMap().grid.get(new Vector2(34,15)));
        list2.add(Game.get().getMap().grid.get(new Vector2(35,15)));
        list2.add(Game.get().getMap().grid.get(new Vector2(28,7)));
        list2.add(Game.get().getMap().grid.get(new Vector2(28,8)));
        list2.add(Game.get().getMap().grid.get(new Vector2(28,12)));
        list2.add(Game.get().getMap().grid.get(new Vector2(28,13)));
        list2.add(Game.get().getMap().grid.get(new Vector2(28,14)));

        Area office = new Area(list2,AreaTypes.office);

        ArrayList<Cell> list3 = new ArrayList<>();
        for(int x =0; x<6; x++) {
            for(int y=6; y<23; y++)
                list3.add(Game.get().getMap().grid.get(new Vector2(x,y)));
        }
        Area residence = new Area(list3,AreaTypes.residential);

        areas.put(0,shop);
        areas.put(1,office);
        areas.put(2,residence);

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
        return l.getID();
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
                    tempV=new ArrayList<>();
                    tempC = a.getCells();
                    for(int b=0; b<tempC.size();b++){
                        if(tempC.get(b).getBuilding()!=null) {
                            tempV.add(tempC.get(b).getCoordinates());

                        }
                    }
                    hm.put(BuildingEnum.HOUSE,tempV);
                    break;

                case office:
                    tempV=new ArrayList<>();
                    tempC = a.getCells();
                    for(int b=0; b<tempC.size();b++){
                        if(tempC.get(b).getBuilding()!=null) {
                            tempV.add(tempC.get(b).getCoordinates());

                        }
                    }
                    hm.put(BuildingEnum.OFFICE,tempV);
                    break;

                case shopping:
                    tempV=new ArrayList<>();
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


    public List<Map.Entry<Vector2,Vector2>> getPartsVectorsOf(int lineID) {
        return lines.get(lineID).getPartsVectors();
    }

    public String getLineString(int lineID) {
        String res = "LINE "+lineID+" DOES NOT EXIST";
        Line l = lines.get(lineID);
        if(l!=null)
            res=l.toString();
        return res;
    }

    public ArrayList<Vector2> getStations() {
        ArrayList<Vector2> res = new ArrayList<>();
        res.addAll(stations.keySet());
        return res;
    }

    public boolean isAtExtremity(Vector2 pos) {
        boolean res=false;
        for(Line l : lines.values()) {
            res=l.isAtExtremity(pos);
            if(res) break;
        }
        return res;
    }

    public void Update() {
        // Déplacement des trams
        for(Line l : lines.values()) {
            l.Update();
        }
        // Génération des bâtiments
        bg.buildingsGeneration();
        pg.peopleGeneration();

    }

    public ArrayList<Tramway> getTramsOf(int lineID) {
        return this.lines.get(lineID).getTrams();
    }

    public int getFirstIndexOf(int lineID) {
        return this.lines.get(lineID).getFirstIndex();
    }

    public int getLastIndexOf(int lineID) {
        return this.lines.get(lineID).getLastIndex();
    }

    /**
     * Retourne le nombre de personnes à un endroit donné
     * @param pos l'endroit en question
     * @return nombre de personnes
     */
    public int getAmountOf(Vector2 pos) {
        int res = -1;
        if(this.getCellAt(pos).getBuilding() instanceof PlaceToBe) {
            res = getCellAt(pos).getBuilding().Amount();
        }
        return res;
    }

    private SplittableRandom randGen = new SplittableRandom();

    /**
     * Retourne une maison aléatoire
     * @return
     */
    public House getRandomHouse() {
        House res = null;

        Area a = null;
        while(a==null) {
            int nb = (int)(randGen.nextInt(areas.size()));
            a = areas.get(nb);
            if(a.getType() != AreaTypes.residential)
                a=null;
        }

        res = (House)a.getRandomBuilding();
        return res;
    }

    /**
     * Retourne un bâtiment commercial ou de bureaux aléatoire
     * @return
     */
    public Building getRandomTarget() {
        Building res = null;

        Area a = null;
        while(a==null) {
            int nb = (int)(randGen.nextInt(areas.size()));
            a = areas.get(nb);
            if(a.getType() == AreaTypes.residential)
                a=null;
        }

        res = a.getRandomBuilding();
        return res;
    }

    public int linesCount() {
        return lines.size();
    }

    /**
     * Retourne la station la plus proche
     * @param from
     * @return
     */
    public Vector2 getClosestStation(Vector2 from) {
        double min = Double.POSITIVE_INFINITY;
        Vector2 res = null;
        for(Vector2 s : stations.keySet()) {
            double d = Vector2.Distance(from,s);
            if(d<min && d<((Station)getBuildingAt(s)).radius()) {
                min = d;
                res = s;
            }
        }
        return res;
    }

    public PlaceToBe VectorToPlace(Vector2 v) {
        return getCellAt(v).getBuilding();
    }

    public boolean isOnLine(Vector2 station, int lineID) {
        boolean res = false;
        Station s = stations.get(station);

        if(s!=null) {
            for(Line l : s.getLines()) {
                if (l.getID() == lineID) {
                    res = true;
                    break;
                }
            }
        }
        return res;
    }

    public LinePart getPartBetween(Vector2 pos1, Vector2 pos2) {
        LinePart res = null;
        for(Line l : lines.values()) {
            for(LinePart lp : l.getParts()) {
                if( (lp.getStartPos().equals(pos1) && lp.getEndPos().equals(pos2)) || (lp.getStartPos().equals(pos2) && lp.getEndPos().equals(pos1))) {
                    res=lp;
                    break;
                }
            }
            if(res!=null)
                break;
        }
        return res;
    }

    public Integer[] getLinesID() {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.addAll(lines.keySet());
        return ids.toArray(new Integer[0]);
    }


    /**
     * Détruit la station passée en paramètre si cela est possible
     * @param stationtodestroy station à détruire
     * @return liste des lignes modifiées
     */
    public ArrayList<Integer> destroyStation(Vector2 stationtodestroy) {
        ArrayList<Integer> updatedLines = new ArrayList<>();

        if(getBuildingAt(stationtodestroy) instanceof Station) {
            Station s = (Station) getBuildingAt(stationtodestroy);
            boolean canContinue = true;

            // Si la station est bien sur une extrémité de chacune de ses lignes
            for(Line l : s.getLines()) {
                if(!l.isAtExtremity(stationtodestroy))
                    canContinue=false;
            }

            if(canContinue) {
                boolean canDestroy = true;
                for(Line l : s.getLines()) {
                    boolean trimmed = l.Trim(stationtodestroy);
                    if(trimmed) {
                        updatedLines.add(l.getID());
                        linesToUpdate.add(l.getID());
                        s.removeLine(l.getID());
                        // On rembourse 15 par tronçon détruit
                        Game.get().addMoney(15);
                    } else {
                        canDestroy=false;
                    }
                }
                if(canDestroy) {
                    stations.remove(stationtodestroy);
                    // On rembourse 30 si la station est détruite
                    Game.get().addMoney(30);
                }
                // Nettoyer la liste des stations (détruire celles qui ne sont plus reliées à rien)

            }
        }



        return updatedLines;
    }
}
