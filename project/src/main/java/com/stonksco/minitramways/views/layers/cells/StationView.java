package com.stonksco.minitramways.views.layers.cells;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.items.ImageGetter;
import com.stonksco.minitramways.views.items.ImagesEnum;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Représente l'affichage d'une station
 */
public class StationView extends CellView {

    private ImageView sprite;

    private int people = 0;

    public StationView(GameView gw, Vector2 gridPos) {
        super(gw,gridPos);
        enable();
    }

    private void enable() {
        Image img = new ImageGetter().getImageOf(ImagesEnum.STATION);
        sprite = new ImageView();

        sprite.fitHeightProperty().bind(gw.getCellSizeY().multiply(0.95d));
        sprite.fitWidthProperty().bind(gw.getCellSizeX().multiply(0.95d));
        sprite.setImage(img);
        Game.Debug(3,"Created station image :"+sprite.getBoundsInLocal());
        this.getChildren().add(sprite);
        Game.Debug(3,"Created a station with a cell size of "+gw.getCellSizeX().get()+" * "+gw.getCellSizeY().get());
    }
}
