package com.stonksco.minitramways.logic.map;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.building.Station;
import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Représente une ligne de trams
 */
public class Line {

	/**
	 * Table de hachage qui associe chaque station de la ligne à un entier multiple de 100 qui correspond é sa position sur la ligne
	 */
	private HashMap<Integer,Station> stations;
	private ArrayList<Tramway> tramways;
	private LinePart first;

	public float getSpeed() {
		return speed;
	}

	private float speed = 1f;

	public int getID() {
		return id;
	}

	private final int id;

	public Tramway addTram() {
		if(tramways==null)
			tramways= new ArrayList<>();

		Tramway t = new Tramway(this);
		this.tramways.add(t);
		return t;
	}

	/**
	 * Code couleur de la ligne
	 */
	private int color;

	/**
	 * Crée une nouvelle ligne entre deux stations
	 * @param start
	 * @param end
	 */
	public Line(Vector2 start, Vector2 end,int id) {
		first = new LinePart(this,start, end,null);
		this.id = id;

		stations = new HashMap<>();

		Station startStation = Game.get().getMap().addStation(start);

		Station endStation = null;
		// si la deuxième station existe déjà, alors on en crée pas et on ajoute la ligne
		if(Game.get().getMap().getCellAt(end).getBuilding() instanceof Station) {
			endStation = (Station)Game.get().getMap().getCellAt(end).getBuilding();
		} else {
			endStation = Game.get().getMap().addStation(end);
		}


		stations.put(0,startStation);
		stations.put(100,endStation);
		startStation.addLine(this);
		endStation.addLine(this);

		addTram();
	}


	public LinePart getFirstPart() {
		return first;
	}

	/**
	 * Divise un tronçon en deux selon une station intermédiaire
	 * @param start Point de départ du tronçon à diviser
	 * @param end Point d'arrivée du tronçon à diviser
	 * @param at Endroit où la station intermédiaire est construite
	 * @return true si la division a bien été effectuée
	 */
	public LinePart divide(Vector2 start, Vector2 end ,Vector2 at) {
		return first.divide(start,end,at);
	}

	public ArrayList<LinePart> getParts() {
		ArrayList<LinePart> res = new ArrayList<>();
		LinePart lp = first;
		while(lp != null) {
			res.add(lp);
			lp = lp.getNext();
		}
		return res;
	}

	public LinePart extend(Vector2 from, Vector2 to) {
		LinePart lp = null;
		Vector2 fromPos = null;
		Vector2 toPos = null;

		if(from.equals(first.getStartPos())) {
			fromPos = to;
			toPos = from;
		} else if (from.equals(first.getLast().getEndPos())) {
			fromPos = from;
			toPos = to;
		}


		// Si from est une extrémité
		if(fromPos != null && toPos != null) {
			boolean needAdd = !(first==null);
			lp=new LinePart(this,fromPos,toPos,first);

			if(needAdd)
				this.first.add(lp);

			Station endStation = null;
			// si la deuxième station existe déjà, alors on en crée pas et on ajoute la ligne
			if(Game.get().getMap().getCellAt(toPos).getBuilding() instanceof Station) {
				endStation = (Station)Game.get().getMap().getCellAt(toPos).getBuilding();
			} else {
				endStation = Game.get().getMap().addStation(toPos);
			}

			if(lp.getStartPos()==first.getStartPos())
				stations.put(lp.getStart(),endStation);
			else
				stations.put(lp.getEnd(),endStation);

			endStation.addLine(this);
			((Station)Game.get().getMap().getCellAt(from).getBuilding()).addLine(this);

			Game.Debug(1,"Line "+id+" extended from "+from+" to "+to);
		}
		return lp;
	}

	public String toString() {
		String res = "-------EMPTY LINE------";
		if(first!=null)
			res= first.toStringFull();
		return res;
	}

	public List<Map.Entry<Vector2,Vector2>> getPartsVectors() {
		ArrayList<Map.Entry<Vector2,Vector2>> res = new ArrayList<>();
		for(LinePart p : first.getParts()) {
			res.add(new AbstractMap.SimpleEntry<Vector2,Vector2>(p.getStartPos(),p.getEndPos()));
		}
		return res;
	}

	public void setFirst(LinePart newFirst) {
		this.first= newFirst;
	}


	public boolean isAtExtremity(Vector2 pos) {
		boolean res = false;
		if(first.getStartPos().equals(pos))
			res=true;
		if(first.getLast().getEndPos().equals(pos))
			res=true;
		return res;
	}

	public void Update() {
		for(Tramway t : tramways) {
			// Pour chaque tram, on le fait avancer d'une certaine distance
			t.Update();
		}
	}

	public Vector2 getPositionAt(double at) {
		return first.getPosAt(at);
	}


	public LinePart getPartAt(double at) {
		return first.getPartAt(at);
	}

	public ArrayList<Tramway> getTrams() {
		return (ArrayList<Tramway>) this.tramways.clone();
	}

	public int getFirstIndex() {
		return first.getStart();
	}

	public int getLastIndex() {
		return first.getLast().getEnd();
	}
}