/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonmcts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Laurin
 */
public class BackgammonMCTS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        
        
        //HashMap<Long, Memory> hash = new HashMap<>();
        //Memory m1 = new Memory(2,2d);
        //Memory m2 = new Memory(5,5d);
        //hash.put(1L, m1);
        //hash.put(2L, m2);
        //try {
        //    FileOutputStream f = new FileOutputStream(new File("hash.ser"));
        //    ObjectOutputStream o = new ObjectOutputStream(f);
        //    o.writeObject(hash);
        //    o.close();
        //    f.close();
        //} catch (FileNotFoundException e) {
        //    System.out.println("File not found");
        //} catch (IOException e) {
        //    System.out.println("Error initializing stream");
        //    e.printStackTrace();
        //}
        
        /*  HASHTABLE TEST
        try {
            FileInputStream f = new FileInputStream(new File("hash.ser"));
            ObjectInputStream o = new ObjectInputStream(f);
            HashMap<Long, Memory> hash = (HashMap) o.readObject();
            o.close();
            f.close();
            Memory m1 = hash.get(1L);
            System.out.println("Visits to m1:" +m1.visits);
            System.out.println("Visits to m2:" +hash.get(2L).visits);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }
        */
        
        /* MULTITHREAD TEST
        MCTS a = new MCTS();
        Board b = new Board();
        Roll r = new Roll(3,5);
        State test = new State(b, true, 0, 0, 0, r);
        int move = a.MCTS_hsp_rootparallel(test, 10, 3000);
        System.out.println("The MCTS recommends move " +Arrays.toString(a.tree.root.state.movelist.get(move)) +".");
        */
    //}
        //Tester t = new Tester();
        //t.test(10, 10, 1, 6000, 3000);
        // Board Test
        //Board board = new Board();
        //System.out.println(board.black);
        //System.out.println(board.white);
        //int[] test = {0, 2, 5, 0, 0, -4, -2, 1, 1, 5, 6, 3, 3, 4, -2, -2, 3, 0, 0, 0, 0, 0, 2, -2, 0, 0};
        //board.setboard(test);
        //System.out.println(Arrays.toString(board.board));
        //System.out.println(board.black);
        //System.out.println(board.white);
        // Movelist Test
        //Roll r = new Roll (4,3);
        //int[] b = new int[] {0, -1, 0, 0, 0, 0, 0, 0, 2, -2, 0, 0, -1, 2, 2, 0, -1, 0, 0, 0, -2, -2, 0, 3, 0, 0};
        //Board test = new Board(b);
        //State teststate = new State(test, true, 0, 0, r);
        //for (int i = 0; i < 100000000; i++) {
        //    teststate.movehelper(test, r, true);
        //    teststate.movelist.clear();
        //}
        //teststate.movehelper(test, r, true);
        //System.out.println("Moves, White to play:");
        //for (int i = 0; i < teststate.movelist.size(); i++) {
        //    System.out.println(Arrays.toString(teststate.movelist.get(i)));
        //}
        //teststate.movelist.clear();
        //teststate.movehelper(test, r, false);
        //System.out.println("Moves, Black to play:");
        //for (int i = 0; i < teststate.movelist.size(); i++) {
        //    System.out.println(Arrays.toString(teststate.movelist.get(i)));
        // Movelist Test 2 
        //Roll r = new Roll (2,6);
        //int[] b = new int[] {0, 0, 0, -2, -1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, -2, 0, 0, 0, 0, 0, 3, 0, 0, 0};
        //Board test = new Board(b);
        //State teststate = new State(test, true, 0, 0, r);
        //teststate.movehelper(test, r, true);
        //System.out.println("Moves, White to play:");
        //for (int i = 0; i < teststate.movelist.size(); i++) {
        //    System.out.println(Arrays.toString(teststate.movelist.get(i)));
        //}
        //teststate.movelist.clear();
        //teststate.movehelper(test, r, false);
        //System.out.println("Moves, Black to play:");
        //for (int i = 0; i < teststate.movelist.size(); i++) {
        //    System.out.println(Arrays.toString(teststate.movelist.get(i)));
        //}
        // Bar Test
        
        //int[] b = new int[] {-2, 0, -2, 1, 0, 0, 2, -1, 0, 3, 2, 0, 0, -3, 2, 0, 0, 1, -1, 0, 0, 0, 2, -2, 0, 0};
        //Roll r = new Roll (3,1);
        //Board test = new Board(b);
        
        //int[] b = new int[] {-3, 0, 3, 0, -1, 2, 0, 0, 0, 2, 0, 0, 0, -3, 1, 0, 0, 0, 2, 0, -1, 0, 2, 2, 0, 2};
        //Roll r = new Roll (2,6);
        //Board test = new Board(b);
        
        //int[] b = new int[] {-1, 0, -2, 3, 3, 0, 0, -1, 0, 1, 0, 0, 2, 1, -3, 4, 2, 0, -1, 2, -3, 0, 0, 0, 2, 0};
        //Roll r = new Roll (4,3);
        //Board test = new Board(b);
        
        //int[]b = new int[] {-1, 0, 2, -1, 2, 0, 2, 3, -1, 0, 0, 0, 0, 0, 0, 0, -2, -2, 0, 0, 2, 0, 0, 0, 0, 1};
        //Roll r = new Roll (3,4);                                                                                  //Problem
        //Board test = new Board (b);
        
        //int[]b = new int[] {0, 0, 0, 3, 0, 0, 0, 0, 2, 0, 3, 0, 0, 0, 0, 0, 0, 0, -1, 0, -2, 0, -2, 2, -1, 3};
        //Roll r = new Roll (6,3);                                                                                  //Problem
        //Board test = new Board (b);
        
        //int[]b = new int[] {0, 0, 0, 0, 0, 0, 2, 2, 1, 0, 0, 0, 0, 4, 3, 0, -1, 0, 2, 0, -1, 3, 0, -2, 0, 0};
        //Roll r = new Roll (2,5);
        //Board test = new Board (b);
        
        //int[]b = new int[] {0, 1, 1, 0, 2, 0, 0, 0, 0, 0, 4, 0, 4, 0, 0, 0, 0, 2, 0, 0, -2, 2, -1, 2, 0, 1};
        //Roll r = new Roll (1,3);
        //Board test = new Board (b);
        
        //int[]b = new int[] {-1, 0, 2, 1, 2, 0, 0, -2, 2, 0, 2, 0, 0, 0, 0, -1, 0, 2, 2, -1, 1, 0, 2, 2, -2, 1};
        //Roll r = new Roll (3,5);
        //Board test = new Board (b);
        
        //int[]b = new int[] {0, 0, 0, 0, 1, 1, 0, 3, 3, 0, 0, 0, 0, 2, 0, -1, 2, 0, 0, -1, -1, -2, 4, 0, 1, 0};
        //Roll r = new Roll (5,6);
        //Board test = new Board (b);
        
        //int[]b = new int[] {0, 4, 0, 0, 3, 3, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0, -1, 0, 0, -1, 2, 0, 2, 2, -4, 2};
        //Roll r = new Roll (6,4);
        //Board test = new Board (b);
        
        //int[]b = new int[] {0, 4, 0, 1, 0, 0, 2, 0, 0, 1, 0, 3, 1, 0, 2, 1, 0, 0, 5, -1, -1, -4, 0, -4, 2, 0};
        //Roll r = new Roll (3,2);
        //Board test = new Board (b);
        
        //int[]b = new int[] {-1, 3, -1, -2, 2, 1, 0, 2, -4, 0, 0, 0, 2, 0, 1, 0, -2, 0, 0, -3, 0, 2, 2, -3, 0, 2};
        //Roll r = new Roll (5, 5);
        //Board test = new Board (b);
        
        //DOUBLE MOVEFINDER TEST BOARDS
        
        //Incomplete Move:
        //int[]b = new int[] {0,0,0,-3,-2,0,0,2,3,-1,0,0,2,1,0,0,3,0,0,0,2,-2,0,0,1,1};
        //Roll r = new Roll (4, 4);
        //Board test = new Board(b);
        
        //Bears
        //int[]b = new int[] {0,0,2,2,0,0,0,0,3,0,0,0,3,0,-2,1,0,0,0,1,2,0,-4,0,-1,0};
        //Roll r = new Roll (5, 5);
        //Board test = new Board(b);
        
        //Bar, Incomplete Move:
        //int[]b = new int[] {-1,0,2,0,-3,0,2,3,0,0,-1,0,0,1,0,-2,-3,2,2,4,0,0,1,-1,0,0};
        //Roll r = new Roll (3, 3);
        //Board test = new Board(b);
        
        //Bears
        //int[]b = new int[] {0,0,1,0,3,4,1,0,0,0,0,0,0,1,0,0,-1,0,0,-2,-2,-3,2,-1,-3,0};
        //Roll r = new Roll (3, 3);
        //Board test = new Board(b);
        
        //Bar, Incomplete Move:
        //int[]b = new int[] {-1,0,2,-3,0,0,1,0,-1,2,0,0,0,0,2,-1,0,-4,4,0,0,5,0,4,1,0};
        //Roll r = new Roll (6, 6);
        //Board test = new Board(b);
        
        //Shitton of Moves:
        //int[]b = new int[] {0,2,3,-2,0,-5,0,0,-1,2,-2,0,1,5,-4,0,1,0,-1,3,0,0,3,0,-2,2};
        //Roll r = new Roll (2, 2);
        //Board test = new Board(b);
        
        //Two-Version tests (doubles and singles), for White:
        //int[]b = new int[] {0,-3,0,-2,-1,0,0,3,0,0,0,0,-1,0,0,2,0,0,0,-2,-1,0,0,-2,0,2};
        //Roll r = new Roll (3, 3);
        //Roll r = new Roll (3, 5);
        //Board test = new Board(b);
        
        //int[]b = new int[] {-1,-2,0,-1,0,-3,4,0,0,-1,1,0,0,0,0,0,-2,-2,-4,0,0,-1,-1,0,0,0};
        //Roll r = new Roll (6, 6);
        //Roll r = new Roll (6, 5);
        //Board test = new Board(b);
        
        //int[]b = new int[] {-2,-2,0,-3,0,1,0,2,0,1,0,-1,0,1,0,0,-3,0,2,-2,0,0,1,0,-2,1};
        //Roll r = new Roll (2, 2);
        //Roll r = new Roll (3, 6);
        //Board test = new Board(b);
        
        //int[]b = new int[] {0,-1,-1,-2,0,0,0,1,0,0,0,0,0,0,0,0,0,0,-2,-1,0,0,0,-1,0,0};
        //Roll r = new Roll (4, 4);
        //Roll r = new Roll (4, 3);
        //Board test = new Board(b);
        
        //SINGLES COMBINATION TESTS:
        //aI
        //int[]b = new int[] {0,0,0,0,0,0,0,0,-2,0,-2,0,-2,0,0,0,0,1,0,0,0,0,0,0,0,0};
        //Roll r = new Roll (5,3);
        //aII
        //int[]b = new int[] {0,0,-2,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        //Roll r = new Roll (5,6);
        //bI
        //int[]b = new int[] {0,0,0,0,0,0,0,-2,0,0,-2,0,-2,0,0,1,0,0,0,0,0,0,0,0,0,0};
        //Roll r = new Roll (3,4);
        //bII
        //int[]b = new int[] {0,0,0,0,-2,-2,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        //Roll r = new Roll (3,5);
        //cI
        //int[]b = new int[] {0,0,0,0,0,-2,0,0,-2,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0};
        //Roll r = new Roll (3,5);
        //cII
        //int[]b = new int[] {0,0,0,0,-2,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        //Roll r = new Roll (5,3);
        //dI
        //int[]b = new int[] {0,0,0,-2,0,-2,0,0,0,1,0,0,0,0,-2,0,1,0,0,0,0,0,0,0,0,0};
        //Roll r = new Roll (4,2);
        //dII
        //int[]b = new int[] {0,1,-2,0,0,1,0,-2,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        //Roll r = new Roll (5,4);
        //e
        //int[]b = new int[] {0,0,0,0,0,0,0,0,0,0,-2,0,0,0,-2,1,0,0,0,0,0,0,0,0,-2,1};
        //Roll r = new Roll (5,1);
        //e*
        //int[]b = new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,-2,1,0,0,0,0,0,0,0,0,0,1};
        //Roll r = new Roll (5,1);
        //f
        //int[]b = new int[] {0,0,0,0,0,0,0,0,0,0,0,-2,-2,0,0,1,0,0,0,0,0,-2,0,0,0,1};
        //Roll r = new Roll (4,3);
        //f*
        //int[]b = new int[] {0,0,0,0,0,0,0,0,0,0,0,-2,0,0,0,1,0,0,0,0,0,0,0,0,0,1};
        //Roll r = new Roll (4,3);
        //gI
        //int[]b = new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0};
        //Roll r = new Roll (4,2);
        //gII
        //int[]b = new int[] {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        //Roll r = new Roll (3,5);
        //bar+bear test
        //int[]b = new int[] {0,0,1,0,0,0,0,0,0,0,0,0,0,0,-2,0,0,0,0,0,0,0,0,0,0,1};
        //Roll r = new Roll (5,6);
        //confirmation tests that single white is working okay:
        //int[]b = new int[] {0,0,0,0,2,-2,0,0,0,0,-2,1,0,0,0,0,1,0,0,0,-2,0,2,0,0,0};
        //Roll r = new Roll (6,2);
        //int[]b = new int[] {0,0,2,0,0,0,0,0,0,0,0,-2,2,0,0,0,0,0,0,-1,0,0,0,0,-2,1};
        //Roll r = new Roll (1,5);
        //int[]b = new int[] {0,-2,0,1,0,0,2,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        //Roll r = new Roll (4,5);
        //int[]b = new int[] {0,0,0,0,0,0,0,0,1,2,0,0,0,0,1,1,0,0,0,0,0,2,0,0,0,0};
        //Roll r = new Roll (4,2);
        
        //Roll r = new Roll (6,2);
        
        //SINGLE MCTS TEST:
        //Board test = new Board();
        //Roll r = new Roll(3,5);
        //State teststate = new State(test, true, 0, 0, 0, r);
        //MCTS m = new MCTS();
        //int n = m.TimeMCTSmostvisited(teststate);
        //System.out.println("Moves, White to play:");
        //for (int i = 0; i < teststate.movelist.size(); i++) {
        //    System.out.println(Arrays.toString(teststate.movelist.get(i)));
        //}
        //System.out.println("The MCTS recommends move " +Arrays.toString(m.tree.root.state.movelist.get(n)) +".");
        
        /*
        //ENTIRE GAME TEST:
        Board test = new Board();
        Roll r = new Roll();
        while (r.steps[0] == r.steps[1]) {
            r = new Roll();
        }
        boolean white = (r.steps[0] > r.steps[1]);
        State start = new State(test, white, 0, 0, 0, r);
        MCTS m = new MCTS();
        int n = 0;
        State next = new State();
        while (start.wincheck() == 0) {
            n = m.MCTSmostvisitedpruned(start, 7);
            //n = m.MCTSmostvisited(start);
            System.out.println(((start.white) ? "White" : "Black") + " rolled " +start.roll.steps[0]+ ", " +start.roll.steps[1] + ".");
            if (m.tree.root.state.movelist.isEmpty()) {
                System.out.println(((start.white) ? "White" : "Black") + " is forced to pass.");
                next = new State(start, new int[]{});
            } else {
                System.out.println(((start.white) ? "White" : "Black") + " plays " +Arrays.toString(m.tree.root.state.movelist.get(n)) +".");
                next = new State(start, m.tree.root.state.movelist.get(n));
            }
            start = next;
        }
        if (start.wincheck() > 0) {
            System.out.println("White wins the game!");
        } else {
            System.out.println("Black wins the game!");
        }
        */
        
        /*ENTIRE GAME TEST WITH SEPARATE STATS FOR NODES AND ROLLOUTS:
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
                n = w.MCTSmostvisitedpruned(start, 7, 3000);
                System.out.println(((start.white) ? "White" : "Black") + " rolled " +start.roll.steps[0]+ ", " +start.roll.steps[1] + ".");
                if (w.tree.root.state.movelist.isEmpty()) {
                    System.out.println(((start.white) ? "White" : "Black") + " is forced to pass.");
                    next = new State(start, new int[]{});
                } else {
                    System.out.println(((start.white) ? "White" : "Black") + " plays " +Arrays.toString(w.tree.root.state.movelist.get(n)) +".");
                    next = new State(start, w.tree.root.state.movelist.get(n));
                }
            start = next;
            } else {
                n = b.MCTSmostvisitedpruned(start, 1, 5000);
                System.out.println(((start.white) ? "White" : "Black") + " rolled " +start.roll.steps[0]+ ", " +start.roll.steps[1] + ".");
                if (b.tree.root.state.movelist.isEmpty()) {
                    System.out.println(((start.white) ? "White" : "Black") + " is forced to pass.");
                    next = new State(start, new int[]{});
                } else {
                    System.out.println(((start.white) ? "White" : "Black") + " plays " +Arrays.toString(b.tree.root.state.movelist.get(n)) +".");
                    next = new State(start, b.tree.root.state.movelist.get(n));
                }
            start = next;
            }
        }
        if (start.wincheck() > 0) {
            System.out.println("White wins the game!");
        } else {
            System.out.println("Black wins the game!");
        }
        System.out.println("Total nodes expanded for White: " +w.totalnodesexpanded);
        System.out.println("Rollouts completed for White: " +w.totalrollouts);
        System.out.println("Total nodes expanded for Black: " +b.totalnodesexpanded);
        System.out.println("Rollouts completed for Black: " +b.totalrollouts);
        
        */

        //ENTIRE GAME TEST MULTI VS TRUE RANDOM
        Board test;
        Roll r;
        State start;
        State next;
        boolean white;
        int whitewins = 0;
        int blackwins = 0;
        int n;
        boolean first;
        MCTS w = new MCTS();
        w.setpruningprofile(1.0, 1.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0);
        MCTS b = new MCTS();
        for (int i = 0; i<100; i++) {
            test = new Board();
            r = new Roll();
            while (r.steps[0] == r.steps[1]) {
                r = new Roll();
            }
            white = (r.steps[0] > r.steps[1]);
            first = white;
            start = new State(test, white, 0, 0, 0, r);
            n = 0;
            next = new State();
            while (start.wincheck() == 0) {
                if (start.white) {
                    //n = w.MCTS_hsp_rootparallel(start, 1, 450);
                    n = w.MCTSmostvisitedpruned(start, 1, 50);
                    //n = w.MCTS_mvp_rootparallel(start, 1, 50);
                    //n = w.MCTS_mvp_vcrp(start, 1, 6, 150);
                    //n = w.MCTShighestscorepruned(start, 1, 50);
                    //n = w.MCTSmostvisited(start, 50);
                    //n = w.MCTShighestscore(start, 450);
                    //System.out.println(((start.white) ? "White" : "Black") + " rolled " +start.roll.steps[0]+ ", " +start.roll.steps[1] + ".");
                    if (first) {
                        int[] move = FirstMove.opening(r, true);
                        next = new State(start, move);
                        first = false;
                    } else {
                        if (w.tree.root.state.movelist.isEmpty()) {
                            //System.out.println(((start.white) ? "White" : "Black") + " is forced to pass.");
                            next = new State(start, new int[]{});
                        } else {
                            //System.out.println(((start.white) ? "White" : "Black") + " plays " +Arrays.toString(w.tree.root.state.movelist.get(n)) +".");
                            next = new State(start, w.tree.root.state.movelist.get(n));
                        }
                    }
                    start = next;
                } else {
                    n = b.fullrandom(start);
                    //System.out.println(((start.white) ? "White" : "Black") + " rolled " +start.roll.steps[0]+ ", " +start.roll.steps[1] + ".");
                    if (b.tree.root.state.movelist.isEmpty()) {
                        //System.out.println(((start.white) ? "White" : "Black") + " is forced to pass.");
                        next = new State(start, new int[]{});
                    } else {
                        n = ThreadLocalRandom.current().nextInt(0, b.tree.root.state.movelist.size());
                        //System.out.println(((start.white) ? "White" : "Black") + " plays " +Arrays.toString(b.tree.root.state.movelist.get(n)) +".");
                        next = new State(start, b.tree.root.state.movelist.get(n));
                    }
                    start = next;
                }
            }
            if (start.wincheck() > 0) {
                System.out.println("White wins the game!");
                whitewins++;
            } else {
                System.out.println("Black wins the game!");
                blackwins++;
            }
        //System.out.println("Total nodes expanded for White: " +w.totalnodesexpanded);
        //System.out.println("Rollouts completed for White: " +w.totalrollouts);
        //System.out.println("Total nodes expanded for Black: " +b.totalnodesexpanded);
        //System.out.println("Rollouts completed for Black: " +b.totalrollouts);
    
        }
        System.out.println("Out of 100 matches, White won " + whitewins + " times, and Black won " + blackwins + " times.");
        
        
        /*
        //BACKPROPAGATE TEST
        int[] inta = new int[]{-1, 0, -13, -1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] intb = new int[]{-1, 0, -13, -1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] intc = new int[]{-2, 0, -13, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] intd = new int[]{-2, 0, -13, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] inte = new int[]{-2, 0, -13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Board boarda = new Board(inta);
        Board boardb = new Board(intb);
        Board boardc = new Board(intc);
        Board boardd = new Board(intd);
        Board boarde = new Board(inte);
        Roll rolla = new Roll(5,5);
        Roll rollb = new Roll(1,1);
        Roll rollc = new Roll(3,3);
        Roll rolld = new Roll(4,6);
        Roll rolle = new Roll(1,2);
        State statea = new State(boarda, false, 0, 0, 0.0, rolla);
        State stateb = new State(boardb, true, 0, 0, 0.0, rollb);
        State statec = new State(boardc, false, 0, 0, 0.0, rollc);
        State stated = new State(boardd, true, 0, 0, 0.0, rolld);
        State statee = new State(boarde, false, 0, 0, 0.0, rolle);
        Node nodea = new Node(statea);
        Node nodeb = new Node(stateb, nodea, 0);
        Node nodec = new Node(statec, nodeb, 0);
        Node noded = new Node(stated, nodec, 0);
        Node nodee = new Node(statee, noded, 0);
        statee.winner = statee.wincheck();
        System.out.println("Winner field for the final state is: "+statee.winner+".");
        MCTS m = new MCTS();
        m.backpropagate(nodee, statee.winner);
        System.out.println("State a Visits = " +statea.visits+ ", State a Score = " +statea.score+ ".");
        System.out.println("State b Visits = " +stateb.visits+ ", State a Score = " +stateb.score+ ".");
        System.out.println("State c Visits = " +statec.visits+ ", State a Score = " +statec.score+ ".");
        System.out.println("State d Visits = " +stated.visits+ ", State a Score = " +stated.score+ ".");
        System.out.println("State e Visits = " +statee.visits+ ", State a Score = " +statee.score+ ".");
        //System.out.println(statee.wincheck());
        */
        
        //Board b = new Board();
        //Roll r = new Roll(4,2);
        //State start = new State(b, true, 0, 0, 0, r);
        //MCTS w = new MCTS();
        //int n = w.MCTS_mvp_rootparallel(start, 2, 500);
        
        
        //for (int i = 0; i < 10000000; i++) {
               
        //    teststate.movehelper(test, r, false);
        //    teststate.movelist.clear();
        //}
        //teststate.movehelper(test, r, true);
        //teststate.doublesorter(true);
        //System.out.println("Moves, White to play:");
        //for (int i = 0; i < teststate.movelist.size(); i++) {
        //    System.out.println(Arrays.toString(teststate.movelist.get(i)));
            
        /*Component Testing
        
        int[] b = new int[]{0,0,0,0,-2,3,0,0,1,1,0,3,0,0,0,0,0,0,-2,0,3,1,1,0,-1,0};    //should evaluate WAY in favor of black
        int[] c = new int[]{0,1,0,0,-1,-1,0,0,0,3,2,0,0,0,0,-2,-3,0,0,-1,0,2,0,0,0,0};  //should evaluate slightly in favor of white
        Board boardb = new Board(b);
        Board boardc = new Board(c);
        State boardbwhite = new State(boardb, true, 0, 0, 0.0, new Roll(3,2));
        State boardbblack = new State(boardb, false, 0, 0, 0.0, new Roll(3,2));
        State boardcwhite = new State(boardc, true, 0, 0, 0.0, new Roll(2,4));
        State boardcblack = new State(boardc, false, 0, 0, 0.0, new Roll(2,4));
        Node one = new Node(boardbwhite);
        Node two = new Node(boardbblack);
        Node three = new Node(boardcwhite);
        Node four = new Node(boardcblack);
        //System.out.println("Shorteval for the first State - white to play - returns: " + Evals.shorteval(boardbwhite));
        //System.out.println("Longeval for the first State - white to play - returns: " + Evals.longeval(boardbwhite, 1.0));
        //System.out.println("Shorteval for the first State - black to play - returns: " + Evals.shorteval(boardbblack));
        //System.out.println("Longeval for the first State - black to play - returns: " + Evals.longeval(boardbblack, 1.0));
        //System.out.println("Shorteval for the second State - white to play - returns: " + Evals.shorteval(boardcwhite));
        //System.out.println("Shorteval for the second State - black to play - returns: " + Evals.shorteval(boardcblack));
        one.expandnode();
        one.evalchildren(1.0, 0, 0, 0, 1.0, 0, 0, 0);
        for (int i = 0; i < one.children.size(); i++) {
            System.out.println("The move " + Arrays.toString(boardbwhite.movelist.get(i)) + " leads to a State evaluated at " + one.children.get(i).state.eval + ".");
        }
        System.out.println("To confirm: The evaluation of the child with Siblingnumber 1 is " +one.children.get(1).state.eval + ".");
        if (one.children.get(1).state.white) {
            System.out.println("The associated state is white.");
        } else {
            System.out.println("The associated state is black.");
        }
        one.prunenode(10);
        System.out.println("After pruning, the following moves are considered:");
        for (int i = 0; i<10; i++) {
            System.out.println("The move " + Arrays.toString(boardbwhite.movelist.get(one.children.get(i).getsiblingnumber())) +", which leads to a State evaluated at " + one.children.get(i).state.eval + ".");
        }
        */
    
    }
}







