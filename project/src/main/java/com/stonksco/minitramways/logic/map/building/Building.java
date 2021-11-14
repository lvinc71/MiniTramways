package com.stonksco.minitramways.logic.map.building;

import com.stonksco.minitramways.logic.map.Cell;
import com.stonksco.minitramways.logic.map.PlaceToBe;

/**
 * Représente un bâtiment
 */
public abstract class Building implements PlaceToBe {

	Cell cell;

	/**
	 * Retourne la case correspondant au bâtiment
	 */
	public Cell getCell() {
		return this.cell;
	}

	/**
	 * 
	 * @param c
	 */
	public Building(Cell c) {
		this.cell = c;
	}

}