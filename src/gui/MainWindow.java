package gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class MainWindow extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // BUTTONS PLAY & QUIT  ************************************************************************************
        VBox vBox = new VBox();
        vBox.setPrefWidth(250);
        vBox.setPrefHeight(100);

        Button play = new Button("Play");
        play.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
        play.setTranslateX(350);
        play.setTranslateY(200);
        play.setMinWidth(vBox.getPrefWidth());
        play.setMinHeight(vBox.getPrefHeight());
        play.setStyle("-fx-background-color: LIGHTGREY");
        play.setOnMousePressed(event -> {
            play.setStyle("-fx-background-color: LINEN");
        });
        play.setOnMouseEntered(event -> {
            play.setStyle("-fx-background-color: LIGHTSLATEGREY");

        });
        play.setOnMouseExited(event -> {
            play.setStyle("-fx-background-color: LIGHTGREY");

        });

        Button quit = new Button("Quit");
        quit.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
        quit.setTranslateX(350);
        quit.setTranslateY(370);
        quit.setMinWidth(vBox.getPrefWidth());
        quit.setMinHeight(vBox.getPrefHeight());
        quit.setStyle("-fx-background-color: LIGHTGREY");
        quit.setOnMousePressed(event -> {
            quit.setStyle("-fx-background-color: LINEN");
        });
        quit.setOnMouseEntered(event -> {
            quit.setStyle("-fx-background-color: LIGHTSLATEGREY");

        });
        quit.setOnMouseExited(event -> {
            quit.setStyle("-fx-background-color: LIGHTGREY");

        });

        EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // TODO Auto-generated method stub

                if (event.getSource() == play) {
                    ModeSelectionWindow.start(primaryStage);
                }
                if (event.getSource() == quit) {
                    System.exit(0);
                }

            }

        };

        play.setOnMouseClicked(handler);
        quit.setOnMouseClicked(handler);
        // BUTTONS PLAY & QUIT  ************************************************************************************

        //General window setup
        Group root = new Group();
        Scene scene = new Scene(root, 985, 685);

        root.getChildren().addAll(play, quit);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Backgammon Video Game");
        primaryStage.setResizable(false);
        primaryStage.show();
    }


}  