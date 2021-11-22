package com.stonksco.minitramways.views.layers.cells;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class PinView extends CellView {



    /**
     * Évènement de clic sur la cellule
     * @author Léo Vincent
     */
    EventHandler<MouseEvent> cellClickEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            // Clic gauche
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                Game.Debug(3,"Cell click triggered.");
                    CellView clicked = (CellView)mouseEvent.getSource();
                    Game.Debug(2,"Clicked on cell at ( "+clicked.getGridPos().getX()+" , "+clicked.getGridPos().getY()+" )");
                    gw.cellClick(clicked);
                }
            }
        };

    public PinView(GameView gw, Vector2 gridPos) {
        super(gw,gridPos);
        this.addEventFilter(MouseEvent.MOUSE_CLICKED,cellClickEvent);
    }

}
