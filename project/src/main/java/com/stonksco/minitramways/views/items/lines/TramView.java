package com.stonksco.minitramways.views.items.lines;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.items.ImageGetter;
import com.stonksco.minitramways.views.items.ImagesEnum;
import com.stonksco.minitramways.views.items.PinView;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Gère l'affichage d'un tram
 */
public class TramView extends Group {

    private final LineView lv;
    private LinePartView lp;
    private final GameView gw;


    private final ImageView sprite;
    private PinView pv;

    public TramView(LineView lv,double at, GameView gw, int colorID, int peopleAmount) {
        super();
        this.lv = lv;
        this.gw = gw;
        this.lp = lv.getPartAt(at);


        Image img = null;

        try{
            switch (colorID){
                case 0:
                    img = new ImageGetter().getImageOf(ImagesEnum.TRAMWAY_GOLD);
                    break;
                case 1:
                    img = new ImageGetter().getImageOf(ImagesEnum.TRAMWAY_BLUE);
                    break;
                case 2:
                    img = new ImageGetter().getImageOf(ImagesEnum.TRAMWAY_RED);
                    break;
                case 3:
                    img = new ImageGetter().getImageOf(ImagesEnum.TRAMWAY_LIME);
                    break;
                case 4:
                    img = new ImageGetter().getImageOf(ImagesEnum.TRAMWAY_PURPLE);
                    break;
                case 5:
                    img = new ImageGetter().getImageOf(ImagesEnum.TRAMWAY_CYAN);
                    break;
                case 6:
                    img = new ImageGetter().getImageOf(ImagesEnum.TRAMWAY_YELLOW);
                    break;
                case 7:
                    img = new ImageGetter().getImageOf(ImagesEnum.TRAMWAY_ROSEGOLD);
                    break;
            }
        } catch(Exception e) {
            Game.Debug(1,"ERROR loading an asset : "+e.getMessage());
        }

        //Image img = new ImageGetter().getImageOf(ImagesEnum.TRAMWAY_GOLD);
        sprite  = new ImageView();
        sprite.setPreserveRatio(true);
        // Règle l'échelle par rapport à la taille d'une cellule
        sprite.fitHeightProperty().bind(gw.getCellSizeY().divide(2));
        double ratio = img.getWidth()/img.getHeight();
        sprite.fitWidthProperty().bind(sprite.fitHeightProperty().multiply(ratio));
        // Règle la rotation selon le tronçon courant

        sprite.setImage(img);


        sprite.translateXProperty().bind(sprite.fitWidthProperty().divide(-2d));
        sprite.translateYProperty().bind(sprite.fitHeightProperty().divide(-2d));

        this.getChildren().add(sprite);


        // Pin

        if(peopleAmount>0) {
            pv = new PinView(gw,peopleAmount);
            this.getChildren().add(pv);
            pv.translateXProperty().bind(sprite.fitWidthProperty().multiply(-0.5d));
            pv.translateYProperty().bind(sprite.fitHeightProperty().multiply(-1d));
        }

        positionAt(at);

    }

    private void positionAt(double at) {
        this.layoutXProperty().bind(lv.getPosXAt(at));
        this.layoutYProperty().bind(lv.getPoxYAt(at));
        lp = lv.getPartAt(at);
        this.sprite.setRotate(lp.getOrientation());
    }



}
