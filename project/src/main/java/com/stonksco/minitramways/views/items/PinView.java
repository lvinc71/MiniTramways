package com.stonksco.minitramways.views.items;

import com.stonksco.minitramways.views.ColorEnum;
import com.stonksco.minitramways.views.GameView;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectExpression;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.*;

public class PinView extends StackPane {

    private ImageView pinImage;
    private final int nb;


    public PinView(GameView gw, int nb) {
        super();
        this.nb = nb;

        if(nb > 0){
            Image img = new ImageGetter().getImageOf(ImagesEnum.PIN);
            pinImage = new ImageView();
            Text text = new Text(String.valueOf(nb));


            ObjectExpression<Font> dynFont = Bindings.createObjectBinding(() -> Font.font("Helvetica", FontWeight.SEMI_BOLD, FontPosture.REGULAR, getWidth()*0.55d), widthProperty());
            text.fontProperty().bind(dynFont);
            text.setFill(gw.getColor(ColorEnum.PIN_COLOR));

            pinImage.fitHeightProperty().bind(gw.getCellSizeY().multiply(0.85d+(nb/15d)));
            pinImage.setPreserveRatio(true);
            pinImage.translateYProperty().bind(gw.getCellSizeY().multiply(-0.5d));
            pinImage.setImage(img);


            this.getChildren().add(pinImage);

            text.setTextAlignment(TextAlignment.CENTER);
            text.wrappingWidthProperty().bind(gw.getCellSizeY().multiply(1d));
            text.translateYProperty().bind(pinImage.fitHeightProperty().multiply(-0.15).add(gw.getCellSizeY().multiply(-0.5)));
            text.autosize();

            this.getChildren().add(text);

        }
    }

    public int getNb() {
        return nb;
    }

}

