package gui;

public class Board {
    // public static javax.swing.Timer timer;
    // public static int counter = 0;
    public static PieceStack[] triangles;
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

        // numPiecesBlack = 15;
        // numPiecesWhite = 15;
    }

    public static int moveChecker(int stackNumber, int die, String color, Board board) throws IllegalArgumentException {
        if (color == "white" && !(stackNumber == 24)) {
            // CHECK IF THERE ARE PIECES IN THE MIDDLE!
            if (outOfGameWhite.size() != 0) {
                throw new IllegalArgumentException("IllegalMove");
            }
        } else if (color == "black" && !(stackNumber == 24)) {
            // CHECK IF THERE ARE PIECES IN THE MIDDLE!
            if (outOfGameBlack.size() != 0) {
                throw new IllegalArgumentException("IllegalMove");
            }
        }
        if (stackNumber == 24) {
            try {
                int value = board.movePieceFromMiddle(color, die);
                return value;
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("IllegalMove");
            }
        } else {
            try {
                int value = movePiece(stackNumber, die, color);
                return value;
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("IllegalMove");
            }
        }
    }

    public static int moveClassifier(int stackNumber, int die, String color, Board board) throws IllegalArgumentException {
        if (color == "white" && !(stackNumber == 24)) {
            // CHECK IF THERE ARE PIECES IN THE MIDDLE!
            if (outOfGameWhite.size() != 0) {
                throw new IllegalArgumentException("IllegalMove");
            }
        } else if (color == "black" && !(stackNumber == 24)) {
            // CHECK IF THERE ARE PIECES IN THE MIDDLE!
            if (outOfGameBlack.size() != 0) {
                throw new IllegalArgumentException("IllegalMove");
            }
        }
        if (stackNumber == 24) {
            try {
                int value = board.movePieceFromMiddle(color, die);
                return value;
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("IllegalMove");
            }
        } else {
            try {
                int value = movePiece(stackNumber, die, color);
                return value;
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("IllegalMove");
            }
        }
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
            p.stackNumber = 24;
            p.setMiddle(true);
            outOfGameWhite.push(p);
        } else {
            p.stackNumber = 24;
            p.setMiddle(true);
            outOfGameBlack.push(p);
        }
    }

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
                if (isLegal == "normal") {
                    return "IllegalMove";
                } else if (isLegal == "middle") {
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

    public static Boolean canTakePiecesOff(String color) {
        int totalCounter = 0;
        int counterEnd = 0;
        for (int p = 0; p < 24; p++) {
            if (triangles[p].getColour() == color) {
                totalCounter = totalCounter + triangles[p].size();
            }
        }
        if (color == "white") {
            for (int z = 18; z < 24; z++) {
                if (triangles[z].getColour() == color) {
                    counterEnd = counterEnd + triangles[z].size();
                }
            }
        } else if (color == "black") {
            for (int zz = 0; zz < 6; zz++) {
                if (triangles[zz].getColour() == color) {
                    counterEnd = counterEnd + triangles[zz].size();
                }
            }
        }
        return totalCounter == counterEnd;
    }

    // create new method
    public void adjustLogic(int[] arr) throws IllegalColourException {

//        System.out.println("LOGIC - NEW: [");
//        for (int i = 0; i < arr.length; i++) {
//            System.out.print(arr[i] + " ");
//        }
//        System.out.println("]");

        int whiteOutOfGame = 15;
        int blackOutOfGame = 15;
        int numPieces = 0;

        for (int i = 0; i < arr.length; i++) {
            int x;
            if (i == 0) {
                for (x = 1; x <= Math.abs(arr[i]); x++) {
                    numPieces++;
                    blackOutOfGame--;
                }
            } else if (i < 25) {
                if (arr[i] > 0) { // all white values
                    for (x = 1; x <= arr[i]; x++) {
                        numPieces++;
                        whiteOutOfGame--;
                    }
                } else if (arr[i] < 0) { // all black values
                    int absValue = Math.abs(arr[i]);
                    for (x = 1; x <= absValue; x++) {
                        numPieces++;
                        blackOutOfGame--;
                    }
                }
            } else if (i == 25) {
                for (x = 1; x <= arr[i]; x++) {
                    numPieces++;
                    whiteOutOfGame--;
                }
            }
        }

//        System.out.println("Black pieces out of board: " + blackOutOfGame);
//        System.out.println("White pieces out of board: " + whiteOutOfGame);

        PieceStack outOfGameWhiteTemp = setUpStack(arr[25], "white", 24); // board[25]
        PieceStack outOfGameBlackTemp = setUpStack(Math.abs(arr[0]), "black", 24); // Math.abs(board[0])

        PieceStack succesfullyRemovedWhiteTemp = setUpStack(whiteOutOfGame, "white", 29); // whiteOutOfGame
        PieceStack succesfullyRemovedBlackTemp = setUpStack(blackOutOfGame, "black", 29); // blackOutOfGame

//        System.out.println("UP UNTIL HERE AS WELL");

        triangles = adjustBoard(arr); //

        this.outOfGameWhite = outOfGameWhiteTemp;
        this.outOfGameBlack = outOfGameBlackTemp;
        succesfullyRemovedWhite = succesfullyRemovedWhiteTemp;
        succesfullyRemovedBlack = succesfullyRemovedBlackTemp;

//        System.out.println("this works as well :)");
        HvCo.pieces4(adjustBoard(arr), outOfGameWhiteTemp,outOfGameBlackTemp,succesfullyRemovedWhiteTemp,succesfullyRemovedBlackTemp); //
//        System.out.println("this works as well :) x 2");

    }

    private PieceStack[] adjustBoard(int[] arr) throws IllegalColourException {

        System.out.println("LOGIC ** - NEW: [");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println("]");

        PieceStack[] trianglestest = new PieceStack[24];

        // CHECK THAT IT CONVERTS PROPERLY!!

        // 1-24
        //// Where 1 = 23
        //// Where 24 = 0

        // 0-25 : 1-24 and pieces each one

        // we need to take 1-24 of Laurins array and turn it into 0-23 piecestack
        for (int i = 1; i < 25; i++) {
            int stackNumber = (i - 24) * - 1;
            if (arr[i] != 0) {
                String color;
                if (arr[i] > 0) {
                    color = "white";
                } else {
                    color = "black";
                }
                trianglestest[stackNumber] = setUpStack(Math.abs(arr[i]), color, stackNumber);
//                System.out.println(stackNumber+" HEIGHT: "+ trianglestest[i-1].size());
            } else {
                trianglestest[stackNumber] = new PieceStack("white");
//                System.out.println(stackNumber+" HEIGHT: "+ trianglestest[i-1].size());
            }

        }
        return trianglestest;
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
        return triangles;
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
        triangles = new PieceStack[24];
        // Populate whites
        triangles[0] = twoWhites;
        triangles[16] = threeWhites;
        triangles[11] = fiveWhites;
        triangles[18] = fiveWhites2;
        // Populate Blacks
        triangles[23] = twoBlacks;
        triangles[7] = threeBlacks;
        triangles[12] = fiveBlacks;
        triangles[5] = fiveBlacks2;
        // Populate Null fields
        for (int i = 0; i < 24; i++) {
            if (triangles[i] == null) {
                triangles[i] = new PieceStack("white");
            }
        }
    }

    // MAKE QUICK FUNCTION TAKE PIECE FROM MIDDLE AND MOVE

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

    private PieceStack setUpStackOut(int numPieces, String colour, int stackNumber) throws IllegalColourException {

        // Initialize Stack
        PieceStack pieces = new PieceStack(colour);

        // Populate Stack
        for (int i = 0; i < numPieces; i++) {
            pieces.push(new Piece(colour, stackNumber, i + 1));
        }

        return pieces;
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

    private PieceStack setUpStackMiddle(int numPieces, String colour, int stackNumber) throws IllegalColourException {

        // Initialize Stack
        PieceStack pieces = new PieceStack(colour);

        // Populate Stack
        for (int i = 0; i < numPieces; i++) {
            pieces.push(new Piece(colour, stackNumber, i + 1));
        }

        return pieces;
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
                        pieceToMove.setMiddle(false); // CHANGE TO STACK
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
}
