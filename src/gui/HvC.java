package gui;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class HvC {

    public static void start(Stage primaryStage)  {

        Rectangle background = new Rectangle();
        background.setWidth(1300);
        background.setHeight(600);
        background.setFill(Color.BEIGE);

        Line middleLine = new Line();
        middleLine.setStartX(600.0f);
        middleLine.setStartY(0.0f);
        middleLine.setEndX(600.0f);
        middleLine.setEndY(600.0f);

        Line endLine = new Line();
        endLine.setStartX(1200.0f);
        endLine.setStartY(0.0f);
        endLine.setEndX(1200.0f);
        endLine.setEndY(1200.0f);

        int temp = 100;
        Group pieces = new Group();
        Button button = new Button("wahtevs");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
//                final int finaltemp = temp;
//                for(int i = 0; i < 5; i++) {
//                    Rectangle r = new Rectangle();
//                    r.setWidth(100);
//                    r.setHeight(50);
//                    r.setFill(Color.RED);
//                    r.setLayoutX(finaltemp);
//                    r.setLayoutY(finaltemp);
//                    temp += 200;
//                    pieces.getChildren().add(r);
//
//                    try {
//                        Thread.sleep(500);
//                    } catch(Exception e){
//                        continue;
//                    }
//                }
            }
        });



        Group board = new Group();
        board.getChildren().addAll(background, middleLine, endLine, pieces, button);
        board.setTranslateX(20);
        board.setTranslateY(20);


        Group root = new Group();
        root.getChildren().addAll(board);
        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);

        primaryStage.setScene(scene);
        primaryStage.setWidth(1500);
        primaryStage.setHeight(900);
        primaryStage.centerOnScreen();



    }
}
