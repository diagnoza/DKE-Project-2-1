package gui;

import backgammonmcts.Board;
import backgammonmcts.MCTS;
import backgammonmcts.Roll;
import backgammonmcts.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class AIvAI {

    public static int[] board;
    static private Group pieces = new Group();

    /**
     * Starts the game, as the name would suggest. This method will paint the board in the background and the initial
     * set up of pieces on the board.
     */
    public static void start(Stage primaryStage) {

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
        Button start = new Button("Play");
        start.setPrefSize(200, 100);
        start.setTranslateX(700);
        start.setTranslateY(700);
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                gameLoop();
            }
        });

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

        Group board = new Group();
        board.getChildren().addAll(background, middleLine, endLine, triangles, pieces, start, back);
        board.setTranslateX(20);
        board.setTranslateY(20);

        backgammonmcts.Board boardButNotGay = new Board();
        setupPieces(boardButNotGay.board);

        Group root = new Group();
        root.getChildren().addAll(board);
        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);

        primaryStage.setScene(scene);
        primaryStage.setWidth(1500);
        primaryStage.setHeight(900);
        primaryStage.centerOnScreen();
    }


    /**
     * This is the entry point for central game loop for the AI playing against itself. Here two MCTS objects are
     * created and passed the Animation timer to do the actual work.
     */
    private static void gameLoop() {
        backgammonmcts.Board boardButNotGay = new Board();
        setupPieces(boardButNotGay.board);
        Roll r = new Roll();
        State state = new State(boardButNotGay, true, 0, 0, 0, r);
        MCTS w = new MCTS();
        MCTS b = new MCTS();
        w.setpruningprofile(1.0, 1.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0);
        b.setpruningprofile(1.0, 1.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0);
        MCTS_AnimationTimer animation = new MCTS_AnimationTimer(state, w, b, "MostVisitedPruned");
        animation.start();
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

    public static void setupPieces(int[] board) {

        pieces.getChildren().clear();

        Rectangle piece;
        int whiteOutOfGame = 15;
        int blackOutOfGame = 15;
        int numPieces = 0;

        for (int i = 0; i < board.length; i++) {
            int x;
            if (i == 0) {
                // black middle
                for (x = 1; x <= Math.abs(board[i]); x++) {
                    numPieces++;
                    piece = returnPiece("Middle", 0, 0, "black");
                    blackOutOfGame--;
                    pieces.getChildren().add(piece);
                }
            } else if (i < 25) {
                if (board[i] > 0) { // all white values
                    for (x = 1; x <= board[i]; x++) {
                        numPieces++;
                        piece = returnPiece("Board", (i - 24)*-1, x, "white");
                        whiteOutOfGame--;
                        pieces.getChildren().add(piece);
                    }
                } else if (board[i] < 0) { // all black values
                    int absValue = Math.abs(board[i]);
                    for (x = 1; x <= absValue; x++) {
                        numPieces++;
                        piece = returnPiece("Board", (i - 24)*-1, x, "black");
                        blackOutOfGame--;
                        pieces.getChildren().add(piece);
                    }
                }
            } else if (i == 25) {
                // white middle
                for (x = 1; x <= board[i]; x++) {
                    numPieces++;
                    piece = returnPiece("Middle", 0, 0, "white");
                    whiteOutOfGame--;
                    pieces.getChildren().add(piece);
                }
            }
        }

        System.out.println("number of pieces mapped: " + numPieces);

        for (int i = 1; i <= whiteOutOfGame; i++) {
            piece = returnPiece("Removed", 0, i, "white");
            pieces.getChildren().add(piece);
        }

        for (int i = 1; i <= blackOutOfGame; i++) {
            piece = returnPiece("Removed", 0, i, "black");
            pieces.getChildren().add(piece);
        }

        System.out.println(pieces.getChildren());

//        Alert alert = new Alert(AlertType.INFORMATION);
//        alert.setTitle("Information Dialog");
//        alert.setHeaderText(null);
//        alert.setContentText("I have a great message for you!");
//
//        alert.showAndWait();
    }

    /**
     * Creates a new Piece from MCTS board
     *
     * @param board       Is the Piece on the board or not?
     * @param stackNumber Position of the Piece on the board
     * @param height      Position on the triangle
     * @param color       White or Black
     * @return New Piece
     */
    public static Rectangle returnPiece(String board, int stackNumber, int height, String color) {
        Rectangle piece;
        piece = new Rectangle();
        piece.setHeight(17);
        piece.setWidth(70);

        if (color.equals("white")) {
            piece.setFill(Color.WHITE);
            piece.setStroke(Color.BLACK);
        } else {
            piece.setFill(Color.BLACK);
            piece.setStroke(Color.WHITE);
        }

        int[] coordinates = coordinates(board, stackNumber, height, color);
        piece.setX(coordinates[0]);
        piece.setY(coordinates[1]);
        return piece;
    }

    public static int[] coordinates(String type, int stackNumber, int height, String colour) {
        int xPosition = 0;
        int yPosition = 0;
        int[] coordinates = new int[2];
        if (type.equals("Board")) {
            if (stackNumber >= 0 && stackNumber <= 5) {
                xPosition = 1115 - (stackNumber * 100);
                yPosition = 2 + ((height - 1) * 19);
            } else if (stackNumber >= 6 && stackNumber <= 11) {
                xPosition = 515 - ((stackNumber - 6) * 100);
                yPosition = 2 + ((height - 1) * 19);
            } else if (stackNumber >= 12 && stackNumber <= 17) {
                xPosition = 15 + ((stackNumber - 12) * 100);
                yPosition = 581 - ((height - 1) * 19);
            } else if (stackNumber >= 18 && stackNumber <= 23) {
                xPosition = 615 + ((stackNumber - 18) * 100);
                yPosition = 581 - ((height - 1) * 19);
            }
        } else if (type.equals("Removed")) {
            if (colour.equals("white")) {
                xPosition = 1215;
                yPosition = 581 - ((height - 1) * 19);
            } else if (colour.equals("black")) {
                xPosition = 1215;
                yPosition = 2 + ((height - 1) * 19);
            }
        } else if (type.equals("Middle")) {
            if (colour.equals("white")) {
                xPosition = 515;
                yPosition = 299;
            } else if (colour.equals("black")) {
                xPosition = 615;
                yPosition = 299;
            }
        }

        coordinates[0] = xPosition;
        coordinates[1] = yPosition;
        return coordinates;
    }
}
