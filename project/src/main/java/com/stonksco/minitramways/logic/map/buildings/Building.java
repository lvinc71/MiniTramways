package com.stonksco.minitramways.logic.map.buildings;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.people.People;
import com.stonksco.minitramways.logic.map.Cell;
import com.stonksco.minitramways.logic.map.PlaceToBe;

import java.util.ArrayList;

/**
 * Représente un bâtiment
 */
public abstract class Building implements PlaceToBe {

	Cell cell;
	protected ArrayList<People> people;

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
		c.setBuilding(this);
		people = new ArrayList<>();
	}

	@Override
	public int Amount() {
		return people.size();
	}

	@Override
	public void Enter(People p){
		this.people.add(p);
	}

	@Override
	public void Exit(People p) {
		this.people.remove(p);
	}

	@Override
	public Vector2 getCoordinates() {
		return this.cell.getCoordinates();
	}




}