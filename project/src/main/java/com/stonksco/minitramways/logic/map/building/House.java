package com.stonksco.minitramways.logic.map.building;

import com.stonksco.minitramways.logic.People;
import com.stonksco.minitramways.logic.map.Cell;
import com.stonksco.minitramways.logic.map.building.Building;

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
	 * Fait apparaétre une personne dans la maison.
	 */
	public People spawn() {
		// TODO - implement House.spawn
		throw new UnsupportedOperationException();
	}

	/**
	 * Retourne le nombre de personnes actuellement é cet endroit
	 * @return le nombre de personnes | -1 si le lieu ne "stocke" pas les personnes
	 */
	public int Amount() {
		// TODO - implement House.Amount
		throw new UnsupportedOperationException();
	}

	/**
	 * Déplace une personne dans l'endroit courant
	 * @param p
	 */
	public void Enter(People p) {
		// TODO - implement House.Enter
		throw new UnsupportedOperationException();
	}

	/**
	 * Retire une personne de l'endroit courant
	 * @param p
	 */
	public void Exit(People p) {
		// TODO - implement House.Exit
		throw new UnsupportedOperationException();
	}

}