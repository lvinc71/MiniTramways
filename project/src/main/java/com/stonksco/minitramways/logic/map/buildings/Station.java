package com.stonksco.minitramways.logic.map.buildings;

import com.stonksco.minitramways.logic.people.People;
import com.stonksco.minitramways.logic.map.Cell;
import com.stonksco.minitramways.logic.map.lines.Line;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Représente une station de trams
 */
public class Station extends Building {

	HashMap<Integer,Line> lines;

	
	/**
	 * Personnes dans la station
	 */
	private ArrayList<People> people;

	public int getCapacity() {
		return capacity;
	}

	/**
	 * Nombre maximal de lignes qui peuvent désservir la station
	 */
	private int capacity;

	/**
	 * Rayon dans lequel la station est en mesure de désservir les bâtiments
	 */
	private final double radius;

	/**
	 * @param c
	 */
	public Station(Cell c) {
		super(c);
		people = new ArrayList<>();
		capacity = 14;
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


	@Override
	public String toString() {
		return "Building:"+getCoordinates();
	}

	public double radius() {
		return radius;
	}

}