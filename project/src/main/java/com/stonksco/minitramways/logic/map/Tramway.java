package com.stonksco.minitramways.logic.map;

import com.stonksco.minitramways.logic.People;
import com.stonksco.minitramways.logic.map.building.Station;

import java.util.ArrayList;

public class Tramway implements PlaceToBe {

	Line line;
	ArrayList<People> people;
	/**
	 * Station visit�e avant la station courante
	 * Cette donn�e permet de savoir dans quelle direction le tram se dirige
	 */
	private Station lastVisitedStation;
	/**
	 * Position sur la ligne, par tranches de pourcentages
	 * 
	 * Par exemple, si le tram se trouve � mi-chemin entre la deuxi�me et la troisi�me station de la ligne, alors cette donn�e vaut 250
	 */
	private double linePartPos;
	private LinePart currentPart;
	/**
	 * Nombre maximal de personnes que peut accueillir ce tram
	 */
	private int capacity;

	/**
	 * 
	 * @param s
	 */
	public boolean goTo(Station s) {
		// TODO - implement Tramway.goTo
		throw new UnsupportedOperationException();
	}

	/**
	 * Retourne la station visit�e avant la station courante
	 */
	public Station getLastStation() {
		// TODO - implement Tramway.getLastStation
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param line
	 */
	public Tramway() {
		// TODO - implement Tramway.Tramway
		throw new UnsupportedOperationException();
	}


	public void moveTo(LinePart part, int pos) {

	}

	/**
	 * Retourne le nombre de personnes actuellement � cet endroit
	 * @return le nombre de personnes | -1 si le lieu ne "stocke" pas les personnes
	 */
	public int Amount() {
		// TODO - implement Tramway.Amount
		throw new UnsupportedOperationException();
	}

	/**
	 * D�place une personne dans l'endroit courant
	 * @param p
	 */
	public void Enter(People p) {
		people.add(p);
	}

	/**
	 * Retire une personne de l'endroit courant
	 * @param p
	 */
	public void Exit(People p) {
		people.remove(p);
	}

	public int getPeopleAmount() {
		return people.size();
	}

}