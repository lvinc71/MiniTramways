package com.stonksco.minitramways.logic.map.lines;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.people.People;
import com.stonksco.minitramways.logic.map.PlaceToBe;
import com.stonksco.minitramways.logic.map.buildings.Station;
import com.stonksco.minitramways.views.Clock;

import java.util.ArrayList;

public class Tramway implements PlaceToBe {

	Line line;
	ArrayList<People> people;

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
	private Vector2 realPos;

	public int getCapacity() {
		return capacity;
	}

	/**
	 * Nombre maximal de personnes que peut accueillir ce tram
	 */
	private int capacity = 8;



	/**
	 * 
	 * @param line
	 */
	public Tramway(Line l) {
		this.line = l;
		linePos = 0;
		people = new ArrayList<>();
	}

	/**
	 * Retourne le nombre de personnes actuellement � cet endroit
	 * @return le nombre de personnes | -1 si le lieu ne "stocke" pas les personnes
	 */
	public int Amount() {
		return people.size();
	}

	/**
	 * Déplace une personne dans l'endroit courant
	 * @param p
	 */
	public void Enter(People p) {
		people.add(p);
		p.getCurrentPlace().Exit(p);
		p.setCurrentPlace(this);
	}

	/**
	 * Retire une personne de l'endroit courant
	 * @param p
	 */
	public void Exit(People p) {
		people.remove(p);
	}

	@Override
	public Vector2 getCoordinates() {
		return realPos.clone();
	}

	// Direction du déplacement : true = avant, false = arrière
	private boolean moveDirection = true;

	private long timeSinceLastUpdateNs = 0;

	public void Update() {

		if(timeSinceLastUpdateNs > 20000000) {

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




			LinePart newPart = line.getPartAt(newpos);
			if(newPart!=null) {
				if (!newPart.equals(currentPart))
					Game.Debug(2, "Tramway changed of part from " + currentPart + " to " + newPart + " on line " + line.getID());

				Game.Debug(4, "Tramway moved from " + linePos + " to " + newpos + " on line " + line.getID());

				linePos = newpos;
				currentPart = newPart;
				timeSinceLastUpdateNs = 0;

				updateRealPos();
			}
		} else {
			timeSinceLastUpdateNs += Clock.get().GameDeltaTimeNs();
		}

		
	}

	public void positionAt(double linePos) {
		this.linePos = linePos;
		currentPart = line.getPartAt(linePos);
		updateRealPos();
	}

	private void updateRealPos() {
		Vector2 scaledPartPos = currentPart.getEndPos().sub(currentPart.getStartPos()).scale((linePos%100)/100d);
		Vector2 pos = this.currentPart.getStartPos().add(scaledPartPos);
		realPos = pos;
	}

	public int lineID() {
		return line.getID();
	}

	public Vector2 getTarget() {
		Vector2 res = null;
		if(moveDirection)
			res = currentPart.getEndPos();
		else
			res = currentPart.getStartPos();
		return res;
	}

}