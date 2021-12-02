package com.stonksco.minitramways.views.layers.cells;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.items.ImageGetter;
import com.stonksco.minitramways.views.items.ImagesEnum;
import com.stonksco.minitramways.views.items.PinView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Représente l'affichage d'une station
 */
public class StationView extends CellView {

    private final ImageView sprite;
    private PinView pv;


    public StationView(GameView gw, Vector2 gridPos) {
        super(gw,gridPos);

        Image img = new ImageGetter().getImageOf(ImagesEnum.STATION);
        sprite = new ImageView();

        sprite.fitHeightProperty().bind(gw.getCellSizeY().multiply(0.95d));
        sprite.fitWidthProperty().bind(gw.getCellSizeX().multiply(0.95d));
        sprite.setImage(img);
        this.getChildren().add(sprite);
        
    }

    public void enable() {

        this.pv = new PinView(gw,Game.get().getAmountOf(gridPos));
        this.getChildren().add(pv);

        // Affichage coordonnées
        if(Game.get().getDebug()>2) {
            Text t = new Text(gridPos.toString());
            t.setFill(Color.RED);
            t.wrappingWidthProperty().bind(gw.getCellSizeX());
            t.autosize();
            this.getChildren().add(t);
        }


    }

    public void showRadius() {
        gw.getRadiusLayer().showRadiusAt(gridPos);
    }

    public void hideRadius() {
        gw.getRadiusLayer().hideRadiusAt(gridPos);
    }


}
