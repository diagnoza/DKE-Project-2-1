package GameLogic;

import java.util.Stack;

public class Board {

    private PieceStack[] triangles;
    private PieceStack outOfGameWhite;
    private PieceStack outOfGameBlack;


    public Board() throws IllegalColourException {
        setUpBoard();
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

        }


    }


}
