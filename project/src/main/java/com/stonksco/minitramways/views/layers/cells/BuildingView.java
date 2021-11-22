package com.stonksco.minitramways.views.layers.cells;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.logic.map.building.BuildingEnum;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.items.ImageGetter;
import com.stonksco.minitramways.views.items.ImagesEnum;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Représente l'affichage d'un bâtiment
 */
public class BuildingView extends CellView {
    private ImageView sprite;

    private int people = 0;

    private BuildingEnum type;

    public BuildingView(GameView gw, Vector2 gridPos, BuildingEnum type) {
        super(gw,gridPos);
        this.type = type;

        if (this.type == BuildingEnum.HOUSE) {
            Image img = new ImageGetter().getImageOf(ImagesEnum.HOUSE);
            sprite = new ImageView();

            sprite.fitHeightProperty().bind(gw.getCellSizeY().multiply(0.95d));
            sprite.fitWidthProperty().bind(gw.getCellSizeX().multiply(0.95d));
            sprite.setImage(img);
            Game.Debug(3, "Created Building image HOUSE :" + sprite.getBoundsInLocal());
            this.getChildren().add(sprite);
        }
        else if(this.type == BuildingEnum.SHOP) {
            Image img = new ImageGetter().getImageOf(ImagesEnum.SHOP);
            sprite = new ImageView();

            sprite.fitHeightProperty().bind(gw.getCellSizeY().multiply(0.95d));
            sprite.fitWidthProperty().bind(gw.getCellSizeX().multiply(0.95d));
            sprite.setImage(img);
            Game.Debug(3, "Created Building image SHOP :" + sprite.getBoundsInLocal());
            this.getChildren().add(sprite);
        }
        else if(this.type == BuildingEnum.OFFICE) {
            Image img = new ImageGetter().getImageOf(ImagesEnum.OFFICE);
            sprite = new ImageView();

            sprite.fitHeightProperty().bind(gw.getCellSizeY().multiply(0.95d));
            sprite.fitWidthProperty().bind(gw.getCellSizeX().multiply(0.95d));
            sprite.setImage(img);
            Game.Debug(3, "Created Building image OFFICE :" + sprite.getBoundsInLocal());
            this.getChildren().add(sprite);
        }

        // Rotation variable 
        int rotation = ((int)gridPos.getX() + (int)gridPos.getY())%4;
        sprite.setRotate(rotation*90);

    }

}