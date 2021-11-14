package com.stonksco.minitramways.views;

import com.stonksco.minitramways.control.MapController;
import com.stonksco.minitramways.control.interfaces.Listener;
import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.layers.*;
import com.stonksco.minitramways.views.layers.cells.CellView;
import com.stonksco.minitramways.views.layers.cells.GridDisplayCell;
import com.stonksco.minitramways.views.layers.cells.StationView;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;

public class GameView extends Scene implements Listener {

    private Group root;
    private Stage primaryStage;

    // Calques et conteneurs
    private GridDisplay gridDisplay; // Points de la grille
    private StationsView gridStations; // Stations
    private BuildingsView gridBuildings; // Bâtiments (sauf stations)
    private PinsView gridPins; // Épingles représentant le nombre de personnes
    private AreasView areasPane; // Quartiers
    private LinesView linesPane; // Lignes et trams

    private StackPane mainPane; // Conteneur principal remplissant la fenêtre
    private Pane centerPane; // Conteneur central contenant la carte du jeu

    // Contrôleurs
    private MapController mapController;


    // Couleurs
    private Map<ColorEnum,Color> colors = Map.ofEntries(
            Map.entry(ColorEnum.BACKGROUND,Color.web("0xE9E9E9",1)),
            Map.entry(ColorEnum.GRID_DOT,Color.web("0xC2C2C2",1)),
            Map.entry(ColorEnum.LINE_BLUE,Color.web("0x3333FF",1)),
            Map.entry(ColorEnum.LINE_CYAN,Color.web("0x0099CC",1)),
            Map.entry(ColorEnum.LINE_GOLD,Color.web("0xCCCC33",1)),
            Map.entry(ColorEnum.LINE_LIME,Color.web("0x2FD61D",1)),
            Map.entry(ColorEnum.LINE_ROSEGOLD,Color.web("0xE0BFB8",1)),
            Map.entry(ColorEnum.LINE_PURPLE,Color.web("0xCC00CC",1)),
            Map.entry(ColorEnum.LINE_RED,Color.web("0xCC0000",1)),
            Map.entry(ColorEnum.LINE_YELLOW,Color.web("0xFFFF33",1))
    );

    // Sélection de cellules
    private CellView firstCell = null;
    private CellView secondCell = null;

    // Taille des cellules
    DoubleProperty cellSizeX;
    DoubleProperty cellSizeY;


    /**
     * Crée une nouvelle fenêtre de jeu, avec sa grille et l'UI associée
     * @param parent
     * @param primaryStage
     * @author Léo Vincent
     */
    public GameView(Group parent, Stage primaryStage) {
        super(parent, 1600,900);

        this.root = parent;
        this.primaryStage = primaryStage;

        mapController = new MapController(Game.get().getMap(), this);

        Game.get().initGame();

        this.setFill(getColor(ColorEnum.BACKGROUND));
    }

    public void enable() {
        initWindowLayout();
        initMapLayers();
    }


    /**
     * Initialise les zones de la fenêtre
     * @author Léo Vincent
     */
    private void initWindowLayout() {

        centerPane = new Pane();
        mainPane = new StackPane();

        mainPane.prefWidthProperty().bind(this.widthProperty());
        mainPane.prefHeightProperty().bind(this.heightProperty());

        root.layout();
        mainPane.layout();

        if(Game.get().getDebug()>2) {
            BorderStroke[] strokes = {
                    new BorderStroke(Color.RED,BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(2d)),
                    new BorderStroke(Color.RED,BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(2d)),
                    new BorderStroke(Color.RED,BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(2d)),
                    new BorderStroke(Color.RED,BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(2d))
            };
            centerPane.setBorder(new Border(strokes));
            mainPane.setBorder(new Border(strokes));
        }


        mainPane.paddingProperty().bind(Bindings.createObjectBinding(() -> new Insets(mainPane.heightProperty().multiply(0.05d).get())));
        mainPane.getChildren().add(centerPane);
        root.getChildren().add(mainPane);

        When bindCondition = new When(mainPane.widthProperty().lessThan(mainPane.heightProperty().multiply(Game.get().getMapSize().getX()/Game.get().getMapSize().getY())));
        SimpleDoubleProperty widthBinding = new SimpleDoubleProperty();
        SimpleDoubleProperty heightBinding = new SimpleDoubleProperty();
        widthBinding.bind(bindCondition.then(mainPane.widthProperty().multiply(0.85d)).otherwise(  heightBinding.divide(Game.get().getMapSize().getY()).multiply(Game.get().getMapSize().getX())  ));
        heightBinding.bind(bindCondition.then(  widthBinding.divide(Game.get().getMapSize().getX()).multiply(Game.get().getMapSize().getY())  ).otherwise(mainPane.heightProperty().multiply(0.85d)));

        centerPane.prefWidthProperty().bind(widthBinding);
        centerPane.prefHeightProperty().bind(heightBinding);
        centerPane.maxWidthProperty().bind(widthBinding);
        centerPane.maxHeightProperty().bind(heightBinding);


        root.layout();
        mainPane.layout();
        centerPane.layout();
        mainPane.layout();
        root.layout();

        Game.Debug(3,"Main container got width of "+mainPane.widthProperty().get()+" (wanted: "+mainPane.prefWidthProperty().get()+" ) pixels and height of "+mainPane.heightProperty().get()+" pixels (wanted: "+mainPane.prefHeightProperty().get()+" )");
        Game.Debug(3,"Center container got width of "+centerPane.widthProperty().get()+" (wanted: "+centerPane.prefWidthProperty().get()+" ) pixels and height of "+centerPane.heightProperty().get()+" pixels (wanted: "+centerPane.prefHeightProperty().get()+" )");

    }


