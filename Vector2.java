/**
 * Classe qui représente un vecteur à deux valeurs.
 * Elle peut être utilisée pour représenter des coordonnées, des mouvements, ou encore calculer des distances.
 */
public class Vector2 {

	private double x;
	private double y;

	public double getX() {
		return this.x;
	}

	/**
	 * 
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	/**
	 * 
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Retourne la somme du vecteur courant et de celui passé en paramètre
	 * @param v
	 */
	public Vector2 add(Vector2 v) {
		// TODO - implement Vector2.add
		throw new UnsupportedOperationException();
	}

	/**
	 * Retourne la soustraction du vecteur passé en paramètre au vecteur courant
	 * @param v
	 */
	public Vector2 sub(Vector2 v) {
		// TODO - implement Vector2.sub
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param from
	 * @param to
	 */
	public static double distanceBetween(Vector2 from, Vector2 to) {
		// TODO - implement Vector2.distanceBetween
		throw new UnsupportedOperationException();
	}

}