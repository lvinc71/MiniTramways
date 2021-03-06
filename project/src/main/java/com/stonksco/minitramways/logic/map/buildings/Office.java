package com.stonksco.minitramways.logic.map.buildings;

import com.stonksco.minitramways.logic.map.Cell;

/**
 * Repr�sente un b�timent de bureaux
 */
public class Office extends Building {

	/**
	 * @param c
	 */
	public Office(Cell c) {
		super(c);
	}


	@Override
	public String toString() {
		return "Office:"+getCoordinates();
	}

}