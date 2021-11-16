package com.stonksco.minitramways.logic.map;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.building.Building;
import com.stonksco.minitramways.logic.map.building.House;
import com.stonksco.minitramways.logic.map.building.Office;
import com.stonksco.minitramways.logic.map.building.Shop;

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

	// densité = nombre de building créé
	private int densite = 3;

	//Constructeur de la classe area qui prend en parametre une liste de cell et un type
	public Area(ArrayList<Cell> area,AreaTypes areasType) {
		buildings = new ArrayList<>();
		this.cells = area;
		this.type = areasType;
	}

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
		buildings.add(b);
	}

	/**
	 * Retourne la densit� de la zone (rapport cases disponibles/cases occup�es)
	 */
	public double getDensity() {
		int nbOfCells = cells.size();
		int notEmptyCells = buildings.size();
		double res = (double)notEmptyCells/(double)nbOfCells;
		return res;
	}

	/**
	 * Demande � la zone de g�n�rer un nouveau b�timent
	 */
	public boolean generateBuilding() {
		boolean res=false;
		switch (type){
			case office:
				Office o = new Office(cells.get(buildings.size()));
				cells.get(buildings.size()).setBuilding(o);
				addBuilding(o);
				res=true;
				break;
			case shopping:
				Shop s = new Shop(cells.get(buildings.size()));
				cells.get(buildings.size()).setBuilding(s);
				addBuilding(s);
				res=true;
				break;
			case residential:
				House h = new House(cells.get(buildings.size()));
				cells.get(buildings.size()).setBuilding(h);
				addBuilding(h);
				res=true;
				break;
		}
		return res;

	}

    public boolean isIn(Vector2 pos) {
		return cells.contains(Game.get().getMap().getCellAt(pos));
    }

	public AreaTypes getType() {
		return type;
	}

	public ArrayList<Building> getBuildings() {
		return buildings;
	}

	public ArrayList<Cell> getCells() {
		return cells;
	}
}