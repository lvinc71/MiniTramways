package com.stonksco.minitramways.views.layers;

import com.stonksco.minitramways.views.Clock;
import com.stonksco.minitramways.views.GameView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class TextLayer  extends Pane {
    private GameView gw;
    private String scores = "Score =1000";
    private HBox rootscore;
    private HBox rootclock;
    private HBox rootdate;
    private Label score;
    private Label clock;
    private Label date;
    private long time;

    public TextLayer(GameView gw){
        this.gw = gw;
        rootclock = new HBox();
        rootscore = new HBox();
        rootdate = new HBox();

        rootdate.setPadding(new Insets(5,5,5,5));
        rootscore.setPadding(new Insets(30,5,5,5));
        rootclock.setPadding(new Insets(55,5,5,5));

        score = new Label(scores);
        clock = new Label("Time : "+time);
        date = new Label("Date :");

        time = Clock.get().DeltaTime();

        score.setFont(Font.font("Inter",20));
        clock.setFont(Font.font("Inter",20));
        date.setFont(Font.font("Inter",20));

        rootscore.getChildren().add(score);

        rootclock.getChildren().add(clock);

        rootdate.getChildren().add(date);

        rootdate.layoutYProperty().bind(gw.getCellSizeY().multiply(2d));
        rootdate.layoutXProperty().bind(gw.getCellSizeX().multiply(43d));

        rootclock.layoutYProperty().bind(gw.getCellSizeY().multiply(2d));
        rootclock.layoutXProperty().bind(gw.getCellSizeX().multiply(43d));

        rootscore.layoutYProperty().bind(gw.getCellSizeY().multiply(2d));
        rootscore.layoutXProperty().bind(gw.getCellSizeX().multiply(43d));

        this.getChildren().add(rootdate);
        this.getChildren().add(rootclock);
        this.getChildren().add(rootscore);

    }

    public void update(){
        score.setText(scores);
        time+=Clock.get().GameDeltaTime();
        clock.setText("Time : "+time);
    }


}
