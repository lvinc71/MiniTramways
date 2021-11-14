package com.stonksco.minitramways.logic.map;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.building.Station;
import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Représente une ligne de trams
 */
public class Line {

	/**
	 * Table de hachage qui associe chaque station de la ligne é un entier multiple de 100 qui correspond é sa position sur la ligne
	 */
	private HashMap<Integer,Station> stations;
	private ArrayList<Tramway> tramways;
	private LinePart first;
	/**
	 * Code couleur de la ligne
	 */
	private int color;

	/**
	 * Crée une nouvelle ligne entre deux stations
	 * @param startStation
	 * @param endStation
	 */
	public Line(Station startStation, Station endStation) {
		first = new LinePart(this,startStation.getCell().getCoordinates(), endStation.getCell().getCoordinates(),first);

	}

	/**
	 * Ajoute une station au début ou é la fin de la ligne
	 * @param place définit si on doit placer la station en début (true) ou en fin (false) de ligne
	 * @return true si succés
	 * @param s
	 * @param place
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
}