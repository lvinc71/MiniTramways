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

    private Line line;
    private CellView start;
    private CellView end;

    public LinePart(GameView gw, CellView start, CellView end) {
        super();
        this.gw = gw;
        this.start = start;
        this.end = end;
        DrawLine();
    }


    private void DrawLine()
    {
        Vector2 pxStart = gw.CellToPixels(start.getGridPos());
        Vector2 pxEnd = gw.CellToPixels(end.getGridPos());

        line = new Line();
        line.setStartX(pxStart.getX());
        line.setStartY(pxStart.getY());
        line.setEndX(pxEnd.getX());
        line.setEndY(pxEnd.getY());
        line.setStrokeWidth(13);
        line.setStroke(Color.GOLD);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        gw.AddNodeToView(line);
        Game.Debug(3,"Line drawn from "+pxStart.toString()+" to "+pxEnd.toString());
    }

}
