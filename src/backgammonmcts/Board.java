/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonmcts;

import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Laurin
 * This is the actual board, represented as an array with 26 indices. White plays from 25-1, Black plays from 0-24. White tokens on the bar go into 26, Black tokens on the bar go into 0. 
 * In every index, an entry of +X represents X White tokens, an entry of -X represents X Black tokens.
 */
public class Board {
    public int[] board;
    //public BGAList black;
    //public BGAList white;
    public Board () {                                       //constructors
        board = new int[26];
        board[1] = -2; board[6] = 5; board[8] = 3; board[12] = -5; board[13] = 5; board[17] = -3; board[19] = -5; board[24] = 2;
        //black = new BGAList();
        //Collections.addAll(black, 4, 1, 12, 17, 19);
        //white = new BGAList();
        //Collections.addAll(white, 4, 24, 13, 8, 6);
    }
    public Board (int[] b) {                                //don't call this wth arrays of sizes other than 26 or we get Null Pointer Exceptions.
        this.board = b.clone();
        //black = new BGAList();
        //white = new BGAList();
        //for (int i = 0; i < 26; i++) {
        //    if (board[i] < 0) {
        //        black.add(i);
        //    } else if (board[i] > 0) {
        //        white.add(i);
        //    }
        //}
        //black.add(0, black.size());
        //white.add(0, white.size());
        //white.whitesort();
    }
    public int[] getboard () {                              //getters and setters
        return this.board;
    }
    public void setboard (int[] b) {                        //don't call this with arrays of sizes other than 26 or we get Null Pointer Exceptions.
        this.board = b.clone();
    //    black.clear();
    //    white.clear();
    //    for (int i = 0; i < 26; i++) {
    //        if (board[i] < 0) {
    //            black.add(i);
    //        } else if (board[i] > 0) {
    //            white.add(i);
    //        }    
    //    }
    //    black.add(0, black.size());
    //    white.add(0, white.size());
    //    white.whitesort();
    }
    public void applymove (int[] m) {
        if (m.length == 0) {
            //System.out.println("Move is empty");
            return;
        } else {
            //System.out.println("Current Move is " + Arrays.toString(m));
            boolean white = (this.board[m[0]] > 0);
            if (white) {
                //System.out.println("Applying move " +Arrays.toString(m)+ " for White.");
                for (int i = 0; i < m.length; i+=2) {
                    this.board[m[i]]--;
                    if (m[i+1] > 0) {
                        if (this.board[m[i+1]] < 0) {
                            this.board[m[i+1]] = 1;
                            this.board[0]--;
                        } else {
                            this.board[m[i+1]]++;
                        }
                    }
                }
            } else {
                //System.out.println("Applying move " +Arrays.toString(m)+ " for Black.");
                for (int i = 0; i < m.length; i+=2) {
                    this.board[m[i]]++;
                    if (m[i+1] < 26) {
                        if (this.board[m[i+1]] > 0) {
                            this.board[m[i+1]] = -1;
                            this.board[25]++;
                        } else {
                            this.board[m[i+1]]--;
                        }
                    } 
                }
            }
        }
    }
    public void flip () {
        int n;
        for (int i = 0; i < 13; i++) {
            n = this.board[i];
            this.board[i] = this.board[25 - i];
            this.board[25 - i] = n;
        }
    }
    
        
        
    
}
