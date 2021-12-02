package com.stonksco.minitramways.logic.map;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.people.People;

/**
 * Repr�sente un endroit o� des personnes peuvent se trouver
 */
public interface PlaceToBe {

	/**
	 * Retourne le nombre de personnes actuellement � cet endroit
	 * @return le nombre de personnes | -1 si le lieu ne "stocke" pas les personnes
	 */
	int Amount();

	/**
	 * D�place une personne dans l'endroit courant
	 * @param p
	 */
	void Enter(People p);

	/**
	 * Retire une personne de l'endroit courant
	 * @param p
	 */
	void Exit(People p);

    Vector2 getCoordinates();
}