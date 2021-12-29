package com.stonksco.minitramways.views;

import com.stonksco.minitramways.control.MapController;
import com.stonksco.minitramways.control.utils.Listener;
import com.stonksco.minitramways.control.utils.Notification;
import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.interactions.ClickStateMachine;
import com.stonksco.minitramways.logic.map.buildings.BuildingEnum;
import com.stonksco.minitramways.views.layers.*;
import com.stonksco.minitramways.views.layers.cells.CellView;
import com.stonksco.minitramways.views.layers.cells.GridDisplayCell;
import com.stonksco.minitramways.views.layers.cells.StationView;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;

public class GameView extends Scene implements Listener {

    private static final ArrayList<GameView> instances = new ArrayList<>();
    private final Group root;
    private final Stage primaryStage;
    // Contrôleurs
    private final MapController mapController;

    // Couleurs
    private final Map<ColorEnum,Color> colors = Map.ofEntries(
            Map.entry(ColorEnum.LINE_BLUE,Color.web("0x3333FF",1)),
            Map.entry(ColorEnum.LINE_CYAN,Color.web("0x0099CC",1)),
            Map.entry(ColorEnum.LINE_GOLD,Color.web("0xCCCC33",1)),
            Map.entry(ColorEnum.LINE_LIME,Color.web("0x2FD61D",1)),
            Map.entry(ColorEnum.LINE_ROSEGOLD,Color.web("0xE0BFB8",1)),
            Map.entry(ColorEnum.LINE_PURPLE,Color.web("0xCC00CC",1)),
            Map.entry(ColorEnum.LINE_RED,Color.web("0xCC0000",1)),
            Map.entry(ColorEnum.LINE_YELLOW,Color.web("0xFFFF33",1)),
            Map.entry(ColorEnum.BACKGROUND,Color.web("0xE9E9E9",1)),
            Map.entry(ColorEnum.GRID_DOT,Color.web("0x000000",0.07)),
            Map.entry(ColorEnum.RESIDENTIAL_BACKGROUND,Color.web("0xD1DFBC",1)),
            Map.entry(ColorEnum.RESIDENTIAL_BORDER,Color.web("0xE9FFD6",1)),
            Map.entry(ColorEnum.COMMERCIAL_BACKGROUND,Color.web("0xEE6F66",1)),
            Map.entry(ColorEnum.COMMERCIAL_BORDER,Color.web("0xFF8A6D",1)),
            Map.entry(ColorEnum.OFFICE_BACKGROUND,Color.web("0x53B0D1",1)),
            Map.entry(ColorEnum.OFFICE_BORDER,Color.web("0x65D6FF",1)),
            Map.entry(ColorEnum.PIN_COLOR,Color.web("0xED362E",1)),
            Map.entry(ColorEnum.TARGET_COLOR,Color.web("0xE0BFB8",0.2d)),
            Map.entry(ColorEnum.TARGET_OUTLINE_COLOR,Color.web("0xE0BFB8",0.6d))
    );
    // Taille des cellules
    DoubleProperty cellSizeX;
    DoubleProperty cellSizeY;
    // Calques et conteneurs
    private GridDisplay gridDisplay; // Points de la grille
    private StationsLayer gridStations; // Stations
    private BuildingsLayer gridBuildings; // Bâtiments (sauf stations)
    private CellInteractionsLayer interactionLayer; // Épingles représentant le nombre de personnes
    private AreasLayer areasPane; // Quartiers
    private LinesLayer linesPane; // Lignes et trams
    private RadiusLayer radiusLayer; // Rayons des stations
    private TargetsLayer targetsLayer; // Objectifs des personnes
    private PinsLayer pinsLayer; // Nombre de personnes dans bâtiments
    private UILayer uiLayer; // Interface

    private StackPane mainPane; // Conteneur principal remplissant la fenêtre
    private Pane centerPane; // Conteneur central contenant la carte du jeu
    // Sélection de cellules
    private CellView firstCell = null;
    private CellView secondCell = null;
    // Station temporaire pour affichage ; aucun lien métier
    private StationView tempStation;


