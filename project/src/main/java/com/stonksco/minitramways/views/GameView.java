package com.stonksco.minitramways.views;

import com.stonksco.minitramways.control.MapController;
import com.stonksco.minitramways.control.interfaces.Listener;
import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.Map;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import com.stonksco.minitramways.control.MapController;

import java.util.HashMap;

public class GameView extends Scene implements Listener {



    private Group root  = null;
    private Vector2 windowSize = null;

    private GridPane grid = null;
    private BorderPane regions = null;
    private HashMap<Vector2, Node> cells = null;

    // Controllers
    private MapController mapController;

    private Color backgroundColor = Color.web("0xE9E9E9",1);
    private Color dotColor = Color.web("0xC2C2C2",1);

    // Click construction
    private Node firstCell = null;
    private Node secondCell = null;

    /**
     * Crée une nouvelle fenêtre de jeu, avec sa grille et l'UI associée
     * @param parent
     * @param windowSize Taille de la fenêtre en pixels
     * @author Léo Vincent
     */
    public GameView(Group parent, Vector2 windowSize) {
        super(parent,windowSize.getX(),windowSize.getY());

        this.root = parent;
        this.windowSize = windowSize;

        mapController = new MapController(Game.get().getMap(),this);

        Game.get().initGame();

        this.setFill(backgroundColor);

        grid = new GridPane();

        regions = new BorderPane();

        initGrid();
        initWindowRegions();
    }

    /**
     * Initialise l'affichage de la grille
     * @author Léo Vincent
     */
    private void initGrid() {
        Game.Debug(1,"Generating grid display...");
        grid.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(grid,Pos.CENTER);

        if(Game.get().getDebug()>2)
            grid.setGridLinesVisible(true);

        Map map = Game.get().getMap();

        if(cells!=null)
            cells.clear();
        cells=new HashMap<>();

        for(int i=0; i<map.getGridSize().getY(); i++) {
            for(int j=0; j<map.getGridSize().getX(); j++) {
                // Ici, dessiner points pour chaque case

                CellView cell = new CellView();
                cell.setAlignment(Pos.CENTER);
                cells.put(new Vector2(i,i),cell);
                grid.add(cell,i,j);

                if(i!=map.getGridSize().getY()-1 && j!=map.getGridSize().getX()-1) {

                    Ellipse e = new Ellipse(0,0,3,3);
                    e.setTranslateX(3);
                    e.setTranslateY(3);
                    e.setFill(dotColor);
                    cell.getChildren().add(e);
                    StackPane.setAlignment(e,Pos.BOTTOM_RIGHT);
                }


            }
        }

        double gridHeight = windowSize.getY()*0.88;
        grid.setPrefHeight(gridHeight);
        grid.setPrefWidth((gridHeight/(double)grid.getRowCount())*(double)grid.getColumnCount());

        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(100d/grid.getColumnCount());

        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(100d/grid.getRowCount());

        for(int i = 0; i<grid.getColumnCount(); i++)
            grid.getColumnConstraints().add(cc);
        for(int i = 0; i<grid.getRowCount(); i++)
            grid.getRowConstraints().add(rc);

        Game.Debug(1,"Grid displayed.");

        grid.addEventFilter(MouseEvent.MOUSE_CLICKED,gridClickEvent);

    }

    /**
     * Initialise les zones de la fenêtre
     * @author Léo Vincent
     */
    private void initWindowRegions() {
        double topSpaceHeight = windowSize.getY() * 0.08d;
        double bottomSpaceHeight = windowSize.getY() * 0.04d;

        HBox top = new HBox();
        top.prefHeight(topSpaceHeight);
        HBox bottom = new HBox();
        bottom.prefHeight(bottomSpaceHeight);

        regions.setTop(top);
        regions.setBottom(bottom);
        regions.setRight(new VBox());
        regions.setLeft(new VBox());


        Game.Debug(3,"Calculated top spacing : "+topSpaceHeight+" | Bottom spacing : "+bottomSpaceHeight);

        BorderPane.setAlignment(regions.getTop(),Pos.TOP_CENTER);

        regions.setCenter(grid);
        this.root.getChildren().add(regions);
    }


    /**
     * Évènement de clic sur la grille
     * @author Léo Vincent
     */
    EventHandler<MouseEvent> gridClickEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            // Clic gauche
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                Game.Debug(3,"Grid click triggered.");
                Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
                if(clickedNode != grid) {
                    Node parent = clickedNode.getParent();
                    while(parent != grid) {
                        clickedNode = parent;
                        parent = clickedNode.getParent();
                    }

                    int col = GridPane.getColumnIndex(clickedNode);
                    int row = GridPane.getRowIndex(clickedNode);
                    Game.Debug(2,"Clicked on cell at ( "+col+" , "+row+" )");
                    cellClick((CellView) clickedNode);

                }

            }
            // Clic droit
            else if(mouseEvent.getButton() == MouseButton.SECONDARY)
            {
                resetCellSelection();
            }

        }
    };

    /**
     * Appelée au clic sur une cellule de la grille
     * @param cell sur laquelle le clic a été fait
     * @author Thomas Coulon
     */
    private void cellClick(CellView cell)
    {
            if (firstCell == null)
            {
                firstCell = cell;
                Game.Debug(2, "First cell at ( " + GridPane.getColumnIndex(firstCell)+ " , "+ GridPane.getRowIndex(firstCell) + " )");

            }
            else if (secondCell == null && firstCell != null)
            {
                secondCell = cell;
                Game.Debug(2, "Second cell at ( " + GridPane.getColumnIndex(secondCell)+ " , "+ GridPane.getRowIndex(secondCell)+ " )");
            }

            if (firstCell != null && secondCell != null)
            {
                Vector2 firstPos = new Vector2(GridPane.getColumnIndex(firstCell),GridPane.getRowIndex(firstCell));
                Vector2 secondPos = new Vector2(GridPane.getColumnIndex(secondCell),GridPane.getRowIndex(secondCell));
                firstCell = null;
                secondCell = null;
                Game.Debug(2, "Two cells selected.");
                mapController.createLine(firstPos,secondPos);
            }


    }

    /**
     * Réinitialise les cellules actuellement sélectionnées
     * @author Thomas Coulon
     */
    private void resetCellSelection() {
        firstCell = null;
        secondCell = null;
        Game.Debug(2,"Cell selection cleared.");
    }

    @Override
    public void Notify(String msg) {

    }
}
