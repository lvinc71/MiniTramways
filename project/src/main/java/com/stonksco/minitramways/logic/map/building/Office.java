package com.stonksco.minitramways.logic.map.building;

import com.stonksco.minitramways.logic.People;
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

	/**
	 * Retourne le nombre de personnes actuellement � cet endroit
	 * @return le nombre de personnes | -1 si le lieu ne "stocke" pas les personnes
	 */
	public int Amount() {
		// TODO - implement Office.Amount
		throw new UnsupportedOperationException();
	}

	/**
	 * D�place une personne dans l'endroit courant
	 * @param p
	 */
	public void Enter(People p) {
		// TODO - implement Office.Enter
		throw new UnsupportedOperationException();
	}

	/**
	 * Retire une personne de l'endroit courant
	 * @param p
	 */
	public void Exit(People p) {
		// TODO - implement Office.Exit
		throw new UnsupportedOperationException();
	}

}