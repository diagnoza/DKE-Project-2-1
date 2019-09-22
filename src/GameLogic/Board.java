package GameLogic;

import java.util.Stack;

public class Board {

    private PieceStack[] triangles;
    private PieceStack outOfGameWhite;
    private PieceStack outOfGameBlack;
    private int numPiecesWhite;
    private int numPiecesBlack;


    public Board() throws IllegalColourException {
        setUpBoard();
        outOfGameBlack = new PieceStack("black");
        outOfGameWhite = new PieceStack("white");
        numPiecesBlack = 15;
        numPiecesWhite = 15;
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

    /**
     * Sets up a board with initial configuration of Pieces.
     */
    private void setUpBoard() throws IllegalColourException {

        // Template Stacks for white
        PieceStack twoWhites = setUpStack(2, "white");
        PieceStack threeWhites = setUpStack(3, "white");
        PieceStack fiveWhites = setUpStack(5, "white");



        // Template Stacks for white
        PieceStack twoBlacks = setUpStack(2, "black");
        PieceStack threeBlacks = setUpStack(3, "black");
        PieceStack fiveBlacks = setUpStack(5, "black");

        // Populate triangles
        this.triangles = new PieceStack[24];

        // Populate whites
        this.triangles[0] = twoWhites;
        this.triangles[11] = fiveWhites;
        this.triangles[16] = threeWhites;
        this.triangles[18] = (PieceStack)fiveWhites.clone();

        // Populate Blacks
        this.triangles[23] = twoBlacks;
        this.triangles[12] = fiveBlacks;
        this.triangles[7] = threeBlacks;
        this.triangles[5] = (PieceStack)fiveBlacks.clone();

        // Populate Null fields
        for(int i=0; i<24;i++){
            if(this.triangles[i] == null){
                this.triangles[i] = new PieceStack("white");
            }
        }



    }

    /**
     * Helper function. Sets up a stack of pieces for the initial board configuration.
     * @param numPieces The number of pieces on the specific triangle
     * @param colour The colour of the pieces to put on the stack
     * @return A Stack of pieces of a given colour
     */
    private PieceStack setUpStack(int numPieces, String colour) throws IllegalColourException {

        // Initialize Stack
        PieceStack pieces = new PieceStack(colour);

        // Populate Stack
        for(int i=0; i<numPieces; i++){
            pieces.push(new Piece(colour));
        }

        return pieces;
    }

    /**
     * Move a piece on the board
     */
    public void movePiece(int from, int dieRoll){

        Piece pieceToMove = triangles[from].pop();
        int newTriangle = pieceToMove.move(from, dieRoll);
        if(newTriangle != -1){
            Piece pieceFromStack = triangles[newTriangle].push(pieceToMove);
            if(!pieceFromStack.getColour().equals(pieceToMove.getColour()))
                takePieceToMiddle(pieceFromStack);
        }
        else
            removePieceFromGame(pieceToMove);

    }

    private void removePieceFromGame(Piece p){

        if(p.getColour().equals("white"))
            numPiecesWhite--;
        else
            numPiecesBlack--;
    }

    /**
     * Removes a piece from the game temporarily.
     * @param p Single Piece on a Triangle, that is taken out of the game by the opponent.
     */
    private void takePieceToMiddle(Piece p){

        if(p.getColour().equals("white"))
            outOfGameWhite.push(p);
        else
            outOfGameBlack.push(p);
    }

    /**
     * Check if a piece can be put on a specific triangle
     * @param from triangle you are taking the piece from
     * @param to The triangle you are moving the piece to
     * @return True if the move is legal, False otherwise
     */
    public boolean isLegalMove(int from, int to){
        return triangles[to].isLegalPush(triangles[from].peek());
    }





}
