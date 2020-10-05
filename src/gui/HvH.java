package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
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

    public static PieceStack[] ArrayCopy;
    public static Board board;
    public static PieceButton[] buttons = new PieceButton[40];

    public static void start(Stage primaryStage) {

        try {
            board = new Board();
            ArrayCopy = board.getTriangles();
        } catch (IllegalColourException e) {
            e.printStackTrace();
        }

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

        Group triangles = triangles();

        Button back = new Button("Go Back");
        back.setPrefSize(200, 100);
        back.setTranslateX(929);
        back.setTranslateY(700);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ModeSelectionWindow.start(primaryStage);
            }
        });

        Group pieces = pieces();
        Group board = new Group();
        board.getChildren().addAll(background, middleLine, endLine, triangles, pieces, back);
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



        // setText and getText
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
        for (int i = 12; i >= 1; i--) {
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
        Group pieces = new Group();
        PieceButton piece;
        int totalPieces = 0;
        int i;
        for (i = 0; i < 24; i++) {
            int size = ArrayCopy[i].size();
            String colorStack = ArrayCopy[i].getColour();
            for (int x = 0; x < size; x++) {
                totalPieces = totalPieces + 1;
                piece = new PieceButton(board.getTriangles()[i].get(x),totalPieces);
                if (piece.piece.colour =="white"){
                    piece.setFill(Color.WHITE);
                    piece.setStroke(Color.BLACK);
                } else {
                    piece.setFill(Color.BLACK);
                    piece.setStroke(Color.WHITE);
                }
                buttons[totalPieces] = piece;
                piece.setHeight(17);
                piece.setWidth(70);
                piece.piece.linkButton(totalPieces);
                piece.setX(piece.piece.xPosition);
                piece.setY(piece.piece.yPosition);
                PieceButton finalPiece = piece;

                final int totalPiecesTemp = totalPieces;

                piece.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    int stackNumber1 = buttons[totalPiecesTemp].piece.stackNumber;
                    String colorz = buttons[totalPiecesTemp].piece.getColour();

                    int height1 = 0;
                    int sizex = 10;

                    if (stackNumber1 < 24){
                        height1 = buttons[totalPiecesTemp].piece.getHeight(board.getTriangles());
                        sizex = board.getTriangles()[stackNumber1].size();
                    }

                    if (height1 + 1 == sizex || stackNumber1 ==24) { // ONLY THE TOP PIECE CAN BE MOVED!
                        if (die1.getStyle().equals("-fx-base: #b6e7c9;") && !(die2.getStyle().equals("-fx-base: #b6e7c9;"))) {
                            try {
                                int value = board.moveClassifier(stackNumber1, res1, buttons[totalPiecesTemp].piece.colour, board);
                                if (value == -20 || value == -70) {
                                    int x_Coordinate = buttons[totalPiecesTemp].piece.xPosition;
                                    int y_Coordinate = buttons[totalPiecesTemp].piece.yPosition;
                                    buttons[totalPiecesTemp].setX(x_Coordinate);
                                    buttons[totalPiecesTemp].setY(y_Coordinate);
                                } else {
                                    int x_CoordinateRemove = buttons[value].piece.xPosition;
                                    int y_CoordinateRemove = buttons[value].piece.yPosition;
                                    buttons[value].setX(x_CoordinateRemove);
                                    buttons[value].setY(y_CoordinateRemove);
                                    int x_Coordinate = buttons[totalPiecesTemp].piece.xPosition;
                                    int y_Coordinate = buttons[totalPiecesTemp].piece.yPosition;
                                    buttons[totalPiecesTemp].setX(x_Coordinate);
                                    buttons[totalPiecesTemp].setY(y_Coordinate);
                                }
                            } catch (IllegalArgumentException d) {
                                System.out.println("MOVE NOT ALLOWED!");
                            }
                        } else if (die2.getStyle().equals("-fx-base: #b6e7c9;") && !(die1.getStyle().equals("-fx-base: #b6e7c9;"))){
                            System.out.println("dice values are: "+res2);
                            try {
                                int value = board.moveClassifier(stackNumber1, res2, buttons[totalPiecesTemp].piece.colour, board);
                                if (value == -20 || value == -70) {
                                    int x_Coordinate = buttons[totalPiecesTemp].piece.xPosition;
                                    int y_Coordinate = buttons[totalPiecesTemp].piece.yPosition;
                                    buttons[totalPiecesTemp].setX(x_Coordinate);
                                    buttons[totalPiecesTemp].setY(y_Coordinate);
                                } else {
                                    int x_CoordinateRemove = buttons[value].piece.xPosition;
                                    int y_CoordinateRemove = buttons[value].piece.yPosition;
                                    buttons[value].setX(x_CoordinateRemove);
                                    buttons[value].setY(y_CoordinateRemove);
                                    int x_Coordinate = buttons[totalPiecesTemp].piece.xPosition;
                                    int y_Coordinate = buttons[totalPiecesTemp].piece.yPosition;
                                    buttons[totalPiecesTemp].setX(x_Coordinate);
                                    buttons[totalPiecesTemp].setY(y_Coordinate);
                                }
                            } catch (IllegalArgumentException d) {
                                System.out.println("MOVE NOT ALLOWED!");
                            }
                        } else if (die2.getStyle().equals("-fx-base: #b6e7c9;") && die1.getStyle().equals("-fx-base: #b6e7c9;")){
                            System.out.println("dice values are: "+res1);
                            System.out.println("dice values are: "+res2);
                            try {
                                int value = board.moveClassifier(stackNumber1, res1+res2, buttons[totalPiecesTemp].piece.colour, board);
                                if (value == -20 || value == -70) {
                                    int x_Coordinate = buttons[totalPiecesTemp].piece.xPosition;
                                    int y_Coordinate = buttons[totalPiecesTemp].piece.yPosition;
                                    buttons[totalPiecesTemp].setX(x_Coordinate);
                                    buttons[totalPiecesTemp].setY(y_Coordinate);
                                } else {
                                    int x_CoordinateRemove = buttons[value].piece.xPosition;
                                    int y_CoordinateRemove = buttons[value].piece.yPosition;
                                    buttons[value].setX(x_CoordinateRemove);
                                    buttons[value].setY(y_CoordinateRemove);
                                    int x_Coordinate = buttons[totalPiecesTemp].piece.xPosition;
                                    int y_Coordinate = buttons[totalPiecesTemp].piece.yPosition;
                                    buttons[totalPiecesTemp].setX(x_Coordinate);
                                    buttons[totalPiecesTemp].setY(y_Coordinate);
                                }
                            } catch (IllegalArgumentException d) {
                                System.out.println("MOVE NOT ALLOWED!");
                            }
                        }
                        if (isGameOver() == true) {
                            System.out.println("GAME OVER!");
                        }
                    } else{
                        System.out.println("Move a different piece!");
                    }
                });
                pieces.getChildren().add(piece);
            }
        }
        return pieces;
    }

    public static Boolean isGameOver(){
        return board.getSuccesfullyRemovedWhite().size() == 15 || board.getSuccesfullyRemovedBlack().size() == 15;
    }

}
