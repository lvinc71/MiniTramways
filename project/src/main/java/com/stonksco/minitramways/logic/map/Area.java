package com.stonksco.minitramways.logic.map;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.buildings.Building;
import com.stonksco.minitramways.logic.map.buildings.House;
import com.stonksco.minitramways.logic.map.buildings.Office;
import com.stonksco.minitramways.logic.map.buildings.Shop;

import java.util.ArrayList;
import java.util.Random;
import java.util.SplittableRandom;

/**
 * Repr�sente un quartier d'un type donn� (r�sidentiel, commercial ou d'affaires)
 */
public class Area {

	ArrayList<Cell> cells;
	ArrayList<Building> buildings;
	/**
	 * Type de zone
	 */
	private final AreaTypes type;

	// densité = nombre de building créé
	private final int densite = 3;

	//Constructeur de la classe area qui prend en parametre une liste de cell et un type
	public Area(ArrayList<Cell> area,AreaTypes areasType) {
		buildings = new ArrayList<>();
		this.cells = area;
		this.type = areasType;
	}

	/**
	 * Ajoute un b�timent � la table de hachage.
	 * Cette m�thode ne g�n�re aucun b�timent, elle l'ajoute simplement � la table pour simplifier l'acc�s.
	 * @param b
	 */
	private void addBuilding(Cell at) {
		switch (type){
			case office:
				Office o = new Office(at);
				at.setBuilding(o);
				buildings.add(o);
				break;
			case shopping:
				Shop s = new Shop(at);
				at.setBuilding(s);
				buildings.add(s);
				break;
			case residential:
				House h = new House(at);
				at.setBuilding(h);
				buildings.add(h);
				break;
		}

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

	private SplittableRandom randGen = new SplittableRandom();

	private Cell getRandomCell() {
		int rand = Math.round(randGen.nextInt(cells.size()-1));
		if(rand<0)
			rand=0;
		return cells.get(rand);
	}

	/**
	 * Demande � la zone de g�n�rer un nouveau b�timent
	 */
	public boolean generateBuilding() {
		boolean res=false;

		Cell c = null;

		int toGenerate = 1;
		if(type==AreaTypes.residential)
			toGenerate = 2+(int)(randGen.nextInt(3));

		for(int i = 0; i<500; i++) {
			c = getRandomCell();
			if(c!=null) {
				addBuilding(c);
				toGenerate--;
				res=true;
			}

			if(toGenerate==0)
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

	public Building getRandomBuilding() {
		Building res = null;

		int nb = randGen.nextInt(buildings.size());
		res = buildings.get(nb);
		return res;
	}


}