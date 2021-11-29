package com.stonksco.minitramways.views.items.lines;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

/**
 * Représente un tronçon de ligne entre deux stations
 */
public class LinePartView extends Group {

    private final GameView gw;
    private final LineView line;

    // Coordonnées en pixels des deux points du tronçon
    private final ReadOnlyDoubleProperty  pxStartX;
    private final ReadOnlyDoubleProperty  pxStartY;
    private final ReadOnlyDoubleProperty  pxEndX;
    private final ReadOnlyDoubleProperty  pxEndY;

    // Cellules correspondant aux deux extrémités du tronçon (stations)
    private final Vector2 startPos;
    private final Vector2 endPos;

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    // Mutliples de 100 décrivant la position du tronçon sur la ligne
    private final int start;
    private final int end;

    // Ligne graphique
    private Line lineShape;
    private final Color color;


    public LinePartView(GameView gw, LineView line, Vector2 startPos, Vector2 endPos, int start, int end, Color color) {
        super();
        this.gw = gw;
        this.line = line;
        this.startPos = startPos;
        this.endPos = endPos;
        this.start = start;
        this.end = end;
        this.color = color;

        pxStartX = gw.CellToPixelsX(startPos);
        pxStartY = gw.CellToPixelsY(startPos);
        pxEndX = gw.CellToPixelsX(endPos);
        pxEndY = gw.CellToPixelsY(endPos);

        DrawLine();
    }


    /**
     * Dessine la ligne entre les deux stations
     */
    private void DrawLine()
    {
        lineShape = new Line();

        lineShape.startXProperty().bind(pxStartX);
        lineShape.startYProperty().bind(pxStartY);
        lineShape.endXProperty().bind(pxEndX);
        lineShape.endYProperty().bind(pxEndY);
        lineShape.strokeWidthProperty().bind(gw.getCellSizeX().multiply(0.2d));
        lineShape.setStroke(color);
        lineShape.setStrokeLineCap(StrokeLineCap.ROUND);
        line.getChildren().add(this);
        this.getChildren().add(lineShape);
    }

    /**
     * Retourne l'orientation, en degré, que les tramways devront appliquer pour être alignés à ce tronçon
     * @return angle en degrés
     * @author Léo Vincent
     */
    public double getOrientation() {
        Vector2 startPos = this.startPos;
        Vector2 endPos = this.endPos;
        Vector2 lineVector = endPos.sub(startPos);

        Vector2 normalizedLineVector = lineVector.normalize();
        Vector2 horizontalVector = new Vector2(1,0);

        double scalar = normalizedLineVector.scalar(horizontalVector);

        double angle = Math.acos(scalar);
        angle = Math.toDegrees(angle);

        // On corrige l'angle à cause des coordonnées Y en sens inverse (0 en haut)
        if(startPos.getY()>endPos.getY())
            angle = -angle;

        return angle;
    }

    /**
     * Retourne les coordonnées en pixels sur la ligne selon le pourcentage fourni en paramètre
     * 0% = Station de départ | 100% = Station d'arrivée
     * @param at pourcentage sur la ligne
     * @return coordonnées en pixels
     */
    public ReadOnlyDoubleProperty getPosXAt(double at) {

        at = at%100;
        if(at<0)
            at +=100;

        SimpleDoubleProperty res = new SimpleDoubleProperty(0);
        if(at==0)
            res.bind(pxStartX);
        else if(at==100)
            res.bind(pxEndX);
        else
        {
            res.bind(pxEndX.subtract(pxStartX).multiply((at/100d)).add(pxStartX));
        }

        return res;
    }

    public ReadOnlyDoubleProperty getPosYAt(double at) {

        at = at%100;
        if(at<0)
            at +=100;

        SimpleDoubleProperty res = new SimpleDoubleProperty(0);
        if(at==0)
            res.bind(pxStartY);
        else if(at==100)
            res.bind(pxEndY);
        else
        {
            res.bind(pxEndY.subtract(pxStartY).multiply((at/100d)).add(pxStartY));
        }

        return res;
    }

}
