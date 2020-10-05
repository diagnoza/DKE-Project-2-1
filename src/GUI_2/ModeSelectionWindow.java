package GUI_2;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class ModeSelectionWindow {

    public static void start(Stage primaryStage) {
        // TODO Auto-generated method stub
        VBox vBox1 = new VBox();
        vBox1.setPrefWidth(300);
        vBox1.setPrefHeight(90);
        Button HH = new Button("Human vs Human");
        HH.setFont(Font.font("Times New Roman", FontWeight.BOLD, 26));
        HH.setTranslateX(370);
        HH.setTranslateY(150);
        HH.setMinWidth(vBox1.getPrefWidth());
        HH.setMinHeight(vBox1.getPrefHeight());
        HH.setStyle("-fx-background-color: LIGHTGREY");
        HH.setOnMousePressed(event -> {
            HH.setStyle("-fx-background-color: LINEN");
        });
        HH.setOnMouseEntered(event -> {
            HH.setStyle("-fx-background-color: LIGHTSLATEGREY");

        });
        HH.setOnMouseExited(event -> {
            HH.setStyle("-fx-background-color: LIGHTGREY");

        });

        Button HC = new Button("Human vs AI");
        HC.setFont(Font.font("Times New Roman", FontWeight.BOLD, 26));
        HC.setTranslateX(370);
        HC.setTranslateY(300);
        HC.setMinWidth(vBox1.getPrefWidth());
        HC.setMinHeight(vBox1.getPrefHeight());
        HC.setStyle("-fx-background-color: LIGHTGREY");
        HC.setOnMousePressed(event -> {
            HC.setStyle("-fx-background-color: LINEN");
        });
        HC.setOnMouseEntered(event -> {
            HC.setStyle("-fx-background-color: LIGHTSLATEGREY");

        });
        HC.setOnMouseExited(event -> {
            HC.setStyle("-fx-background-color: LIGHTGREY");

        });

        Button CC = new Button("AI vs AI");
        CC.setFont(Font.font("Times New Roman", FontWeight.BOLD, 26));
        CC.setTranslateX(370);
        CC.setTranslateY(450);
        CC.setMinWidth(vBox1.getPrefWidth());
        CC.setMinHeight(vBox1.getPrefHeight());
        CC.setStyle("-fx-background-color: LIGHTGREY");
        CC.setOnMousePressed(event -> {
            CC.setStyle("-fx-background-color: LINEN");
        });
        CC.setOnMouseEntered(event -> {
            CC.setStyle("-fx-background-color: LIGHTSLATEGREY");

        });
        CC.setOnMouseExited(event -> {
            CC.setStyle("-fx-background-color: LIGHTGREY");

        });

        //Creating EventHandler
        EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                // TODO Auto-generated method stub

                if (event.getSource() == HH) {
                    HvH.start(primaryStage);
                }
                if (event.getSource() == HC) {
                }
                if (event.getSource() == CC) {
                }
            }

        };

        //Adding Handler for the play and pause button
        HH.setOnMouseClicked(handler);
        HC.setOnMouseClicked(handler);

        //Creating Group and scene
        Group root = new Group();
        root.getChildren().addAll(HH, HC, CC);

        Scene scene = new Scene(root, 985, 685);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}  