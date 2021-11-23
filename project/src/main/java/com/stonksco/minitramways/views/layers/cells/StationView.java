package com.stonksco.minitramways.views.layers.cells;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.items.ImageGetter;
import com.stonksco.minitramways.views.items.ImagesEnum;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Représente l'affichage d'une station
 */
public class StationView extends CellView {

    private ImageView sprite;
    private Circle circle;
    private Pane areaStation;

    public StationView(GameView gw, Vector2 gridPos) {
        super(gw,gridPos);
        this.areaStation = new Pane();
        this.getChildren().add(areaStation);
        Circle();
        enable();
        this.setOnMouseEntered(new EventHandler<javafx.scene.input.MouseEvent>() { // Si la souris passe sur le sprite alors
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                circle.setFill(Color.BLUE); //changement de couleur blue
            }
        });
       this.setOnMouseExited(new EventHandler<MouseEvent>() { //Si la souis n'est plus sur le sprite alors
            @Override
            public void handle(MouseEvent mouseEvent) {
                circle.setFill(null); // changement de couleur
            }
        });
    }

    private void enable() {
        Image img = new ImageGetter().getImageOf(ImagesEnum.STATION);
        sprite = new ImageView();

        sprite.fitHeightProperty().bind(gw.getCellSizeY().multiply(0.95d));
        sprite.fitWidthProperty().bind(gw.getCellSizeX().multiply(0.95d));
        sprite.setImage(img);
        this.getChildren().add(sprite);

        if(Game.get().getDebug()>2) {
            Text t = new Text(gridPos.toString());
            t.setFill(Color.RED);
            t.wrappingWidthProperty().bind(gw.getCellSizeX());
            t.autosize();
            this.getChildren().add(t);
        }
    }

    private void Circle(){
        //création de la zone de la station
        this.circle = new Circle();
        circle.radiusProperty().bind(gw.getCellSizeX().multiply(3));
        circle.translateXProperty().bind(gw.getCellSizeX().multiply(0.5));
        circle.translateYProperty().bind(gw.getCellSizeX().multiply(0.5));
        circle.setFill(null);
        circle.setOpacity(0.2);
        this.areaStation.getChildren().add(circle);
        Game.Debug(2, "AreaStation on"+this.gridPos.toString() );
    }

    public Circle getCircle() {
        return this.circle;
    }

    public ImageView getSprite() {
        return sprite;
    }

}
