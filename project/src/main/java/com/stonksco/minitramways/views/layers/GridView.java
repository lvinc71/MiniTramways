package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.items.ItemsEnum;
import com.stonksco.minitramways.views.layers.cells.CellView;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public abstract class GridView extends GridPane {

    protected GameView gw;

    protected Vector2 gridSize;
    protected HashMap<Vector2, CellView> cells;

    public GridView(GameView gw, Vector2 size) {
        super();
        gridSize = size;
        this.gw = gw;
    }

    /**
     * Initialise toutes les cellules de la grille en y insérant des instances d'un type
     * @param type Type de cellules à instancier
     */
    protected void fill(Class<?> type) {
        if(CellView.class.isAssignableFrom(type)) {
            Game.Debug(3,"Starting grid fill with "+type);
            cells = new HashMap<>();
            for(int i=0; i<gridSize.getY(); i++) {
                for(int j=0; j<gridSize.getX(); j++) {

                    try {
                        Vector2 v = new Vector2(j,i);
                        // Instanciation du type passé en paramètre en lui donnant le GameView
                        CellView cell = (CellView) type.getDeclaredConstructor(GameView.class,Vector2.class).newInstance(gw,v);

                        // Alignement
                        cell.setAlignment(Pos.CENTER);

                        // Insertion de la cellule dans la grille
                        cells.put(v,cell);
                        this.add(cell,j,i);

                    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        Game.Debug(1,"ERROR when filling a grid : "+e.getMessage()+"\n"+e.getCause());
                    }
                }
            }
            Game.Debug(3,"Grid successfully filled with "+cells.size()+" cells.");
            setConstraints();
        } else {
            Game.Debug(2,"ERROR when trying to fill grid : "+type+" is not a CellView.");
        }
    }

    private void setConstraints() {
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(100d/this.getColumnCount());

        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(100d/this.getRowCount());

        for(int i = 0; i<this.getColumnCount(); i++)
            this.getColumnConstraints().add(cc);
        for(int i = 0; i<this.getRowCount(); i++)
            this.getRowConstraints().add(rc);
    }

    /**
     * Retourne la cellule correspondant aux coordonnées passées en paramètre
     * @param pos
     * @return
     */
    public CellView getCellAt(Vector2 pos) {
        return cells.get(pos);
    }

    /**
     * TODO
     * Retourne le type d'item qui se trouve à l'endroit passé en paramètre
     * @param pos Coordonnées sur la carte
     * @return Type d'item ou NULL si aucun item
     */
    public ItemsEnum whatIsThere(Vector2 pos) {
        return null;
    }

}
