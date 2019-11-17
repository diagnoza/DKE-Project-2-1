package GameLogic;

public class LogicTester {

    public static void main(String[] args){


        try {
            // Start game
            Board board = new Board();
            // Move pieces

            // Test dice rolling
            Die die = new Die();
            int dieRoll1 =  die.roll();
            int dieRoll2 =  die.roll();

            // Test isLegalPush
            System.out.println(board.isLegalMove(23, 22));
            // Test moving a piece
            board.movePiece(23, 1);
            board.movePiece(0, 22);
            System.out.println("Done");
        }
        catch(IllegalColourException e){
            e.printStackTrace();
        }

    }
}
