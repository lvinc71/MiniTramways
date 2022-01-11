package com.stonksco.minitramways.views.ui;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.views.Clock;
import com.stonksco.minitramways.views.ColorEnum;
import com.stonksco.minitramways.views.GameView;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Map;

public class infosLayer extends BorderPane {

    private GameView gw;

    // Zones
    private HBox topRight;


    // elements top-right
    private Group money;
    private Group time;
    private Group satisfaction;

    // Time
    private GridPane timeCounter;
    private Text min1;
    private Text min2;
    private Text min3;
    private Text seperator;
    private Text second1;
    private Text second2;

    // Money
    private GridPane moneyCounter;
    private Text m1;
    private Text m2;
    private Text m3;
    private Text m4;

    // Error message
    private Text errorText;
    private Rectangle errorBackground;
    private StackPane errorGroup;

    // Satisfaction
    private ProgressBar satisfactionBar;

    public infosLayer(GameView gw) {
        this.gw = gw;

        topRight = new HBox(15);
        time = new Group();
        money = new Group();
        topRight.setAlignment(Pos.CENTER_RIGHT);
        topRight.setPadding(new Insets(15));
        this.setTop(topRight);

        setupSatisfaction();
        setupMoney();
        setupTime();
        setupError();

    }

    public void setupMoney() {
        moneyCounter = new GridPane();
        m1 = new Text();
        m2 = new Text();
        m3 = new Text();
        m4 = new Text();

        Font f = new Font(22);
        m1.setFont(f);
        m2.setFont(f);
        m3.setFont(f);
        m4.setFont(f);

        topRight.getChildren().add(moneyCounter);

        moneyCounter.add(m1,0,0);
        moneyCounter.add(m2,1,0);
        moneyCounter.add(m3,2,0);
        moneyCounter.add(m4,3,0);

    }

    public void setupTime() {
        timeCounter = new GridPane();
        min1 = new Text();
        min2 = new Text();
        min3 = new Text();
        seperator = new Text(":");
        second1 = new Text();
        second2 = new Text();

        Font f = new Font(22);
        min1.setFont(f);
        min2.setFont(f);
        min3.setFont(f);
        seperator.setFont(f);
        second1.setFont(f);
        second2.setFont(f);

        timeCounter.add(min1,0,0);
        timeCounter.add(min2,1,0);
        timeCounter.add(min3,2,0);
        timeCounter.add(seperator,3,0);
        timeCounter.add(second1,4,0);
        timeCounter.add(second2,5,0);

        updateTimer();

        topRight.getChildren().add(timeCounter);

    }

    private void updateTimer() {

        long elapsed = Clock.get().GameElapsed();
        elapsed /= Math.pow(10,9);

        int minutes = (int) (elapsed/60);
        int seconds = (int) (elapsed%60);

        if(minutes>999)
            minutes=999;

        String minStr = String.valueOf(minutes);
        if(minutes<10)
            minStr = "0"+minStr;
        if(minutes<100)
            minStr = "0"+minStr;

        String secondsStr = String.valueOf(seconds);

        if(seconds<10)
            secondsStr= "0"+secondsStr;

        char[] minChars = minStr.toCharArray();
        char[] secChars = secondsStr.toCharArray();

        if(minutes>99)
            min1.setText(String.valueOf(minChars[0]));
        else
            min1.setText("");

        if(minutes>9)
            min2.setText(String.valueOf(minChars[1]));
        else
            min2.setText("");

        min3.setText(String.valueOf(minChars[2]));

        second1.setText(String.valueOf(secChars[0]));
        second2.setText(String.valueOf(secChars[1]));

    }

    private void updateMoney() {
        int money = Game.get().getMoney();

        String moneyStr = String.valueOf(money);
        if(money<1000)
            moneyStr = "0"+moneyStr;
        if(money<100)
            moneyStr = "0"+moneyStr;
        if(money<10)
            moneyStr = "0"+moneyStr;

        char[] moneyChars = moneyStr.toCharArray();

        m1.setText(String.valueOf(moneyChars[0]));
        m2.setText(String.valueOf(moneyChars[1]));
        m3.setText(String.valueOf(moneyChars[2]));
        m4.setText(String.valueOf(moneyChars[3]));
    }

    public void Update() {
        updateTimer();
        updateMoney();
        updateSatisfaction();
        updateErrorMessage();
    }

    private void setupSatisfaction() {
        satisfactionBar = new ProgressBar();
        topRight.getChildren().add(satisfactionBar);
        updateSatisfaction();
    }

    private void updateSatisfaction() {
        satisfactionBar.progressProperty().setValue(Game.get().getSatisfaction()/100d);
    }

    private final Map<String, String> messages = Map.ofEntries(
            Map.entry("money","Vous n'avez pas suffisamment d'argent pour faire cela."),
            Map.entry("obstructed","Un autre objet vous empêche de construire ici."),
            Map.entry("unknown","Une erreur inconnue vous empêche de faire ceci.")
    );

    private void setupError() {
        errorBackground = new Rectangle();
        errorText = new Text("");
        errorGroup = new StackPane();

        errorBackground.setFill(gw.getColor(ColorEnum.PIN_COLOR));
        errorBackground.arcWidthProperty().bind(gw.getCellSizeX().multiply(0.5d));
        errorBackground.arcHeightProperty().bind(gw.getCellSizeY().multiply(0.5d));
        errorBackground.heightProperty().bind(gw.getCellSizeY().multiply(2.5d));
        errorBackground.widthProperty().bind(gw.getCellSizeX().multiply(12.5d));

        ObjectProperty<Font> dynFont = new SimpleObjectProperty<Font>(new Font(25));

        widthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth)
            {
                dynFont.set(Font.font(gw.getCellSizeX().divide(2d).doubleValue()));
            }

        });

        errorText.fontProperty().bind(dynFont);
        this.layout();
        errorText.setFill(Color.WHITE);

        errorGroup.getChildren().add(errorBackground);
        errorGroup.getChildren().add(errorText);
        this.setCenter(errorGroup);
        errorGroup.setOpacity(0);
    }

    private long timeUntilMessageHides = 0l;
    private boolean messageHidden = true;

    public void setErrorMessage(String messagecode) {

        if(messages.get(messagecode)!=null) {
            errorText.setText(messages.get(messagecode));
        } else {
            errorText.setText(messages.get("unknown"));
        }
        timeUntilMessageHides=7000000000l;
    }

    private void updateErrorMessage() {
        if(timeUntilMessageHides>0) {
            if(messageHidden) {
                errorGroup.setOpacity(1);
                messageHidden=false;
            }
            timeUntilMessageHides-=Clock.get().DeltaTime();
        } else {
            if(!messageHidden) {
                errorGroup.setOpacity(0);
                messageHidden=true;
            }
        }
    }

}

