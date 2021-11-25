package com.stonksco.minitramways.views.items;

import com.stonksco.minitramways.views.ColorEnum;
import com.stonksco.minitramways.views.GameView;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.text.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PinView extends StackPane {

    private ImageView pinImage;
    private int nb;


    public PinView(GameView gw, int nb) {
        super();
        this.nb = nb;

        if(nb > 0){
            Image img = new ImageGetter().getImageOf(ImagesEnum.PIN);
            pinImage = new ImageView();
            Text text = new Text(String.valueOf(nb));

            text.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 16));
            text.setFill(gw.getColor(ColorEnum.PIN_COLOR));

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

