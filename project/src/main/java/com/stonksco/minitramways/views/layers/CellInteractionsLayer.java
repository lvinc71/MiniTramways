package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.layers.cells.CellInteractionView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CellInteractionsLayer extends GridView {

    public CellInteractionsLayer(GameView gw, Vector2 size) {
        super(gw,size);
        fill(CellInteractionView.class);
        this.addEventFilter(MouseEvent.MOUSE_CLICKED,gridClickEvent);
    }

    /**
     * Évènement de clic droit sur la grille
     * @author Léo Vincent
     */
    EventHandler<MouseEvent> gridClickEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            // Clic droit
            if(mouseEvent.getButton() == MouseButton.SECONDARY)
            {
                gw.gridRightClick();
            }

        }
    };
}
