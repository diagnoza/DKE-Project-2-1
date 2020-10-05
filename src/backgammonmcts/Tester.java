package backgammonmcts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
*
* @author Rudy
*/

public class Tester {
    
	 
    public void test(int bla, int testNumber, int whitePrune, int whiteTree, int blackPrune, int blackTree, int whiteTime, int blackTime, int AImodeW, int AImodeB) throws IOException, ClassNotFoundException, InterruptedException {
    	//Creating variables for the test class
        int x = 0;
        int[] totalTurnCount = new int[testNumber];
        double numWhiteWon = 0;
        double numBlackWon = 0;
        double WhiteBlackPercentage;
        boolean first = true;
        long[] timeRec = new long[testNumber];
        int doubleIsWin = 0;
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
        	//Recording begin time of a game
        	long beginTime = System.currentTimeMillis();
        	
        	//Creater a counter that counts the number of turns per game
        	int turnCount = 0;
        	
        	//Creating a board
            Board test = new Board();
            //Creating a random dice roll
            Roll r = new Roll();
            while (r.steps[0] == r.steps[1]) {
                r = new Roll();
            }
            
            //Determines who starts the game
            boolean white = /*false;//*/(r.steps[0] > r.steps[1]);
            if(bla==1) {
            	white = /*false;//*/(r.steps[0] > r.steps[1]);
            }
            else if(bla==2) {
            	white = true;//*/(r.steps[0] > r.steps[1]);
            }
            else if(bla==3) {
            	white = false;//*/(r.steps[0] > r.steps[1]);
            }
            /*//Increment whiteCounter if white starts
            if (white) {
            	whiteCounter++;
            }
            */
            
            State start = new State(test, white, 0, 0, 0, r);
            MCTS w = new MCTS();
            MCTS b = new MCTS();
            w.setpruningprofile(1.0, 1.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0);
         /*R*/    w.setBlitz(Math.random(), Math.random(), Math.random(), Math.random(), Math.random(), Math.random(), Math.random(), Math.random(), Math.random(), Math.random());
         /*G2*/   //w.setBlitz(2,2,8,6,14,10,8,6,4,2);
         /*B1*/   //w.setBlitz(-0.5224574058473519, -0.43822557600830836, 0.7677599901444533, -0.8843270059728154, 1.4918403199827495, 0.3917130961501726, 0.14536011023021267, 1.3777890621429008, 0.07484067705732034, 0.5685463681692843);
         /*B2*/   //w.setBlitz(-0.21931933848532903, -0.005063795582513353, 0.21354109857048464, -0.8109029965594525, 0.656447823141098, 0.35451159761504947, 0.10639820035996672, 0.4556263738643437, 0.1621018169387538, 0.7640447171151256);
            b.setpruningprofile(1.0, 1.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0);
         /*Bad MCTS*/   //b.setpruningprofile(2.60121, 2.60121, 2.73559, 2.73559, 4.27389, 4.27389, 9.40285, 9.40285);
            int n = 0;
            first = true;
            State next = new State();
            
            while (start.wincheck() == 0) {
            	turnCount++;
                if (start.white) {
                	//MCTSmostVisitedPruned
                	if(AImodeW == 1) {
                		n = w.MCTSmostvisitedpruned(start, whitePrune, whiteTree, whiteTime);
                	
                		if (first) {
                            int[] move = FirstMove.opening(r, true);
                            next = new State(start, move);
                            first = false;
                        } else {
                            if (n==-1 || w.tree.root.state.movelist.isEmpty()) {
                                //System.out.println(((start.white) ? "White" : "Black") + " is forced to pass.");
                                next = new State(start, new int[]{});
                            } else {
                                //System.out.println(((start.white) ? "White" : "Black") + " plays " +Arrays.toString(w.tree.root.state.movelist.get(n)) +".");
                                next = new State(start, w.tree.root.state.movelist.get(n));
                            }
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
                		//If first move of the game, use standard move
                		if(first) {
                    		int[] move = FirstMove.opening(r, white);
                            next = new State(start, move);
                            first = false;
                            start = next;
                    	}
                		//Else use Blitz
                		else {
                			next = new State(start, w.Blitz(start));
                			start = next;
                		}
                    }
                	//MCTS_hsp_rootparallel
                	else if(AImodeW == 4) {
                		n = w.MCTS_hsp_rootparallel(start, whitePrune, whiteTree, whiteTime);
                        //System.out.println(((start.white) ? "White" : "Black") + " rolled " +start.roll.steps[0]+ ", " +start.roll.steps[1] + ".");
                        if (n==-1 || w.tree.root.state.movelist.isEmpty()) {
                           // System.out.println(((start.white) ? "White" : "Black") + " is forced to pass.");
                            next = new State(start, new int[]{});
                        } else {
                            //System.out.println(((start.white) ? "White" : "Black") + " plays " +Arrays.toString(w.tree.root.state.movelist.get(n)) +".");
                            next = new State(start, w.tree.root.state.movelist.get(n));
                        }
                        start = next;
                	}
                	//MCTShighestscorepruned
                	else if(AImodeW == 5) {
                		n = w.MCTShighestscorepruned(start, whitePrune, whiteTree, whiteTime);
                		
                		if (first) {
                            int[] move = FirstMove.opening(r, true);
                            next = new State(start, move);
                            first = false;
                        } else {
                		//System.out.println(((start.white) ? "White" : "Black") + " rolled " +start.roll.steps[0]+ ", " +start.roll.steps[1] + ".");
                		if (n==-1 || w.tree.root.state.movelist.isEmpty()) {
                			//System.out.println(((start.white) ? "White" : "Black") + " is forced to pass.");
                			next = new State(start, new int[]{});
                			} else {
                			//System.out.println(((start.white) ? "White" : "Black") + " plays " +Arrays.toString(w.tree.root.state.movelist.get(n)) +".");
                			next = new State(start, w.tree.root.state.movelist.get(n));
                			}
                        }
                		start = next;
                	}
                	//MCTS_mvp_rootparallel
                	else if(AImodeW == 6) {
                		n = w.MCTS_mvp_rootparallel(start, whitePrune, whiteTree, whiteTime);
                        //System.out.println(((start.white) ? "White" : "Black") + " rolled " +start.roll.steps[0]+ ", " +start.roll.steps[1] + ".");
                        if (n==-1 || w.tree.root.state.movelist.isEmpty()) {
                           // System.out.println(((start.white) ? "White" : "Black") + " is forced to pass.");
                            next = new State(start, new int[]{});
                        } else {
                            //System.out.println(((start.white) ? "White" : "Black") + " plays " +Arrays.toString(w.tree.root.state.movelist.get(n)) +".");
                            next = new State(start, w.tree.root.state.movelist.get(n));
                        }
                        start = next;
                	}
                } else {
                	//MCTSmostVisitedPruned
                	if(AImodeB == 1) {
                		n = b.MCTSmostvisitedpruned(start, blackPrune, blackTree, blackTime);
                	
                		if (first) {
                            int[] move = FirstMove.opening(r, white);
                            next = new State(start, move);
                            first = false;
                        } else {
                            if (n==-1 || b.tree.root.state.movelist.isEmpty()) {
                                //System.out.println(((start.white) ? "White" : "Black") + " is forced to pass.");
                                next = new State(start, new int[]{});
                            } else {
                                //System.out.println(((start.white) ? "White" : "Black") + " plays " +Arrays.toString(w.tree.root.state.movelist.get(n)) +".");
                                next = new State(start, b.tree.root.state.movelist.get(n));
                            }
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
                		//If first move of the game, use standard move
                		if(first) {
                    		int[] move = FirstMove.opening(r, white);
                            next = new State(start, move);
                            first = false;
                            start = next;
                    	}
                		//Else use blitz
                		else {
                			next = new State(start, b.Blitz(start));
                    		start = next;
                		}
                	}
                	//MCTS_hsp_rootparallel
                	else if(AImodeB == 4) {
                		n = b.MCTS_hsp_rootparallel(start, blackPrune, blackTree, blackTime);
                        //System.out.println(((start.white) ? "White" : "Black") + " rolled " +start.roll.steps[0]+ ", " +start.roll.steps[1] + ".");
                        if (n==-1 || b.tree.root.state.movelist.isEmpty()) {
                           // System.out.println(((start.white) ? "White" : "Black") + " is forced to pass.");
                            next = new State(start, new int[]{});
                        } else {
                            //System.out.println(((start.white) ? "White" : "Black") + " plays " +Arrays.toString(w.tree.root.state.movelist.get(n)) +".");
                            next = new State(start, b.tree.root.state.movelist.get(n));
                        }
                        start = next;
                	}
                	//MCTShighestscorepruned
                	else if(AImodeB == 5) {
                		n = b.MCTShighestscorepruned(start, blackPrune, blackTree, blackTime);
                		
                		if (first) {
                            int[] move = FirstMove.opening(r, true);
                            next = new State(start, move);
                            first = false;
                        } else {
                		//System.out.println(((start.white) ? "White" : "Black") + " rolled " +start.roll.steps[0]+ ", " +start.roll.steps[1] + ".");
                		if (n==-1 || b.tree.root.state.movelist.isEmpty()) {
                			//System.out.println(((start.white) ? "White" : "Black") + " is forced to pass.");
                			next = new State(start, new int[]{});
                			} else {
                			//System.out.println(((start.white) ? "White" : "Black") + " plays " +Arrays.toString(w.tree.root.state.movelist.get(n)) +".");
                			next = new State(start, b.tree.root.state.movelist.get(n));
                			}
                        }
                		start = next;
                	}
                	//MCTS_mvp_rootparallel
                	else if(AImodeB == 6) {
                		n = b.MCTS_mvp_rootparallel(start, blackPrune, blackTree, blackTime);
                        //System.out.println(((start.white) ? "White" : "Black") + " rolled " +start.roll.steps[0]+ ", " +start.roll.steps[1] + ".");
                        if (n==-1 || b.tree.root.state.movelist.isEmpty()) {
                           // System.out.println(((start.white) ? "White" : "Black") + " is forced to pass.");
                            next = new State(start, new int[]{});
                        } else {
                            //System.out.println(((start.white) ? "White" : "Black") + " plays " +Arrays.toString(w.tree.root.state.movelist.get(n)) +".");
                            next = new State(start, b.tree.root.state.movelist.get(n));
                        }
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
            
            //Adding amount of turns to total array
            totalTurnCount[x] = turnCount;
            
            x++;
            System.out.println("Game# " + x + "/" + testNumber + " W: " + numWhiteWon + " B: " + numBlackWon);
            
            //recording ending time of a game and calculating time difference
            long endTime = System.currentTimeMillis();
            long difTime = endTime - beginTime;
            timeRec[x-1] = difTime;
        }
        //Calculating the average time of all games
        long sum = 0;
        for(int i=0;i<testNumber;i++) {
        	sum = sum + timeRec[i];
        }
        long avgTime = sum/testNumber;
        
        //Calculating average turns per game
        double avgTurnCount = 0;
        for(int p=0;p<totalTurnCount.length;p++) {
        	avgTurnCount = avgTurnCount + totalTurnCount[p];
        }
        avgTurnCount = avgTurnCount/testNumber;
        
        /*//Printing whiteCounter (how many times white started)
        System.out.println("White started " + whiteCounter + " times.");
		*/
        
        //Calculating winning percentage of white team
        WhiteBlackPercentage = (numWhiteWon/(testNumber)) * 100;
        
        //Writing data into file
        writer.println("NEW TEST " + ts);
        if(AImodeW == 1) {
        	writer.println("AI-mode white: MCTSmostVisitedPruned");
        	writer.println("White pruning factor: " + whitePrune + " , Tree pruning: " + whiteTree + " , Thinking time: " + whiteTime + " ms");
        }
        if(AImodeW == 2) {
        	writer.println("AI-mode white: Random moves");
        }
        if(AImodeW == 3) {
        	writer.println("AI-mode white: Blitz");
        }
        if(AImodeW == 4) {
        	writer.println("AI-mode white: MCTS_hsp_rootparallel");
        	writer.println("White pruning factor: " + whitePrune + " , Tree pruning: " + whiteTree + " , Thinking time: " + whiteTime + " ms");
        }
        if(AImodeW == 5) {
        	writer.println("AI-mode white: MCTShighestscorepruned");
        	writer.println("White pruning factor: " + whitePrune + " , Tree pruning: " + whiteTree + " , Thinking time: " + whiteTime + " ms");
        }
        if(AImodeW == 6) {
        	writer.println("AI-mode white: MCTS_mvp_rootparallel");
        	writer.println("White pruning factor: " + whitePrune + " , Tree pruning: " + whiteTree + " , Thinking time: " + whiteTime + " ms");
        }
        if(AImodeB == 1) {
        	writer.println("AI-mode black: MCTSmostVisitedPruned");
        	writer.println("Black pruning factor: " + blackPrune + " , Tree pruning: " + blackTree + " , Thinking time: " + blackTime + " ms");
        }
        if(AImodeB == 2) {
        	writer.println("AI-mode black: Random moves");
        }
        if(AImodeB == 3) {
        	writer.println("AI-mode black: Blitz");
        }
        if(AImodeB == 4) {
        	writer.println("AI-mode black: MCTS_hsp_rootparallel");
        	writer.println("Black pruning factor: " + blackPrune + " , Tree pruning: " + blackTree + " , Thinking time: " + blackTime + " ms");
        }
        if(AImodeB == 5) {
        	writer.println("AI-mode black: MCTShighestscorepruned");
        	writer.println("Black pruning factor: " + blackPrune + " , Tree pruning: " + blackTree + " , Thinking time: " + blackTime + " ms");
        }
        if(AImodeB == 6) {
        	writer.println("AI-mode black: MCTS_mvp_rootparallel");
        	writer.println("Black pruning factor: " + blackPrune + " , Tree pruning: " + blackTree + " , Thinking time: " + blackTime + " ms");
        }
        writer.println("Number of games: " + testNumber);
        writer.println("White won: " + numWhiteWon + " times");
        writer.println("Black won: " + numBlackWon + " times");
        writer.println("WhiteBlack percentage is: " + WhiteBlackPercentage + "%");
        writer.println("BlackWhite percentage is: " + (100 - WhiteBlackPercentage) + "%");
        writer.println("Average time per game is: " + avgTime + " ms");
        writer.println("Average turns per game is: " + avgTurnCount);
        writer.println("More doubles equals winning: " + doubleIsWin + "/" + testNumber);
        writer.println("");
        writer.close();
        
        System.out.println("Test complete! Data stored in test.txt");
    }
}

