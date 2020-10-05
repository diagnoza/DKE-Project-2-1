package gui;

import backgammonmcts.MCTS;
import backgammonmcts.Roll;
import backgammonmcts.State;
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

public class HvCo {

    static public State s;
    static public Roll r;
    static public MCTS b;
    public static PieceStack[] ArrayCopy;
    public static Board board;
    public static PieceButton[] buttons = new PieceButton[40];
    static int[] lastMove;
    static private Random randomGenerator = new Random();
    static private int res1, res2;
    static private Button die1, die2;
    static private Group pieces = new Group();

    public static void start(Stage primaryStage) throws IllegalColourException {

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

        Button endTurn = new Button("END TURN");
        endTurn.setPrefSize(200, 100);
        endTurn.setTranslateX(700);
        endTurn.setTranslateY(700);
        endTurn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                s.setWhite(false);
                try {
                    getAImove();
                } catch (IllegalColourException ex) {
                    ex.printStackTrace();
                }
            }
        });

        Group board = new Group();
        game();
//        pieces2();
        board.getChildren().addAll(background, middleLine, endLine, triangles, pieces, endTurn, back);
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

    public static Group pieces2() {
        pieces.getChildren().clear();
        buttons = new PieceButton[31];
        PieceButton piece;
        int totalPieces = 0;
        for (int i = 0; i < 24; i++) {

            int size = ArrayCopy[i].size();

            String colorStack = ArrayCopy[i].getColour();
            for (int x = 0; x < size; x++) {
                totalPieces = totalPieces + 1;

                piece = new PieceButton(ArrayCopy[i].get(x), totalPieces);

                if (piece.piece.colour == "white") {
                    piece.setFill(Color.WHITE);
                    piece.setStroke(Color.BLACK);
                } else {
                    piece.setFill(Color.BLACK);
                    piece.setStroke(Color.WHITE);
                }
                piece.setHeight(17);
                piece.setWidth(70);
                piece.piece.linkButton(totalPieces);
                piece.setX(piece.piece.xPosition);
                piece.setY(piece.piece.yPosition);
                buttons[totalPieces] = piece;
                PieceButton finalPiece = piece;
                final int totalPiecesTemp = totalPieces;
                if (piece.piece.colour == "white") {
                    piece.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        int stackNumber1 = buttons[totalPiecesTemp].piece.stackNumber;
                        String colorz = buttons[totalPiecesTemp].piece.getColour();
                        int height1 = 0;
                        int sizex = 10;
                        if (stackNumber1 < 24) {
                            height1 = buttons[totalPiecesTemp].piece.getHeight(ArrayCopy);
                            sizex = ArrayCopy[stackNumber1].size();
                        }


                        if (height1 + 1 == sizex || stackNumber1 == 24) { // ONLY THE TOP PIECE CAN BE MOVED!
                            System.out.println("TOP PIECE CAN BE MOVED");
                            if (die1.getStyle().equals("-fx-base: #b6e7c9;") && !(die2.getStyle().equals("-fx-base: #b6e7c9;"))) {
                                checkMove(stackNumber1, res1, buttons[totalPiecesTemp].piece.colour, board, totalPiecesTemp);
                            } else if (die2.getStyle().equals("-fx-base: #b6e7c9;") && !(die1.getStyle().equals("-fx-base: #b6e7c9;"))) {
                                checkMove(stackNumber1, res2, buttons[totalPiecesTemp].piece.colour, board, totalPiecesTemp);
                            } else if (die2.getStyle().equals("-fx-base: #b6e7c9;") && die1.getStyle().equals("-fx-base: #b6e7c9;")) {
                                checkMove(stackNumber1, res1 + res2, buttons[totalPiecesTemp].piece.colour, board, totalPiecesTemp);
                            }
                            if (isGameOver() == true) {
                                System.out.println("GAME OVER!");
                            }
                        } else {
                            System.out.println("Move a different piece!");
                        }
                    });
                }
                pieces.getChildren().add(piece);
            }
        }
        return pieces;
    }

    public static Group pieces4(PieceStack[] test, PieceStack whiteMiddle, PieceStack blackMiddle, PieceStack whiteRemoved, PieceStack blackRemoved) {
        pieces.getChildren().clear();
        buttons = new PieceButton[31];
        PieceButton piece;
        int totalPieces = 0;
        int i;
        for (i = 0; i < 24; i++) {
            int size = test[i].size();
            String colorStack = test[i].getColour();
            for (int x = 0; x < size; x++) {
                totalPieces = totalPieces + 1;

                piece = new PieceButton(test[i].get(x), totalPieces);

                if (piece.piece.colour == "white") {
                    piece.setFill(Color.WHITE);
                    piece.setStroke(Color.BLACK);
                } else {
                    piece.setFill(Color.BLACK);
                    piece.setStroke(Color.WHITE);
                }
                piece.setHeight(17);
                piece.setWidth(70);
                piece.piece.linkButton(totalPieces);
                piece.setX(piece.piece.xPosition);
                piece.setY(piece.piece.yPosition);
                buttons[totalPieces] = piece;
                PieceButton finalPiece = piece;
                final int totalPiecesTemp = totalPieces;
                if (piece.piece.colour == "white") {
                    piece.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        int stackNumber1 = buttons[totalPiecesTemp].piece.stackNumber;
                        String colorz = buttons[totalPiecesTemp].piece.getColour();
                        int height1 = 0;
                        int sizex = 10;
                        if (stackNumber1 < 24) {
                            height1 = buttons[totalPiecesTemp].piece.getHeight(test);
                            sizex = test[stackNumber1].size();
                        }


                        if (height1 + 1 == sizex || stackNumber1 == 24) { // ONLY THE TOP PIECE CAN BE MOVED!
                            System.out.println("TOP PIECE CAN BE MOVED");
                            if (die1.getStyle().equals("-fx-base: #b6e7c9;") && !(die2.getStyle().equals("-fx-base: #b6e7c9;"))) {
                                checkMove(stackNumber1, res1, buttons[totalPiecesTemp].piece.colour, board, totalPiecesTemp);
                            } else if (die2.getStyle().equals("-fx-base: #b6e7c9;") && !(die1.getStyle().equals("-fx-base: #b6e7c9;"))) {
                                checkMove(stackNumber1, res2, buttons[totalPiecesTemp].piece.colour, board, totalPiecesTemp);
                            } else if (die2.getStyle().equals("-fx-base: #b6e7c9;") && die1.getStyle().equals("-fx-base: #b6e7c9;")) {
                                checkMove(stackNumber1, res1 + res2, buttons[totalPiecesTemp].piece.colour, board, totalPiecesTemp);
                            }
                            if (isGameOver() == true) {
                                System.out.println("GAME OVER!");
                            }
                        } else {
                            System.out.println("Move a different piece!");
                        }
                    });
                }
                pieces.getChildren().add(piece);
            }
        }

        System.out.println("this should be positive: " + blackMiddle.size());
        int size;

        // totalPieces
        // white middle
        size = whiteMiddle.size();
        for (i = 0; i < size; i++) {
            totalPieces = totalPieces + 1;
            piece = new PieceButton(whiteMiddle.get(i), totalPieces);

            if (piece.piece.colour == "white") {
                piece.setFill(Color.WHITE);
                piece.setStroke(Color.BLACK);
            } else {
                piece.setFill(Color.BLACK);
                piece.setStroke(Color.WHITE);
            }
            piece.setHeight(17);
            piece.setWidth(70);
            piece.piece.linkButton(totalPieces);
            piece.setX(piece.piece.xPosition);
            piece.setY(piece.piece.yPosition);
            buttons[totalPieces] = piece;
            PieceButton finalPiece = piece;
            final int totalPiecesTemp = totalPieces;
            if (piece.piece.colour == "white") {
                piece.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    int stackNumber1 = buttons[totalPiecesTemp].piece.stackNumber;
                    String colorz = buttons[totalPiecesTemp].piece.getColour();
                    int height1 = 0;
                    int sizex = 10;
                    if (stackNumber1 < 24) {
                        height1 = buttons[totalPiecesTemp].piece.getHeight(test);
                        sizex = test[stackNumber1].size();
                    }
                    System.out.println("STACKNUMBER: " + stackNumber1);
                    if (height1 + 1 == sizex || stackNumber1 == 24) { // ONLY THE TOP PIECE CAN BE MOVED!
                        if (die1.getStyle().equals("-fx-base: #b6e7c9;") && !(die2.getStyle().equals("-fx-base: #b6e7c9;"))) {
                            checkMove(stackNumber1, res1, buttons[totalPiecesTemp].piece.colour, board, totalPiecesTemp);
                        } else if (die2.getStyle().equals("-fx-base: #b6e7c9;") && !(die1.getStyle().equals("-fx-base: #b6e7c9;"))) {
                            checkMove(stackNumber1, res2, buttons[totalPiecesTemp].piece.colour, board, totalPiecesTemp);
                        } else if (die2.getStyle().equals("-fx-base: #b6e7c9;") && die1.getStyle().equals("-fx-base: #b6e7c9;")) {
                            checkMove(stackNumber1, res1 + res2, buttons[totalPiecesTemp].piece.colour, board, totalPiecesTemp);
                        }
                        if (isGameOver() == true) {
                            System.out.println("GAME OVER!");
                        }
                    } else {
                        System.out.println("Move a different piece!");
                    }
                });
            }
            pieces.getChildren().add(piece);
        }

        // black middle
        size = blackMiddle.size();
        for (i = 0; i < size; i++) {
            totalPieces = totalPieces + 1;
            piece = new PieceButton(blackMiddle.get(i), totalPieces);

            System.out.println("piece colour: " + piece.piece.colour);
            if (piece.piece.colour == "white") {
                piece.setFill(Color.WHITE);
                piece.setStroke(Color.BLACK);
            } else {
                piece.setFill(Color.BLACK);
                piece.setStroke(Color.WHITE);
            }
            piece.setHeight(17);
            piece.setWidth(70);
            piece.piece.linkButton(totalPieces);
            piece.setX(piece.piece.xPosition);
            piece.setY(piece.piece.yPosition);
            buttons[totalPieces] = piece;
            PieceButton finalPiece = piece;
            final int totalPiecesTemp = totalPieces;
            pieces.getChildren().add(piece);
        }

        // white removed
        size = whiteRemoved.size();
        for (i = 0; i < size; i++) {
            totalPieces = totalPieces + 1;
            piece = new PieceButton(whiteRemoved.get(i), totalPieces);
            System.out.println("piece colour: " + piece.piece.colour);
            if (piece.piece.colour == "white") {
                piece.setFill(Color.WHITE);
                piece.setStroke(Color.BLACK);
            } else {
                piece.setFill(Color.BLACK);
                piece.setStroke(Color.WHITE);
            }
            piece.setHeight(17);
            piece.setWidth(70);
            piece.piece.linkButton(totalPieces);
            piece.setX(piece.piece.xPosition);
            piece.setY(piece.piece.yPosition);
            buttons[totalPieces] = piece;
            PieceButton finalPiece = piece;
            final int totalPiecesTemp = totalPieces;
            pieces.getChildren().add(piece);
        }

        // black removed
        size = blackRemoved.size();
        for (i = 0; i < size; i++) {
            totalPieces = totalPieces + 1;
            piece = new PieceButton(blackRemoved.get(i), totalPieces);
            System.out.println("piece colour: " + piece.piece.colour);
            if (piece.piece.colour == "white") {
                piece.setFill(Color.WHITE);
                piece.setStroke(Color.BLACK);
            } else {
                piece.setFill(Color.BLACK);
                piece.setStroke(Color.WHITE);
            }
            piece.setHeight(17);
            piece.setWidth(70);
            piece.piece.linkButton(totalPieces);
            piece.setX(piece.piece.xPosition);
            piece.setY(piece.piece.yPosition);
            buttons[totalPieces] = piece;
            PieceButton finalPiece = piece;
            final int totalPiecesTemp = totalPieces;
            pieces.getChildren().add(piece);
        }

        // white pieces of the board
        // black pieces in the middle

        // white pieces in the middle
        return pieces;
    }


    public static Group pieces3() {
        pieces.getChildren().clear();
        for (int i = 0; i < 24; i++) {
//            System.out.println(i+" HEIGHT: "+ test[i].size());
        }
        return pieces;
    }

    private static void game() throws IllegalColourException {
        try {
            board = new Board();
            ArrayCopy = Board.triangles;
        } catch (IllegalColourException e) {
            e.printStackTrace();
        }
        backgammonmcts.Board boardButNotGay = new backgammonmcts.Board();
        r = new Roll();
        s = new State(boardButNotGay, true, 0, 0, 0, r);
        b = new MCTS();
        b.setpruningprofile(1.0, 1.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0);
        pieces2();
    }

    public static Boolean checkMove(int stacknumber, int dice, String Color, Board board, int pieceNumber) {
        try {
            int value = Board.moveClassifier(stacknumber, dice, Color, board);
            if (value != -70) {
                int[] move = new int[2];
                if (stacknumber == 24 && Color.equals("white")) {
                    stacknumber = 25;
                    int to = stacknumber - dice;
                    move[0] = stacknumber;
                    move[1] = to;
                } else {
                    int from = (stacknumber - 24) * -1;
                    int to = from - dice;
                    move[0] = from;
                    move[1] = to;
                }
//            System.out.println(move[0] + " " + move[1]);
                s = new State(s, move);

//            System.out.println("NEW: [");
//            for (int i = 0; i < s.getboard().board.length; i++) {
//                System.out.print(s.getboard().board[i] + " ");
//            }
//            System.out.println("]");

                board.adjustLogic(s.getboard().board);
            } else {
                int from = (stacknumber - 24) * -1;
                s.board.board[from]--;
                board.adjustLogic(s.getboard().board);
            }
            return true;
        } catch (IllegalArgumentException | IllegalColourException d) {
            return false;
        }
    }

    public static void getAImove() throws IllegalColourException {
        s = b.doStep(s, Settings.player2);
        board.adjustLogic(s.getboard().board);
    }

    public static Boolean isGameOver() {
        return board.getSuccesfullyRemovedWhite().size() == 15 || board.getSuccesfullyRemovedBlack().size() == 15;
    }
}
