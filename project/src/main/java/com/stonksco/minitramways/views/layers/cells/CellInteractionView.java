package com.stonksco.minitramways.views.layers.cells;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.logic.Vector2;
import com.stonksco.minitramways.views.GameView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class CellInteractionView extends CellView {

    /**
     * Évènement de clic sur la cellule
     * @author Léo Vincent
     */
    EventHandler<MouseEvent> cellClickEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
                CellView clicked = (CellView)mouseEvent.getSource();
                Game.Debug(2,"Clicked on cell at ( "+clicked.getGridPos().getX()+" , "+clicked.getGridPos().getY()+" )");
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    gw.cellLeftClick(clicked);
                } else if(mouseEvent.getButton() == MouseButton.SECONDARY) {
                    gw.cellRightClick(clicked);
                }
            }
        };

    // Évènement d'entrée du curseur dans la cellule
    EventHandler<MouseEvent> cellEnterEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            Vector2 pos = new Vector2(GridPane.getColumnIndex((CellView)mouseEvent.getSource()),GridPane.getRowIndex((CellView)mouseEvent.getSource()));
            gw.CellEnter(pos);
        }
    };

    // Évènement de sortie du curseur de la cellule
    EventHandler<MouseEvent> cellExitEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            gw.CellExit(new Vector2(GridPane.getColumnIndex((CellView)mouseEvent.getSource()),GridPane.getRowIndex((CellView)mouseEvent.getSource())));
        }
    };


    public CellInteractionView(GameView gw, Vector2 gridPos) {
        super(gw,gridPos);
        this.addEventFilter(MouseEvent.MOUSE_CLICKED,cellClickEvent);
        this.addEventFilter(MouseEvent.MOUSE_ENTERED,cellEnterEvent);
        this.addEventFilter(MouseEvent.MOUSE_EXITED,cellExitEvent);
    }

}
