package com.stonksco.minitramways.views;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class StationView extends Group {

    private ImageView sprite;

    private int people = 0;

    public StationView(Vector2 cellSize) {
        super();
        this.setViewOrder(50);
        Image img = new ImageGetter().getImageOf(ImagesEnum.STATION);
        sprite = new ImageView();

        sprite.setFitHeight(cellSize.getY());
        sprite.setFitWidth(cellSize.getX());
        sprite.setImage(img);
        Game.Debug(3,"Created station image :"+sprite.getBoundsInLocal());
        this.getChildren().add(sprite);
        Game.Debug(3,"Created a station with a cell size of "+cellSize);
    }
}
