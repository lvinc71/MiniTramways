package com.stonksco.minitramways.logic.people;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.Cell;
import com.stonksco.minitramways.logic.map.PlaceToBe;
import com.stonksco.minitramways.logic.map.buildings.Building;
import com.stonksco.minitramways.logic.map.buildings.Station;

import java.util.ArrayList;

public class People {

	private static ArrayList<People> instances = new ArrayList<>();

	private static final PathFinder pf = new PathFinder(Game.get().getMap());

	public static ArrayList<People> getAll() {
		return (ArrayList<People>)instances.clone();
	}

	PlaceToBe place;
	PlaceToBe target;
	ArrayList<PlaceToBe> pathToFollow;
	/**
	 * Temps en secondes depuis lequel la personne attend (n'est pas incrémenté si la personne n'a aucun target, et est réinitialisé à 0 dès que la personne se déplace)
	 */
	private double waitingSince;


	public People(PlaceToBe place) {
		instances.add(this);
		this.place = place;
		pathToFollow = new ArrayList<>();

		getTarget();
	}

	private void getPath() {
		// La station la plus proche du lieu actuel devient la première étape du chemin à parcourir
		Vector2 closestStationStart = Game.get().getMap().getClosestStation(place.getCoordinates());
		Vector2 closestStationTarget = Game.get().getMap().getClosestStation(target.getCoordinates());
		if(closestStationStart!=null && closestStationTarget!=null) {
			pf.changeStart(closestStationStart);
			pf.changeTarget(closestStationTarget);
			ArrayList<Vector2> foundPath = pf.getPath();
			if(foundPath!=null) {
				for(Vector2 v : foundPath) {
					pathToFollow.add(Game.get().getMap().VectorToPlace(v));
				}
				Game.Debug(2,"Chemin trouvé pour "+this+" : Départ = "+closestStationStart+"  -   Chemin = "+pathToFollow);
			}


		}
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

	public void getTarget() {
		this.target = Game.get().getMap().getRandomTarget();
	}

	public Vector2 getTargetPos() {
		Vector2 res = null;
		if(this.target!=null)
			res = target.getCoordinates();
		return res;
	}

	public void Update() {
		if(pathToFollow.size() == 0) {
			getPath();
		}
	}

	public PlaceToBe getCurrentPlace() {
		return place;
	}

	/**
	 * Met à jour le graphe du äthFinder pour prendre en compte les dernières modifications de lignes
	 */
	public static void UpdateGraph() {
		pf.updateGraph();
	}
}