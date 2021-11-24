package com.stonksco.minitramways.views.items;

import com.stonksco.minitramways.views.GameView;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

public class PinView extends StackPane {

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
            Text text = new Text("10");


            pinImage.fitHeightProperty().bind(gw.getCellSizeY());
            pinImage.setPreserveRatio(true);
            pinImage.translateYProperty().bind(gw.getCellSizeY().multiply(-0.5d));
            pinImage.setImage(img);

            this.getChildren().add(pinImage);

            text.setTextAlignment(TextAlignment.CENTER);
            text.wrappingWidthProperty().bind(gw.getCellSizeY().multiply(1d));
            text.translateYProperty().bind(gw.getCellSizeY().multiply(-0.7d));
            text.autosize();

            this.getChildren().add(text);

        }
    }

}

