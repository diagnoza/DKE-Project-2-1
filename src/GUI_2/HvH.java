package GUI_2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Random;

public class HvH {

    static private Random randomGenerator = new Random();
    static private int res1, res2;
    static private Button die1, die2;

    public static void start(Stage primaryStage) {

        Rectangle background = new Rectangle();
        background.setWidth(1200);
        background.setHeight(600);
        background.setFill(Color.BEIGE);

        Line middleLine = new Line();
        middleLine.setStartX(600.0f);
        middleLine.setStartY(0.0f);
        middleLine.setEndX(600.0f);
        middleLine.setEndY(600.0f);

        Group triangles = triangles();
        Group pieces = pieces();
        Group board = new Group();
        board.getChildren().addAll(background, middleLine, triangles, pieces);
        board.setTranslateX(20);
        board.setTranslateY(20);

        Group dice = dice();
        dice.setTranslateX(20);
        dice.setTranslateY(650);

        Group root = new Group();
        root.getChildren().addAll(board, dice);
        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);

        primaryStage.setScene(scene);
        primaryStage.setWidth(1500);
        primaryStage.setHeight(900);
        primaryStage.centerOnScreen();

    }


    private static Group dice() {
        Rectangle background = new Rectangle();
        background.setWidth(300);
        background.setHeight(200);
        background.setFill(Color.BEIGE);


        die1 = new Button();
        die1.setText("?");
        die1.setFont(new Font("Arial", 40));
        die1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (die1.getStyle().isEmpty() && !die1.getText().equals("?")) die1.setStyle("-fx-base: #b6e7c9;");
                else die1.setStyle("");
            }
        });
        die2 = new Button();
        die2.setFont(new Font("Arial", 40));
        die2.setTranslateX(100);
        die2.setText("?");
        die2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (die2.getStyle().isEmpty() && !die2.getText().equals("?")) die2.setStyle("-fx-base: #b6e7c9;");
                else die2.setStyle("");
            }
        });
        Group result = new Group();
        result.getChildren().addAll(die1, die2);
        result.setTranslateX(60);
        result.setTranslateY(100);

        Button roll = new Button();
        roll.setText("Roll Dice");
        roll.setPrefSize(100, 50);
        roll.setTranslateX(100);
        roll.setTranslateY(10);
        roll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                die1.setStyle("");
                die2.setStyle("");
                res1 = randomGenerator.nextInt((6 - 1) + 1) + 1;
                die1.setText(String.valueOf(res1));
                res2 = randomGenerator.nextInt((6 - 1) + 1) + 1;
                die2.setText(String.valueOf(res2));
            }
        });


        Group dice = new Group();
        dice.getChildren().addAll(background, roll, result);

        return dice;
    }

    private static Group triangles() {
        Group triangles = new Group();

        double triangleHeight = 250.0;
        double[] topLabelStartsAt = {50.0, -15.0};
        double[] topTrianglesCoordinates = {0.0, 0.0, 50.0, triangleHeight, 100.0, 0.0};
        double[] bottomLabelStartsAt = {45.0, 600};
        double[] bottomTrianglesCoordinates = {0.0, 600.0, 50.0, 600 - triangleHeight, 100.0, 600.0};

        Label l;
        Polygon p;
        for (int i = 1; i <= 12; i++) {
            l = new Label(Integer.toString(i));
            l.setLayoutX(topLabelStartsAt[0]);
            l.setLayoutY(topLabelStartsAt[1]);
            l.setTextFill(Color.BEIGE);
            topLabelStartsAt[0] += 100;

            p = new Polygon();
            p.setFill(Color.RED);
            p.setStroke(Color.BLACK);
            p.getPoints().addAll(
                    topTrianglesCoordinates[0],
                    topTrianglesCoordinates[1],
                    topTrianglesCoordinates[2],
                    topTrianglesCoordinates[3],
                    topTrianglesCoordinates[4],
                    topTrianglesCoordinates[5]);

            topTrianglesCoordinates[0] += 100;
            topTrianglesCoordinates[2] += 100;
            topTrianglesCoordinates[4] += 100;

            triangles.getChildren().addAll(l, p);
        }

        for (int i = 13; i <= 24; i++) {
            l = new Label(Integer.toString(i));
            l.setLayoutX(bottomLabelStartsAt[0]);
            l.setLayoutY(bottomLabelStartsAt[1]);
            l.setTextFill(Color.BEIGE);
            bottomLabelStartsAt[0] += 100;

            p = new Polygon();
            p.setFill(Color.RED);
            p.setStroke(Color.BLACK);
            p.getPoints().addAll(
                    bottomTrianglesCoordinates[0],
                    bottomTrianglesCoordinates[1],
                    bottomTrianglesCoordinates[2],
                    bottomTrianglesCoordinates[3],
                    bottomTrianglesCoordinates[4],
                    bottomTrianglesCoordinates[5]);

            bottomTrianglesCoordinates[0] += 100;
            bottomTrianglesCoordinates[2] += 100;
            bottomTrianglesCoordinates[4] += 100;

            triangles.getChildren().addAll(l, p);
        }

        return triangles;
    }

    private static Group pieces() {

        double circleRadius = 25.0f;
        double[][] BlackInitialCoordinates = {
                /*
                -->BLACK PIECES<--
                 */
                {1150.0f, 25.0f},
                {1150.0f, 75.0f},
                {50.0f, 25.0f},
                {50.0f, 75.0f},
                {50.0f, 125.0f},
                {50.0f, 175.0f},
                {50.0f, 225.0f},
                {450.0f, 575.0f},
                {450.0f, 525.0f},
                {450.0f, 475.0f},
                {650.0f, 575.0f},
                {650.0f, 525.0f},
                {650.0f, 475.0f},
                {650.0f, 425.0f},
                {650.0f, 375.0f},
        };
        double[][] WhiteInitialCoordinates = {
                /*
                -->WHITE PIECES<--
                 */
                {650.0f, 25.0f},
                {650.0f, 75.0f},
                {650.0f, 125.0f},
                {650.0f, 175.0f},
                {650.0f, 225.0f},
                {450.0f, 25.0f},
                {450.0f, 75.0f},
                {450.0f, 125.0f},
                {50.0f, 575.0f},
                {50.0f, 525.0f},
                {50.0f, 475.0f},
                {50.0f, 425.0f},
                {50.0f, 375.0f},
                {1150.0f, 575.0f},
                {1150.0f, 525.0f},

        };

        Group pieces = new Group();
        Circle piece;
        for (double[] xy : BlackInitialCoordinates) {
            piece = new Circle();
            piece.setCenterX(xy[0]);
            piece.setCenterY(xy[1]);
            piece.setFill(Color.BLACK);
            piece.setStroke(Color.WHITE);
            piece.setRadius(circleRadius);
            Circle finalPiece = piece;
            piece.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (die2.getStyle().equals("-fx-base: #b6e7c9;") && die1.getStyle().equals("-fx-base: #b6e7c9;")) {
                    /*
                    IMPORTANT
                    Use getCenter() and setCenter(), do NOT use translate() or relocate()
                     */
                    finalPiece.setCenterX(finalPiece.getCenterX() - 100);

                    /*
                    Get a reference to another piece this way:
                    pieces.getChildren().get(3);
                     */

                    /*
                    Get all existing pieces this way:
                    pieces.getChildren();
                     */

                    /*
                    e.g. search for a piece with coordinates x = 1150.0f and y = 75.0f, move it and
                    fill with a red color:

                    for (Node n : pieces.getChildren()) {
                        Circle c = (Circle)n;
                        if (c.getCenterX() == 1150.0f && c.getCenterY() == 75.0f) {
                            c.setFill(Color.RED);
                            c.setCenterX(200);
                            c.setCenterY(200);
                        }
                    }
                     */
                }
            });
            pieces.getChildren().add(piece);
        }

        for (double[] xy : WhiteInitialCoordinates) {
            piece = new Circle();
            piece.setCenterX(xy[0]);
            piece.setCenterY(xy[1]);
            piece.setFill(Color.WHITE);
            piece.setStroke(Color.BLACK);
            piece.setRadius(circleRadius);
            Circle finalPiece = piece;
            piece.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (die2.getStyle().equals("-fx-base: #b6e7c9;") && die1.getStyle().equals("-fx-base: #b6e7c9;")) {
                    /*
                    IMPORTANT
                    Use getCenter() and setCenter(), do NOT use translate() or relocate()
                     */
                    finalPiece.setCenterX(finalPiece.getCenterX() - 100);

                    /*
                    Get a reference to another piece this way:
                    pieces.getChildren().get(3);
                     */

                    /*
                    Get all existing pieces this way:
                    pieces.getChildren();
                     */

                    /*
                    e.g. search for a piece with coordinates x = 1150.0f and y = 75.0f, move it and
                    fill with a red color:

                    for (Node n : pieces.getChildren()) {
                        Circle c = (Circle)n;
                        if (c.getCenterX() == 1150.0f && c.getCenterY() == 75.0f) {
                            c.setFill(Color.RED);
                            c.setCenterX(200);
                            c.setCenterY(200);
                        }
                    }
                     */
                }
            });
            pieces.getChildren().add(piece);
        }


        return pieces;
    }
}
