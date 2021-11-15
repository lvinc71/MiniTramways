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
        if(first!=null)
            first.add(this,start,end);
        else
            this.setPos(0,100);


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


    private boolean add(LinePart partToAdd, Vector2 start, Vector2 end) {
        boolean res = false;
        if(start==this.startStation) {
            this.prec=partToAdd;
            partToAdd.setPos(this.start,this.start-100);
            res=true;
        }
        else if(start==this.endStation) {
            this.next = partToAdd;
            partToAdd.setPos(this.end,this.end+100);
            res=true;
        }

        return res;
    }

    /**
     * Divise un tronçon en deux selon une station intermédiaire
     * @param start Point de départ du tronçon à diviser
     * @param end Point d'arrivée du tronçon à diviser
     * @param at Endroit où la station intermédiaire est construite
     * @return true si la division a bien été effectuée
     */
    public boolean divide(Vector2 start, Vector2 end ,Vector2 at) {
        boolean res = false;
        if(startStation==start && endStation==end) {
            LinePart newPart = new LinePart(line,at,end,line.getFirstPart());
            newPart.next = this.next;
            newPart.prec = this;
            this.next = newPart;
            this.next.setPos(this.end,this.end+100);
            res=true;
        } else {
            if (next!=null)
                res=next.divide(start,end,at);
        }

        Game.Debug(2,"Line part divided at "+at);
        return res;

    }

    private void setPos(int start, int end) {
        this.start = start;
        this.end = end;
        if(prec!=null)
            prec.setPos(start,start-100);
        if(next != null)
            next.setPos(end,end+100);
    }

    @Override
    public String toString() {
        return startStation+"----------"+endStation;
    }

    public String toStringFull() {
        String s = toString();
        if(next != null)
            s+=next.toStringFull();
        return s;
    }

    public ArrayList<LinePart> getParts() {
        ArrayList<LinePart> parts = new ArrayList<>();
        if(next!=null)
            parts.addAll(next.getParts());
        return parts;
    }

}