    /**
     * Crée une nouvelle fenêtre de jeu, avec sa grille et l'UI associée
     * @param parent
     * @param primaryStage
     * @author Léo Vincent
     */
    public GameView(Group parent, Stage primaryStage, MapController controller) {
        super(parent, 1600,900);
        instances.add(this);

        this.root = parent;
        this.primaryStage = primaryStage;

        mapController = controller;
        controller.register(this);

        Game.get().initGame();

        this.setFill(getColor(ColorEnum.BACKGROUND));
        String stylesheetpath = getClass().getResource("/style.css").toExternalForm();
        this.getStylesheets().clear();
        this.getStylesheets().add(stylesheetpath);
    }

    public static void FrameUpdate() {
        for(GameView gw : instances) {
            gw.Update();
        }
    }

    public void enable() {
        initWindowLayout();
        initMapLayers();
        gridBuildings.updateBuildings();
        Clock.get().start();
    }

    /**
     * Initialise les zones de la fenêtre
     * @author Léo Vincent
     */
    private void initWindowLayout() {

        centerPane = new Pane();
        mainPane = new StackPane();
        uiLayer = new UILayer();

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
        mainPane.getChildren().add(uiLayer);
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
        gridStations = new StationsLayer(this,s);
        gridBuildings = new BuildingsLayer(this,s);
        interactionLayer = new CellInteractionsLayer(this,s);
        areasPane = new AreasLayer(this);
        linesPane = new LinesLayer(this);
        radiusLayer = new RadiusLayer(this);
        targetsLayer = new TargetsLayer(this);
        pinsLayer = new PinsLayer(this);

        centerPane.getChildren().add(areasPane);
        centerPane.getChildren().add(gridDisplay);
        centerPane.getChildren().add(gridBuildings);
        centerPane.getChildren().add(linesPane);
        centerPane.getChildren().add(radiusLayer);
        centerPane.getChildren().add(gridStations);
        centerPane.getChildren().add(pinsLayer);
        centerPane.getChildren().add(targetsLayer);
        centerPane.getChildren().add(interactionLayer);

        centerPane.layout();

        this.getWindow().setWidth(this.getWindow().getWidth()+0.001);
        this.getWindow().setWidth(this.getWindow().getWidth()-0.001);

        gridDisplay.prefWidthProperty().bind(centerPane.widthProperty());
        gridDisplay.prefHeightProperty().bind(centerPane.heightProperty());

        ArrayList<Pane> layersList = new ArrayList<>();
        //layersList.add(gridDisplay);
        layersList.add(gridStations);
        layersList.add(gridBuildings);
        layersList.add(interactionLayer);
        layersList.add(areasPane);
        layersList.add(linesPane);
        layersList.add(radiusLayer);
        layersList.add(targetsLayer);
        layersList.add(pinsLayer);

        for (Pane layer:layersList) {
            layer.prefWidthProperty().bind(gridDisplay.widthProperty());
            layer.prefHeightProperty().bind(gridDisplay.heightProperty());
            layer.maxWidthProperty().bind(gridDisplay.widthProperty());
            layer.maxHeightProperty().bind(gridDisplay.heightProperty());
            layer.minWidthProperty().bind(gridDisplay.widthProperty());
            layer.minHeightProperty().bind(gridDisplay.heightProperty());
        }

        centerPane.layout();

        Game.Debug(1,"Map layers initialized with a size of "+centerPane.widthProperty().get()+" * "+centerPane.heightProperty().get()+" pixels");


    }

