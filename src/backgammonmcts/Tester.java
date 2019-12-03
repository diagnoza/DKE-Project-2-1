package backgammonmcts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

/**
*
* @author Rudy
*/

public class Tester {
    
	 
    public void test(int testNumber, int whitePrune, int blackPrune, int whiteTime, int blackTime, int AImodeW, int AImodeB) throws IOException {
    	//Creating variables for the test class
        int x = 0;
        double numWhiteWon = 0;
        double numBlackWon = 0;
        double WhiteBlackPercentage;
        /*int whiteCounter = 0;*/
        
        //Creating timestamp
        Date date= new Date();
   	 	long time = date.getTime();
   	 	Timestamp ts = new Timestamp(time);
        
        //Creating a file that stores the test data
        File file = new File("test.txt");

        //Creating writer to write data to file
        PrintWriter writer = new PrintWriter(new FileWriter(file, true));  //Does not rewrite file, adds to existing data 

        //Loop that plays numberOfTests times games and adds +1 to winning team variable
        while(x < testNumber) {
        	
        	//Creating a board
            Board test = new Board();
            //Creating a random dice roll
            Roll r = new Roll();
            while (r.steps[0] == r.steps[1]) {
                r = new Roll();
            }
            
            boolean white = (r.steps[0] > r.steps[1]);
            
            /*//Increment whiteCounter if white starts
            if (white) {
            	whiteCounter++;
            }
            */
            
            State start = new State(test, white, 0, 0, 0, r);
            MCTS w = new MCTS();
            MCTS b = new MCTS();
            int n = 0;
            State next = new State();
            
            while (start.wincheck() == 0) {
                if (start.white) {
                	//MCTSmostVisitedPruned
                	if(AImodeW == 1) {
                		n = w.MCTSmostvisitedpruned(start, whitePrune, whiteTime);
                	
                		//System.out.println(((start.white) ? "White" : "Black") + " rolled " +start.roll.steps[0]+ ", " +start.roll.steps[1] + ".");
                		if (w.tree.root.state.movelist.isEmpty()) {
                			//System.out.println(((start.white) ? "White" : "Black") + " is forced to pass.");
                			next = new State(start, new int[]{});
                			} else {
                			//System.out.println(((start.white) ? "White" : "Black") + " plays " +Arrays.toString(w.tree.root.state.movelist.get(n)) +".");
                			next = new State(start, w.tree.root.state.movelist.get(n));
                			}
                		start = next;
                		}
                	//Random
                	else if(AImodeW == 2) {
                    	next = new State(start, w.Random(start));
                    	start = next;
                    }
                	//Blitz
                	else if(AImodeW == 3) {
                		next = new State(start, w.Blitz(start));
                    	start = next;
                    }
                } else {
                	//MCTSmostVisitedPruned
                	if(AImodeB == 1) {
                		n = b.MCTSmostvisitedpruned(start, blackPrune, blackTime);
                		
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
                	//Random
                	else if(AImodeB == 2) {
                		next = new State(start, b.Random(start));
                    	start = next;
                	}
                	//Blitz
                	else if(AImodeB == 3) {
                		next = new State(start, b.Blitz(start));
                    	start = next;
                	}
                }
            }
            //Adding a point to the winning color
            if (start.wincheck() > 0) {
                numWhiteWon++;
            } else {
                numBlackWon++;
            }
            
            //System.out.println("Total nodes expanded for White: " +w.totalnodesexpanded);
            //System.out.println("Rollouts completed for White: " +w.totalrollouts);
            //System.out.println("Total nodes expanded for Black: " +b.totalnodesexpanded);
            //System.out.println("Rollouts completed for Black: " +b.totalrollouts);
            
            x++;
            System.out.println("Game# " + x + "/" + testNumber + " W: " + numWhiteWon + " B: " + numBlackWon);
        }
        
        /*//Printing whiteCounter (how many times white started)
        System.out.println("White started " + whiteCounter + " times.");
		*/
        
        //Calculating winning percentage of white team
        WhiteBlackPercentage = (numWhiteWon/(testNumber)) * 100;
        
        //Writing data into file
        writer.println("NEW TEST " + ts);
        if(AImodeW == 1) {
        	writer.println("AI-mode white: MCTSmostVisitedPruned");
        	writer.println("White pruning factor: " + whitePrune + " , Thinking time: " + whiteTime + " ms");
        }
        if(AImodeW == 2) {
        	writer.println("AI-mode white: Random moves");
        }
        if(AImodeW == 3) {
        	writer.println("AI-mode white: Blitz");
        }
        if(AImodeB == 1) {
        	writer.println("AI-mode black: MCTSmostVisitedPruned");
        	writer.println("Black pruning factor: " + blackPrune + " , Thinking time: " + blackTime + " ms");
        }
        if(AImodeB == 2) {
        	writer.println("AI-mode black: Random moves");
        }
        if(AImodeB == 3) {
        	writer.println("AI-mode black: Blitz");
        }
        writer.println("Number of games: " + testNumber);
        writer.println("White won: " + numWhiteWon + " times");
        writer.println("Black won: " + numBlackWon + " times");
        writer.println("WhiteBlack percentage is: " + WhiteBlackPercentage + "%");
        writer.println("BlackWhite percentage is: " + (100 - WhiteBlackPercentage) + "%");
        writer.println("");
        writer.close();
        
        System.out.println("Test complete! Data stored in test.txt");
    }
}
