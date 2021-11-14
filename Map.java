import java.util.*;

/**
 * Gère la carte et son contenu
 */
public class Map {

	ArrayList<Cell> cells;
	ArrayList<People> people;
	ArrayList<Line> lines;
	ArrayList<Station> stations;
	ArrayList<Area> areas;

	/**
	 * Retourne la case correspondant aux coordonnées passées en paramètre
	 * @param at
	 */
	public void getCellAt(Vector2 at) {
		// TODO - implement Map.getCellAt
		throw new UnsupportedOperationException();
	}

	/**
	 * Retourne l'ensemble des zones composant la carte
	 */
	public Area[] getAreas() {
		// TODO - implement Map.getAreas
		throw new UnsupportedOperationException();
	}

	/**
	 * Calcule le coût de construction d'une ligne
	 * @param start
	 * @param end
	 */
	public int calcLineCost(Vector2 start, Vector2 end) {
		// TODO - implement Map.calcLineCost
		throw new UnsupportedOperationException();
	}

	/**
	 * Convertit des coordonnées en pixels sur l'écran en coordonnées de la grille
	 * @param pixels
	 */
	public Vector2 pixelsToGrid(Vector2 pixels) {
		// TODO - implement Map.pixelsToGrid
		throw new UnsupportedOperationException();
	}

	/**
	 * Crée une ligne entre deux cases
	 * @param startCell
	 * @param endCell
	 */
	private bool createLine(Cell startCell, Cell endCell) {
		// TODO - implement Map.createLine
		throw new UnsupportedOperationException();
	}

	/**
	 * Initialise la grille
	 */
	private void initGrid() {
		// TODO - implement Map.initGrid
		throw new UnsupportedOperationException();
	}

	/**
	 * Lance la procédure d'initialisation de la carte (grille, zones)
	 */
	public void init() {
		// TODO - implement Map.init
		throw new UnsupportedOperationException();
	}

	/**
	 * Initialise les zones de la carte
	 */
	private void initAreas() {
		// TODO - implement Map.initAreas
		throw new UnsupportedOperationException();
	}

	/**
	 * Ajoute une personne à la carte (l'emplacement sera décidé ensuite)
	 * @param p
	 */
	public void addPeople(People p) {
		// TODO - implement Map.addPeople
		throw new UnsupportedOperationException();
	}

	/**
	 * Retire une personne de la carte (et de l'emplacement où elle se trouve actuellement)
	 * @param p
	 */
	public void remPeople(People p) {
		// TODO - implement Map.remPeople
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param c
	 */
	private void addCell(Cell c) {
		// TODO - implement Map.addCell
		throw new UnsupportedOperationException();
	}

}