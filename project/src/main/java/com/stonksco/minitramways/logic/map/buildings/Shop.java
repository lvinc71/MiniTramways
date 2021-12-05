package com.stonksco.minitramways.logic.map.buildings;

import com.stonksco.minitramways.logic.map.Cell;

/**
 * Repr�sente un commerce
 */
public class Shop extends Building {

	/**
	 * @param c
	 */
	public Shop(Cell c) {
		super(c);
	}


	@Override
	public String toString() {
		return "Shop:"+getCoordinates();
	}

}