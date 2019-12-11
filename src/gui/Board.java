package gui;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import backgammonmcts.Tester;

public class Board {

    public static javax.swing.Timer timer;

    private static PieceStack[] triangles;
    private static PieceStack outOfGameWhite;
    private static PieceStack outOfGameBlack;

    private static PieceStack succesfullyRemovedWhite;
    private static PieceStack succesfullyRemovedBlack;

    private static int numPiecesWhite;
    private static int numPiecesBlack;

    public Board() throws IllegalColourException {
        setUpBoard();

        outOfGameBlack = new PieceStack("black");
        outOfGameWhite = new PieceStack("white");

        succesfullyRemovedBlack = new PieceStack("black");
        succesfullyRemovedWhite = new PieceStack("white");

        numPiecesBlack = 15;
        numPiecesWhite = 15;
    }

    public PieceStack getMiddleWhite() {
        return outOfGameWhite;
    }

    public PieceStack getMiddleBlack() {
        return outOfGameBlack;
    }

    public PieceStack getSuccesfullyRemovedWhite() {
        return succesfullyRemovedWhite;
    }

    public PieceStack getSuccesfullyRemovedBlack() {
        return succesfullyRemovedBlack;
    }

    public int getNumPiecesWhite() {
        return numPiecesWhite;
    }

    public int getNumPiecesBlack() {
        return numPiecesBlack;
    }

    public PieceStack[] getTriangles() {
        return this.triangles;
    }

    /**
     * Sets up a board with initial configuration of Pieces.
     */