    /**
     * Appelée au clic gauche sur une cellule de la grille
     * @param cell sur laquelle le clic a été fait
     * @author Thomas Coulon, Léo Vincent
     */
    public void cellLeftClick(CellView cell)
    {
        /*
            if (firstCell == null)
            {
                firstCell = cell;
                Game.Debug(2, "First cell at ( " + GridPane.getColumnIndex(firstCell)+ " , "+ GridPane.getRowIndex(firstCell) + " )");
                if(Game.get().getMap().getCellAt(firstCell.getGridPos()).getBuilding()==null) {
                    tempStation = new StationView(this, firstCell.getGridPos());
                    tempStation.opacityProperty().setValue(0.5);
                    firstCell.getChildren().add(tempStation);
                } else {
                    if (!Game.get().isAtExtremity(firstCell.getGridPos())) {
                        resetCellSelection();
                    }
                }
            }
            else if (secondCell == null && cell != firstCell)
            {
                secondCell = cell;
                Game.Debug(2, "Second cell at ( " + GridPane.getColumnIndex(secondCell)+ " , "+ GridPane.getRowIndex(secondCell)+ " )");
            }

            if (firstCell != null && secondCell != null)
            {
                Vector2 firstPos = new Vector2(GridPane.getColumnIndex(firstCell),GridPane.getRowIndex(firstCell));
                Vector2 secondPos = new Vector2(GridPane.getColumnIndex(secondCell),GridPane.getRowIndex(secondCell));
                Game.Debug(2, "Two cells selected.");
                if (mapController.createLine(firstPos, secondPos)) {
                    firstCell.getChildren().remove(tempStation);
                    tempStation = null;
                    firstCell=secondCell;

                } else {
                    Game.Debug(1, "Line creation aborted.");
                }
                secondCell = null;
            }
         */

        mapController.sendLeftClick(new Vector2(GridPane.getColumnIndex(cell),GridPane.getRowIndex(cell)));
    }

    /**
     * Appelée lors du clic droit sur la grille
     */
    public void cellRightClick(CellView cell) {
        //resetCellSelection();
        mapController.sendRightClick(new Vector2(GridPane.getColumnIndex(cell),GridPane.getRowIndex(cell)));
    }


    public void CellEnter(Vector2 cell) {
        this.gridStations.showRadiusOf(cell);
        targetsLayer.Enter(cell);
    }

    public void CellExit(Vector2 cell) {
        this.gridStations.hideRadiusOf(cell);
        targetsLayer.Exit(cell);
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
    public void Notify(Notification notif) {
        String msg = notif.getMessage();
        switch(msg) {
            case "updatelines" :
                updateLines((ArrayList<Integer>)notif.getData());
                break;
        }
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


    /**
     * Ajoute une batiment aux coordonnées passées en paramètres
     * @param at
     */
    private void addBuildingAt(Vector2 at,BuildingEnum types) {
        gridBuildings.addBuildingAt(at,types);
    }
    
    public ReadOnlyDoubleProperty gridPosX() {
        SimpleDoubleProperty p = new SimpleDoubleProperty();
        p.bind(this.gridDisplay.layoutXProperty());
        return p;
    }

    public ReadOnlyDoubleProperty gridPosY() {
        SimpleDoubleProperty p = new SimpleDoubleProperty();
        p.bind(this.gridDisplay.layoutYProperty());
        return p;
    }

    public void updateLines(ArrayList<Integer> data) {
        for(int l : data) {
            Game.Debug(2,"VIEW : Refreshing line "+l);
            linesPane.removeLine(l);
            linesPane.addLine(l);
        }
        updateStations();
    }

    public void updateStations() {
        gridStations.updateStations();
    }

    /**
     * Appelée à chaque frame
     */
    public void Update() {
        if(Game.get().needPinsUpdate())
            pinsLayer.reset();

        linesPane.Update();
        gridBuildings.updateBuildings();
        gridBuildings.updateBuildingsPins();
        gridStations.updateStationsPins();
        uiLayer.Update();
    }

    public RadiusLayer getRadiusLayer() {
        return radiusLayer;
    }

    public TargetsLayer getTargetsLayer() { return targetsLayer;}

    /**  ------ Pins ------ */

    public int addPin(Vector2 at, int nb) {
        return pinsLayer.addPin(at,nb);
    }

    public void removePin(int id) {
        pinsLayer.removePin(id);
    }

    public void resetPins() {
        pinsLayer.reset();
    }

    public int getPinNumber(int id) {
        return pinsLayer.getNbOf(id);
    }

    public boolean doesPinExists(int id) {
        return pinsLayer.doesPinExists(id);
    }

    public void updatePins() {
        gridStations.updateStationsPins();
        gridBuildings.updateBuildingsPins();
    }

    /** Fin Pins */



}













