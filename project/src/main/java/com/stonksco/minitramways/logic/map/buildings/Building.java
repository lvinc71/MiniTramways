package com.stonksco.minitramways.logic.map.buildings;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.people.People;
import com.stonksco.minitramways.logic.map.Cell;
import com.stonksco.minitramways.logic.map.PlaceToBe;

import java.util.ArrayList;
import java.util.Objects;

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
		people.add(p);
		p.getCurrentPlace().Exit(p);
		p.setCurrentPlace(this);
	}

	@Override
	public void Exit(People p) {
		this.people.remove(p);
	}

	@Override
	public Vector2 getCoordinates() {
		return this.cell.getCoordinates();
	}

	@Override
	public String toString() {
		return "Building:"+getCoordinates();
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Building building = (Building) o;
		return cell.equals(building.cell) && people.equals(building.people);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cell, people);
	}
}