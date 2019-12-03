/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonmcts;

import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Laurin
 * semi- @author Rudy
 */
public class MCTS {

    static int pruningfactor = 100;
    static int randomfactor = pruningfactor;
    int totalnodesexpanded = 0;
    int totalrollouts = 0;
    Tree tree;
    
    
    public MCTS() {
        tree = new Tree();
    }
    
    public int MCTSmostvisited (State s) {
        long end = System.currentTimeMillis() + 12000;
        tree = new Tree(new Node(s));
        //int debugb = 0;
        while (System.currentTimeMillis() < end) {
            Node current = selectnode(tree.root);
            current.expandnode();
            Node rollout = current;
            if (!current.children.isEmpty()) {
                rollout = current.getRandomChild();
            }
            int result = randomplay(rollout);
            //debugb++;
            //System.out.println("Finished rollout number " +debugb+ ", winner is " + (result == 1 ? "White" : "Black") + ".");
            backpropagate(rollout, result);
        }
        Node winner = Collections.max(tree.root.children, Comparator.comparing(c -> c.state.visits));
        //System.out.println("The winning node was visited " +winner.state.visits+ " times, and has a score of " +winner.state.score +".");
        return winner.siblingnumber;
    }
    
    public int MCTSmostvisitedpruned (State s, int n, int time) {
        pruningfactor = n;
        long end = System.currentTimeMillis() + time;
        tree = new Tree(new Node(s));
        //int debugb = 0;
        while (System.currentTimeMillis() < end) {
            Node current = selectnode(tree.root);
            current.expandnode();
            totalnodesexpanded++;
            current.prunenode();
            Node rollout = current;
            if (!current.children.isEmpty()) {
                rollout = current.getRandomChild();
            }
            int result = randomplay(rollout);
            //debugb++;
            //System.out.println("Finished rollout number " +debugb+ ", winner is " + (result == 1 ? "White" : "Black") + ".");
            backpropagate(rollout, result);
        }
        Node winner = Collections.max(tree.root.children, Comparator.comparing(c -> c.state.visits));   
        //System.out.println("The winning node was visited " +winner.state.visits+ " times, and has a score of " +winner.state.score +".");
        return winner.siblingnumber;
    }
    
    //Plays a random move
    public int[] Random(State s) {
    	//Fill the movelist with all legal moves
    	s.movehelper(s.getboard(),s.getroll(),s.getwhite());
    	//Generate a random number between 0 and movelist-size
    	int randomIndex = (int) (Math.random() * s.getmovelist().size());
    	//If movelist is empty then return an empty int[]
    	if (s.getmovelist().isEmpty()) {
    		return new int[]{};
    	}
    	//Else return a random move from the list
    	else {
    		return s.getmovelist().get(randomIndex);
    	}
    }
    
    //Plays according to blitz strategy
    public int[] Blitz(State s) {
    	//Fill the movelist with all legal moves
    	s.movehelper(s.getboard(),s.getroll(),s.getwhite());
    	//If the movelist is empty return  an empty int[]
    	if (s.getmovelist().isEmpty()) {
    		return new int[]{};
    	}
    	//Else continue with blitz strategy
    	else {
	    	int[] tempBoard = s.board.getboard(); //Creates a copy of the board without any changes (moves,etc.)
	    	int[] blitzScore = new int[s.getmovelist().size()]; //The score of each move, the move with the highest score gets returned.
	    	
	    	//Loop that iterates over the movelist
	    	for(int i=0;i<s.getmovelist().size();i++) {
	    		//Creating a local copy of the state (That resets every loop)
	    		State r = new State(s);
	    		//Applying the current move to the copy of the state
	    		r.board.applymove(r.getmovelist().get(i));
	    		
	    		//If the player is white
	    		if(r.getwhite()) {
	    			//Loop that iterates over each moved checker INSIDE the move
	    			for(int j=1;j<r.getmovelist().get(i).length;j+=2) {
	    				//If-statement that prevents arrayOutOfBoundsException
	    				if((r.getmovelist().get(i)[j]) > 0) {
	    					//If there is a single enemy checker on the place the move is, the score of the move will be incremented
	    					//according to the position of said checker (checkers closer to enemy's home give more points)
	    					if(tempBoard[r.getmovelist().get(i)[j]] == -1) {
	    						blitzScore[i] = blitzScore[i] + 1 * (r.getmovelist().get(i)[j]);
	    					}
	    				}
	    			}
	    		}
	    		//If the player is black
	    		else {
	    			//Loop that iterates over each moved checker INSIDE the move
	    			for(int j=1;j<r.getmovelist().get(i).length;j+=2) {
	    				//If-statement that prevents arrayOutOfBoundsException
	    				if((r.getmovelist().get(i)[j]) < 26) {
	    					//If there is a single enemy checker on the place the move is, the score of the move will be incremented
	    					//according to the position of said checker (checkers closer to enemy's home give more points)
	    					if(tempBoard[r.getmovelist().get(i)[j]] == 1) {
	    						blitzScore[i] = blitzScore[i] + 1 * (100-r.getmovelist().get(i)[j]);
	    					}
	    				}
	    			}
	    		}
	    	}
	    	//Creating an integer[] that stores scores and index to compare them with eachother
	    	int[] max = new int[2];
	    	max[0] = 0;
	    	//Loop that goes over all the moves and scores
	    	for(int i=0;i<blitzScore.length;i++) {
	    		//Comparing current score to max and updating max if lower than current score
	    		if(max[0] < blitzScore[i]) {
	    			max[0] = blitzScore[i];
	    			max[1] = i;
	    		}
	    	}
	    	//Returning the move with the highest score
			return s.getmovelist().get(max[1]);
    	}
    }
    
    public double MCTShighestscorepruned (State s, int n) {
        pruningfactor = n;
        long end = System.currentTimeMillis() + 3000;
        tree = new Tree(new Node(s));
        //int debugb = 0;
        while (System.currentTimeMillis() < end) {
            Node current = selectnode(tree.root);
            current.expandnode();
            current.prunenode();
            Node rollout = current;
            if (!current.children.isEmpty()) {
                rollout = current.getRandomChild();
            }
            int result = randomplay(rollout);
            //debugb++;
            //System.out.println("Finished rollout number " +debugb+ ", winner is " + (result == 1 ? "White" : "Black") + ".");
            backpropagate(rollout, result);
        }
        Node winner = Collections.max(tree.root.children, Comparator.comparing(c -> c.state.score));   
        //System.out.println("The winning node was visited " +winner.state.visits+ " times, and has a score of " +winner.state.score +".");
        return winner.siblingnumber;
    }

    public Node selectnode(Node n) {
        Node node = n;
        while (!node.children.isEmpty()) {
            node = UCT.findbestnode(node);
        }
        return node;
    }
    
    public void backpropagate(Node n, int r) {
        Node temp = n;
        while (temp != null) {
            temp.state.visits++;
            if ((r > 0) && (temp.state.white)) {
                temp.state.score++;
            }
            if ((r < 0) && (!temp.state.white)) {
                temp.state.score++;
            }
            temp = temp.parent;
        }
    }
    
    public int randomplay(Node n) {
        Node temp = n;
        while (temp.state.winner == 0) {
            temp.expandnode();
            totalnodesexpanded++;
            temp.prunenode();
            temp = temp.getRandomChild();
        }
        totalrollouts++;
        return temp.state.winner;
    }
    
    




}