    /**
     * Initialise l'affichage de la grille
     * @author Léo Vincent
     */
    private void initMapLayers() {
        Vector2 s = Game.get().getMapSize();

        gridDisplay = new GridDisplay(this,s);
        gridStations = new StationsView(this,s);
        gridBuildings = new BuildingsView(this,s);
        gridPins = new PinsView(this,s);
        areasPane = new AreasView(this);
        linesPane = new LinesView(this);

        areasPane.AddArea();

        centerPane.getChildren().add(areasPane);
        centerPane.getChildren().add(gridDisplay);
        centerPane.getChildren().add(gridBuildings);
        centerPane.getChildren().add(linesPane);
        centerPane.getChildren().add(gridStations);
        centerPane.getChildren().add(gridPins);

        centerPane.layout();

        this.getWindow().setWidth(this.getWindow().getWidth()+0.001);
        this.getWindow().setWidth(this.getWindow().getWidth()-0.001);

        gridDisplay.prefWidthProperty().bind(centerPane.widthProperty());
        gridDisplay.prefHeightProperty().bind(centerPane.heightProperty());

        ArrayList<Pane> layersList = new ArrayList<>();
        //layersList.add(gridDisplay);
        layersList.add(gridStations);
        layersList.add(gridBuildings);
        layersList.add(gridPins);
        layersList.add(areasPane);
        layersList.add(linesPane);

        for (Pane layer:layersList) {
            layer.prefWidthProperty().bind(gridDisplay.widthProperty());
            layer.prefHeightProperty().bind(gridDisplay.heightProperty());
        }

        centerPane.layout();

        Game.Debug(1,"Map layers initialized with a size of "+centerPane.widthProperty().get()+" * "+centerPane.heightProperty().get()+" pixels");

    }





    // Station temporaire pour affichage ; aucun lien métier
    private StationView tempStation;

    /**
     * Appelée au clic sur une cellule de la grille
     * @param cell sur laquelle le clic a été fait
     * @author Thomas Coulon
     */
    public void cellClick(CellView cell)
    {
            if (firstCell == null)
            {
                firstCell = cell;
                Game.Debug(2, "First cell at ( " + GridPane.getColumnIndex(firstCell)+ " , "+ GridPane.getRowIndex(firstCell) + " )");
                tempStation = new StationView(this,firstCell.getGridPos());
                firstCell.getChildren().add(tempStation);
            }
            else if (secondCell == null && firstCell != null && cell != firstCell)
            {
                secondCell = cell;
                Game.Debug(2, "Second cell at ( " + GridPane.getColumnIndex(secondCell)+ " , "+ GridPane.getRowIndex(secondCell)+ " )");
            }

            if (firstCell != null && secondCell != null)
            {
                Vector2 firstPos = new Vector2(GridPane.getColumnIndex(firstCell),GridPane.getRowIndex(firstCell));
                Vector2 secondPos = new Vector2(GridPane.getColumnIndex(secondCell),GridPane.getRowIndex(secondCell));
                Game.Debug(2, "Two cells selected.");
                if(!mapController.createLine(firstPos,secondPos))
                    Game.Debug(1,"Line creation aborted.");
                resetCellSelection();
            }


    }

    /**
     * Appelée lors du clic droit sur la grille
     */
    public void gridRightClick() {
        resetCellSelection();
    }


    /**
     * Réinitialise les cellules actuellement sélectionnées
     * @author Thomas Coulon
     */
    private void resetCellSelection() {
        if(firstCell!=null)
            firstCell.getChildren().remove(tempStation);
        tempStation = null;
        firstCell = null;
        secondCell = null;
        Game.Debug(2,"Cell selection cleared.");
    }

    @Override
    public void Notify(String msg) {

    }

    public void CreateLine (Vector2 start, Vector2 end)
    {
            linesPane.createLine(start,end);
    }


    /**
     * Retourne les coordonnées  X en pixels du centre d'une cellule de la grille
     * @param cellPos coordonnées de la cellule dans la grille
     * @return coordonnées en pixels dans la scène
     * @author Léo Vincent
     */
    public ReadOnlyDoubleProperty CellToPixelsX(Vector2 cellPos) {
        return ((GridDisplayCell)gridDisplay.getCellAt(cellPos)).getPixelsX();
    }

    public ReadOnlyDoubleProperty CellToPixelsY(Vector2 cellPos) {
        return ((GridDisplayCell)gridDisplay.getCellAt(cellPos)).getPixelsY();
    }


    /**
     * Ajoute une station aux coordonnées passées en paramètres
     * @param at
     */
    public void addStationAt(Vector2 at) {
        gridStations.addStationAt(at);
    }

    /**
     * Retourne la largeur en pixels des cellules de la grille
     * @return
     */
    public ReadOnlyDoubleProperty getCellSizeX() {
        DoubleProperty res;
        if(cellSizeX==null) {
            cellSizeX = new SimpleDoubleProperty(0);
            cellSizeX.bind(centerPane.widthProperty().divide(Game.get().getMapSize().getX()));
        }
        res = cellSizeX;
        return res;
    }

    /**
     * Retourne la hauteur en pixels des cellules de la grille
     * @return
     */
    public ReadOnlyDoubleProperty getCellSizeY() {
        DoubleProperty res;
        if(cellSizeY==null) {
            cellSizeY = new SimpleDoubleProperty(0);
            cellSizeY.bind(centerPane.heightProperty().divide(Game.get().getMapSize().getY()));
        }
        res = cellSizeY;
        return res;
    }



    /**
     * Retourne la couleur associée à la couleur demandée
     * @param c
     * @return
     */
    public Color getColor(ColorEnum c) {
        return this.colors.get(c);
    }


}
