package com.stonksco.minitramways.views.items;

import com.stonksco.minitramways.views.GameView;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PinView extends Group {

    private ImageView pinImage;
    private int nb;

    public PinView(GameView gw, int nb) {
        super();
        this.nb = nb;

        if(nb == 0){
            return;
        } else {
            Image img = new ImageGetter().getImageOf(ImagesEnum.PIN);
            pinImage = new ImageView();

            pinImage.fitHeightProperty().bind(gw.getCellSizeY().multiply(0.95d));
            pinImage.fitWidthProperty().bind(gw.getCellSizeX().multiply(0.95d));
            pinImage.setImage(img);
            this.getChildren().add(pinImage);
        }
    }

}

