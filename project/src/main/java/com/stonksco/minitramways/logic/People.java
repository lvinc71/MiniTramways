package com.stonksco.minitramways.logic;

import com.stonksco.minitramways.logic.map.PlaceToBe;

import java.util.ArrayList;

public class People {

	private static ArrayList<People> instances = new ArrayList<>();

	public static ArrayList<People> getAll() {
		return (ArrayList<People>)instances.clone();
	}

	PlaceToBe place;
	/**
	 * Temps en secondes depuis lequel la personne attend (n'est pas incrémenté si la personne n'a aucun target, et est réinitialisé à 0 dès que la personne se déplace)
	 */
	private double waitingSince;


	public People(PlaceToBe place) {
		instances.add(this);
		this.place = place;
	}

	/**
	 * Retourne le temps depuis lequel la personne attend
	 */
	public double getWait() {
		return waitingSince;
	}

	/**
	 * Réinitialise le temps depuis lequel la personne attend é 0.
	 */
	public void resetWait() {
		waitingSince=0;
	}

	public void moveTo(PlaceToBe place) {
		this.place.Exit(this);
		place.Enter(this);
	}

}