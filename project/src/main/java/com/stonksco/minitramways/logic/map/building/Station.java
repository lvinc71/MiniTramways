package com.stonksco.minitramways.logic.map.building;

import com.stonksco.minitramways.logic.People;
import com.stonksco.minitramways.logic.map.Cell;
import com.stonksco.minitramways.logic.map.Line;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Représente une station de trams
 */
public class Station extends Building {

	HashMap<Integer,Line> lines;

	/**
	 * Nombre maximal de lignes qui peuvent désservir la station
	 */
	private int maxLines;

	/**
	 * Rayon dans lequel la station est en mesure de désservir les bâtiments
	 */
	private double radius;

	/**
	 * @param c
	 */
	public Station(Cell c) {
		super(c);
		maxLines = 4;
		radius = 4;
	}

	/**
	 * Ajoute une ligne é la station
	 * @return true si succès
	 * @param line
	 */
	public boolean addLine(Line line) {
		if(lines==null)
			lines = new HashMap<>();
		return !(lines.put(line.getID(),line)==null);
	}

	/**
	 * Retourne l'ensemble des lignes qui désservent par la station
	 */
	public Line[] getLines() {
		Line[] res;
		if(lines.size()>0)
			res = lines.values().toArray(new Line[0]);
		else
			res = null;
		return res;
	}

	/**
	 * Retourne le nombre de personnes actuellement é cet endroit
	 * @return le nombre de personnes | -1 si le lieu ne "stocke" pas les personnes
	 */
	public int Amount() {
		// TODO - implement Station.Amount
		throw new UnsupportedOperationException();
	}

	/**
	 * Déplace une personne dans l'endroit courant
	 * @param p
	 */
	public void Enter(People p) {
		// TODO - implement Station.Enter
		throw new UnsupportedOperationException();
	}

	/**
	 * Retire une personne de l'endroit courant
	 * @param p
	 */
	public void Exit(People p) {
		// TODO - implement Station.Exit
		throw new UnsupportedOperationException();
	}

}