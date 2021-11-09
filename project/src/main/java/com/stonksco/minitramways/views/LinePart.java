package com.stonksco.minitramways.views;
import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

/**
 * Représente un tronçon de ligne entre deux stations
 */
public class LinePart extends Node {

    private GameView gw;

    private Vector2 pxStart;
    private Vector2 pxEnd;

    private Line line;
    private CellView start;
    private CellView end;

    public LinePart(GameView gw, CellView start, CellView end) {
        super();
        this.gw = gw;
        this.start = start;
        this.end = end;

        pxStart = gw.CellToPixels(start.getGridPos());
        pxEnd = gw.CellToPixels(end.getGridPos());

        DrawLine();
    }


    private void DrawLine()
    {
        line = new Line();
        line.setStartX(pxStart.getX());
        line.setStartY(pxStart.getY());
        line.setEndX(pxEnd.getX());
        line.setEndY(pxEnd.getY());
        line.setStrokeWidth(8);
        line.setStroke(Color.GOLD);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setViewOrder(40);
        gw.AddNodeToView(line);
        Game.Debug(3,"Line drawn from "+pxStart.toString()+" to "+pxEnd.toString());
    }

    /**
     * Retourne l'orientation, en degré, que les tramways devront appliquer pour être alignés à ce tronçon
     * @return
     */
    public double getOrientation() {
        Vector2 startPos = start.getGridPos();
        Vector2 endPos = end.getGridPos();
        Vector2 lineVector = endPos.sub(startPos);

        Vector2 normalizedLineVector = lineVector.normalize();
        Vector2 horizontalVector = new Vector2(1,0);

        double scalar = normalizedLineVector.scalar(horizontalVector);

        double angle = Math.acos(scalar);
        angle = Math.toDegrees(angle);

        // On corrige l'angle à cause des coordonnées Y en sens inverse (0 en haut)
        if(startPos.getY()>endPos.getY())
            angle = -angle;

        Game.Debug(3,"Calculated tramway rotation for line "+lineVector+" (normalized : "+normalizedLineVector+" ) : "+angle+" degrees");
        return angle;
    }

    /**
     * Retourne les coordonnées en pixels sur la ligne selon le pourcentage fourni en paramètre
     * @param at
     * @return
     */
    public Vector2 getPosAt(double at) {
        Vector2 res = new Vector2(0,0);
        if(at==0)
            res = pxStart.clone();
        else if(at==100)
            res = pxEnd.clone();
        else
        {
            Vector2 lineVector = pxEnd.sub(pxStart);
            Vector2 posVector = lineVector.scale(at/100d);
            res = pxStart.add(posVector);
        }

        return res;
    }

}
