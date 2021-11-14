package com.stonksco.minitramways.logic.map.building;

import com.stonksco.minitramways.logic.People;
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

	/**
	 * Retourne le nombre de personnes actuellement � cet endroit
	 * @return le nombre de personnes | -1 si le lieu ne "stocke" pas les personnes
	 */
	public int Amount() {
		// TODO - implement Shop.Amount
		throw new UnsupportedOperationException();
	}

	/**
	 * D�place une personne dans l'endroit courant
	 * @param p
	 */
	public void Enter(People p) {
		// TODO - implement Shop.Enter
		throw new UnsupportedOperationException();
	}

	/**
	 * Retire une personne de l'endroit courant
	 * @param p
	 */
	public void Exit(People p) {
		// TODO - implement Shop.Exit
		throw new UnsupportedOperationException();
	}

}