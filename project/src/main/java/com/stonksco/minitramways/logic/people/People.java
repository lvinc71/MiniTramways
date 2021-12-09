package com.stonksco.minitramways.logic.people;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.Cell;
import com.stonksco.minitramways.logic.map.PlaceToBe;
import com.stonksco.minitramways.logic.map.buildings.Building;
import com.stonksco.minitramways.logic.map.buildings.Station;
import com.stonksco.minitramways.logic.map.lines.LinePart;
import com.stonksco.minitramways.logic.map.lines.Tramway;
import com.stonksco.minitramways.views.Clock;

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
			} else System.out.println("AUCUN CHEMIN OBTENU de "+place.getCoordinates()+" vers "+target.getCoordinates());
		} else System.out.println("AUCUNE STATION TROUVEE POUR REJOINDRE CIBLE");
		System.out.println("STATIONS : "+Game.get().getMap().getStations());
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
		if(pathToFollow.size() == 0 && target!=null) {
			getPath();
		} else {
			chooseMove();
		}

	}

	public PlaceToBe getCurrentPlace() {
		return place;
	}

	public void setCurrentPlace(PlaceToBe place) {
		this.place = place;
	}
	/**
	 * Met à jour le graphe du pathFinder pour prendre en compte les dernières modifications de lignes
	 */
	public static void UpdateGraph() {
		pf.updateGraph();
	}

	/**
	 * Méthode qui détermine si un déplacement doit se faire et, le cas échéant, lequel
	 */
	private void chooseMove() {
		if(pathToFollow.size()>0) {
			//System.out.println("TEMP searching for move from "+place.getCoordinates()+" to "+pathToFollow.get(0).getCoordinates());
			if(place instanceof Tramway) {
				// Si on est actuellement dans un tram (<=> on n'est pas au point de départ)
				Vector2 targetPos = pathToFollow.get(0).getCoordinates();
				// On vérifie si on est "sur" la station visée
				if(Vector2.AbstractDistance(targetPos,getCurrentPlace().getCoordinates())<0.2d) {

					if(pathToFollow.size()>1) {
						// Si on est pas sur la station d'arrivée
						// Si le tram continue sur la bonne ligne, alors on reste dedans
						if(Game.get().getMap().isOnLine(pathToFollow.get(1).getCoordinates(), ((Tramway) place).lineID())) {
							pathToFollow.remove(0); // On considère que la station est visitée
						} else {
							// Sinon, on descend à la station
							move(pathToFollow.get(0));
						}
					} else {
						// Si on est sur la station d'arrivée
						move(target);
					}


				}
			} else if(place instanceof Station){
				// On attend un tram

				// On incrémente le compteur d'attente
				waitingSince+= Clock.get().GameDeltaTime();


				// On récupère la ligne correspondant au tronçon visé
				LinePart part = Game.get().getMap().getPartBetween(place.getCoordinates(),pathToFollow.get(0).getCoordinates());

				Tramway closestTram = null;
				double closestTramDistance = Double.POSITIVE_INFINITY;


				for(Tramway t : part.getLine().getTrams()) {
					double tramDistance = Vector2.AbstractDistance(t.getCoordinates(),place.getCoordinates());
					if(tramDistance<closestTramDistance) {
						closestTramDistance = tramDistance;
						closestTram = t;
					}
				}

				//System.out.println("TEMP closest tram is at "+closestTramDistance);

				// Si un des trams de la ligne est "sur" la station et s'il se dirige vers la cible
				if(closestTramDistance<0.15d && closestTram.getTarget().equals(pathToFollow.get(0).getCoordinates()))
					// Si le tram a de la place
					if(closestTram.Amount()<closestTram.getCapacity())
						// On bouge
						move(closestTram);

			} else {
				// On est au point de départ
				//System.out.println("ON EST AU POINT DE DEP");
				// Si la station cible a de la place
				if(((Station)pathToFollow.get(0)).getCapacity() > pathToFollow.get(0).Amount()) {
					// On bouge
					move(pathToFollow.get(0));
				}
					
			}
		}
	}

	/**
	 * Méthode qui exécute un mouvement vers le prochain bâtiment
	 */
	private void move(PlaceToBe to) {
		// Se déplacer
		// Mettre à jour le chemin
		// Mettre à jour place

		to.Enter(this);
		if(to.equals(pathToFollow.get(0)))
			pathToFollow.remove(0);

		if(place.equals(target)) {
			pathToFollow.clear();
			target=null;
			dispawn();
		}

		Game.get().requestPinsUpdate();

		//// Dispawn/score si arrivée
	}

	private void dispawn() {
		People.instances.remove(this);
		this.place=null;
		this.target=null;
		this.pathToFollow=null;
	}
}