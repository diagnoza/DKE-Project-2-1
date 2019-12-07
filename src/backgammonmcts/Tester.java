package backgammonmcts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
*
* @author Rudy
*/

public class Tester {
    
	 
    public void test(int testnumber, int whiteprune, int blackprune, int whitetime, int blacktime) throws IOException {
        int x = 0;
        double numWhiteWon = 0;
        double numBlackWon = 0;
        double WhiteBlackPercentage;
        File file = new File("test.txt");
        
//        Check if file exists
//        Path path = Files.createTempFile("test", ".txt");
//        if (!Files.exists(path)) {
//            Creating file to store data
//            file = new File("test.txt");
//        }

        //Creating writer to write data to file
        PrintWriter writer = new PrintWriter(new FileWriter(file, true));  //Does not rewrite file, adds to existing data 

        //Loop that plays numberOfTests times games and adds +1 to winning team variable
        while(x < testnumber) {
            Board test = new Board();
            Roll r = new Roll();
            while (r.steps[0] == r.steps[1]) {
                r = new Roll();
            }
            boolean white = (r.steps[0] > r.steps[1]);
            State start = new State(test, white, 0, 0, 0, r);
            MCTS w = new MCTS();
            MCTS b = new MCTS();
            int n = 0;
            State next = new State();
            while (start.wincheck() == 0) {
                if (start.white) {
                    n = w.MCTSmostvisitedpruned(start, whiteprune, whitetime);
                    //System.out.println(((start.white) ? "White" : "Black") + " rolled " +start.roll.steps[0]+ ", " +start.roll.steps[1] + ".");
                    if (w.tree.root.state.movelist.isEmpty()) {
                        //System.out.println(((start.white) ? "White" : "Black") + " is forced to pass.");
                        next = new State(start, new int[]{});
                    } else {
                        //System.out.println(((start.white) ? "White" : "Black") + " plays " +Arrays.toString(w.tree.root.state.movelist.get(n)) +".");
                        next = new State(start, w.tree.root.state.movelist.get(n));
                    }
                    start = next;
                } else {
                    n = b.MCTSmostvisitedpruned(start, 1, 5000);
                    //System.out.println(((start.white) ? "White" : "Black") + " rolled " +start.roll.steps[0]+ ", " +start.roll.steps[1] + ".");
                    if (b.tree.root.state.movelist.isEmpty()) {
                        //System.out.println(((start.white) ? "White" : "Black") + " is forced to pass.");
                        next = new State(start, new int[]{});
                    } else {
                        //System.out.println(((start.white) ? "White" : "Black") + " plays " +Arrays.toString(b.tree.root.state.movelist.get(n)) +".");
                        next = new State(start, b.tree.root.state.movelist.get(n));
                    }
                    start = next;
                }
            }
            if (start.wincheck() > 0) {
                numWhiteWon++;
                //System.out.println("White wins the game!");
            } else {
                numBlackWon++;
                //System.out.println("Black wins the game!");
            }
            //System.out.println("Total nodes expanded for White: " +w.totalnodesexpanded);
            //System.out.println("Rollouts completed for White: " +w.totalrollouts);
            //System.out.println("Total nodes expanded for Black: " +b.totalnodesexpanded);
            //System.out.println("Rollouts completed for Black: " +b.totalrollouts);
            x++;
        }

        //Calculating winning percentage of white team
        WhiteBlackPercentage = (numWhiteWon/(testnumber)) * 100;

        //Writing data into file
        writer.println("NEW TEST");
        writer.println("White pruning factor: " + whiteprune + " , Thinking time: " + whitetime);
        writer.println("Black pruning factor: " + blackprune + " , Thinking time: " + blacktime);
        writer.println("Number of games is: " + testnumber);
        writer.println("White won: " + numWhiteWon + " times");
        writer.println("Black won: " + numBlackWon + " times");
        writer.println("WhiteBlack percentage is: " + WhiteBlackPercentage + "%");
        writer.println("BlackWhite percentage is: " + (100 - WhiteBlackPercentage) + "%");
        writer.println("");
        writer.close();
    }
}