    public void runAI() {
        Tester t = new Tester();
        try {
            t.test(1, 3, 3, 1000, 1000, 1, 1);
        } catch (ClassNotFoundException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    //Make Human vs Ai
    public static void makeMove(int[] move,String color)
    {
    	if(color == "white")
    	{//Do Human Move
    		
    	}
    	else {makeMoveAI(move,color);}
    	
    }
    public static int makeMoveAI(int[] move, String color) {

        // CHECK IF PIECE IS TAKEN OUT OF GAME! :/

        // ONLY LET BUTTON PRESS WORK ONCE
        // ALSO COLORS DO NOT MATCH MOVES!!

        // CONVERT move to proper move on this representation
        // if successfull return 1
        // find relevant pieces
        // update coordinates for relevant pieces
        // get number of button need to be changed
        // fifthwindow.reMap()
        // System.out.println("IT WORKS");
        if (move.length == 1) {
            System.out.println(color + " does move: " + Arrays.toString(move));
        } else if (move.length > 1) {
            System.out.println(color + " does move: " + Arrays.toString(move));
        }
        // white: 1-24 THIS IS WRONG!
        // black: 24-1
        if (move.length > 1) {
            // Move has been done!
            for (int i = 0; i < move.length; i = i + 2) {
                // move[i] and move[i+1]
                if ((move[i] < 25 && move[i + 1] < 25) && (move[i] > 0 && move[i + 1] > 0)) {
                    // On the board
                    if (move[i] > move[i + 1]) {
                        // white move
                        int from = (move[i] - 24) * -1;
                        int to = (move[i + 1] - 24) * -1;
                        System.out.println("FROM: " + from);
                        System.out.println("TO: " + to);
                        System.out.println("DIEROLL: " + (to - from));
                        System.out.println("");

                        Piece topPiece = triangles[from].get((triangles[from].size()) - 1);

                        int buttonNumber = topPiece.linkButton;
                        System.out.println(buttonNumber);
                        System.out.println("X1: " + topPiece.xPosition);
                        System.out.println("Y1: " + topPiece.yPosition);
                        System.out.println("");

                        movePiece(from, to - from, "white");

                        System.out.println("X2: " + topPiece.xPosition);
                        System.out.println("Y2: " + topPiece.yPosition);

                        int number = fifthwindow.reMap(buttonNumber);
                        System.out.println("REMAPPED: 3");
                        System.out.println("---------------------------");
                        System.out.println("");


                    } else if (move[i] < move[i + 1]) {
                        // black move
                        int from = (move[i] - 24) * -1;
                        int to = (move[i + 1] - 24) * -1;
                        System.out.println("FROM: " + from);
                        System.out.println("TO: " + to);
                        System.out.println("DIEROLL: " + (to - from));
                        System.out.println("");

                        Piece topPiece = triangles[from].get((triangles[from].size()) - 1);

                        int buttonNumber = topPiece.linkButton;
                        System.out.println("X1: " + topPiece.xPosition);
                        System.out.println("Y1: " + topPiece.yPosition);
                        System.out.println("");

                        movePiece(from, from - to, "black");

                        System.out.println("X2: " + topPiece.xPosition);
                        System.out.println("Y2: " + topPiece.yPosition);

                        int number = fifthwindow.reMap(buttonNumber);
                        System.out.println("REMAPPED: 3");
                        System.out.println("---------------------------");
                        System.out.println("");
                        // wait 2 seconds

                    }
                } else {
                    // EITHER TAKING PIECES TO MIDDLE / OFF MIDDLE / OFF THE BOARD
                }
            }
            System.out.println("");
        } else {
            System.out.println("No move possible");
        }
        return 1;
    }

    private void setUpBoard() throws IllegalColourException {
        // Template Stacks for white
        PieceStack twoWhites = setUpStack(2, "white", 0);
        PieceStack threeWhites = setUpStack(3, "white", 16);
        PieceStack fiveWhites = setUpStack(5, "white", 11);
        PieceStack fiveWhites2 = setUpStack(5, "white", 18); // CLOWN DOES NOT WORK
        // Template Stacks for white
        PieceStack twoBlacks = setUpStack(2, "black", 23);
        PieceStack threeBlacks = setUpStack(3, "black", 7);
        PieceStack fiveBlacks = setUpStack(5, "black", 12);
        PieceStack fiveBlacks2 = setUpStack(5, "black", 5);
        // Populate triangles
        this.triangles = new PieceStack[24];
        // Populate whites
        this.triangles[0] = twoWhites;
        this.triangles[16] = threeWhites;
        this.triangles[11] = fiveWhites;
        this.triangles[18] = fiveWhites2;
        // Populate Blacks
        this.triangles[23] = twoBlacks;
        this.triangles[7] = threeBlacks;
        this.triangles[12] = fiveBlacks;
        this.triangles[5] = fiveBlacks2;
        // Populate Null fields
        for (int i = 0; i < 24; i++) {
            if (this.triangles[i] == null) {
                this.triangles[i] = new PieceStack("white");
            }
        }

        // Tester t = new Tester();
        // t.test(100, 3, 3, 50, 50, 2, 1);

        // THE BOARD HAS BEEN SETUP - INITIATE TESTER

    }

    /**
     * Helper function. Sets up a stack of pieces for the initial board
     * configuration.
     *
     * @param numPieces The number of pieces on the specific triangle
     * @param colour    The colour of the pieces to put on the stack
     * @return A Stack of pieces of a given colour
     */
    private PieceStack setUpStack(int numPieces, String colour, int stackNumber) throws IllegalColourException {

        // Initialize Stack
        PieceStack pieces = new PieceStack(colour);

        // Populate Stack
        for (int i = 0; i < numPieces; i++) {
            pieces.push(new Piece(colour, stackNumber, i + 1));
        }

        return pieces;
    }

    /**
     * Move a piece on the board
     */

    // 1 - TYPE OF CHANGE COORDINATES: MOVE_ON_BOARD, REMOVE_FROM_BOARD,
    // ADD_TO_MIDDLE
    // 2 - Make sure piece stacknumber and middle are always up to date
    // 3 - Calculate Coordinate function in piece class
    // 4 - Pass through correct parts on the board class
    // 5 - The thirdwindow class

    public static int movePiece(int from, int dieRoll, String color) throws IllegalArgumentException {
        if (color == "white") {
            if (isLegalMove(from, (from + dieRoll), color) == "LegalBoard") {
                Piece pieceToMove = triangles[from].pop();

                int newTriangle = pieceToMove.move(from, dieRoll);
                if (newTriangle != -1) {
                    triangles[newTriangle].push(pieceToMove);
                    // CHANGE COORDINATES (MOVE_ON_BOARD)
                    // System.out.println("IMPORTANT NUMBER: "+ pieceToMove.getHeight(triangles));
                    pieceToMove.calculateCoordinates("Board", pieceToMove.getHeight(triangles) + 1);
                    return -20; // BOARD
                } else {
                    removePieceFromGame(pieceToMove);
                    // CHANGE COORDINATES (REMOVE_FROM_BOARD)
                    if (pieceToMove.getColour() == "white") {
                        pieceToMove.calculateCoordinates("Removed",
                                pieceToMove.getHeightRemoved(succesfullyRemovedWhite) + 1);
                    } else {
                        pieceToMove.calculateCoordinates("Removed",
                                pieceToMove.getHeightRemoved(succesfullyRemovedBlack) + 1);
                    }
                    return -70; // REMOVED
                }
            } else if (isLegalMove(from, (from + dieRoll), color) == "LegalMiddle") { // hits piece to the middle
                Piece pieceToMove = triangles[from].pop();
                Piece pieceToMiddle = triangles[from + dieRoll].pop();
                takePieceToMiddle(pieceToMiddle);
                pieceToMiddle.calculateCoordinates("Middle", 0);
                int newTriangle = pieceToMove.move(from, dieRoll);
                triangles[newTriangle].push(pieceToMove);
                pieceToMove.calculateCoordinates("Board", pieceToMove.getHeight(triangles) + 1);
                return pieceToMiddle.linkButton;
            } else {
                throw new IllegalArgumentException("IllegalMove");
            }
        } else {
            if (isLegalMove(from, (from - dieRoll), color) == "LegalBoard") {
                Piece pieceToMove = triangles[from].pop();

                int newTriangle = pieceToMove.move(from, dieRoll);
                if (newTriangle != -1) {
                    triangles[newTriangle].push(pieceToMove);
                    // CHANGE COORDINATES (MOVE_ON_BOARD)
                    // System.out.println("IMPORTANT NUMBER: "+ pieceToMove.getHeight(triangles));

                    pieceToMove.calculateCoordinates("Board", pieceToMove.getHeight(triangles) + 1);
                    
                    return -20; // BOARD
                } else {
                    removePieceFromGame(pieceToMove);
                    // CHANGE COORDINATES (REMOVE_FROM_BOARD)
                    if (pieceToMove.getColour() == "white") {
                        pieceToMove.calculateCoordinates("Removed",
                                pieceToMove.getHeightRemoved(succesfullyRemovedWhite) + 1);
                    } else {
                        pieceToMove.calculateCoordinates("Removed",
                                pieceToMove.getHeightRemoved(succesfullyRemovedBlack) + 1);
                    }
                    return -70; // REMOVED
                }
            } else if (isLegalMove(from, (from - dieRoll), color) == "LegalMiddle") { // hits piece to the middle
                Piece pieceToMove = triangles[from].pop();
                Piece pieceToMiddle = triangles[from - dieRoll].pop();
                takePieceToMiddle(pieceToMiddle);
                pieceToMiddle.calculateCoordinates("Middle", 0);
                int newTriangle = pieceToMove.move(from, dieRoll);
                triangles[newTriangle].push(pieceToMove);
                pieceToMove.calculateCoordinates("Board", pieceToMove.getHeight(triangles) + 1);
                return pieceToMiddle.linkButton;
            } else {
                throw new IllegalArgumentException("IllegalMove");
            }
        }

    }

    public int movePieceFromMiddle(String color, int dieRoll) throws IllegalArgumentException {
        if (color == "black") {
            String isLegal = isLegalMoveMiddle(color, dieRoll);
            if (isLegal == "LegalBoard") {
                Piece pieceToMove = outOfGameBlack.pop();
                if (dieRoll > 1) {
                    int newTriangle = pieceToMove.move(23, dieRoll - 1); // CHANGE TO 0
                    if (newTriangle != -1) {
                        triangles[newTriangle].push(pieceToMove);
                        pieceToMove.setMiddle(false);
                        pieceToMove.calculateCoordinates("Board", pieceToMove.getHeight(triangles) + 1);
                    }
                    return -20;
                } else {
                    triangles[23].push(pieceToMove);
                    pieceToMove.setMiddle(false);
                    pieceToMove.stackNumber = 23;
                    pieceToMove.calculateCoordinates("Board", pieceToMove.getHeight(triangles) + 1);
                    return -70;
                }
            } else if (isLegal == "LegalMiddle") { // CHECK
                Piece pieceToMove = outOfGameBlack.pop();
                Piece pieceToMiddle = triangles[23 - (dieRoll - 1)].pop(); // CHANGE
                takePieceToMiddle(pieceToMiddle);
                pieceToMiddle.calculateCoordinates("Middle", 0);
                if (dieRoll > 1) {
                    int newTriangle = pieceToMove.move(23, (dieRoll - 1)); // CHANGE TO 0 !!
                    if (newTriangle != -1) {
                        triangles[newTriangle].push(pieceToMove);
                        pieceToMove.setMiddle(false);
                        pieceToMove.calculateCoordinates("Board", pieceToMove.getHeight(triangles) + 1);
                    }
                } else {
                    triangles[23].push(pieceToMove);
                    pieceToMove.setMiddle(false);
                    pieceToMove.stackNumber = 23;
                    pieceToMove.calculateCoordinates("Board", pieceToMove.getHeight(triangles) + 1);
                }
                return pieceToMiddle.linkButton;
            } else {
                throw new IllegalArgumentException("IllegalMove");
            }
        } else {
            String isLegal = isLegalMoveMiddle(color, dieRoll);
            if (isLegal == "LegalBoard") {
                Piece pieceToMove = outOfGameWhite.pop();
                if (dieRoll > 1) {
                    int newTriangle = pieceToMove.move(0, dieRoll - 1);
                    triangles[newTriangle].push(pieceToMove);
                    pieceToMove.setMiddle(false);
                    pieceToMove.calculateCoordinates("Board", pieceToMove.getHeight(triangles) + 1);
                    return -20;
                } else {
                    triangles[0].push(pieceToMove);
                    pieceToMove.setMiddle(false);
                    pieceToMove.stackNumber = 0;
                    pieceToMove.calculateCoordinates("Board", pieceToMove.getHeight(triangles) + 1);
                    return -70;
                }
            } else if (isLegal == "LegalMiddle") { // CHECK
                Piece pieceToMove = outOfGameWhite.pop();
                Piece pieceToMiddle = triangles[dieRoll - 1].pop();

                takePieceToMiddle(pieceToMiddle);
                pieceToMiddle.calculateCoordinates("Middle", 0);

                if (dieRoll > 1) {
                    int newTriangle = pieceToMove.move(0, (dieRoll - 1));
                    if (newTriangle != -1) {
                        triangles[newTriangle].push(pieceToMove);
                        pieceToMove.setMiddle(false);
                        pieceToMove.calculateCoordinates("Board", pieceToMove.getHeight(triangles) + 1);
                    }
                } else {
                    triangles[0].push(pieceToMove);
                    pieceToMove.setMiddle(false);
                    pieceToMove.stackNumber = 0;
                    pieceToMove.calculateCoordinates("Board", pieceToMove.getHeight(triangles) + 1);
                }
                return pieceToMiddle.linkButton;
            } else {
                throw new IllegalArgumentException("IllegalMove");
            }
        }
    }

    // MAKE QUICK FUNCTION TAKE PIECE FROM MIDDLE AND MOVE

    public static void removePieceFromGame(Piece p) {
        if (p.getColour().equals("white")) {
            succesfullyRemovedWhite.push(p);
            numPiecesWhite--;
        } else {
            succesfullyRemovedBlack.push(p);
            numPiecesBlack--;
        }
    }

    /**
     * Removes a piece from the game temporarily.
     *
     * @param p Single Piece on a Triangle, that is taken out of the game by the
     *          opponent.
     */
    private static void takePieceToMiddle(Piece p) {
        if (p.getColour().equals("white")) {
            p.setMiddle(true);
            outOfGameWhite.push(p);
        } else {
            p.setMiddle(true);
            outOfGameBlack.push(p);
        }
    }

    // public int makeMoveAI(int[] move){
    // // CONVERT move to proper move on this representation
    // // if successfull return 1
    // // find relevant pieces
    // // update coordinates for relevant pieces
    // // get number of button need to be changed
    // //
    // fifthwindow.reMap()
    // return 1;
    // }

    /**
     * Check if a piece can be put on a specific triangle
     *
     * @param from triangle you are taking the piece from
     * @param to   The triangle you are moving the piece to
     * @return True if the move is legal, False otherwise
     */
    public static String isLegalMove(int from, int to, String color) {
        // ARE THERE ANY PIECES IN THE MIDDLE BECAUSE THEY MUST BE TAKEN OUT FIRST
        // IF PIECES ARE TO BE TAKEN OF BOARD CHECK IF ALL PIECES ARE IN THE FINAL
        // QUADRANT
        if ((color == "white" && outOfGameWhite.size() > 0) || (color == "black" && outOfGameBlack.size() > 0)) {
            if (!(to > 23 || to < 0)) {
                String isLegal = triangles[to].isLegalPush(triangles[from].peek());
                if (isLegal == "normal"){
                    return "IllegalMove";
                } else if (isLegal == "middle"){
                    return "LegalMiddle";
                } else {
                    return "IllegalMove";
                }
            } else {
                if (canTakePiecesOff(color) == true) { // ADJUST
                    return "IllegalMove";
                } else {
                    return "IllegalMove";
                }
            }
        } else {
            if (!(to > 23 || to < 0)) {
                String isLegal = triangles[to].isLegalPush(triangles[from].peek());
                if (isLegal == "normal") {
                    return "LegalBoard";
                } else if (isLegal == "middle") {
                    return "LegalMiddle";
                } else {
                    return "IllegalMove";
                }
            } else {
                if (canTakePiecesOff(color) == true) { // ADJUST
                    return "LegalBoard";
                } else {
                    return "IllegalMove";
                }
            }
        }

    }

    public String isLegalMoveMiddle(String color, int die) {
        int position = die - 1;
        String isLegal;
        if (color == "white") {
            isLegal = triangles[position].isLegalPush(outOfGameWhite.peek());
        } else {
            isLegal = triangles[23 - position].isLegalPush(outOfGameBlack.peek());
        }
        if (isLegal == "normal") {
            return "LegalBoard";
        } else if (isLegal == "middle") {
            return "LegalMiddle";
        } else {
            return "IllegalMove";
        }
    }

    public static Boolean canTakePiecesOff(String color) {
        int totalCounter = 0;
        int counterEnd = 0;
        for (int p = 0; p < 24; p++) {
            if (triangles[p].getColour() == color) {
                totalCounter = totalCounter + triangles[p].size();
            }
        }
        if (color == "white"){
            for (int z = 18; z < 24; z++) {
                if (triangles[z].getColour() == color) {
                    counterEnd = counterEnd + triangles[z].size();
                }
            }
        } else if (color == "black"){
            for (int zz = 0; zz < 6; zz++) {
                if (triangles[zz].getColour() == color) {
                    counterEnd = counterEnd +triangles[zz].size();
                }
            }
        }
        if (totalCounter == counterEnd) {
            return true;
        } else {
            return false;
        }
    }


}
