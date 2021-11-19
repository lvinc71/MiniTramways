package com.stonksco.minitramways.logic.map;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.building.Station;

import java.util.ArrayList;

public class LinePart {

    private Vector2 startStation;
    private Vector2 endStation;

    private LinePart prec;
    private LinePart next;

    public Line getLine() {
        return line;
    }

    private Line line;

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    private int start;
    private int end;

    public Vector2 getStartPos() {
        return startStation.clone();
    }

    public Vector2 getEndPos() {
        return endStation.clone();
    }


    public LinePart(Line line, Vector2 start, Vector2 end, LinePart first) {
        this.line = line;
        this.startStation = start;
        this.endStation = end;
        if(first==null)
            this.setPos(0,100,0);


    }

    public LinePart getNext() {
        return next;
    }

    public LinePart getLast() {
        LinePart res = this;
        if(next!=null)
            res=next.getLast();
        return res;
    }

    public boolean add(LinePart partToAdd) {
        boolean res = false;
            // Cas où on est du côté droit de la ligne
            if(partToAdd.startStation.equals(this.endStation) && next==null) {
                this.next = partToAdd;
                partToAdd.prec = this;
                partToAdd.setPos(this.end,this.end+100,1);
                Game.Debug(3,"Line "+line.getID()+" extended from right.");
                res=true;
            } else if(partToAdd.startStation.equals(this.startStation) && prec==null){
                // Cas où on est du côté gauche
                this.prec = partToAdd;
                partToAdd.next = this;
                line.setFirst(partToAdd);
                partToAdd.setPos(this.start-100,this.start,2);
                if(partToAdd.start<0) {
                    // On inverse les positions départ et arrivée pour conserver l'ordre tout au long de la ligne
                    partToAdd.reversePositions();
                }
                Game.Debug(3,"Line "+line.getID()+" extended from left.");
                res=true;
            } else {
                if(next!=null)
                    res = next.add(partToAdd);
            }

        if(!res)
            Game.Debug(1,"ERROR when trying to add part "+partToAdd+" to line "+line.toString());
        return res;
    }

    /**
     * Divise un tronçon en deux selon une station intermédiaire
     * @param start Point de départ du tronçon à diviser
     * @param end Point d'arrivée du tronçon à diviser
     * @param at Endroit où la station intermédiaire est construite
     * @return le LinePart nouvellement créé, null si aucun créé
     */
    public LinePart divide(Vector2 start, Vector2 end ,Vector2 at) {
        LinePart res = null;
        if(at!=null) {
            if(startStation.equals(start) && endStation.equals(end)) {
                LinePart newPart = new LinePart(line,at,end,line.getFirstPart());
                newPart.next = this.next;
                newPart.prec = this;
                this.next = newPart;
                this.endStation = at;
                this.next.setPos(this.end,this.end+100,1);
                res=newPart;
                Game.Debug(2,"Line part "+this+" divided at "+at);
            } else {
                if (next!=null)
                    res=next.divide(start,end,at);
            }
        } else {
            Game.Debug(1,"ERROR when trying to divide line part : intersection point is null");
        }


        return res;

    }

    /**
     * Redéfinit la position dans la chaîne de ce membre, et de tous les autres membres par récursion
     * @param start Position de départ sur la ligne
     * @param end Position de fin sur la lign
     * @param from 0 = depuis nulle part ; 1 = depuis le précédent ; 2 = depuis le suivant
     */
    private void setPos(int start, int end, int from) {
        this.start = start;
        this.end = end;

        if(from == 0) {
            if(next!=null)
                next.setPos(end,end+100,1);
            if(prec != null)
                prec.setPos(start,start-100,2);
        } else if(from == 1) {
            if(next!=null)
                next.setPos(end,end+100,1);
        } else if(from == 2) {
            if(prec!=null)
                prec.setPos(start,start-100,2);
        }

    }

    @Override
    public String toString() {
        return startStation+"["+start+"]----------["+end+"]"+endStation;
    }

    public String toStringFull() {
        String s = startStation+"["+start+"]----------["+end+"]";
        if(next != null)
            s = s+next.toStringFull();
        else
            s+=endStation;
        return s;
    }

    public ArrayList<LinePart> getParts() {
        ArrayList<LinePart> parts = new ArrayList<>();
        parts.add(this);
        if(next!=null)
            parts.addAll(next.getParts());
        return parts;
    }

    public void reversePositions() {
        Vector2 temp = startStation;
        startStation = endStation;
        endStation=temp;
    }

}
