package com.stonksco.minitramways.logic;

import com.stonksco.minitramways.logic.map.PlaceToBe;

public class People {

	PlaceToBe place;
	/**
	 * Temps en secondes depuis lequel la personne attend (n'est pas incrémenté si la personne n'a aucun target, et est réinitialisé é 0 dés que la personne se déplace)
	 */
	private double waitingSince;

	/**
	 * Incrémente le temps depuis lequel la personne attend
	 * @param time
	 */
	public void addWait(double time) {
		// TODO - implement People.addWait
		throw new UnsupportedOperationException();
	}

	/**
	 * Retourne le temps depuis lequel la personne attend
	 */
	public double getWait() {
		// TODO - implement People.getWait
		throw new UnsupportedOperationException();
	}

	/**
	 * Réinitialise le temps depuis lequel la personne attend é 0.
	 */
	public void resetWait() {
		// TODO - implement People.resetWait
		throw new UnsupportedOperationException();
	}

	public void moveTo(PlaceToBe place) {
		this.place.Exit(this);
		place.Enter(this);
	}

}