package com.stonksco.minitramways;

import com.stonksco.minitramways.logic.Game;
import com.stonksco.minitramways.views.GameView;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;

public class MiniTramways extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        System.out.println("Launching Mini Tramways...");


        System.out.println("\n\n  ______     __                          __                         ______                \n" +
                " /      \\   |  \\                        |  \\                       /      \\               \n" +
                "|  $$$$$$\\ _| $$_     ______   _______  | $$   __   _______       |  $$$$$$\\  ______      \n" +
                "| $$___\\$$|   $$ \\   /      \\ |       \\ | $$  /  \\ /       \\      | $$   \\$$ /      \\     \n" +
                " \\$$    \\  \\$$$$$$  |  $$$$$$\\| $$$$$$$\\| $$_/  $$|  $$$$$$$      | $$      |  $$$$$$\\    \n" +
                " _\\$$$$$$\\  | $$ __ | $$  | $$| $$  | $$| $$   $$  \\$$    \\       | $$   __ | $$  | $$    \n" +
                "|  \\__| $$  | $$|  \\| $$__/ $$| $$  | $$| $$$$$$\\  _\\$$$$$$\\      | $$__/  \\| $$__/ $$ __ \n" +
                " \\$$    $$   \\$$  $$ \\$$    $$| $$  | $$| $$  \\$$\\|       $$       \\$$    $$ \\$$    $$|  \\\n" +
                "  \\$$$$$$     \\$$$$   \\$$$$$$  \\$$   \\$$ \\$$   \\$$ \\$$$$$$$         \\$$$$$$   \\$$$$$$  \\$$\n\n\n");



        getParameters().getNamed().toString();

        if(getParameters().getNamed().containsKey("debug")) {
            int nb = Integer.valueOf(getParameters().getNamed().get("debug"));
            if (nb > 0)
                Game.get().setDebug(nb);
        }

        primaryStage.setResizable(true);
        primaryStage.setTitle("Mini Tramways");

        Group root = new Group();

        GameView gw = new GameView(root,primaryStage);
        primaryStage.setScene(gw);
        primaryStage.show();
        gw.enable();







    }
}
