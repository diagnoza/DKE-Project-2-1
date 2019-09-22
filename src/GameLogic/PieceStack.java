package GameLogic;

import java.util.Stack;

/**
 * Stack for storing pieces on the board. Works like normal stack, with additional checks for Backgammon rules. 'pushPiece'
 * should be used instead of push.
 */
public class PieceStack extends Stack<Piece>{

    String colour;

    public PieceStack(String colour) throws IllegalColourException {

        super();

        if(colour.equals("white") || colour.equals("black"))
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
    public boolean isLegalPush(Piece p){
        if(p.getColour().equals(this.colour))
            return true;
        else
            return super.size() <= 1;

    }

    @Override
    public Piece push(Piece p){
        try {
            return pushPiece(p);
        }
        catch (IllegalPushException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Pushes item onto this stack in accordance with Backgammon rules.
     * @param p Piece to push
     * @return p or opponents's piece if there is exactly one on this PieceStack
     * @throws IllegalPushException
     */
    public Piece pushPiece(Piece p) throws IllegalPushException{

        Piece returnPiece = p;

        if(p.getColour().equals(this.colour)) {
            super.push(p);
        }
        else
        if (this.size() == 1) {
            returnPiece = this.pop();
            this.colour = p.getColour();
            super.push(p);
        }
        else if(this.isEmpty()){
            this.colour = p.getColour();
            super.push(p);
        }
        else
            throw new IllegalPushException("The piece cannot be put on this stack");

        return returnPiece;
    }

    @Override
    public Object clone()  {
        try {
            PieceStack clonedStack;
            if (this.colour.equals("white"))
                clonedStack = new PieceStack("white");
            else
                clonedStack = new PieceStack("black");

            for(int i=0; i<this.size(); i++){
                clonedStack.push(new Piece(this.colour));
            }
            return clonedStack;
        }
        catch (IllegalColourException e){
            System.out.println(e.getMessage());
            return null;
        }
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
