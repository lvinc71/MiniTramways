package com.stonksco.minitramways.logic.map;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.building.Station;
import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

	public int getID() {
		return id;
	}

	private final int id;



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
	}

	/**
	 * Ajoute une station au début ou é la fin de la ligne
	 * @param place définit si on doit placer la station en début (true) ou en fin (false) de ligne
	 * @param s
	 * @return true si succés
	 */
	public boolean addStation(Station s, boolean place) {
		// TODO - implement Line.addStation
		throw new UnsupportedOperationException();
	}

	/**
	 * Retourne la valeur de position maximale possible sur cette ligne
	 */
	public int getMaxPos() {
		// TODO - implement Line.getMaxPos
		throw new UnsupportedOperationException();
	}

	/**
	 * Retourne la valeur de position minimale possible sur cette ligne
	 */
	public int getMinPos() {
		// TODO - implement Line.getMinPos
		throw new UnsupportedOperationException();
	}

	/**
	 * Retourne les coordonnées sur l'écran correspondant é une position sur la ligne
	 * @param pos
	 */
	public Vector2 getPixelsAt(double pos) {
		// TODO - implement Line.getPixelsAt
		throw new UnsupportedOperationException();
	}

	/**
	 * Ajoute un tramway é la ligne
	 * @param t
	 */
	public void addTramway(Tramway t) {
		// TODO - implement Line.addTramway
		throw new UnsupportedOperationException();
	}

	/**
	 * étend une ligne
	 * @from la station depuis laquelle la ligne doit étre étendue
	 * @to la station vers laquelle la ligne doit étre étendue
	 * @param from
	 * @param to
	 */
	public boolean extend(Station from, Station to) {
		// TODO - implement Line.expand
		throw new UnsupportedOperationException();
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
	public boolean divide(Vector2 start, Vector2 end ,Vector2 at) {
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

		// Si from est une extrémité
		if(from.equals(first.getStartPos()) || from.equals(first.getLast().getEndPos())) {
			boolean needAdd = !(first==null);
			lp=new LinePart(this,from,to,first);

			if(needAdd)
				this.first.add(lp);

			Station endStation = null;
			// si la deuxième station existe déjà, alors on en crée pas et on ajoute la ligne
			if(Game.get().getMap().getCellAt(to).getBuilding() instanceof Station) {
				endStation = (Station)Game.get().getMap().getCellAt(to).getBuilding();
			} else {
				endStation = Game.get().getMap().addStation(to);
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

	public Set<Map.Entry<Vector2,Vector2>> getPartsVectors() {
		HashMap<Vector2,Vector2> res = new HashMap<>();
		for(LinePart p : first.getParts()) {
			res.put(p.getStartPos(),p.getEndPos());
		}
		return res.entrySet();
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
}