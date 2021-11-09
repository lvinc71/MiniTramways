package com.stonksco.minitramways.views;

import com.stonksco.minitramways.logic.Game;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

public class ImageGetter {

    public Image getImageOf(ImagesEnum img) {
        Image image = null;
        URL imgPath;
        try {
            switch (img) {
                case STATION:
                    imgPath = getClass().getResource("/assets/station.png");
                    Game.Debug(3,"Found URL for STATION image : "+imgPath);
                    image = new Image(imgPath.toString());
                    break;
                case TRAMWAY_GOLD:
                    imgPath = getClass().getResource("/assets/tram_gold.png");
                    Game.Debug(3,"Found URL for TRAMWAY_GOLD image : "+imgPath);
                    image = new Image(imgPath.toString());
                    break;
            }
        } catch(Exception e) {
            Game.Debug(1,"ERROR loading an asset : "+e.getMessage());
        }
        return image;
    }

}

