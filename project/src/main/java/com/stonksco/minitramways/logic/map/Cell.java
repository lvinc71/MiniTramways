package com.stonksco.minitramways.logic.map;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.buildings.Building;

/**
 * Représente une case de la grille
 */
public class Cell {

	private final GameMap map;

	private Area area;
	private Building building;
	private final Vector2 coordinates;


	/**
	 * Retourne true si aucun bétiment n'est présent sur cette case
	 */
	public Building getBuilding() {
		return building;
	}

	/**
	 * Retourne la zone é laquelle la case appartient
	 */
	public Area getArea() {
		return this.area;
	}

	/**
	 * Définit le bétiment occupant la zone
	 * @param b
	 */
	public void setBuilding(Building b) {
		this.building = b;
	}

	/**
	 * Définit la zone é laquelle appartient cette case
	 * @param a
	 */
	public void setArea(Area a) {
		this.area = a;
	}

	/**
	 * 
	 * @param pos
	 */
	public Cell(Vector2 pos) {
		map= Game.get().getMap();
		this.coordinates = pos;
	}

	public Vector2 getCoordinates() {
		return coordinates.clone();
	}

}