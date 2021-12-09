package com.stonksco.minitramways.logic.map.buildings;

import com.stonksco.minitramways.logic.people.People;
import com.stonksco.minitramways.logic.map.Cell;

/**
 * Représente une maison
 */
public class House extends Building {

	/**
	 * @param c
	 */
	public House(Cell c) {
		super(c);
	}

	/**
	 * Fait apparaître une personne dans la maison.
	 */
	public People spawn() {
		People p = new People(this);
		this.people.add(p);
		return p;
	}

	@Override
	public String toString() {
		return "House:"+getCoordinates();
	}


}