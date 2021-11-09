package com.stonksco.minitramways.views;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

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
        sprite.setFitHeight(gw.GetCellSize().getY()/2d);
        sprite.setRotate(lp.getOrientation());

        Vector2 pos = lp.getPosAt(50);
        sprite.setX(pos.getX());
        sprite.setY(pos.getY());
        this.setViewOrder(35);
        sprite.setImage(img);
        sprite.setTranslateY(sprite.getBoundsInLocal().getHeight()/-2d);
        sprite.setTranslateX(sprite.getBoundsInLocal().getWidth()/-2d);
        this.getChildren().add(sprite);
        Game.Debug(3,"Tramway created at "+pos+" in those bounds : "+sprite.getBoundsInLocal());
    }

}
