package gui;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class AIvAIsettings {

    public static void start(Stage primaryStage) {
        // TODO Auto-generated method stub
        VBox vBox1 = new VBox();
        vBox1.setPrefWidth(300);
        vBox1.setPrefHeight(90);

        Button easyW = new Button("Easy");
        Button mediumW = new Button("Medium");
        Button hardW = new Button("Hard");

        Button easyB = new Button("Easy");
        Button mediumB = new Button("Medium");
        Button hardB = new Button("Hard");

        Button BACK = new Button("Back");
        Button FORWARD = new Button("Play");

        BACK.setFont(Font.font("Times New Roman", FontWeight.BOLD, 26));
        BACK.setTranslateX(320);
        BACK.setTranslateY(570);
        BACK.setMinWidth(170);
        BACK.setMinHeight(vBox1.getPrefHeight());
        BACK.setOnMousePressed(event -> {

        });

        FORWARD.setFont(Font.font("Times New Roman", FontWeight.BOLD, 26));
        FORWARD.setTranslateX(550);
        FORWARD.setTranslateY(570);
        FORWARD.setMinWidth(170);
        FORWARD.setMinHeight(vBox1.getPrefHeight());
        FORWARD.setOnMousePressed(event -> {
            System.out.println("CHANGE SCREEN2");

            if (easyW.getStyle().isEmpty() || mediumW.getStyle().isEmpty() || hardW.getStyle().isEmpty()){
            System.out.println("CHANGE SCREEN3");
            } else {
                System.out.println("CHANGE SCREEN");
                AIvAI.start(primaryStage);
            }
        });

        // WHITE SETTINGS
        easyW.setFont(Font.font("Times New Roman", FontWeight.BOLD, 26));
        easyW.setTranslateX(204);
        easyW.setTranslateY(130);
        easyW.setMinWidth(vBox1.getPrefWidth());
        easyW.setMinHeight(vBox1.getPrefHeight());
        easyW.setStyle("");
        easyW.setOnMousePressed(event -> {
            if (easyW.getStyle().isEmpty()){
                easyW.setStyle("-fx-background-color: LINEN");
                mediumW.setStyle("");
                hardW.setStyle("");
            } else {
                easyW.setStyle("");
            }
        });
        mediumW.setFont(Font.font("Times New Roman", FontWeight.BOLD, 26));
        mediumW.setTranslateX(204);
        mediumW.setTranslateY(270);
        mediumW.setMinWidth(vBox1.getPrefWidth());
        mediumW.setMinHeight(vBox1.getPrefHeight());
        mediumW.setStyle("");
        mediumW.setOnMousePressed(event -> {
            if (mediumW.getStyle().isEmpty()){
                mediumW.setStyle("-fx-background-color: LINEN");
                hardW.setStyle("");
                easyW.setStyle("");

            } else {
                mediumW.setStyle("");
            }
        });
        hardW.setFont(Font.font("Times New Roman", FontWeight.BOLD, 26));
        hardW.setTranslateX(204);
        hardW.setTranslateY(420);
        hardW.setMinWidth(vBox1.getPrefWidth());
        hardW.setMinHeight(vBox1.getPrefHeight());
        hardW.setStyle("");
        hardW.setOnMousePressed(event -> {
            if (hardW.getStyle().isEmpty()){
                hardW.setStyle("-fx-background-color: LINEN");
                easyW.setStyle("");
                mediumW.setStyle("");
            } else {
                hardW.setStyle("");
            }
        });

        // BLACK SETTINGS
        easyB.setFont(Font.font("Times New Roman", FontWeight.BOLD, 26));
        easyB.setTranslateX(536);
        easyB.setTranslateY(130);
        easyB.setMinWidth(vBox1.getPrefWidth());
        easyB.setMinHeight(vBox1.getPrefHeight());
        easyB.setStyle("");
        easyB.setOnMousePressed(event -> {
            if (easyB.getStyle().isEmpty()){
                easyB.setStyle("-fx-background-color: LINEN");
                mediumB.setStyle("");
                hardB.setStyle("");
            } else {
                easyB.setStyle("");
            }
        });
        mediumB.setFont(Font.font("Times New Roman", FontWeight.BOLD, 26));
        mediumB.setTranslateX(536);
        mediumB.setTranslateY(270);
        mediumB.setMinWidth(vBox1.getPrefWidth());
        mediumB.setMinHeight(vBox1.getPrefHeight());
        mediumB.setStyle("");
        mediumB.setOnMousePressed(event -> {
            if (mediumB.getStyle().isEmpty()){
                mediumB.setStyle("-fx-background-color: LINEN");
                hardB.setStyle("");
                easyB.setStyle("");

            } else {
                mediumB.setStyle("");
            }
        });
        hardB.setFont(Font.font("Times New Roman", FontWeight.BOLD, 26));
        hardB.setTranslateX(536);
        hardB.setTranslateY(420);
        hardB.setMinWidth(vBox1.getPrefWidth());
        hardB.setMinHeight(vBox1.getPrefHeight());
        hardB.setStyle("");
        hardB.setOnMousePressed(event -> {
            if (hardB.getStyle().isEmpty()){
                hardB.setStyle("-fx-background-color: LINEN");
                easyB.setStyle("");
                mediumB.setStyle("");
            } else {
                hardB.setStyle("");
            }
        });

        Label w = new Label("White");
        w.setFont(Font.font("Times New Roman", FontWeight.BOLD, 39));
        w.setTranslateX(300);
        w.setTranslateY(55);

        Label b = new Label("Black");
        b.setFont(Font.font("Times New Roman", FontWeight.BOLD, 39));
        b.setTranslateX(640);
        b.setTranslateY(55);

        //Creating EventHandler
        EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // TODO Auto-generated method stub

                if (event.getSource() == FORWARD && (!(hardW.getStyle().isEmpty()) || !(easyW.getStyle().isEmpty()) || !(mediumW.getStyle().isEmpty())) && (!(hardB.getStyle().isEmpty()) || !(easyB.getStyle().isEmpty()) || !(mediumB.getStyle().isEmpty()))) {

                    Settings game = new Settings();

                    if (!(easyW.getStyle().isEmpty())){
                        game.changeType1("fullrandom");
                    }

                    if (!(mediumW.getStyle().isEmpty())){
                        game.changeType1("HighestScorePruned");
//                        game.changeType1("HighestScore");
                    }

                    if (!(hardW.getStyle().isEmpty())){
                        game.changeType1("MostVisitedPruned");
                    }

                    if (!(easyB.getStyle().isEmpty())){
                        game.changeType2("fullrandom");
                    }

                    if (!(mediumB.getStyle().isEmpty())){
//                        game.changeType2("HighestScore");
                        game.changeType2("HighestScorePruned");

                    }

                    if (!(hardB.getStyle().isEmpty())){
                        game.changeType2("MostVisitedPruned");
                    }

                    AIvAI.start(primaryStage);
                }

                if (event.getSource() == BACK) {
                    ModeSelectionWindow.start(primaryStage);
                }

//                if (event.getSource() == CC) {
//                    System.out.println("C V C");
//                    AIvAI.start(primaryStage);
//                }
            }

        };

        //Adding Handler for the play and pause button
        easyW.setOnMouseClicked(handler);
        mediumW.setOnMouseClicked(handler);
        hardW.setOnMouseClicked(handler);

        easyB.setOnMouseClicked(handler);
        mediumB.setOnMouseClicked(handler);
        hardB.setOnMouseClicked(handler);

        FORWARD.setOnMouseClicked(handler);
        BACK.setOnMouseClicked(handler);



        //Creating Group and scene
        Group root = new Group();
        root.getChildren().addAll(easyW, mediumW, hardW, easyB, mediumB, hardB, BACK, FORWARD, w, b);

        Scene scene = new Scene(root, 985, 685);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}  