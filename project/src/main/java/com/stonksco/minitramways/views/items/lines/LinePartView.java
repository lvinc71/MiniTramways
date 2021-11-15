package com.stonksco.minitramways.views.items.lines;
import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.layers.LinesView;
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

    private GameView gw;
    private LineView line;

    // Coordonnées en pixels des deux points du tronçon
    private ReadOnlyDoubleProperty  pxStartX;
    private ReadOnlyDoubleProperty  pxStartY;
    private ReadOnlyDoubleProperty  pxEndX;
    private ReadOnlyDoubleProperty  pxEndY;

    // Cellules correspondant aux deux extrémités du tronçon (stations)
    private Vector2 start;
    private Vector2 end;

    // Ligne graphique
    private Line lineShape;
    private Color color;


    public LinePartView(GameView gw, LineView line, Vector2 start, Vector2 end, Color color) {
        super();
        this.gw = gw;
        this.line = line;
        this.start = start;
        this.end = end;
        this.color = color;

        line.getChildren().add(this);

        pxStartX = gw.CellToPixelsX(start);
        pxStartY = gw.CellToPixelsY(start);
        pxEndX = gw.CellToPixelsX(end);
        pxEndY = gw.CellToPixelsY(end);

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
        lineShape.setStrokeWidth(6);
        lineShape.setStroke(color);
        lineShape.setStrokeLineCap(StrokeLineCap.ROUND);
        this.getChildren().add(lineShape);
        Game.Debug(3,"Line drawn from "+pxStartX.get()+","+pxStartY.get()+" to "+pxEndX.get()+","+pxEndY.get());
    }

    /**
     * Retourne l'orientation, en degré, que les tramways devront appliquer pour être alignés à ce tronçon
     * @return angle en degrés
     * @author Léo Vincent
     */
    public double getOrientation() {
        Vector2 startPos = start;
        Vector2 endPos = end;
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
     * 0% = Station de départ | 100% = Station d'arrivée
     * @param at pourcentage sur la ligne
     * @return coordonnées en pixels
     */
    public ReadOnlyDoubleProperty getPosXAt(double at) {
        SimpleDoubleProperty res = new SimpleDoubleProperty(0);
        if(at==0)
            res.bind(pxStartX);
        else if(at==100)
            res.bind(pxEndX);
        else
        {
            res.bind(pxEndX.subtract(pxStartX).multiply(at/100d).add(pxStartX));
        }

        return res;
    }

    public ReadOnlyDoubleProperty getPosYAt(double at) {
        SimpleDoubleProperty res = new SimpleDoubleProperty(0);
        if(at==0)
            res.bind(pxStartY);
        else if(at==100)
            res.bind(pxEndY);
        else
        {
            res.bind(pxEndY.subtract(pxStartY).multiply(at/100d).add(pxStartY));
        }

        return res;
    }

}
