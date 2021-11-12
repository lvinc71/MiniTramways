package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import com.stonksco.minitramways.views.layers.cells.PinView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class PinsView extends GridView {

    public PinsView(GameView gw, Vector2 size) {
        super(gw,size);
        fill(PinView.class);
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