/*
TEST BOARDS:
            Black                                         White                         Roll 
1.      2+ on bar, but both can move                
{-2, 0, -2, 1, 0, 0, 2, -1, 0, 3, 2, 0, 0, -3, 2, 0, 0, 1, -1, 0, 0, 0, 2, -2, 0, 0}    3,1

2.      2+ on bar, only 1 move possible 
{-3, 0, 3, 0, -1, 2, 0, 0, 0, 2, 0, 0, 0, -3, 1, 0, 0, 0, 2, 0, -1, 0, 2, 2, 0, 2}      2,6

3.      1 on bar, no moves possible
{-1, 0, -2, 3, 3, 0, 0, -1, 0, 1, 0, 0, 2, 1, -3, 4, 2, 0, -1, 2, -3, 0, 0, 0, 2, 0}    4,3

4.      1 on bar, then move                                                            
{-1, 0, 2, -1, 2, 0, 2, 3, -1, 0, 0, 0, 0, 0, 0, 0, -2, -2, 0, 0, 2, 0, 0, 0, 0, 1}     3,4

5.      Bear 1                                                      
{0, 0, 0, 3, 0, 0, 0, 0, 2, 0, 3, 0, 0, 0, 0, 0, 0, 0, -1, 0, -2, 0, -2, 2, -1, 3}      6,3

6.      Bear 1, partial move
{0, 0, 0, 0, 0, 0, 2, 2, 1, 0, 0, 0, 0, 4, 3, 0, -1, 0, 2, 0, -1, 3, 0, -2, 0, 0}       2,5

7.      Bear 0, partial move
{0, 1, 1, 0, 2, 0, 0, 0, 0, 0, 4, 0, 4, 0, 0, 0, 0, 2, 0, 0, -2, 2, -1, 2, 0, 1}        1,3

8.      Bar, then move
{-1, 0, 2, 1, 2, 0, 0, -2, 2, 0, 2, 0, 0, 0, 0, -1, 0, 2, 2, -1, 1, 0, 2, 2, -2, 1}     3,5

9.      Bear 1
{0, 0, 0, 0, 1, 1, 0, 3, 3, 0, 0, 0, 0, 2, 0, -1, 2, 0, 0, -1, -1, -2, 4, 0, 1, 0}      5,6

10.     No move b/c Bear 1
{0, 4, 0, 0, 3, 3, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0, -1, 0, 0, -1, 2, 0, 2, 2, -4, 2}       6,4

11.     Bear 0, full move
{0, 4, 0, 1, 0, 0, 2, 0, 0, 1, 0, 3, 1, 0, 2, 1, 0, 0, 5, -1, -1, -4, 0, -4, 2, 0}      3,2

*/
