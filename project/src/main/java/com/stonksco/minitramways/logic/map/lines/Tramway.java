package com.stonksco.minitramways.logic.map.lines;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.People;
import com.stonksco.minitramways.logic.map.PlaceToBe;
import com.stonksco.minitramways.logic.map.buildings.Station;
import com.stonksco.minitramways.views.Clock;

import java.util.ArrayList;

public class Tramway implements PlaceToBe {

	Line line;
	ArrayList<People> people;
	private final int amount = (int)(Math.random()*5);
	/**
	 * Station visit�e avant la station courante
	 * Cette donn�e permet de savoir dans quelle direction le tram se dirige
	 */
	private Station lastVisitedStation;

	public double getLinePos() {
		return linePos;
	}

	/**
	 * Position sur la ligne, par tranches de pourcentages
	 * 
	 * Par exemple, si le tram se trouve � mi-chemin entre la deuxi�me et la troisi�me station de la ligne, alors cette donn�e vaut 250
	 */
	private double linePos;
	private LinePart currentPart;
	/**
	 * Nombre maximal de personnes que peut accueillir ce tram
	 */
	private int capacity;



	/**
	 * 
	 * @param line
	 */
	public Tramway(Line l) {
		this.line = l;
		linePos = 0;
	}

	/**
	 * Retourne le nombre de personnes actuellement � cet endroit
	 * @return le nombre de personnes | -1 si le lieu ne "stocke" pas les personnes
	 */
	public int Amount() {
		return amount;
	}

	/**
	 * Déplace une personne dans l'endroit courant
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


	// Direction du déplacement : true = avant, false = arrière
	private boolean moveDirection = true;

	private long timeSinceLastUpdateNs = 0;

	public void Update() {

		if(timeSinceLastUpdateNs > 30000000) {

			if(currentPart==null)
				currentPart=line.getPartAt(linePos);
			// Déplacement du tram à une vitesse constante
			double increment = 1/ currentPart.getLength()*line.getSpeed()*(timeSinceLastUpdateNs/Math.pow(10,9))*100;
			if(!moveDirection)
				increment *=-1;

			double newpos = linePos + increment;

			if(newpos<line.getFirstIndex()) {
				newpos = line.getFirstIndex()+(line.getFirstIndex() - newpos);
				moveDirection = !moveDirection;
			} else if(newpos>=line.getLastIndex()) {
				newpos = line.getLastIndex()-(newpos-line.getLastIndex());
				moveDirection = !moveDirection;
			}


			Game.Debug(4,"Tramway moved from "+linePos+" to "+newpos+" on line "+line.getID());
			linePos = newpos;
			LinePart newPart = line.getPartAt(newpos);
			if(!newPart.equals(currentPart))
				Game.Debug(2,"Tramway changed of part from "+currentPart+" to "+newPart+" on line "+line.getID());

			currentPart = newPart;
			timeSinceLastUpdateNs=0;
			
			
		} else {
			timeSinceLastUpdateNs += Clock.get().GameDeltaTimeNs();
		}

		
	}

	public void positionAt(double linePos) {
		this.linePos = linePos;
		currentPart = line.getPartAt(linePos);
	}

}