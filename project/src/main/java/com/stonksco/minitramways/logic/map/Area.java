package com.stonksco.minitramways.logic.map;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.building.Building;

import java.util.ArrayList;

/**
 * Repr�sente un quartier d'un type donn� (r�sidentiel, commercial ou d'affaires)
 */
public class Area {

	ArrayList<Cell> cells;
	ArrayList<Building> buildings;
	/**
	 * Type de zone
	 */
	private AreaTypes type;

	/**
	 * Ajoute une case de la grille � la zone
	 * @param c
	 */
	public boolean addCell(Cell c) {
		// TODO - implement Area.addCell
		throw new UnsupportedOperationException();
	}

	/**
	 * Retire une case de la grille de la zone
	 * @param c
	 */
	public boolean removeCell(Cell c) {
		// TODO - implement Area.removeCell
		throw new UnsupportedOperationException();
	}

	/**
	 * Envoie chaque personne pr�sente dans les maisons (House) de la zone dans la station accessible la moins congestionn�e
	 */
	public void sendToStation() {
		// TODO - implement Area.sendToStation
		throw new UnsupportedOperationException();
	}

	/**
	 * Ajoute un b�timent � la table de hachage.
	 * Cette m�thode ne g�n�re aucun b�timent, elle l'ajoute simplement � la table pour simplifier l'acc�s.
	 * @param b
	 */
	private void addBuilding(Building b) {
		// TODO - implement Area.addBuilding
		throw new UnsupportedOperationException();
	}

	/**
	 * Retourne la densit� de la zone (rapport cases disponibles/cases occup�es)
	 */
	public double getDensity() {
		// TODO - implement Area.getDensity
		throw new UnsupportedOperationException();
	}

	/**
	 * Demande � la zone de g�n�rer un nouveau b�timent
	 */
	public boolean generateBuilding() {
		// TODO - implement Area.generateBuilding
		throw new UnsupportedOperationException();
	}

    public boolean isIn(Vector2 pos) {
		return cells.contains(Game.get().getMap().getCellAt(pos));
    }
}