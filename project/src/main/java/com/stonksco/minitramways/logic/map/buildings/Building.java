package com.stonksco.minitramways.logic.map.buildings;

import com.stonksco.minitramways.logic.People;
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

	public void Enter(People p){
		this.people.add(p);
	}

	public void Exit(People p) {
		this.people.remove(p);
	}





}