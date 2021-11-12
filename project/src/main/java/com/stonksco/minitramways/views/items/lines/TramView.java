package com.stonksco.minitramways.views.items.lines;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.items.ImageGetter;
import com.stonksco.minitramways.views.items.ImagesEnum;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * Gère l'affichage d'un tram
 */
public class TramView extends Group {

    private LineView lv;
    private LinePart lp;
    private GameView gw;

    private ImageView sprite;

    public TramView(LineView lv, LinePart lp, GameView gw) {
        super();
        this.lp = lp;
        this.lv = lv;
        this.gw = gw;

        Image img = new ImageGetter().getImageOf(ImagesEnum.TRAMWAY_GOLD);
        sprite  = new ImageView();
        sprite.setPreserveRatio(true);
        // Règle l'échelle par rapport à la taille d'une cellule
        sprite.fitHeightProperty().bind(gw.getCellSizeY().divide(2));
        double ratio = img.getWidth()/img.getHeight();
        sprite.fitWidthProperty().bind(sprite.fitHeightProperty().multiply(ratio));
        // Règle la rotation selon le tronçon courant
        sprite.setRotate(lp.getOrientation());

        positionAt(50);

        sprite.setImage(img);


        sprite.translateXProperty().bind(sprite.fitWidthProperty().divide(-2d));
        sprite.translateYProperty().bind(sprite.fitHeightProperty().divide(-2d));


        this.getChildren().add(sprite);
        Game.Debug(3,"Tramway created at "+new Vector2(sprite.getX(),sprite.getY())+" in those bounds : "+sprite.getBoundsInLocal());
    }

    public void positionAt(int percentage) {
        this.translateYProperty().bind(lp.getPosYAt(percentage));
        this.translateXProperty().bind(lp.getPosXAt(percentage));

        if(Game.get().getDebug()>2) {
            Ellipse e = new Ellipse(0,0,3,3);
            e.setFill(Color.RED);
            this.getChildren().add(e);
        }

    }

}
