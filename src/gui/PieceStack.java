import java.util.Stack;

/**
 * Stack for storing pieces on the board. Works like normal stack, with additional checks for Backgammon rules. 'pushPiece'
 * should be used instead of push.
 */
public class PieceStack extends Stack<Piece> {

     String colour;

    public PieceStack(String colour) throws IllegalColourException {

        super();

        if (colour.equals("white") || colour.equals("black"))
            this.colour = colour;
        else
            throw new IllegalColourException("colour must be 'black' or 'white'");

    }

    /**
     * @param p Piece to check
     * @return True if colours of p and this PieceStack match
     * or if the stack is empty
     * or if an opponent's piece can be taken out of the game
     * False otherwise
     */
    public String isLegalPush(Piece p) {
        if (p.getColour().equals(this.colour))
            return "normal";
        else {
          if(super.size() == 1){
            return "middle";
          } else if (super.size() == 0) {
            return "normal";
          } else{
            return "false";
          }
        }
    }

    @Override
    public Piece push(Piece p) {
        try {
            return pushPiece(p);
        } catch (IllegalPushException e) {
            e.printStackTrace();
            return p;
        }
    }

    /**
     * Pushes item onto this stack in accordance with Backgammon rules.
     *
     * @param p Piece to push
     * @return p or opponents's piece if there is exactly one on this PieceStack
     * @throws IllegalPushException
     */
    public Piece pushPiece(Piece p) throws IllegalPushException {

        Piece returnPiece = p;

        if (p.getColour().equals(this.colour)) {
            super.push(p);
        } else if (this.size() == 1) {
            // PUSH THE PIECE INTO THE CORRECT STACK
            returnPiece = this.pop();
            this.colour = p.getColour();
            super.push(p);
        } else if (this.isEmpty()) {
            this.colour = p.getColour();
            super.push(p);
        } else
            throw new IllegalPushException("The piece cannot be put on this stack");

        return returnPiece;
    }


    public String getColour() {
        return colour;
    }

    /**
     * Is thrown when programme attempts to put piece of one colour on a stack with at least one piece of the opposing
     * colour.
     */
    class IllegalPushException extends Exception {
        IllegalPushException(String message) {
            super(message);
        }
    }
}
