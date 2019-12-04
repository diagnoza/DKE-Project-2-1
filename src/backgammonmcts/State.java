/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonmcts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author Laurin This represents the state of the entire board at a particular
 * step of the game. The board itself (including pieces) is contained in Board,
 * but the State also contains the boolean white (TRUE if white plays next), the
 * visits and score used for evaluating Nodes in the MCTS, the current Roll, and
 * methods to list all possible moves and select one at random to play.
 */
public class State {

    Board board;
    boolean white;
    boolean checked;
    int visits;
    int stage = 0;
    int winner = 0;
    double score;
    Roll roll;
    double eval = 0;
    ArrayList<int[]> movelist;
    static double[] risks = new double[]{0.305, 0.319, 0.347, 0.361, 0.361, 0.388, 0.083, 0.083, 0.069, 0.041, 0.027, 0.041, 0.0, 0.0, 0.013, 0.013, 0.0, 0.013, 0.0, 0.013, 0.0, 0.0, 0.0, 0.013};

    public State() {
    }

    public State(Board b, boolean w, int v, int x, double s, Roll r) {
        this.board = b;
        this.white = w;
        this.checked = false;
        this.visits = v;
        this.stage = 0;
        this.winner = x;
        this.score = s;
        this.roll = r;
        //this.eval = longeval();
        this.movelist = new ArrayList<>();
    }

    public State(State s) {
        this.board = new Board(s.board.board);
        this.white = s.white;
        this.checked = s.checked;
        this.visits = s.visits;
        this.stage = s.stage;
        this.winner = s.winner;
        this.score = s.score;
        this.roll = s.roll;
        this.eval = s.eval;
        this.movelist = new ArrayList<>();
        Iterator<int[]> iterator = movelist.iterator();
        while (iterator.hasNext()) {
            this.movelist.add((int[]) iterator.next().clone());
        }
    }

    public State(State s, int[] move) {
        this.board = new Board(s.board.board);
        this.board.applymove(move);
        this.stage = s.stage;
        this.stagefinder();
        this.winner = wincheck();
        this.white = (!s.white);
        this.checked = false;
        this.visits = 0;
        this.score = 0;
        this.roll = new Roll();
        //this.eval = longeval();
        this.movelist = new ArrayList<>();
    }

    public Board getboard() {
        return this.board;
    }

    public boolean getwhite() {
        return this.white;
    }

    public boolean getchecked() {
        return this.checked;
    }

    public int getvisits() {
        return this.visits;
    }

    public double getscore() {
        return this.score;
    }

    public Roll getroll() {
        return this.roll;
    }

    public ArrayList<int[]> getmovelist() {
        return this.movelist;
    }

    public void setboard(Board b) {
        this.board = b;
    }

    public void setwhite(boolean w) {
        this.white = w;
    }

    public void setchecked(boolean w) {
        this.checked = w;
    }

    public void setvisits(int v) {
        this.visits = v;
    }

    public void setscore(double s) {
        this.score = s;
    }

    public void setroll(Roll r) {
        this.roll = r;
    }

    public void setmovelist(ArrayList<int[]> m) {               //setting the movelist to include Arrays with size other than 4 will lead to NPEs ?????
        this.movelist = m;
    }
    
    public long hash () {
        long result = 0;
        if (this.white) {
            for (int i = 0; i < 26; i++) {
                result = 31 * result + this.board.board[i];
            }
            result = 31 * result + this.roll.steps[0];
            result = 31 * result + this.roll.steps[1];
            return result;
        } else {
            Board temp = new Board(this.board.board);
            temp.flip();
            for (int i = 0; i < 26; i++) {
                result = 31 * result + temp.board[i];
            }
            result = 31 * result + this.roll.steps[0];
            result = 31 * result + this.roll.steps[1];
            return result;
        }
    }
    

    public int wincheck() {                                      //this will return a black win before a white win in the silly case that someone continues to play moves on a board that is already an endstate and manages to get both sides off the board
        boolean whitefound = false;
        boolean blackfound = false;
        for (int i = 0; (i < 26) && (!(blackfound && whitefound)); i++) {
            if (this.board.board[i] > 0) {
                whitefound = true;
            }
            if (this.board.board[i] < 0) {
                blackfound = true;
            }
        }
        if (!blackfound) {
            return gammoncheck(false);
        } else if (!whitefound) {
            return gammoncheck(true);
        } else {
            return 0;
        }
    }
    
    public int gammoncheck(boolean white) {
        int result = 0;
        if (white) {
            for (int i = 0; i < 26; i++) {
                result -= this.board.board[i];
            }
            if (result == 15) {
                if (this.board.board[0] < 0) {
                    return 3;
                } else {
                    return 2;
                }
            } else {
                return 1;
            }
        } else {
            for (int i = 0; i < 26; i++) {
                result -= this.board.board[i];
            }
            if (result == -15) {
                if (this.board.board[25] > 0) {
                    return -3;
                } else {
                    return -2;
                }
            } else {
                return -1;
            }
        }
    }

    public void movehelper(Board b, Roll r, boolean white) {
        if (r.steps[0] == r.steps[1]) {
            if (white) {
                doublemovesw(b, r);
            } else {
                doublemovesb(b, r);
            }
        } else {
            ArrayList<Integer> first = new ArrayList<>();
            ArrayList<Integer> second = new ArrayList<>();
            if (white) {
                movelisterw(b, r, first, second);
            } else {
                movelisterb(b, r, first, second);
            }
        }
        this.checked = true;
    }

    public void doublemovesw(Board b, Roll r) {
        int bar = b.board[25];
        int n = r.steps[0];
        int onebreak = 500;
        int twobreak = 500;
        int threebreak = 500;
        int fourbreak = 500;
        int desta = 0;
        int destb = 0;
        int destc = 0;
        int destd = 0;
        boolean bear = false;
        boolean bearcheck = false;
        if ((bar > 0) && (b.board[25-n] < -1)) {
            return;
        } else if (bar > 3) {
            this.movelist.add(new int[]{25, 25-n, 25, 25-n, 25, 25-n, 25, 25-n});
            return;
        }
        ArrayList<Integer> one = new ArrayList<>();
        ArrayList<Integer> two = new ArrayList<>();
        ArrayList<Integer> three = new ArrayList<>();
        ArrayList<Integer> four = new ArrayList<>();
        ArrayList<Integer> bears = new ArrayList<>();
        if ((b.board[25]) == 1) {
            if (b.board[25 - n - n] > -2) {
                if (b.board[25 - n - n - n] > -2) {
                    if (b.board[25 - n - n - n - n] > -2) {
                        three.add(25-n);
                    }
                    two.add(25-n);
                }
                one.add(25-n);
            }
            bears.add(25);
        }
        if ((b.board[25]) == 2) {
            if (b.board[25 - n - n] > -2) {
                if (b.board[25 - n - n - n] > -2) {
                    two.add(25 - n);
                }
                one.add(25 - n);
                one.add(25 - n);
            }
            bears.add(25);
            bears.add(25);
        }
        if ((b.board[25]) == 3) {
            if (b.board[25 - n - n] > -2) {
                one.add(25 - n);
            }
            bears.add(25);
            bears.add(25);
            bears.add(25);
        }
        for (int i = 24; i > 0; i--) {
            if (b.board[i] == 1) {
                if ((i - n > 0) && (b.board[i - n] > -2)) {
                    if ((i - n - n > 0) && (b.board[i - n - n] > -2)) {
                        if ((i - n - n - n > 0) && (b.board[i - n - n - n] > -2)) {
                            if ((i - n - n - n - n > 0) && (b.board[i - n - n - n - n] > -2)) {
                                four.add(i);
                            }
                            three.add(i);
                        }
                        two.add(i);
                    }
                    one.add(i);
                }
                if (i > 6) {
                    bears.add(i);
                }
            }
            if (b.board[i] == 2) {                                                                 
                if ((i - n > 0) && (b.board[i - n] > -2)) {
                    if ((i - n - n > 0) && (b.board[i - n - n] > -2)) {
                        if ((i - n - n - n > 0) && (b.board[i - n - n - n] > -2)) {
                            if ((i - n - n - n - n > 0) && (b.board[i - n - n - n - n] > -2)) {
                                four.add(i);
                            }
                            three.add(i);
                        }
                        two.add(i);
                        two.add(i);
                    }
                    one.add(i);
                    one.add(i);
                }
                if (i > 6) {
                    bears.add(i);
                    bears.add(i);
                }
            }
            if (b.board[i] == 3) {
                if ((i - n > 0) && (b.board[i - n] > -2)) {
                    if ((i - n - n > 0) && (b.board[i - n - n] > -2)) {
                        if ((i - n - n - n > 0) && (b.board[i - n - n - n] > -2)) {
                            if ((i - n - n - n - n > 0) && (b.board[i - n - n - n - n] > -2)) {
                                four.add(i);
                            }
                            three.add(i);
                        }
                        two.add(i);
                        two.add(i);
                    }
                    one.add(i);
                    one.add(i);
                    one.add(i);
                }
                if (i > 6) {
                    bears.add(i);
                    bears.add(i);
                    bears.add(i);
                }
            }
            if (b.board[i] > 3) {
                if ((i - n > 0) && (b.board[i - n] > -2)) {
                    if ((i - n - n > 0) && (b.board[i - n - n] > -2)) {
                        if ((i - n - n - n > 0) && (b.board[i - n - n - n] > -2)) {
                            if ((i - n - n - n - n > 0) && (b.board[i - n - n - n - n] > -2)) {
                                four.add(i);
                            }
                            three.add(i);
                        }
                        two.add(i);
                        two.add(i);
                    }
                    one.add(i);
                    one.add(i);
                    one.add(i);
                    one.add(i);
                }
                if (i > 6) {
                    bears.add(i);
                    bears.add(i);
                    bears.add(i);
                    bears.add(i);
                }
            }
        }
        Collections.sort(one);
        Collections.sort(two);
        Collections.sort(three);
        Collections.sort(four);
        //System.out.println("One contains " + Arrays.toString(one.toArray()));
        //System.out.println("Two contains " + Arrays.toString(two.toArray()));
        //System.out.println("Three contains " + Arrays.toString(three.toArray()));
        //System.out.println("Four contains " + Arrays.toString(four.toArray()));
        //System.out.println("Bears contains " + Arrays.toString(bears.toArray()));
        if (bears.isEmpty()) {
            bear = true;
        } else if (bears.size() < 4) {
            bearcheck = true;
            onebreak = one.size();
            twobreak = two.size();
            threebreak = three.size();
            fourbreak = four.size();
        }
        if (((bears.isEmpty()) || (bears.size() < 4)) && (b.board[25] == 0)) {
            for (int i = 0 + n + n + n + n; i > 0; i--) {
                if (b.board[i] == 1) {
                    if (i - n < 1) {
                        one.add(i);
                    } else if ((b.board[i - n] > -2) && (i - n - n < 1)) {
                        two.add(i);
                    } else if ((b.board[i - n] > -2) && (b.board[i - n - n] > -2) && (i - n - n - n < 1)) {
                        three.add(i);
                    } else if ((b.board[i - n] > -2) && (b.board[i - n - n] > -2) && (b.board[i - n - n - n] > -2) && (i - n - n - n - n < 1)) {
                        four.add(i);
                    }
                }
                if (b.board[i] == 2) {              
                    if (i - n < 1) {
                        one.add(i);
                        one.add(i);
                    } else if ((b.board[i - n] > -2) && (i - n - n < 1)) {
                        two.add(i);
                        two.add(i);
                    } else if ((b.board[i - n] > -2) && (b.board[i - n - n] > -2) && (i - n - n - n < 1)) {
                        three.add(i);
                    } else if ((b.board[i - n] > -2) && (b.board[i - n - n] > -2) && (b.board[i - n - n - n] > -2) && (i - n - n - n - n < 1)) {
                        four.add(i);
                    }
                }
                if (b.board[i] == 3) {
                    if (i - n < 1) {
                        one.add(i);
                        one.add(i);
                        one.add(i);
                    } else if ((b.board[i - n] > -2) && (i - n - n < 1)) {
                        two.add(i);
                        two.add(i);
                    } else if ((b.board[i - n] > -2) && (b.board[i - n - n] > -2) && (i - n - n - n < 1)) {
                        three.add(i);
                    } else if ((b.board[i - n] > -2) && (b.board[i - n - n] > -2) && (b.board[i - n - n - n] > -2) && (i - n - n - n - n < 1)) {        
                        four.add(i);
                    }
                }
                if (b.board[i] > 3) {
                    if (i - n < 1) {
                        one.add(i);
                        one.add(i);
                        one.add(i);
                        one.add(i);
                    } else if ((b.board[i - n] > -2) && (i - n - n < 1)) {
                        two.add(i);
                        two.add(i);
                    } else if ((b.board[i - n] > -2) && (b.board[i - n - n] > -2) && (i - n - n - n < 1)) {
                        three.add(i);
                    } else if ((b.board[i - n] > -2) && (b.board[i - n - n] > -2) && (b.board[i - n - n - n] > -2) && (i - n - n - n - n < 1)) {
                        four.add(i);
                    }
                }
            }
        }
        if (onebreak == 500) {
            onebreak = one.size();
        }
        if (twobreak == 500) {
            twobreak = two.size();
        }
        if (threebreak == 500) {
            threebreak = three.size();
        }
        if (fourbreak == 500) {
            fourbreak = four.size();
        }                                                                                                   
        //System.out.println("onebreak is " + onebreak);
        //System.out.println("twobreak is " + twobreak);
        //System.out.println("threebreak is " + threebreak);
        //System.out.println("fourbreak is " + fourbreak);
        //System.out.println("bar is " + bar);
        if (bar == 3) {                                                               //////the checks for four.add in the above section were wrong, gotta fix that also in the black version
            int tempa = 100;
            for (int i = 0; i < onebreak; i++) {
                if (one.get(i) != tempa) {
                    tempa = one.get(i);
                    this.movelist.add(new int[]{25, 25 - n, 25, 25 - n, 25, 25 - n, tempa, tempa - n});
                }
            }
        } else if (bar == 2) {
            int tempa = 100;
            int tempb = 100;
            for (int i = 0; i < onebreak; i++) {
                if (one.get(i) != tempa) {
                    tempa = one.get(i);
                    tempb = 100;
                    for (int j = i + 1; j < onebreak; j++) {
                        if (one.get(j) != tempb) {
                            tempb = one.get(j);
                            this.movelist.add(new int[]{25, 25 - n, 25, 25 - n, tempa, tempa - n, tempb, tempb - n});
                        }
                    }
                }
            }
            tempa = 100;
            for (int i = 0; i < twobreak; i++) {
                if (two.get(i) != tempa) {
                    tempa = two.get(i);
                    if (one.contains(tempa - n)) {
                        continue;
                    }
                    this.movelist.add(new int[]{25, 25 - n, 25, 25 - n, tempa, tempa - n, tempa - n, tempa - n - n});
                }
            }
        } else if (bar == 1) {
            int tempa = 100;
            int tempb = 100;
            int tempc = 100;
            boolean skipped = false;
            for (int i = 0; i < onebreak; i++) {
                if (one.get(i) != tempa) {
                    tempa = one.get(i);
                    tempb = 100;
                    for (int j = i + 1; j < onebreak; j++) {
                        if (one.get(j) != tempb) {
                            tempb = one.get(j);
                            tempc = 100;
                            for (int k = j + 1; k < onebreak; k++) {
                                if (one.get(k) != tempc) {
                                    tempc = one.get(k);
                                    this.movelist.add(new int[]{25, 25 - n, tempa, tempa - n, tempb, tempb - n, tempc, tempc - n});
                                }
                            }
                        }
                    }
                }
            }
            tempa = 100;
            tempb = 100;
            for (int i = 0; i < twobreak; i++) {
                if (two.get(i) != tempa) {
                    tempa = two.get(i);
                    tempb = 100;
                    if (one.contains(tempa - n)) {
                        if (Collections.frequency(one, tempa - n) == 1) {
                            this.movelist.add(new int[]{25, 25 - n, tempa, tempa - n, tempa - n, tempa - n - n, tempa - n, tempa - n - n});
                        }
                        continue;
                    }
                    skipped = false;
                    for (int j = 0; j < onebreak; j++) {
                        if ((one.get(j) == tempa) && (!skipped)) {
                            skipped = true;
                        } else if (one.get(j) != tempb) {
                            tempb = one.get(j);
                            this.movelist.add(new int[]{25, 25 - n, tempa, tempa - n, tempa - n, tempa - n - n, tempb, tempb - n});
                        }
                    }
                }
            }
            tempa = 100;
            for (int i = 0; i < threebreak; i++) {
                if (three.get(i) != tempa) {
                    tempa = three.get(i);
                    if ((one.contains(tempa - n - n)) || (two.contains(tempa - n))) {
                        continue;
                    }
                    this.movelist.add(new int[]{25, 25 - n, tempa, tempa - n, tempa - n, tempa - n - n, tempa - n - n, tempa - n - n - n});
                }
            }
        } else if ((bar == 0) && (!bearcheck)) {
            int tempa = 100;
            int tempb = 100;
            int tempc = 100;
            int tempd = 100;
            boolean skipped = false;
            for (int i = 0; i < onebreak; i++) {
                if (one.get(i) != tempa) {
                    tempa = one.get(i);
                    tempb = 100;
                    for (int j = i + 1; j < onebreak; j++) {
                        if (one.get(j) != tempb) {
                            tempb = one.get(j);
                            tempc = 100;
                            for (int k = j + 1; k < onebreak; k++) {
                                if (one.get(k) != tempc) {
                                    tempc = one.get(k);
                                    tempd = 100;
                                    for (int l = k + 1; l < onebreak; l++) {
                                        if (one.get(l) != tempd) {
                                            tempd = one.get(l);
                                            desta = tempa - n < 1 ? -1 : tempa - n;
                                            destb = tempb - n < 1 ? -1 : tempb - n;
                                            destc = tempc - n < 1 ? -1 : tempc - n;
                                            destd = tempd + n < 1 ? -1 : tempd - n;
                                            this.movelist.add(new int[]{tempa, desta, tempb, destb, tempc, destc, tempd, destd});
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            tempa = 100;
            tempb = 100;
            tempc = 100;
            for (int i = 0; i < twobreak; i++) {                                                                                               
                if (two.get(i) != tempa) {
                    tempa = two.get(i);
                    tempb = 100;
                    if (one.contains(tempa - n)) {
                        if (Collections.frequency(one, tempa - n) == 2) {
                            this.movelist.add(new int[]{tempa, tempa - n, tempa - n, tempa - n - n, tempa - n, tempa - n - n, tempa - n, tempa - n - n});
                        } else if (Collections.frequency(one, tempa - n) == 1) {
                            for (int l = 0; l < onebreak; l++) {
                                if ((one.get(l) == tempa) && (!skipped)) {
                                    skipped = true;
                                } else if ((one.get(l) != tempa - n) && (one.get(l) != tempb)) {
                                    tempb = one.get(l);
                                    desta = tempa - n - n < 1 ? -1 : tempa - n - n;
                                    destb = tempb - n < 1 ? -1 : tempb - n;
                                    this.movelist.add(new int[]{tempa, tempa - n, tempa - n, desta, tempa - n, desta, tempb, destb});
                                }
                            }
                        }
                        continue;
                    }
                    skipped = false;
                    for (int j = 0; j < onebreak; j++) {
                        if ((one.get(j) == tempa) && (!skipped)) {
                            skipped = true;
                        } else if (one.get(j) != tempb) {
                            tempb = one.get(j);
                            tempc = 100;
                            for (int k = j + 1; k < onebreak; k++) {
                                if (one.get(k) != tempc) {
                                    tempc = one.get(k);
                                    desta = tempa - n - n < 1 ? -1 : tempa - n - n;
                                    destb = tempb - n < 1 ? -1 : tempb - n;
                                    destc = tempc - n < 1 ? -1 : tempc - n;
                                    this.movelist.add(new int[]{tempa, tempa - n, tempa - n, desta, tempb, destb, tempc, destc});
                                }
                            }
                        }
                    }
                }
            }
            tempa = 100;
            tempb = 100;
            for (int i = 0; i < twobreak; i++) {
                if (two.get(i) != tempa) {
                    tempa = two.get(i);
                    tempb = 100;
                    if (one.contains(tempa - n)) {
                        continue;
                    }
                    for (int j = i + 1; j < twobreak; j++) {
                        if (two.get(j) != tempb) {
                            tempb = two.get(j);
                            if (one.contains(tempb - n)) {
                                continue;
                            }
                            desta = tempa - n - n < 1 ? -1 : tempa - n - n;
                            destb = tempb - n - n < 1 ? -1 : tempb - n - n;
                            this.movelist.add(new int[]{tempa, tempa - n, tempa - n, desta, tempb, tempb - n, tempb - n, destb});
                        }
                    }
                }
            }
            tempa = 100;
            tempb = 100;
            for (int i = 0; i < threebreak; i++) {
                if (three.get(i) != tempa) {
                    tempa = three.get(i);
                    tempb = 100;
                    if (two.contains(tempa - n)) {
                        continue;
                    } else if (one.contains(tempa - n - n)) {
                        if (Collections.frequency(one, tempa - n - n) == 1) {
                            desta = tempa - n - n - n < 1 ? -1 : tempa - n - n - n;
                            this.movelist.add(new int[]{tempa, tempa - n, tempa - n, tempa - n - n, tempa - n - n, desta, tempa - n - n, desta});
                        }
                        continue;
                    }
                    skipped = false;
                    for (int j = 0; j < onebreak; j++) {
                        if ((one.get(j) == tempa) && (!skipped)) {
                            skipped = true;
                        } else if (one.get(j) != tempb) {
                            tempb = one.get(j);
                            desta = tempa - n - n - n < 1 ? -1 : tempa - n - n - n;
                            destb = tempb - n < 1 ? -1 : tempb - n;
                            this.movelist.add(new int[]{tempa, tempa - n, tempa - n, tempa - n - n, tempa - n - n, desta, tempb, destb});
                        }
                    }
                }
            }
            tempa = 100;
            for (int i = 0; i < fourbreak; i++) {
                if (four.get(i) != tempa) {
                    tempa = four.get(i);
                    if ((one.contains(tempa - n - n - n)) || (two.contains(tempa - n - n)) || (three.contains(tempa - n))) {
                        continue;
                    }
                    desta = tempa - n - n - n - n < 1 ? -1 : tempa - n - n - n - n;
                    this.movelist.add(new int[]{tempa, tempa - n, tempa - n, tempa - n - n, tempa - n - n, tempa - n - n - n, tempa - n - n - n, desta});
                }
            }
        } else if (bar == 0) {
            int tempa = 100;
            int tempb = 100;
            int tempc = 100;
            int tempd = 100;
            boolean skipped = false;
            for (int i = 0; i < onebreak; i++) {
                if (one.get(i) != tempa) {
                    ArrayList<Integer> moveda = copier(bears);
                    tempa = one.get(i);
                    tempb = 100;
                    if (tempa - n < 7) {
                        moveda.remove(Integer.valueOf(tempa));
                    }
                    int limita = moveda.isEmpty() ? one.size() : onebreak;
                    for (int j = i + 1; j < limita; j++) {
                        if (one.get(j) != tempb) {
                            ArrayList<Integer> movedb = copier(moveda);
                            tempb = one.get(j);
                            tempc = 100;
                            if (tempb - n < 7) {
                                movedb.remove(Integer.valueOf(tempb));
                            }
                            int limitb = movedb.isEmpty() ? one.size() : onebreak;
                            for (int k = j + 1; k < limitb; k++) {
                                if (one.get(k) != tempc) {
                                    ArrayList<Integer> movedc = copier(movedb);
                                    tempc = one.get(k);
                                    tempd = 100;
                                    if (tempc - n < 7) {
                                        movedc.remove(Integer.valueOf(tempc));
                                    }
                                    int limitc = movedc.isEmpty() ? one.size() : onebreak;
                                    for (int l = k + 1; l < limitc; l++) {
                                        if (one.get(l) != tempd) {
                                            tempd = one.get(l);
                                            destb = tempb - n < 1 ? -1 : tempb - n;
                                            destc = tempc - n < 1 ? -1 : tempc - n;
                                            destd = tempd - n < 1 ? -1 : tempd - n;
                                            this.movelist.add(new int[]{tempa, tempa - n, tempb, destb, tempc, destc, tempd, destd});
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            tempa = 100;
            tempb = 100;
            tempc = 100;
            for (int i = 0; i < twobreak; i++) {
                if (two.get(i) != tempa) {
                    ArrayList<Integer> moveda = copier(bears);
                    tempa = two.get(i);
                    tempb = 100;
                    if (one.contains(tempa - n)) {
                        if (Collections.frequency(one, tempa - n) == 2) {
                            this.movelist.add(new int[]{tempa, tempa - n, tempa - n, tempa - n - n, tempa - n, tempa - n - n, tempa - n, tempa - n - n});
                        } else if (Collections.frequency(one, tempa - n) == 1) {
                            ArrayList<Integer> altmoveda = copier(bears);
                            if (tempa - n - n < 7) {
                                altmoveda.remove(Integer.valueOf(tempa));
                                altmoveda.remove(Integer.valueOf(tempa - n));
                            }
                            int limit = altmoveda.isEmpty() ? one.size() : onebreak;
                            skipped = false;
                            for (int l = 0; l < limit; l++) {
                                if ((one.get(l) == tempa) && (!skipped)) {
                                    skipped = true;
                                } else if (one.get(l) != tempa - n) {                                                                       
                                    tempb = one.get(l);
                                    destb = tempb - n < 1 ? -1 : tempb - n;
                                    this.movelist.add(new int[]{tempa, tempa - n, tempa - n, tempa - n - n, tempa - n, tempa - n - n, tempb, destb});
                                }
                            }
                        }
                        continue;
                    }
                    if (tempa - n - n < 7) {
                        moveda.remove(Integer.valueOf(tempa));
                    }
                    int limita = moveda.isEmpty() ? one.size() : onebreak;
                    skipped = false;
                    for (int j = 0; j < limita; j++) {
                        if ((one.get(j) == tempa) && (!skipped)) {
                            skipped = true;
                        } else if (one.get(j) != tempb) {
                            ArrayList<Integer> movedb = copier(moveda);
                            tempb = one.get(j);
                            tempc = 100;
                            if (tempb - n < 7) {
                                movedb.remove(Integer.valueOf(tempb));
                            }
                            int limitb = movedb.isEmpty() ? one.size() : onebreak;
                            for (int k = j + 1; k < limitb; k++) {
                                if (one.get(k) != tempc) {
                                    tempc = one.get(k);
                                    destb = tempb - n < 1 ? -1 : tempb - n;
                                    destc = tempc - n < 1 ? -1 : tempc - n;
                                    this.movelist.add(new int[]{tempa, tempa - n, tempa - n, tempa - n - n, tempb, destb, tempc, destc});
                                }
                            }
                        }
                    }
                }
            }
            tempa = 100;
            tempb = 100;
            tempc = 100;
            for (int i = twobreak; i < two.size(); i++) {                                                               
                if (two.get(i) != tempa) {
                    ArrayList<Integer> moveda = copier(bears);
                    tempa = two.get(i);
                    tempb = 100;
                    if (one.contains(tempa - n)) {
                        if (Collections.frequency(one, tempa - n) == 2) {
                            ArrayList<Integer> altmoveda = copier(bears);
                            altmoveda.remove(Integer.valueOf(tempa));
                            altmoveda.remove(Integer.valueOf(tempa - n));
                            altmoveda.remove(Integer.valueOf(tempa - n));
                            if (altmoveda.isEmpty()) {
                                this.movelist.add(new int[]{tempa, tempa - n, tempa - n, -1, tempa - n, -1, tempa - n, -1});
                            }
                        } else if (Collections.frequency(one, tempa - n) == 1) {
                            ArrayList<Integer> altmovedb = copier(bears);
                            altmovedb.remove(Integer.valueOf(tempa));
                            altmovedb.remove(Integer.valueOf(tempa - n));
                            int limit = altmovedb.isEmpty() ? one.size() : onebreak;
                            skipped = false;
                            for (int l = 0; l < limit; l++) {
                                if ((one.get(l) == tempa) && (!skipped)) {
                                    skipped = true;
                                } else if (one.get(l) != tempa - n) {
                                    ArrayList<Integer> altmovedc = copier(altmovedb);
                                    tempb = one.get(l);
                                    if (tempb - n < 7) {
                                        altmovedc.remove(Integer.valueOf(tempb));
                                    }
                                    if (altmovedc.isEmpty()) {
                                        destb = tempb - n < 1 ? -1 : tempb - n;
                                        this.movelist.add(new int[]{tempa, tempa - n, tempa - n, -1, tempa - n, -1, tempb, destb});
                                    }
                                }
                            }
                        }
                        continue;
                    }
                    if (tempa - n - n < 7) {
                        moveda.remove(Integer.valueOf(tempa));
                    }
                    int limita = moveda.isEmpty() ? one.size() : onebreak;
                    skipped = false;
                    for (int j = 0; j < limita; j++) {
                        if ((one.get(j) == tempa) && (!skipped)) {
                            skipped = true;
                        } else if (one.get(j) != tempb) {
                            ArrayList<Integer> movedb = copier(moveda);
                            tempb = one.get(j);
                            tempc = 100;
                            if (tempb - n < 7) {
                                movedb.remove(Integer.valueOf(tempb));
                            }
                            int limitb = movedb.isEmpty() ? one.size() : onebreak;
                            for (int k = j + 1; k < limitb; k++) {
                                if (one.get(k) != tempc) {
                                    ArrayList<Integer> movedc = copier(movedb);
                                    tempc = one.get(k);
                                    if (tempc - n < 7) {
                                        movedc.remove(Integer.valueOf(tempc));
                                    }
                                    if (movedc.isEmpty()) {
                                        desta = tempa - n - n < 1 ? -1 : tempa - n - n;
                                        destb = tempb - n < 1 ? -1 : tempb - n;
                                        destc = tempc - n < 1 ? -1 : tempc - n;
                                        this.movelist.add(new int[]{tempb, destb, tempc, destc, tempa, tempa - n, tempa - n, desta});
                                    }
                                }
                            }
                        }
                    }
                }
            }                                                                                               
            tempa = 100;
            tempb = 100;
            for (int i = 0; i < twobreak; i++) {
                if (two.get(i) != tempa) {
                    ArrayList<Integer> moveda = copier(bears);
                    tempa = two.get(i);
                    tempb = 100;
                    if (one.contains(tempa - n)) {
                        continue;
                    }
                    if (tempa - n - n < 7) {
                        moveda.remove(Integer.valueOf(tempa));
                    }
                    int limita = moveda.isEmpty() ? two.size() : twobreak;
                    for (int j = i + 1; j < limita; j++) {
                        if (two.get(j) != tempb) {
                            tempb = two.get(j);
                            if (one.contains(tempb - n)) {
                                continue;
                            }
                            destb = tempb - n - n < 1 ? -1 : tempb - n - n;
                            this.movelist.add(new int[]{tempa, tempa - n, tempa - n, tempa - n - n, tempb, tempb - n, tempb - n, destb});
                        }
                    }
                }
            }
            tempa = 100;
            tempb = 100;
            for (int i = 0; i < threebreak; i++) {
                if (three.get(i) != tempa) {
                    ArrayList<Integer> moveda = copier(bears);
                    tempa = three.get(i);
                    tempb = 100;
                    if (two.contains(tempa - n)) {
                        continue;
                    } else if (one.contains(tempa - n - n)) {
                        if (Collections.frequency(one, tempa - n - n) == 1) {
                            this.movelist.add(new int[]{tempa, tempa - n, tempa - n, tempa - n - n, tempa - n - n, tempa - n - n - n, tempa - n - n, tempa - n - n - n});
                        }
                        continue;
                    }
                    if (tempa - n - n - n < 7) {
                        moveda.remove(Integer.valueOf(tempa));
                    }
                    int limita = moveda.isEmpty() ? one.size() : onebreak;
                    skipped = false;
                    for (int j = 0; j < limita; j++) {
                        if ((one.get(j) == tempa) && (!skipped)) {
                            skipped = true;
                        } else if (one.get(j) != tempb) {
                            tempb = one.get(j);
                            destb = tempb - n < 1 ? -1 : tempb - n;
                            this.movelist.add(new int[]{tempa, tempa - n, tempa - n, tempa - n - n, tempa - n - n, tempa - n - n - n, tempb, destb});
                        }
                    }
                }
            }
            tempa = 100;
            tempb = 100;
            for (int i = threebreak; i < three.size(); i++) {
                if (three.get(i) != tempa) {
                    ArrayList<Integer> moveda = copier(bears);
                    tempa = three.get(i);
                    tempb = 100;
                    if (two.contains(tempa - n)) {
                        continue;
                    } else if (one.contains(tempa - n - n)) {
                        if (Collections.frequency(one, tempa - n - n) == 1) {
                            ArrayList<Integer> altmoveda = copier(bears);
                            altmoveda.remove(Integer.valueOf(tempa));
                            altmoveda.remove(Integer.valueOf(tempa - n - n));
                            if (altmoveda.isEmpty()) {
                                this.movelist.add(new int[]{tempa, tempa - n, tempa - n, tempa - n - n, tempa - n - n, -1, tempa - n - n, -1});
                            }
                        }
                        continue;
                    }
                    if (tempa - n - n - n < 7) {
                        moveda.remove(Integer.valueOf(tempa));
                    }
                    int limita = moveda.isEmpty() ? one.size() : onebreak;
                    skipped = false;
                    for (int j = 0; j < limita; j++) {
                        if ((one.get(j) == tempa) && (!skipped)) {
                            skipped = true;
                        } else if (one.get(j) != tempb) {
                            ArrayList<Integer> movedb = copier(moveda);
                            tempb = one.get(j);
                            if (tempb - n < 7) {
                                movedb.remove(Integer.valueOf(tempb));
                            }
                            if (movedb.isEmpty()) {
                                desta = tempa - n - n - n < 1 ? -1 : tempa - n - n - n;
                                destb = tempb - n < 1 ? -1 : tempb - n;
                                this.movelist.add(new int[]{tempb, destb, tempa, tempa - n, tempa - n, tempa - n - n, tempa - n - n, desta});
                            }
                        }
                    }
                }
            }
            tempa = 100;
            for (int i = 0; i < fourbreak; i++) {
                if (four.get(i) != tempa) {
                    tempa = four.get(i);
                    if ((one.contains(tempa - n - n - n)) || (two.contains(tempa - n - n)) || (three.contains(tempa - n))) {
                        continue;
                    }
                    this.movelist.add(new int[]{tempa, tempa - n, tempa - n, tempa - n - n, tempa - n - n, tempa - n - n - n, tempa - n - n - n, tempa - n - n - n - n});
                }
            }
            tempa = 100;
            for (int i = fourbreak; i < four.size(); i++) {
                if (four.get(i) != tempa) {
                    ArrayList<Integer> moveda = copier(bears);
                    tempa = four.get(i);
                    if ((one.contains(tempa - n - n - n)) || (two.contains(tempa - n - n)) || (three.contains(tempa - n))) {
                        continue;
                    }
                    if (tempa - n - n - n - n < 7) {
                        moveda.remove(Integer.valueOf(tempa));
                    }
                    if (moveda.isEmpty()) {
                        this.movelist.add(new int[]{tempa, tempa - n, tempa - n, tempa - n - n, tempa - n - n, tempa - n - n - n, tempa - n - n - n, -1});
                    }
                }
            }
        }
        if (this.movelist.isEmpty()) {
            if (bar == 3) {                                                                         
                this.movelist.add(new int[]{25, 25-n, 25, 25-n, 25, 25-n});
            } else if (bar == 2) {
                int tempa = 100;
                for (int i = 0; i < onebreak; i++) {
                    if (one.get(i) != tempa) {
                        tempa = one.get(i);
                        this.movelist.add(new int[]{25, 25-n, 25, 25-n, tempa, tempa - n});
                    }
                }
            } else if (bar == 1) {
                int tempa = 100;
                int tempb = 100;
                for (int i = 0; i < onebreak; i++) {
                    if (one.get(i) != tempa) {
                        tempa = one.get(i);
                        tempb = 100;
                        for (int j = i + 1; j < onebreak; j++) {
                            if (one.get(j) != tempb) {
                                tempb = one.get(j);
                                this.movelist.add(new int[]{25, 25-n, tempa, tempa - n, tempb, tempb - n});
                            }
                        }
                    }
                }
                tempa = 100;
                for (int i = 0; i < twobreak; i++) {
                    if (two.get(i) != tempa) {
                        tempa = two.get(i);
                        if (one.contains(tempa - n)) {
                            continue;
                        }
                        this.movelist.add(new int[]{25, 25-n, tempa, tempa - n, tempa - n, tempa - n - n});
                    }
                }
            } else if ((bar == 0) && (!bearcheck)) {
                int tempa = 100;
                int tempb = 100;
                int tempc = 100;
                boolean skipped = false;
                for (int i = 0; i < onebreak; i++) {
                    if (one.get(i) != tempa) {
                        tempa = one.get(i);
                        tempb = 100;
                        for (int j = i + 1; j < onebreak; j++) {
                            if (one.get(j) != tempb) {
                                tempb = one.get(j);
                                tempc = 100;
                                for (int k = j + 1; k < onebreak; k++) {
                                    if (one.get(k) != tempc) {
                                        tempc = one.get(k);
                                        desta = tempa - n < 1 ? -1 : tempa - n;
                                        destb = tempb - n < 1 ? -1 : tempb - n;
                                        destc = tempc - n < 1 ? -1 : tempc - n;
                                        this.movelist.add(new int[]{tempa, desta, tempb, destb, tempc, destc});
                                    }
                                }
                            }
                        }
                    }
                }
                tempa = 100;
                tempb = 100;
                for (int i = 0; i < twobreak; i++) {
                    if (two.get(i) != tempa) {
                        tempa = two.get(i);
                        tempb = 100;
                        if (one.contains(tempa - n)) {
                            if (Collections.frequency(one, tempa - n) == 1) {
                                desta = tempa - n - n < 1 ? -1 : tempa - n - n;
                                this.movelist.add(new int[]{tempa, tempa - n, tempa - n, desta, tempa - n, desta});           ////¡!!!!¡!!!!¡!!!!!!
                            }
                            continue;
                        }
                        skipped = false;
                        for (int j = 0; j < onebreak; j++) {
                            if ((one.get(j) == tempa) && (!skipped)) {
                                skipped = true;
                            } else if (one.get(j) != tempb) {
                                tempb = one.get(j);
                                desta = tempa - n - n < 1 ? -1 : tempa - n - n;
                                destb = tempb - n < 1 ? -1 : tempb - n;
                                this.movelist.add(new int[]{tempa, tempa - n, tempa - n, desta, tempb, destb});
                            }
                        }
                    }
                }
                tempa = 100;
                for (int i = 0; i < threebreak; i++) {
                    if (three.get(i) != tempa) {
                        tempa = three.get(i);
                        if ((one.contains(tempa - n - n)) || (two.contains(tempa - n))) {
                            continue;
                        }
                        desta = tempa - n - n - n < 1 ? -1 : tempa - n - n - n;
                        this.movelist.add(new int[]{tempa, tempa - n, tempa - n, tempa - n - n, tempa - n - n, desta});
                    }
                }
            } else if (bar == 0) {
                int tempa = 100;
                int tempb = 100;
                int tempc = 100;
                boolean skipped = false;
                for (int i = 0; i < onebreak; i++) {
                    if (one.get(i) != tempa) {
                        ArrayList<Integer> moveda = copier(bears);
                        tempa = one.get(i);
                        tempb = 100;
                        if (tempa - n < 7) {
                            moveda.remove(Integer.valueOf(tempa));
                        }
                        int limita = moveda.isEmpty() ? one.size() : onebreak;
                        for (int j = i + 1; j < limita; j++) {
                            if (one.get(j) != tempb) {
                                ArrayList<Integer> movedb = copier(moveda);
                                tempb = one.get(j);
                                tempc = 100;
                                if (tempb - n < 7) {
                                    movedb.remove(Integer.valueOf(tempb));
                                }
                                int limitb = movedb.isEmpty() ? one.size() : onebreak;
                                for (int k = j + 1; k < limitb; k++) {
                                    if (one.get(k) != tempc) {
                                        tempc = one.get(k);
                                        destb = tempb - n < 1 ? -1 : tempb - n;
                                        destc = tempc - n < 1 ? -1 : tempc - n;
                                        this.movelist.add(new int[]{tempa, tempa - n, tempb, destb, tempc, destc});
                                    }
                                }
                            }
                        }
                    }
                }
                tempa = 100;
                tempb = 100;
                for (int i = 0; i < twobreak; i++) {
                    if (two.get(i) != tempa) {
                        ArrayList<Integer> moveda = copier(bears);
                        tempa = two.get(i);
                        tempb = 100;
                        if (one.contains(tempa - n)) {
                            if (Collections.frequency(one, tempa - n) == 1) {
                                this.movelist.add(new int[]{tempa, tempa - n, tempa - n, tempa - n - n, tempa - n, tempa - n - n});
                            }
                            continue;
                        }
                        if (tempa - n - n < 7) {
                            moveda.remove(Integer.valueOf(tempa));
                        }
                        int limita = moveda.isEmpty() ? one.size() : onebreak;
                        skipped = false;
                        for (int j = 0; j < limita; j++) {
                            if ((one.get(j) == tempa) && (!skipped)) {
                                skipped = true;
                            } else if (one.get(j) != tempb) {
                                tempb = one.get(j);
                                destb = tempb - n < 1 ? -1 : tempb - n;
                                this.movelist.add(new int[]{tempa, tempa - n, tempa - n, tempa - n - n, tempb, destb});
                            }
                        }
                    }
                }
                tempa = 100;
                tempb = 100;
                for (int i = twobreak; i < two.size(); i++) {                                                               //unsure about this part!!!!!! DOUBLE CHECK!!!  SECOND SKIPPED
                    if (two.get(i) != tempa) {
                        ArrayList<Integer> moveda = copier(bears);
                        tempa = two.get(i);
                        tempb = 100;
                        if (one.contains(tempa - n)) {
                            if (Collections.frequency(one, tempa - n) == 1) {
                                ArrayList<Integer> altmoveda = copier(bears);
                                altmoveda.remove(Integer.valueOf(tempa));
                                altmoveda.remove(Integer.valueOf(tempa - n));
                                if (altmoveda.isEmpty()) {
                                    this.movelist.add(new int[]{tempa, tempa - n, tempa - n, -1, tempa - n, -1});       
                                }
                            }
                            continue;
                        }
                        if (tempa - n - n < 7) {
                            moveda.remove(Integer.valueOf(tempa));
                        }
                        int limita = moveda.isEmpty() ? one.size() : onebreak;
                        skipped = false;
                        for (int j = 0; j < limita; j++) {
                            if ((one.get(j) == tempa) && (!skipped)) {
                                skipped = true;
                            } else if (one.get(j) != tempb) {
                                ArrayList<Integer> movedb = copier(moveda);
                                tempb = one.get(j);
                                if (tempb - n < 7) {
                                    movedb.remove(Integer.valueOf(tempb));
                                }
                                if (movedb.isEmpty()) {
                                    desta = tempa - n - n < 1 ? -1 : tempa - n - n;
                                    destb = tempb - n < 1 ? -1 : tempb - n;
                                    this.movelist.add(new int[]{tempb, destb, tempa, tempa - n, tempa - n, desta});
                                }
                            }
                        }
                    }
                }
                tempa = 100;
                for (int i = 0; i < threebreak; i++) {
                    if (three.get(i) != tempa) {
                        tempa = three.get(i);
                        if ((one.contains(tempa - n - n)) || (two.contains(tempa - n))) {
                            continue;
                        }
                        this.movelist.add(new int[]{tempa, tempa - n, tempa - n, tempa - n - n, tempa - n - n, tempa - n - n - n});
                    }
                }
                tempa = 100;
                for (int i = threebreak; i < three.size(); i++) {
                    if (three.get(i) != tempa) {
                        ArrayList<Integer> moveda = copier(bears);
                        tempa = three.get(i);
                        if ((one.contains(tempa - n - n)) || (two.contains(tempa - n))) {
                            continue;
                        }
                        if (tempa - n - n - n < 7) {
                            moveda.remove(Integer.valueOf(tempa));
                        }
                        if (moveda.isEmpty()) {
                            desta = tempa - n - n - n < 1 ? -1 : tempa - n - n - n;
                            this.movelist.add(new int[]{tempa, tempa - n, tempa - n, tempa - n - n, tempa - n - n, desta});
                        }
                    }
                }
            }
        }
        if (this.movelist.isEmpty()) {
            if (bar > 1) {
                this.movelist.add(new int[]{25, 25-n, 25, 25-n});
            } else if (bar == 1) {
                int tempa = 100;
                for (int i = 0; i < onebreak; i++) {
                    if (one.get(i) != tempa) {
                        tempa = one.get(i);
                        this.movelist.add(new int[]{25, 25-n, tempa, tempa - n});
                    }
                }
            } else if ((bar == 0) && (!bearcheck)) {
                int tempa = 100;
                int tempb = 100;
                for (int i = 0; i < onebreak; i++) {
                    if (one.get(i) != tempa) {
                        tempa = one.get(i);
                        tempb = 100;
                        for (int j = i + 1; j < onebreak; j++) {
                            if (one.get(j) != tempb) {
                                tempb = one.get(j);
                                desta = tempa - n < 1 ? -1 : tempa - n;
                                destb = tempb - n < 1 ? -1 : tempb - n;
                                this.movelist.add(new int[]{tempa, desta, tempb, destb});
                            }
                        }
                    }
                }
                tempa = 100;
                for (int i = 0; i < twobreak; i++) {
                    if (two.get(i) != tempa) {
                        tempa = two.get(i);
                        if (one.contains(tempa - n)) {
                            continue;
                        }
                        desta = tempa - n - n < 1 ? -1 : tempa - n - n;
                        this.movelist.add(new int[]{tempa, tempa - n, tempa - n, desta});
                    }
                }
            } else if (bar == 0) {
                int tempa = 100;
                int tempb = 100;
                for (int i = 0; i < onebreak; i++) {
                    if (one.get(i) != tempa) {
                        ArrayList<Integer> moveda = copier(bears);
                        tempa = one.get(i);
                        tempb = 100;
                        if (tempa - n < 7) {
                            moveda.remove(Integer.valueOf(tempa));
                        }
                        int limita = moveda.isEmpty() ? one.size() : onebreak;
                        for (int j = i + 1; j < limita; j++) {
                            if (one.get(j) != tempb) {
                                tempb = one.get(j);
                                destb = tempb - n < 1 ? -1 : tempb - n;
                                this.movelist.add(new int[]{tempa, tempa - n, tempb, destb});
                            }
                        }
                    }
                }
                tempa = 100;
                for (int i = 0; i < twobreak; i++) {
                    if (two.get(i) != tempa) {
                        tempa = two.get(i);
                        if (one.contains(tempa - n)) {
                            continue;
                        }
                        this.movelist.add(new int[]{tempa, tempa - n, tempa - n, tempa - n - n});
                    }
                }
                tempa = 100;
                for (int i = twobreak; i < two.size(); i++) {
                    if (two.get(i) != tempa) {
                        ArrayList<Integer> moveda = copier(bears);
                        tempa = two.get(i);
                        if (one.contains(tempa - n)) {
                            continue;
                        }
                        if (tempa - n - n < 7) {
                            moveda.remove(Integer.valueOf(tempa));
                        }
                        if (moveda.isEmpty()) {
                            desta = tempa - n - n < 1 ? -1 : tempa - n - n;
                            this.movelist.add(new int[]{tempa, tempa - n, tempa - n, desta});
                        }
                    }
                }
            }
        }
        if (this.movelist.isEmpty()) {
            if (bar > 0) {
                this.movelist.add(new int[]{25, 25-n});
            } else if ((bar == 0) && (!bearcheck)) {
                int tempa = 100;
                for (int i = 0; i < onebreak; i++) {
                    if (one.get(i) != tempa) {
                        tempa = one.get(i);
                        desta = tempa - n < 1 ? -1 : tempa - n;
                        this.movelist.add(new int[]{tempa, desta});
                    }
                }
            } else if (bar == 0) {
                int tempa = 100;
                for (int i = 0; i < onebreak; i++) {
                    if (one.get(i) != tempa) {
                        tempa = one.get(i);
                        this.movelist.add(new int[]{tempa, tempa - n});
                    }
                }
                tempa = 100;
                for (int i = onebreak; i < one.size(); i++) {
                    if (one.get(i) != tempa) {
                        ArrayList<Integer> moveda = copier(bears);
                        tempa = one.get(i);
                        if (tempa - n < 7) {
                            moveda.remove(Integer.valueOf(tempa));
                        }
                        if (moveda.isEmpty()) {
                            desta = tempa - n < 1 ? -1 : tempa - n;
                            this.movelist.add(new int[]{tempa, desta});
                        }
                    }
                }
            }
        }
        //doublesorter(false);
    }                                                         //Check this again on test cases

    public void doublemovesb(Board b, Roll r) {
        int bar = b.board[0];
        int n = r.steps[0];
        int onebreak = 500;
        int twobreak = 500;
        int threebreak = 500;
        int fourbreak = 500;
        int desta = 0;
        int destb = 0;
        int destc = 0;
        int destd = 0;
        boolean bear = false;
        boolean bearcheck = false;
        if ((bar < 0) && (b.board[n] > 1)) {
            return;
        } else if (bar < -3) {
            this.movelist.add(new int[]{0, n, 0, n, 0, n, 0, n});
            return;
        }
        ArrayList<Integer> one = new ArrayList<>();
        ArrayList<Integer> two = new ArrayList<>();
        ArrayList<Integer> three = new ArrayList<>();
        ArrayList<Integer> four = new ArrayList<>();
        ArrayList<Integer> bears = new ArrayList<>();
        if ((b.board[0]) == -1) {
            if (b.board[n + n] < 2) {
                if (b.board[n + n + n] < 2) {
                    if (b.board[n + n + n + n] < 2) {
                        three.add(n);
                    }
                    two.add(n);
                }
                one.add(n);
            }
            bears.add(0);
        }
        if ((b.board[0]) == -2) {
            if (b.board[n + n] < 2) {
                if (b.board[n + n + n] < 2) {
                    two.add(n);
                }
                one.add(n);
                one.add(n);
            }
            bears.add(0);
            bears.add(0);
        }
        if ((b.board[0]) == -3) {
            if (b.board[n + n] < 2) {
                one.add(n);
            }
            bears.add(0);
            bears.add(0);
            bears.add(0);
        }
        for (int i = 1; i < 25; i++) {
            if (b.board[i] == -1) {
                if ((i + n < 25) && (b.board[i + n] < 2)) {
                    if ((i + n + n < 25) && (b.board[i + n + n] < 2)) {
                        if ((i + n + n + n < 25) && (b.board[i + n + n + n] < 2)) {
                            if ((i + n + n + n + n < 25) && (b.board[i + n + n + n + n] < 2)) {
                                four.add(i);
                            }
                            three.add(i);
                        }
                        two.add(i);
                    }
                    one.add(i);
                }
                if (i < 19) {
                    bears.add(i);
                }
            }
            if (b.board[i] == -2) {
                if ((i + n < 25) && (b.board[i + n] < 2)) {
                    if ((i + n + n < 25) && (b.board[i + n + n] < 2)) {
                        if ((i + n + n + n < 25) && (b.board[i + n + n + n] < 2)) {
                            if ((i + n + n + n + n < 25) && (b.board[i + n + n + n + n] < 2)) {
                                four.add(i);
                            }
                            three.add(i);
                        }
                        two.add(i);
                        two.add(i);
                    }
                    one.add(i);
                    one.add(i);
                }
                if (i < 19) {
                    bears.add(i);
                    bears.add(i);
                }
            }
            if (b.board[i] == -3) {
                if ((i + n < 25) && (b.board[i + n] < 2)) {
                    if ((i + n + n < 25) && (b.board[i + n + n] < 2)) {
                        if ((i + n + n + n < 25) && (b.board[i + n + n + n] < 2)) {
                            if ((i + n + n + n + n < 25) && (b.board[i + n + n + n + n] < 2)) {
                                four.add(i);
                            }
                            three.add(i);
                        }
                        two.add(i);
                        two.add(i);
                    }
                    one.add(i);
                    one.add(i);
                    one.add(i);
                }
                if (i < 19) {
                    bears.add(i);
                    bears.add(i);
                    bears.add(i);
                }
            }
            if (b.board[i] < -3) {
                if ((i + n < 25) && (b.board[i + n] < 2)) {
                    if ((i + n + n < 25) && (b.board[i + n + n] < 2)) {
                        if ((i + n + n + n < 25) && (b.board[i + n + n + n] < 2)) {
                            if ((i + n + n + n + n < 25) && (b.board[i + n + n + n + n] < 2)) {
                                four.add(i);
                            }
                            three.add(i);
                        }
                        two.add(i);
                        two.add(i);
                    }
                    one.add(i);
                    one.add(i);
                    one.add(i);
                    one.add(i);
                }
                if (i < 19) {
                    bears.add(i);
                    bears.add(i);
                    bears.add(i);
                    bears.add(i);
                }
            }
        }
        Collections.sort(one);
        Collections.sort(two);
        Collections.sort(three);
        Collections.sort(four);
        //System.out.println("One contains " + Arrays.toString(one.toArray()));
        //System.out.println("Two contains " + Arrays.toString(two.toArray()));
        //System.out.println("Three contains " + Arrays.toString(three.toArray()));
        //System.out.println("Four contains " + Arrays.toString(four.toArray()));
        //System.out.println("Bears contains " + Arrays.toString(bears.toArray()));
        if (bears.isEmpty()) {
            bear = true;
        } else if (bears.size() < 4) {
            bearcheck = true;
            onebreak = one.size();
            twobreak = two.size();
            threebreak = three.size();
            fourbreak = four.size();
        }
        if (((bears.isEmpty()) || (bears.size() < 4)) && (b.board[0] == 0)) {
            for (int i = 25 - n - n - n - n; i < 25; i++) {
                if (b.board[i] == -1) {
                    if (i + n > 24) {
                        one.add(i);
                    } else if ((b.board[i + n] < 2) && (i + n + n > 24)) {
                        two.add(i);
                    } else if ((b.board[i + n] < 2) && (b.board[i + n + n] < 2) && (i + n + n + n > 24)) {
                        three.add(i);
                    } else if ((b.board[i + n] < 2) && (b.board[i + n + n] < 2) && (b.board[i + n + n + n] < 2) && (i + n + n + n + n> 24)) {
                        four.add(i);
                    }
                }
                if (b.board[i] == -2) {
                    if (i + n > 24) {
                        one.add(i);
                        one.add(i);
                    } else if ((b.board[i + n] < 2) && (i + n + n > 24)) {
                        two.add(i);
                        two.add(i);
                    } else if ((b.board[i + n] < 2) && (b.board[i + n + n] < 2) && (i + n + n + n > 24)) {
                        three.add(i);
                    } else if ((b.board[i + n] < 2) && (b.board[i + n + n] < 2) && (b.board[i + n + n + n] < 2) && (i + n + n + n + n > 24)) {
                        four.add(i);
                    }
                }
                if (b.board[i] == -3) {
                    if (i + n > 24) {
                        one.add(i);
                        one.add(i);
                        one.add(i);
                    } else if ((b.board[i + n] < 2) && (i + n + n > 24)) {
                        two.add(i);
                        two.add(i);
                    } else if ((b.board[i + n] < 2) && (b.board[i + n + n] < 2) && (i + n + n + n > 24)) {
                        three.add(i);
                    } else if ((b.board[i + n] < 2) && (b.board[i + n + n] < 2) && (b.board[i + n + n + n] < 2) && (i + n + n + n + n > 24)) {
                        four.add(i);
                    }
                }
                if (b.board[i] < -3) {
                    if (i + n > 24) {
                        one.add(i);
                        one.add(i);
                        one.add(i);
                        one.add(i);
                    } else if ((b.board[i + n] < 2) && (i + n + n > 24)) {
                        two.add(i);
                        two.add(i);
                    } else if ((b.board[i + n] < 2) && (b.board[i + n + n] < 2) && (i + n + n + n > 24)) {
                        three.add(i);
                    } else if ((b.board[i + n] < 2) && (b.board[i + n + n] < 2) && (b.board[i + n + n + n] < 2) && (i + n + n + n + n > 24)) {
                        four.add(i);
                    }
                }
            }
        }
        if (onebreak == 500) {
            onebreak = one.size();
        }
        if (twobreak == 500) {
            twobreak = two.size();
        }
        if (threebreak == 500) {
            threebreak = three.size();
        }
        if (fourbreak == 500) {
            fourbreak = four.size();
        }
        //System.out.println("onebreak is " + onebreak);
        //System.out.println("twobreak is " + twobreak);
        //System.out.println("threebreak is " + threebreak);
        //System.out.println("fourbreak is " + fourbreak);
        //System.out.println("bar is " + bar);
        if (bar == -3) {
            int tempa = 100;
            for (int i = 0; i < onebreak; i++) {
                if (one.get(i) != tempa) {
                    tempa = one.get(i);
                    this.movelist.add(new int[]{0, n, 0, n, 0, n, tempa, tempa + n});
                }
            }
        } else if (bar == -2) {
            int tempa = 100;
            int tempb = 100;
            for (int i = 0; i < onebreak; i++) {
                if (one.get(i) != tempa) {
                    tempa = one.get(i);
                    tempb = 100;
                    for (int j = i + 1; j < onebreak; j++) {
                        if (one.get(j) != tempb) {
                            tempb = one.get(j);
                            this.movelist.add(new int[]{0, n, 0, n, tempa, tempa + n, tempb, tempb + n});
                        }
                    }
                }
            }
            tempa = 100;
            for (int i = 0; i < twobreak; i++) {
                if (two.get(i) != tempa) {
                    tempa = two.get(i);
                    if (one.contains(tempa + n)) {
                        continue;
                    }
                    this.movelist.add(new int[]{0, n, 0, n, tempa, tempa + n, tempa + n, tempa + n + n});
                }
            }
        } else if (bar == -1) {
            int tempa = 100;
            int tempb = 100;
            int tempc = 100;
            boolean skipped = false;
            for (int i = 0; i < onebreak; i++) {
                if (one.get(i) != tempa) {
                    tempa = one.get(i);
                    tempb = 100;
                    for (int j = i + 1; j < onebreak; j++) {
                        if (one.get(j) != tempb) {
                            tempb = one.get(j);
                            tempc = 100;
                            for (int k = j + 1; k < onebreak; k++) {
                                if (one.get(k) != tempc) {
                                    tempc = one.get(k);
                                    this.movelist.add(new int[]{0, n, tempa, tempa + n, tempb, tempb + n, tempc, tempc + n});
                                }
                            }
                        }
                    }
                }
            }
            tempa = 100;
            tempb = 100;
            for (int i = 0; i < twobreak; i++) {
                if (two.get(i) != tempa) {
                    tempa = two.get(i);
                    tempb = 100;
                    if (one.contains(tempa + n)) {
                        if (Collections.frequency(one, tempa + n) == 1) {
                            this.movelist.add(new int[]{0, n, tempa, tempa + n, tempa + n, tempa + n + n, tempa + n, tempa + n + n});
                        }
                        continue;
                    }
                    skipped = false;
                    for (int j = 0; j < onebreak; j++) {
                        if ((one.get(j) == tempa) && (!skipped)) {
                            skipped = true;
                        } else if (one.get(j) != tempb) {
                            tempb = one.get(j);
                            this.movelist.add(new int[]{0, n, tempa, tempa + n, tempa + n, tempa + n + n, tempb, tempb + n});
                        }
                    }
                }
            }
            tempa = 100;
            for (int i = 0; i < threebreak; i++) {
                if (three.get(i) != tempa) {
                    tempa = three.get(i);
                    if ((one.contains(tempa + n + n)) || (two.contains(tempa + n))) {
                        continue;
                    }
                    this.movelist.add(new int[]{0, n, tempa, tempa + n, tempa + n, tempa + n + n, tempa + n + n, tempa + n + n + n});
                }
            }
        } else if ((bar == 0) && (!bearcheck)) {
            int tempa = 100;
            int tempb = 100;
            int tempc = 100;
            int tempd = 100;
            boolean skipped = false;
            for (int i = 0; i < onebreak; i++) {
                if (one.get(i) != tempa) {
                    tempa = one.get(i);
                    tempb = 100;
                    for (int j = i + 1; j < onebreak; j++) {
                        if (one.get(j) != tempb) {
                            tempb = one.get(j);
                            tempc = 100;
                            for (int k = j + 1; k < onebreak; k++) {
                                if (one.get(k) != tempc) {
                                    tempc = one.get(k);
                                    tempd = 100;
                                    for (int l = k + 1; l < onebreak; l++) {
                                        if (one.get(l) != tempd) {
                                            tempd = one.get(l);
                                            desta = tempa + n > 24 ? 26 : tempa + n;
                                            destb = tempb + n > 24 ? 26 : tempb + n;
                                            destc = tempc + n > 24 ? 26 : tempc + n;
                                            destd = tempd + n > 24 ? 26 : tempd + n;
                                            this.movelist.add(new int[]{tempa, desta, tempb, destb, tempc, destc, tempd, destd});
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            tempa = 100;
            tempb = 100;
            tempc = 100;
            for (int i = 0; i < twobreak; i++) {                                                                                                ///NEW (CHECK)
                if (two.get(i) != tempa) {
                    tempa = two.get(i);
                    tempb = 100;
                    if (one.contains(tempa + n)) {
                        if (Collections.frequency(one, tempa + n) == 2) {
                            this.movelist.add(new int[]{tempa, tempa + n, tempa + n, tempa + n + n, tempa + n, tempa + n + n, tempa + n, tempa + n + n});
                        } else if (Collections.frequency(one, tempa + n) == 1) {
                            for (int l = 0; l < onebreak; l++) {
                                if ((one.get(l) == tempa) && (!skipped)) {
                                    skipped = true;
                                } else if ((one.get(l) != tempa + n) && (one.get(l) != tempb)) {
                                    tempb = one.get(l);
                                    desta = tempa + n + n > 24 ? 26 : tempa + n + n;
                                    destb = tempb + n > 24 ? 26 : tempb + n;
                                    this.movelist.add(new int[]{tempa, tempa + n, tempa + n, desta, tempa + n, desta, tempb, destb});
                                }
                            }
                        }
                        continue;
                    }
                    skipped = false;
                    for (int j = 0; j < onebreak; j++) {
                        if ((one.get(j) == tempa) && (!skipped)) {
                            skipped = true;
                        } else if (one.get(j) != tempb) {
                            tempb = one.get(j);
                            tempc = 100;
                            for (int k = j + 1; k < onebreak; k++) {
                                if (one.get(k) != tempc) {
                                    tempc = one.get(k);
                                    desta = tempa + n + n > 24 ? 26 : tempa + n + n;
                                    destb = tempb + n > 24 ? 26 : tempb + n;
                                    destc = tempc + n > 24 ? 26 : tempc + n;
                                    this.movelist.add(new int[]{tempa, tempa + n, tempa + n, desta, tempb, destb, tempc, destc});
                                }
                            }
                        }
                    }
                }
            }
            tempa = 100;
            tempb = 100;
            for (int i = 0; i < twobreak; i++) {
                if (two.get(i) != tempa) {
                    tempa = two.get(i);
                    tempb = 100;
                    if (one.contains(tempa + n)) {
                        continue;
                    }
                    for (int j = i + 1; j < twobreak; j++) {
                        if (two.get(j) != tempb) {
                            tempb = two.get(j);
                            if (one.contains(tempb + n)) {
                                continue;
                            }
                            desta = tempa + n + n > 24 ? 26 : tempa + n + n;
                            destb = tempb + n + n > 24 ? 26 : tempb + n + n;
                            this.movelist.add(new int[]{tempa, tempa + n, tempa + n, desta, tempb, tempb + n, tempb + n, destb});
                        }
                    }
                }
            }
            tempa = 100;
            tempb = 100;
            for (int i = 0; i < threebreak; i++) {
                if (three.get(i) != tempa) {
                    tempa = three.get(i);
                    tempb = 100;
                    if (two.contains(tempa + n)) {
                        continue;
                    } else if (one.contains(tempa + n + n)) {
                        if (Collections.frequency(one, tempa + n + n) == 1) {
                            desta = tempa + n + n + n > 24 ? 26 : tempa + n + n + n;
                            this.movelist.add(new int[]{tempa, tempa + n, tempa + n, tempa + n + n, tempa + n + n, desta, tempa + n + n, desta});
                        }
                        continue;
                    }
                    skipped = false;
                    for (int j = 0; j < onebreak; j++) {
                        if ((one.get(j) == tempa) && (!skipped)) {
                            skipped = true;
                        } else if (one.get(j) != tempb) {
                            tempb = one.get(j);
                            desta = tempa + n + n + n > 24 ? 26 : tempa + n + n + n;
                            destb = tempb + n > 24 ? 26 : tempb + n;
                            this.movelist.add(new int[]{tempa, tempa + n, tempa + n, tempa + n + n, tempa + n + n, desta, tempb, destb});
                        }
                    }
                }
            }
            tempa = 100;
            for (int i = 0; i < fourbreak; i++) {
                if (four.get(i) != tempa) {
                    tempa = four.get(i);
                    if ((one.contains(tempa + n + n + n)) || (two.contains(tempa + n + n)) || (three.contains(tempa + n))) {
                        continue;
                    }
                    desta = tempa + n + n + n + n > 24 ? 26 : tempa + n + n + n + n;
                    this.movelist.add(new int[]{tempa, tempa + n, tempa + n, tempa + n + n, tempa + n + n, tempa + n + n + n, tempa + n + n + n, desta});
                }
            }
        } else if (bar == 0) {
            int tempa = 100;
            int tempb = 100;
            int tempc = 100;
            int tempd = 100;
            boolean skipped = false;
            for (int i = 0; i < onebreak; i++) {
                if (one.get(i) != tempa) {
                    ArrayList<Integer> moveda = copier(bears);
                    tempa = one.get(i);
                    tempb = 100;
                    if (tempa + n > 18) {
                        moveda.remove(Integer.valueOf(tempa));
                    }
                    int limita = moveda.isEmpty() ? one.size() : onebreak;
                    for (int j = i + 1; j < limita; j++) {
                        if (one.get(j) != tempb) {
                            ArrayList<Integer> movedb = copier(moveda);
                            tempb = one.get(j);
                            tempc = 100;
                            if (tempb + n > 18) {
                                movedb.remove(Integer.valueOf(tempb));
                            }
                            int limitb = movedb.isEmpty() ? one.size() : onebreak;
                            for (int k = j + 1; k < limitb; k++) {
                                if (one.get(k) != tempc) {
                                    ArrayList<Integer> movedc = copier(movedb);
                                    tempc = one.get(k);
                                    tempd = 100;
                                    if (tempc + n > 18) {
                                        movedc.remove(Integer.valueOf(tempc));
                                    }
                                    int limitc = movedc.isEmpty() ? one.size() : onebreak;
                                    for (int l = k + 1; l < limitc; l++) {
                                        if (one.get(l) != tempd) {
                                            tempd = one.get(l);
                                            destb = tempb + n > 24 ? 26 : tempb + n;
                                            destc = tempc + n > 24 ? 26 : tempc + n;
                                            destd = tempd + n > 24 ? 26 : tempd + n;
                                            this.movelist.add(new int[]{tempa, tempa + n, tempb, destb, tempc, destc, tempd, destd});
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            tempa = 100;
            tempb = 100;
            tempc = 100;
            for (int i = 0; i < twobreak; i++) {
                if (two.get(i) != tempa) {
                    ArrayList<Integer> moveda = copier(bears);
                    tempa = two.get(i);
                    tempb = 100;
                    if (one.contains(tempa + n)) {
                        if (Collections.frequency(one, tempa + n) == 2) {
                            this.movelist.add(new int[]{tempa, tempa + n, tempa + n, tempa + n + n, tempa + n, tempa + n + n, tempa + n, tempa + n + n});
                        } else if (Collections.frequency(one, tempa + n) == 1) {
                            ArrayList<Integer> altmoveda = copier(bears);
                            if (tempa + n + n > 18) {
                                altmoveda.remove(Integer.valueOf(tempa));
                                altmoveda.remove(Integer.valueOf(tempa + n));
                            }
                            int limit = altmoveda.isEmpty() ? one.size() : onebreak;
                            skipped = false;
                            for (int l = 0; l < limit; l++) {
                                if ((one.get(l) == tempa) && (!skipped)) {
                                    skipped = true;
                                } else if (one.get(l) != tempa + n) {
                                    tempb = one.get(l);
                                    destb = tempb + n > 24 ? 26 : tempb + n;
                                    this.movelist.add(new int[]{tempa, tempa + n, tempa + n, tempa + n + n, tempa + n, tempa + n + n, tempb, destb});
                                }
                            }
                        }
                        continue;
                    }
                    if (tempa + n + n > 18) {
                        moveda.remove(Integer.valueOf(tempa));
                    }
                    int limita = moveda.isEmpty() ? one.size() : onebreak;
                    skipped = false;
                    for (int j = 0; j < limita; j++) {
                        if ((one.get(j) == tempa) && (!skipped)) {
                            skipped = true;
                        } else if (one.get(j) != tempb) {
                            ArrayList<Integer> movedb = copier(moveda);
                            tempb = one.get(j);
                            tempc = 100;
                            if (tempb + n > 18) {
                                movedb.remove(Integer.valueOf(tempb));
                            }
                            int limitb = movedb.isEmpty() ? one.size() : onebreak;
                            for (int k = j + 1; k < limitb; k++) {
                                if (one.get(k) != tempc) {
                                    tempc = one.get(k);
                                    destb = tempb + n > 24 ? 26 : tempb + n;
                                    destc = tempc + n > 24 ? 26 : tempc + n;
                                    this.movelist.add(new int[]{tempa, tempa + n, tempa + n, tempa + n + n, tempb, destb, tempc, destc});
                                }
                            }
                        }
                    }
                }
            }
            tempa = 100;
            tempb = 100;
            tempc = 100;
            for (int i = twobreak; i < two.size(); i++) {                                                               //unsure about this part!!!!!! DOUBLE CHECK!!!  SECOND SKIPPED
                if (two.get(i) != tempa) {
                    ArrayList<Integer> moveda = copier(bears);
                    tempa = two.get(i);
                    tempb = 100;
                    if (one.contains(tempa + n)) {
                        if (Collections.frequency(one, tempa + n) == 2) {
                            ArrayList<Integer> altmoveda = copier(bears);
                            altmoveda.remove(Integer.valueOf(tempa));
                            altmoveda.remove(Integer.valueOf(tempa + n));
                            altmoveda.remove(Integer.valueOf(tempa + n));
                            if (altmoveda.isEmpty()) {
                                this.movelist.add(new int[]{tempa, tempa + n, tempa + n, 26, tempa + n, 26, tempa + n, 26});
                            }
                        } else if (Collections.frequency(one, tempa + n) == 1) {
                            ArrayList<Integer> altmovedb = copier(bears);
                            altmovedb.remove(Integer.valueOf(tempa));
                            altmovedb.remove(Integer.valueOf(tempa + n));
                            int limit = altmovedb.isEmpty() ? one.size() : onebreak;
                            skipped = false;
                            for (int l = 0; l < limit; l++) {
                                if ((one.get(l) == tempa) && (!skipped)) {
                                    skipped = true;
                                } else if (one.get(l) != tempa + n) {
                                    ArrayList<Integer> altmovedc = copier(altmovedb);
                                    tempb = one.get(l);
                                    if (tempb + n > 18) {
                                        altmovedc.remove(Integer.valueOf(tempb));
                                    }
                                    if (altmovedc.isEmpty()) {
                                        destb = tempb + n > 24 ? 26 : tempb + n;
                                        this.movelist.add(new int[]{tempa, tempa + n, tempa + n, 26, tempa + n, 26, tempb, destb});
                                    }
                                }
                            }
                        }
                        continue;
                    }
                    if (tempa + n + n > 18) {
                        moveda.remove(Integer.valueOf(tempa));
                    }
                    int limita = moveda.isEmpty() ? one.size() : onebreak;
                    skipped = false;
                    for (int j = 0; j < limita; j++) {
                        if ((one.get(j) == tempa) && (!skipped)) {
                            skipped = true;
                        } else if (one.get(j) != tempb) {
                            ArrayList<Integer> movedb = copier(moveda);
                            tempb = one.get(j);
                            tempc = 100;
                            if (tempb + n > 18) {
                                movedb.remove(Integer.valueOf(tempb));
                            }
                            int limitb = movedb.isEmpty() ? one.size() : onebreak;
                            for (int k = j + 1; k < limitb; k++) {
                                if (one.get(k) != tempc) {
                                    ArrayList<Integer> movedc = copier(movedb);
                                    tempc = one.get(k);
                                    if (tempc + n > 18) {
                                        movedc.remove(Integer.valueOf(tempc));
                                    }
                                    if (movedc.isEmpty()) {
                                        desta = tempa + n + n > 24 ? 26 : tempa + n + n;
                                        destb = tempb + n > 24 ? 26 : tempb + n;
                                        destc = tempc + n > 24 ? 26 : tempc + n;
                                        this.movelist.add(new int[]{tempb, destb, tempc, destc, tempa, tempa + n, tempa + n, desta});
                                    }
                                }
                            }
                        }
                    }
                }
            }
            tempa = 100;
            tempb = 100;
            for (int i = 0; i < twobreak; i++) {
                if (two.get(i) != tempa) {
                    ArrayList<Integer> moveda = copier(bears);
                    tempa = two.get(i);
                    tempb = 100;
                    if (one.contains(tempa + n)) {
                        continue;
                    }
                    if (tempa + n + n > 18) {
                        moveda.remove(Integer.valueOf(tempa));
                    }
                    int limita = moveda.isEmpty() ? two.size() : twobreak;
                    for (int j = i + 1; j < limita; j++) {
                        if (two.get(j) != tempb) {
                            tempb = two.get(j);
                            if (one.contains(tempb + n)) {
                                continue;
                            }
                            destb = tempb + n + n > 24 ? 26 : tempb + n + n;
                            this.movelist.add(new int[]{tempa, tempa + n, tempa + n, tempa + n + n, tempb, tempb + n, tempb + n, destb});
                        }
                    }
                }
            }
            tempa = 100;
            tempb = 100;
            for (int i = 0; i < threebreak; i++) {
                if (three.get(i) != tempa) {
                    ArrayList<Integer> moveda = copier(bears);
                    tempa = three.get(i);
                    tempb = 100;
                    if (two.contains(tempa + n)) {
                        continue;
                    } else if (one.contains(tempa + n + n)) {
                        if (Collections.frequency(one, tempa + n + n) == 1) {
                            this.movelist.add(new int[]{tempa, tempa + n, tempa + n, tempa + n + n, tempa + n + n, tempa + n + n + n, tempa + n + n, tempa + n + n + n});
                        }
                        continue;
                    }
                    if (tempa + n + n + n > 18) {
                        moveda.remove(Integer.valueOf(tempa));
                    }
                    int limita = moveda.isEmpty() ? one.size() : onebreak;
                    skipped = false;
                    for (int j = 0; j < limita; j++) {
                        if ((one.get(j) == tempa) && (!skipped)) {
                            skipped = true;
                        } else if (one.get(j) != tempb) {
                            tempb = one.get(j);
                            destb = tempb + n > 24 ? 26 : tempb + n;
                            this.movelist.add(new int[]{tempa, tempa + n, tempa + n, tempa + n + n, tempa + n + n, tempa + n + n + n, tempb, destb});
                        }
                    }
                }
            }
            tempa = 100;
            tempb = 100;
            for (int i = threebreak; i < three.size(); i++) {
                if (three.get(i) != tempa) {
                    ArrayList<Integer> moveda = copier(bears);
                    tempa = three.get(i);
                    tempb = 100;
                    if (two.contains(tempa + n)) {
                        continue;
                    } else if (one.contains(tempa + n + n)) {
                        if (Collections.frequency(one, tempa + n + n) == 1) {
                            ArrayList<Integer> altmoveda = copier(bears);
                            altmoveda.remove(Integer.valueOf(tempa));
                            altmoveda.remove(Integer.valueOf(tempa + n + n));
                            if (altmoveda.isEmpty()) {
                                this.movelist.add(new int[]{tempa, tempa + n, tempa + n, tempa + n + n, tempa + n + n, 26, tempa + n + n, 26});
                            }
                        }
                        continue;
                    }
                    if (tempa + n + n + n > 18) {
                        moveda.remove(Integer.valueOf(tempa));
                    }
                    int limita = moveda.isEmpty() ? one.size() : onebreak;
                    skipped = false;
                    for (int j = 0; j < limita; j++) {
                        if ((one.get(j) == tempa) && (!skipped)) {
                            skipped = true;
                        } else if (one.get(j) != tempb) {
                            ArrayList<Integer> movedb = copier(moveda);
                            tempb = one.get(j);
                            if (tempb + n > 18) {
                                movedb.remove(Integer.valueOf(tempb));
                            }
                            if (movedb.isEmpty()) {
                                desta = tempa + n + n + n > 24 ? 26 : tempa + n + n + n;
                                destb = tempb + n > 24 ? 26 : tempb + n;
                                this.movelist.add(new int[]{tempb, destb, tempa, tempa + n, tempa + n, tempa + n + n, tempa + n + n, desta});
                            }
                        }
                    }
                }
            }
            tempa = 100;
            for (int i = 0; i < fourbreak; i++) {
                if (four.get(i) != tempa) {
                    tempa = four.get(i);
                    if ((one.contains(tempa + n + n + n)) || (two.contains(tempa + n + n)) || (three.contains(tempa + n))) {
                        continue;
                    }
                    this.movelist.add(new int[]{tempa, tempa + n, tempa + n, tempa + n + n, tempa + n + n, tempa + n + n + n, tempa + n + n + n, tempa + n + n + n + n});
                }
            }
            tempa = 100;
            for (int i = fourbreak; i < four.size(); i++) {
                if (four.get(i) != tempa) {
                    ArrayList<Integer> moveda = copier(bears);
                    tempa = four.get(i);
                    if ((one.contains(tempa + n + n + n)) || (two.contains(tempa + n + n)) || (three.contains(tempa + n))) {
                        continue;
                    }
                    if (tempa + n + n + n + n > 18) {
                        moveda.remove(Integer.valueOf(tempa));
                    }
                    if (moveda.isEmpty()) {
                        this.movelist.add(new int[]{tempa, tempa + n, tempa + n, tempa + n + n, tempa + n + n, tempa + n + n + n, tempa + n + n + n, 26});
                    }
                }
            }
        }
        if (this.movelist.isEmpty()) {
            if (bar == -3) {
                this.movelist.add(new int[]{0, n, 0, n, 0, n});
            } else if (bar == -2) {
                int tempa = 100;
                for (int i = 0; i < onebreak; i++) {
                    if (one.get(i) != tempa) {
                        tempa = one.get(i);
                        this.movelist.add(new int[]{0, n, 0, n, tempa, tempa + n});
                    }
                }
            } else if (bar == -1) {
                int tempa = 100;
                int tempb = 100;
                for (int i = 0; i < onebreak; i++) {
                    if (one.get(i) != tempa) {
                        tempa = one.get(i);
                        tempb = 100;
                        for (int j = i + 1; j < onebreak; j++) {
                            if (one.get(j) != tempb) {
                                tempb = one.get(j);
                                this.movelist.add(new int[]{0, n, tempa, tempa + n, tempb, tempb + n});
                            }
                        }
                    }
                }
                tempa = 100;
                for (int i = 0; i < twobreak; i++) {
                    if (two.get(i) != tempa) {
                        tempa = two.get(i);
                        if (one.contains(tempa + n)) {
                            continue;
                        }
                        this.movelist.add(new int[]{0, n, tempa, tempa + n, tempa + n, tempa + n + n});
                    }
                }
            } else if ((bar == 0) && (!bearcheck)) {
                int tempa = 100;
                int tempb = 100;
                int tempc = 100;
                boolean skipped = false;
                for (int i = 0; i < onebreak; i++) {
                    if (one.get(i) != tempa) {
                        tempa = one.get(i);
                        tempb = 100;
                        for (int j = i + 1; j < onebreak; j++) {
                            if (one.get(j) != tempb) {
                                tempb = one.get(j);
                                tempc = 100;
                                for (int k = j + 1; k < onebreak; k++) {
                                    if (one.get(k) != tempc) {
                                        tempc = one.get(k);
                                        desta = tempa + n > 24 ? 26 : tempa + n;
                                        destb = tempb + n > 24 ? 26 : tempb + n;
                                        destc = tempc + n > 24 ? 26 : tempc + n;
                                        this.movelist.add(new int[]{tempa, desta, tempb, destb, tempc, destc});
                                    }
                                }
                            }
                        }
                    }
                }
                tempa = 100;
                tempb = 100;
                for (int i = 0; i < twobreak; i++) {
                    if (two.get(i) != tempa) {
                        tempa = two.get(i);
                        tempb = 100;
                        if (one.contains(tempa + n)) {
                            if (Collections.frequency(one, tempa + n) == 1) {
                                desta = tempa + n + n > 24 ? 26 : tempa + n + n;
                                this.movelist.add(new int[]{tempa, tempa + n, tempa + n, desta, tempa + n, desta});
                            }
                            continue;
                        }
                        skipped = false;
                        for (int j = 0; j < onebreak; j++) {
                            if ((one.get(j) == tempa) && (!skipped)) {
                                skipped = true;
                            } else if (one.get(j) != tempb) {
                                tempb = one.get(j);
                                desta = tempa + n + n > 24 ? 26 : tempa + n + n;
                                destb = tempb + n > 24 ? 26 : tempb + n;
                                this.movelist.add(new int[]{tempa, tempa + n, tempa + n, desta, tempb, destb});
                            }
                        }
                    }
                }
                tempa = 100;
                for (int i = 0; i < threebreak; i++) {
                    if (three.get(i) != tempa) {
                        tempa = three.get(i);
                        if ((one.contains(tempa + n + n)) || (two.contains(tempa + n))) {
                            continue;
                        }
                        desta = tempa + n + n + n > 24 ? 26 : tempa + n + n + n;
                        this.movelist.add(new int[]{tempa, tempa + n, tempa + n, tempa + n + n, tempa + n + n, desta});
                    }
                }
            } else if (bar == 0) {
                int tempa = 100;
                int tempb = 100;
                int tempc = 100;
                boolean skipped = false;
                for (int i = 0; i < onebreak; i++) {
                    if (one.get(i) != tempa) {
                        ArrayList<Integer> moveda = copier(bears);
                        tempa = one.get(i);
                        tempb = 100;
                        if (tempa + n > 18) {
                            moveda.remove(Integer.valueOf(tempa));
                        }
                        int limita = moveda.isEmpty() ? one.size() : onebreak;
                        for (int j = i + 1; j < limita; j++) {
                            if (one.get(j) != tempb) {
                                ArrayList<Integer> movedb = copier(moveda);
                                tempb = one.get(j);
                                tempc = 100;
                                if (tempb + n > 18) {
                                    movedb.remove(Integer.valueOf(tempb));
                                }
                                int limitb = movedb.isEmpty() ? one.size() : onebreak;
                                for (int k = j + 1; k < limitb; k++) {
                                    if (one.get(k) != tempc) {
                                        tempc = one.get(k);
                                        destb = tempb + n > 24 ? 26 : tempb + n;
                                        destc = tempc + n > 24 ? 26 : tempc + n;
                                        this.movelist.add(new int[]{tempa, tempa + n, tempb, destb, tempc, destc});
                                    }
                                }
                            }
                        }
                    }
                }
                tempa = 100;
                tempb = 100;
                for (int i = 0; i < twobreak; i++) {
                    if (two.get(i) != tempa) {
                        ArrayList<Integer> moveda = copier(bears);
                        tempa = two.get(i);
                        tempb = 100;
                        if (one.contains(tempa + n)) {
                            if (Collections.frequency(one, tempa + n) == 1) {
                                this.movelist.add(new int[]{tempa, tempa + n, tempa + n, tempa + n + n, tempa + n, tempa + n + n});
                            }
                            continue;
                        }
                        if (tempa + n + n > 18) {
                            moveda.remove(Integer.valueOf(tempa));
                        }
                        int limita = moveda.isEmpty() ? one.size() : onebreak;
                        skipped = false;
                        for (int j = 0; j < limita; j++) {
                            if ((one.get(j) == tempa) && (!skipped)) {
                                skipped = true;
                            } else if (one.get(j) != tempb) {
                                tempb = one.get(j);
                                destb = tempb + n > 24 ? 26 : tempb + n;
                                this.movelist.add(new int[]{tempa, tempa + n, tempa + n, tempa + n + n, tempb, destb});
                            }
                        }
                    }
                }
                tempa = 100;
                tempb = 100;
                for (int i = twobreak; i < two.size(); i++) {                                                               //unsure about this part!!!!!! DOUBLE CHECK!!!  SECOND SKIPPED
                    if (two.get(i) != tempa) {
                        ArrayList<Integer> moveda = copier(bears);
                        tempa = two.get(i);
                        tempb = 100;
                        if (one.contains(tempa + n)) {
                            if (Collections.frequency(one, tempa + n) == 1) {
                                ArrayList<Integer> altmoveda = copier(bears);
                                altmoveda.remove(Integer.valueOf(tempa));
                                altmoveda.remove(Integer.valueOf(tempa + n));
                                if (altmoveda.isEmpty()) {
                                    this.movelist.add(new int[]{tempa, tempa + n, tempa + n, 26, tempa + n, 26});
                                }
                            }
                            continue;
                        }
                        if (tempa + n + n > 18) {
                            moveda.remove(Integer.valueOf(tempa));
                        }
                        int limita = moveda.isEmpty() ? one.size() : onebreak;
                        skipped = false;
                        for (int j = 0; j < limita; j++) {
                            if ((one.get(j) == tempa) && (!skipped)) {
                                skipped = true;
                            } else if (one.get(j) != tempb) {
                                ArrayList<Integer> movedb = copier(moveda);
                                tempb = one.get(j);
                                if (tempb + n > 18) {
                                    movedb.remove(Integer.valueOf(tempb));
                                }
                                if (movedb.isEmpty()) {
                                    desta = tempa + n + n > 24 ? 26 : tempa + n + n;
                                    destb = tempb + n > 24 ? 26 : tempb + n;
                                    this.movelist.add(new int[]{tempb, destb, tempa, tempa + n, tempa + n, desta});
                                }
                            }
                        }
                    }
                }
                tempa = 100;
                for (int i = 0; i < threebreak; i++) {
                    if (three.get(i) != tempa) {
                        tempa = three.get(i);
                        if ((one.contains(tempa + n + n)) || (two.contains(tempa + n))) {
                            continue;
                        }
                        this.movelist.add(new int[]{tempa, tempa + n, tempa + n, tempa + n + n, tempa + n + n, tempa + n + n + n});
                    }
                }
                tempa = 100;
                for (int i = threebreak; i < three.size(); i++) {
                    if (three.get(i) != tempa) {
                        ArrayList<Integer> moveda = copier(bears);
                        tempa = three.get(i);
                        if ((one.contains(tempa + n + n)) || (two.contains(tempa + n))) {
                            continue;
                        }
                        if (tempa + n + n + n > 18) {
                            moveda.remove(Integer.valueOf(tempa));
                        }
                        if (moveda.isEmpty()) {
                            desta = tempa + n + n + n > 24 ? 26 : tempa + n + n + n;
                            this.movelist.add(new int[]{tempa, tempa + n, tempa + n, tempa + n + n, tempa + n + n, desta});
                        }
                    }
                }
            }
        }
        if (this.movelist.isEmpty()) {
            if (bar < -1) {
                this.movelist.add(new int[]{0, n, 0, n});
            } else if (bar == -1) {
                int tempa = 100;
                for (int i = 0; i < onebreak; i++) {
                    if (one.get(i) != tempa) {
                        tempa = one.get(i);
                        this.movelist.add(new int[]{0, n, tempa, tempa + n});
                    }
                }
            } else if ((bar == 0) && (!bearcheck)) {
                int tempa = 100;
                int tempb = 100;
                for (int i = 0; i < onebreak; i++) {
                    if (one.get(i) != tempa) {
                        tempa = one.get(i);
                        tempb = 100;
                        for (int j = i + 1; j < onebreak; j++) {
                            if (one.get(j) != tempb) {
                                tempb = one.get(j);
                                desta = tempa + n > 24 ? 26 : tempa + n;
                                destb = tempb + n > 24 ? 26 : tempb + n;
                                this.movelist.add(new int[]{tempa, desta, tempb, destb});
                            }
                        }
                    }
                }
                tempa = 100;
                for (int i = 0; i < twobreak; i++) {
                    if (two.get(i) != tempa) {
                        tempa = two.get(i);
                        if (one.contains(tempa + n)) {
                            continue;
                        }
                        desta = tempa + n + n > 24 ? 26 : tempa + n + n;
                        this.movelist.add(new int[]{tempa, tempa + n, tempa + n, desta});
                    }
                }
            } else if (bar == 0) {
                int tempa = 100;
                int tempb = 100;
                for (int i = 0; i < onebreak; i++) {
                    if (one.get(i) != tempa) {
                        ArrayList<Integer> moveda = copier(bears);
                        tempa = one.get(i);
                        tempb = 100;
                        if (tempa + n > 18) {
                            moveda.remove(Integer.valueOf(tempa));
                        }
                        int limita = moveda.isEmpty() ? one.size() : onebreak;
                        for (int j = i + 1; j < limita; j++) {
                            if (one.get(j) != tempb) {
                                tempb = one.get(j);
                                destb = tempb + n > 24 ? 26 : tempb + n;
                                this.movelist.add(new int[]{tempa, tempa + n, tempb, destb});
                            }
                        }
                    }
                }
                tempa = 100;
                for (int i = 0; i < twobreak; i++) {
                    if (two.get(i) != tempa) {
                        tempa = two.get(i);
                        if (one.contains(tempa + n)) {
                            continue;
                        }
                        this.movelist.add(new int[]{tempa, tempa + n, tempa + n, tempa + n + n});
                    }
                }
                tempa = 100;
                for (int i = twobreak; i < two.size(); i++) {
                    if (two.get(i) != tempa) {
                        ArrayList<Integer> moveda = copier(bears);
                        tempa = two.get(i);
                        if (one.contains(tempa + n)) {
                            continue;
                        }
                        if (tempa + n + n > 18) {
                            moveda.remove(Integer.valueOf(tempa));
                        }
                        if (moveda.isEmpty()) {
                            desta = tempa + n + n > 24 ? 26 : tempa + n + n;
                            this.movelist.add(new int[]{tempa, tempa + n, tempa + n, desta});
                        }
                    }
                }
            }
        }
        if (this.movelist.isEmpty()) {
            if (bar < 0) {
                this.movelist.add(new int[]{0, n});
            } else if ((bar == 0) && (!bearcheck)) {
                int tempa = 100;
                for (int i = 0; i < onebreak; i++) {
                    if (one.get(i) != tempa) {
                        tempa = one.get(i);
                        desta = tempa + n > 24 ? 26 : tempa + n;
                        this.movelist.add(new int[]{tempa, desta});
                    }
                }
            } else if (bar == 0) {
                int tempa = 100;
                for (int i = 0; i < onebreak; i++) {
                    if (one.get(i) != tempa) {
                        tempa = one.get(i);
                        this.movelist.add(new int[]{tempa, tempa + n});
                    }
                }
                tempa = 100;
                for (int i = onebreak; i < one.size(); i++) {
                    if (one.get(i) != tempa) {
                        ArrayList<Integer> moveda = copier(bears);
                        tempa = one.get(i);
                        if (tempa + n > 18) {
                            moveda.remove(Integer.valueOf(tempa));
                        }
                        if (moveda.isEmpty()) {
                            desta = tempa + n > 24 ? 26 : tempa + n;
                            this.movelist.add(new int[]{tempa, desta});
                        }
                    }
                }
            }
        }
        //doublesorter(false);
    }                                                         //Check this again on test cases

    public ArrayList<Integer> copier(ArrayList<Integer> a) {
        ArrayList<Integer> output = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            output.add(a.get(i));
        }
        return output;
    }

    public void movelisterw(Board b, Roll r, ArrayList<Integer> first, ArrayList<Integer> second) {
        int f = r.steps[0] > r.steps[1] ? r.steps[0] : r.steps[1];
        int s = r.steps[0] < r.steps[1] ? r.steps[0] : r.steps[1];
        //System.out.println("int f is " +f);
        //System.out.println("int s is " +s);
        int bear = 0;
        int highest = 0;
        int out = -1;
        int altout = -1;
        int fbreak = 500;
        int sbreak = 500;
        boolean single = false;
        int bar = b.board[25];
        //System.out.println("int bar is " +bar);
        if (bar >= 2) {
            if (b.board[25-f] > -2) {
                first.add(25);
            }
            if (b.board[25-s] > -2) {
                second.add(25);
            }
            if ((!first.isEmpty()) && (!second.isEmpty())) {
                this.movelist.add(new int[]{25, 25-f, 25, 25-s});
                return;
            } else if (!first.isEmpty()) {
                this.movelist.add(new int[]{25, 25-f});
                return;
            } else if (!second.isEmpty()) {
                this.movelist.add(new int[]{25, 25-s});
                return;
            }
        } else {
            for (int i = 25; i > 0; i--) {
                if ((i > 6) && (b.board[i] > 0)) {
                    bear += (b.board[i]);
                    highest = i;
                }
            }
            //System.out.println("int bear is " +bear);
            //System.out.println("int lowest is " +lowest);
            if (bear == 0) {
                for (int i = 25; i > 0; i--) {
                    if ((b.board[i] == 1) && ((((i - f) > 0) && (b.board[i - f] > -2)) || (i - f < 1))) {
                        first.add(i);
                    }
                    if ((b.board[i] > 1) && ((((i - f) > 0) && (b.board[i - f] > -2)) || (i - f < 1))) {
                        first.add(i);
                        first.add(i);
                    }
                    if ((b.board[i] == 1) && ((((i - s) > 0) && (b.board[i - s] > -2)) || (i - s < 1))) {
                        second.add(i);
                    }
                    if ((b.board[i] > 1) && ((((i - s) > 0) && (b.board[i - s] > -2)) || (i - s < 1))) {
                        second.add(i);
                        second.add(i);
                    }
                }
            } else if (bear > 1) {
                for (int i = 25; i > 0; i--) {
                    if ((b.board[i] == 1) && ((i - f) > 0) && (b.board[i - f] > -2)) {
                        first.add(i);
                    }
                    if ((b.board[i] > 1) && ((i - f) > 0) && (b.board[i - f] > -2)) {
                        first.add(i);
                        first.add(i);
                    }
                    if ((b.board[i] == 1) && ((i - s) > 0) && (b.board[i - s] > -2)) {
                        second.add(i);
                    }
                    if ((b.board[i] > 1) && ((i - s) > 0) && (b.board[i - s] > -2)) {
                        second.add(i);
                        second.add(i);
                    }
                }
            } else {
                for (int i = 25; i > 0; i--) {
                    if ((b.board[i] == 1) && ((i - f) > 0) && (b.board[i - f] > -2)) {
                        first.add(i);
                    }
                    if ((b.board[i] > 1) && ((i - f) > 0) && (b.board[i - f] > -2)) {
                        first.add(i);
                        first.add(i);
                    }
                    if ((b.board[i] == 1) && ((i - s) > 0) && (b.board[i - s] > -2)) {
                        second.add(i);
                    }
                    if ((b.board[i] > 1) && ((i - s) > 0) && (b.board[i - s] > -2)) {
                        second.add(i);
                        second.add(i);
                    }
                }
                if ((first.contains(highest)) && ((highest - f) < 7)) {
                    sbreak = second.size();
                    //System.out.println("int sbreak is " +sbreak);
                    for (int i = 25; i > 0; i--) {
                        if ((b.board[i] == 1) && ((i - s) < 1)) {
                            second.add(i);
                        }
                        if ((b.board[i] > 1) && ((i - s) < 1)) {
                            second.add(i);
                            second.add(i);
                        }
                    }
                }
                if ((second.contains(highest)) && ((highest - s) < 7)) {
                    fbreak = first.size();
                    //System.out.println("int fbreak is " +fbreak);
                    for (int i = 25; i > 0; i--) {
                        if ((b.board[i] == 1) && ((i - f) < 1)) {
                            first.add(i);
                        }
                        if ((b.board[i] > 1) && ((i - f) < 1)) {
                            first.add(i);
                            first.add(i);
                        }
                    }
                }
            }
        }
        //System.out.println("First: " + Arrays.toString(first.toArray()));
        //System.out.println("Second: " + Arrays.toString(second.toArray()));
        if (fbreak == 500) {
            fbreak = first.size();
        }
        if (sbreak == 500) {
            sbreak = second.size();
        }
        if ((b.board[25] > 0) && ((first.isEmpty()) || (first.get(0) != 25)) && ((second.isEmpty()) || (second.get(0) != 25))) {
            return;
        }
        int tempa = 100;
        int tempb = 100;
        int tempc = 100;
        if (bar == 1) {
            if ((!first.isEmpty()) && (first.get(0) == 25)) {
                tempa = 100;
                for (int i = 0; i < sbreak; i++) {
                    if (second.get(i) == tempa) {
                        tempa = 100;
                    } else {
                        tempa = second.get(i);
                        this.movelist.add(new int[]{25, 25-f, tempa, tempa-s});
                    }
                }
                if ((!second.contains(f)) && ((this.board.board[25-f-s] > -2))) {
                    this.movelist.add(new int[]{25, 25-f, 25-f, 25-f-s});
                }
            }
            if ((!second.isEmpty()) &&(second.get(0)) == 25) {
                tempa = 100;
                for (int i = 0; i < fbreak; i++) {
                    if (first.get(i) == tempa) {
                        tempa = 100;
                    } else {
                        tempa = first.get(i);
                        this.movelist.add(new int[]{25, 25-s, tempa, tempa-f});
                    }
                }
                if ((!first.contains(s)) && ((this.board.board[25-s-f] > -2))) {
                    this.movelist.add(new int[]{25, 25-s, 25-s, 25-s-f});
                }
            }
            if (this.movelist.isEmpty()) {
                if ((!first.isEmpty()) && (first.get(0)) == 25) {
                    this.movelist.add(new int[]{25, 25-f});
                }
                if ((!second.isEmpty()) &&(second.get(0)) == 25) {
                    this.movelist.add(new int[]{25, 25-s});
                }
            }
            return;
        }
        for (int j = 0; j < fbreak; j++) {
            if (first.get(j) == tempa) {
                tempa = 100;
            } else {
                tempa = first.get(j);
                tempb = tempa;
                tempc = tempa;
                if (((tempa - f - s > 0) && (b.board[tempa - f - s] > -2)) || ((bear == 0) && (tempa - f > 0) && (tempa - f - s < 1)) || ((bear == 1) && (tempa == highest) && (tempa - f - s < 1))) {
                    if ((bar < 1) || ((bar == 1) && (tempa == 25))) {
                        out = tempa - f - s > 0 ? tempa - f - s : -1;
                        this.movelist.add(new int[]{tempa, tempa - f, tempa - f, out});
                    }
                }
                if ((bar > 1) && (tempa != 25)) {
                    break;
                }
                if (bear == 1) {
                    if (tempa == highest) {
                        for (int k = 0; k < second.size(); k++) {
                            if ((bar > 1) && (second.get(0) != 25)) {
                                break;
                            }
                            if (second.get(k) == tempc) {
                                tempc = 100;
                                tempb = 100;
                            } else if (second.get(k) == tempb) {
                                tempb = 100;
                            } else {
                                tempb = second.get(k);
                                if ((bar != 0) && ((tempa != 25) && (tempb != 25))) {
                                    break;
                                }
                                if (tempb != tempa - f) {
                                    out = tempb - s > 0 ? tempb - s : -1;
                                    this.movelist.add(new int[]{tempa, tempa - f, tempb, out});
                                }
                            }
                            if (tempb == 25) {
                                break;
                            }
                        }
                    } else {
                        for (int k = 0; k < sbreak; k++) {
                            if ((bar > 1) && (second.get(0) != 25)) {
                                break;
                            }
                            if (second.get(k) == tempc) {
                                tempc = 100;
                                tempb = 100;
                            } else if (second.get(k) == tempb) {
                                tempb = 100;
                            } else {
                                tempb = second.get(k);
                                if ((bar != 0) && ((tempa != 25) && (tempb != 25))) {
                                    break;
                                }
                                if (tempb != tempa - f) {
                                    this.movelist.add(new int[]{tempa, tempa - f, tempb, tempb - s});
                                }
                            }
                            if (tempb == 25) {
                                break;
                            }
                        }
                    }
                } else {
                    for (int k = 0; k < sbreak; k++) {
                        if ((bar > 1) && (second.get(0) != 25)) {
                            break;
                        }
                        if (second.get(k) == tempc) {
                            tempc = 100;
                            tempb = 100;
                        } else if (second.get(k) == tempb) {
                            tempb = 100;
                        } else {
                            tempb = second.get(k);
                            if ((bar != 0) && ((tempa != 25) && (tempb != 25))) {
                                break;
                            }
                            if (tempb != tempa - f) {
                                out = tempa - f > 0 ? tempa - f : -1;
                                altout = tempb - s > 0 ? tempb - s : -1;
                                this.movelist.add(new int[]{tempa, out, tempb, altout});
                            }
                        }
                        if (tempb == 25) {
                            break;
                        }
                    }
                }
            }
        }
        tempa = 100;
        tempb = 100;
        tempc = 100;
        for (int j = 0; j < sbreak; j++) {
            if (second.get(j) == tempa) {
                tempa = 100;
            } else {
                tempa = second.get(j);
                tempb = tempa;
                tempc = tempa;
                if (((tempa - s - f > 0) && (b.board[tempa - s - f] > -2)) || ((bear == 0) && (tempa - s > 0) && (tempa - s - f < 1)) || ((bear == 1) && (tempa == highest) && (tempa - s - f < 1))) {
                    if ((bar < 1) || ((bar == 1) && (tempa == 25))) {
                        out = tempa - s - f > 0 ? tempa - s - f : -1;
                        this.movelist.add(new int[]{tempa, tempa - s, tempa - s, out});
                    }
                }
                if ((bar > 1) && (tempa != 25)) {
                    break;
                }
                if (bear == 1) {
                    if (tempa == highest) {
                        for (int k = 0; k < first.size(); k++) {
                            if ((bar > 1) && (first.get(0) != 25)) {
                                break;
                            }
                            if (first.get(k) == tempc) {
                                tempc = 100;
                                tempb = 100;
                            } else if (first.get(k) == tempb) {
                                tempb = 100;
                            } else {
                                tempb = first.get(k);
                                if ((bar != 0) && ((tempa != 25) && (tempb != 25))) {
                                    break;
                                }
                                if (tempb != tempa - s) {
                                    out = tempb - f > 0 ? tempb - f : -1;
                                    this.movelist.add(new int[]{tempa, tempa - s, tempb, out});
                                }
                            }
                            if (tempb == 25) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        if ((this.movelist.isEmpty()) && (!first.isEmpty())) {              
            tempa = 100;
            if ((bar < 1) || (first.get(0) == 25)) {
                for (int m = 0; m < fbreak; m++) {
                    if (first.get(m) == tempa) {
                        tempa = 100;
                    } else {
                        tempa = first.get(m);
                        out = tempa - f > 0 ? tempa - f : -1;
                        this.movelist.add(new int[]{tempa, out});
                        single = true;
                    }
                    if (tempa == 25) {
                        break;
                    }
                }
            }
        }
        if ((this.movelist.isEmpty()) && (!second.isEmpty())) {
            tempa = 100;
            if ((bar < 1) || (second.get(0) == 25)) {
                for (int n = 0; n < sbreak; n++) {
                    if (second.get(n) == tempa) {
                        tempa = 100;
                    } else {
                        tempa = second.get(n);
                        out = tempa - s > 0 ? tempa - s : -1;
                        this.movelist.add(new int[]{tempa, out});
                        single = true;
                    }
                    if (tempa == 25) {
                        break;
                    }
                }
            }
        }
        //if (single) {
        //    singlesorter(false);
        //} else {
        //    listsorter(false);
        //}
    }       //Check this again on test cases

    public void movelisterb(Board b, Roll r, ArrayList<Integer> first, ArrayList<Integer> second) {
        int f = r.steps[0] > r.steps[1] ? r.steps[0] : r.steps[1];
        int s = r.steps[0] < r.steps[1] ? r.steps[0] : r.steps[1];
        //System.out.println("int f is " +f);
        //System.out.println("int s is " +s);
        int bear = 0;
        int lowest = 25;
        int out = 26;
        int altout = 26;
        int fbreak = 500;
        int sbreak = 500;
        boolean single = false;
        int bar = b.board[0];
        //System.out.println("int bar is " +bar);
        if (bar <= -2) {
            if (b.board[f] < 2) {
                first.add(0);
            }
            if (b.board[s] < 2) {
                second.add(0);
            }
            if ((!first.isEmpty()) && (!second.isEmpty())) {
                this.movelist.add(new int[]{0, f, 0, s});
                return;
            } else if (!first.isEmpty()) {
                this.movelist.add(new int[]{0, f});
                return;
            } else if (!second.isEmpty()) {
                this.movelist.add(new int[]{0, s});
                return;
            }
        } else {
            for (int i = 0; i < 25; i++) {
                if ((i < 19) && (b.board[i] < 0)) {
                    bear += (-(b.board[i]));
                    lowest = i;
                }
            }
            //System.out.println("int bear is " +bear);
            //System.out.println("int lowest is " +lowest);
            if (bear == 0) {
                for (int i = 0; i < 25; i++) {
                    if ((b.board[i] == -1) && ((((i + f) < 25) && (b.board[i + f] < 2)) || (i + f > 24))) {
                        first.add(i);
                    }
                    if ((b.board[i] < -1) && ((((i + f) < 25) && (b.board[i + f] < 2)) || (i + f > 24))) {
                        first.add(i);
                        first.add(i);
                    }
                    if ((b.board[i] == -1) && ((((i + s) < 25) && (b.board[i + s] < 2)) || (i + s > 24))) {
                        second.add(i);
                    }
                    if ((b.board[i] < -1) && ((((i + s) < 25) && (b.board[i + s] < 2)) || (i + s > 24))) {
                        second.add(i);
                        second.add(i);
                    }
                }
            } else if (bear > 1) {
                for (int i = 0; i < 25; i++) {
                    if ((b.board[i] == -1) && ((i + f) < 25) && (b.board[i + f] < 2)) {
                        first.add(i);
                    }
                    if ((b.board[i] < -1) && ((i + f) < 25) && (b.board[i + f] < 2)) {
                        first.add(i);
                        first.add(i);
                    }
                    if ((b.board[i] == -1) && ((i + s) < 25) && (b.board[i + s] < 2)) {
                        second.add(i);
                    }
                    if ((b.board[i] < -1) && ((i + s) < 25) && (b.board[i + s] < 2)) {
                        second.add(i);
                        second.add(i);
                    }
                }
            } else {
                for (int i = 0; i < 25; i++) {
                    if ((b.board[i] == -1) && ((i + f) < 25) && (b.board[i + f] < 2)) {
                        first.add(i);
                    }
                    if ((b.board[i] < -1) && ((i + f) < 25) && (b.board[i + f] < 2)) {
                        first.add(i);
                        first.add(i);
                    }
                    if ((b.board[i] == -1) && ((i + s) < 25) && (b.board[i + s] < 2)) {
                        second.add(i);
                    }
                    if ((b.board[i] < -1) && ((i + s) < 25) && (b.board[i + s] < 2)) {
                        second.add(i);
                        second.add(i);
                    }
                }
                if ((first.contains(lowest)) && ((lowest + f) > 18)) {
                    sbreak = second.size();
                    //System.out.println("int sbreak is " +sbreak);
                    for (int i = 0; i < 25; i++) {
                        if ((b.board[i] == -1) && ((i + s) > 24)) {
                            second.add(i);
                        }
                        if ((b.board[i] < -1) && ((i + s) > 24)) {
                            second.add(i);
                            second.add(i);
                        }
                    }
                }
                if ((second.contains(lowest)) && ((lowest + s) > 18)) {
                    fbreak = first.size();
                    //System.out.println("int fbreak is " +fbreak);
                    for (int i = 0; i < 25; i++) {
                        if ((b.board[i] == -1) && ((i + f) > 24)) {
                            first.add(i);
                        }
                        if ((b.board[i] < -1) && ((i + f) > 24)) {
                            first.add(i);
                            first.add(i);
                        }
                    }
                }
            }
        }
        //System.out.println("First: " + Arrays.toString(first.toArray()));
        //System.out.println("Second: " + Arrays.toString(second.toArray()));
        if (fbreak == 500) {
            fbreak = first.size();
        }
        if (sbreak == 500) {
            sbreak = second.size();
        }
        if ((b.board[0] < 0) && ((first.isEmpty()) || (first.get(0) != 0)) && ((second.isEmpty()) || (second.get(0) != 0))) {
            return;
        }
        int tempa = 100;
        int tempb = 100;
        int tempc = 100;
        if (bar == 1) {
            if ((!first.isEmpty()) && (first.get(0) == 0)) {
                tempa = 100;
                for (int i = 0; i < sbreak; i++) {
                    if (second.get(i) == tempa) {
                        tempa = 100;
                    } else {
                        tempa = second.get(i);
                        this.movelist.add(new int[]{0, f, tempa, tempa+s});
                    }
                }
                if ((!second.contains(f)) && ((this.board.board[f+s] < 2))) {
                    this.movelist.add(new int[]{0, f, f, f+s});
                }
            }
            if ((!second.isEmpty()) && (second.get(0)) == 0) {
                tempa = 100;
                for (int i = 0; i < fbreak; i++) {
                    if (first.get(i) == tempa) {
                        tempa = 100;
                    } else {
                        tempa = first.get(i);
                        this.movelist.add(new int[]{0, s, tempa, tempa+f});
                    }
                }
                if ((!first.contains(s)) && ((this.board.board[s+f] < 2))) {
                    this.movelist.add(new int[]{0, s, s, s+f});
                }
            }
            if (this.movelist.isEmpty()) {
                if ((!first.isEmpty()) && (first.get(0)) == 0) {
                    this.movelist.add(new int[]{0, f});
                }
                if ((!second.isEmpty()) && (second.get(0)) == 0) {
                    this.movelist.add(new int[]{0, s});
                }
            }
            return;
        }
        for (int j = 0; j < fbreak; j++) {
            if (first.get(j) == tempa) {
                tempa = 100;
            } else {
                tempa = first.get(j);
                tempb = tempa;
                tempc = tempa;
                if (((tempa + f + s < 25) && (b.board[tempa + f + s] < 2)) || ((bear == 0) && (tempa + f < 25) && (tempa + f + s > 24)) || ((bear == 1) && (tempa == lowest) && (tempa + f + s > 24))) {
                    if ((bar > -1) || ((bar == -1) && (tempa == 0))) {
                        out = tempa + f + s < 25 ? tempa + f + s : 26;
                        this.movelist.add(new int[]{tempa, tempa + f, tempa + f, out});
                    }
                }
                if ((bar < -1) && (tempa != 0)) {
                    break;
                }
                if (bear == 1) {
                    if (tempa == lowest) {
                        for (int k = 0; k < second.size(); k++) {
                            if ((bar < -1) && (second.get(0) != 0)) {
                                break;
                            }
                            if (second.get(k) == tempc) {
                                tempc = 100;
                                tempb = 100;
                            } else if (second.get(k) == tempb) {
                                tempb = 100;
                            } else {
                                tempb = second.get(k);
                                if ((bar != 0) && ((tempa != 0) && (tempb != 0))) {
                                    break;
                                }
                                if (tempb != tempa + f) {
                                    out = tempb + s < 25 ? tempb + s : 26;
                                    this.movelist.add(new int[]{tempa, tempa + f, tempb, out});
                                }
                            }
                            if (tempb == 0) {
                                break;
                            }
                        }
                    } else {
                        for (int k = 0; k < sbreak; k++) {
                            if ((bar < -1) && (second.get(0) != 0)) {
                                break;
                            }
                            if (second.get(k) == tempc) {
                                tempc = 100;
                                tempb = 100;
                            } else if (second.get(k) == tempb) {
                                tempb = 100;
                            } else {
                                tempb = second.get(k);
                                if ((bar != 0) && ((tempa != 0) && (tempb != 0))) {
                                    break;
                                }
                                if (tempb != tempa + f) {
                                    this.movelist.add(new int[]{tempa, tempa + f, tempb, tempb + s});
                                }
                            }
                            if (tempb == 0) {
                                break;
                            }
                        }
                    }
                } else {
                    for (int k = 0; k < sbreak; k++) {
                        if ((bar < -1) && (second.get(0) != 0)) {
                            break;
                        }
                        if (second.get(k) == tempc) {
                            tempc = 100;
                            tempb = 100;
                        } else if (second.get(k) == tempb) {
                            tempb = 100;
                        } else {
                            tempb = second.get(k);
                            if ((bar != 0) && ((tempa != 0) && (tempb != 0))) {
                                break;
                            }
                            if (tempb != tempa + f) {
                                out = tempa + f < 25 ? tempa + f : 26;
                                altout = tempb + s < 25 ? tempb + s : 26;
                                this.movelist.add(new int[]{tempa, out, tempb, altout});
                            }
                        }
                        if (tempb == 0) {
                            break;
                        }
                    }
                }
            }
        }
        tempa = 100;
        tempb = 100;
        tempc = 100;
        for (int j = 0; j < sbreak; j++) {
            if (second.get(j) == tempa) {
                tempa = 100;
            } else {
                tempa = second.get(j);
                tempb = tempa;
                tempc = tempa;
                if (((tempa + s + f < 25) && (b.board[tempa + s + f] < 2)) || ((bear == 0) && (tempa + s < 25) && (tempa + s + f > 24)) || ((bear == 1) && (tempa == lowest) && (tempa + s + f > 24))) {
                    if ((bar < 1) || ((bar == 1) && (tempa == 0))) {
                        out = tempa + s + f < 25 ? tempa + s + f : 26;
                        this.movelist.add(new int[]{tempa, tempa + s, tempa + s, out});
                    }
                }
                if ((bar > 1) && (tempa != 0)) {
                    break;
                }
                if (bear == 1) {
                    if (tempa == lowest) {
                        for (int k = 0; k < first.size(); k++) {
                            if ((bar > 1) && (first.get(0) != 0)) {
                                break;
                            }
                            if (first.get(k) == tempc) {
                                tempc = 100;
                                tempb = 100;
                            } else if (first.get(k) == tempb) {
                                tempb = 100;
                            } else {
                                tempb = first.get(k);
                                if ((bar != 0) && ((tempa != 0) && (tempb != 0))) {
                                    break;
                                }
                                if (tempb != tempa + s) {
                                    out = tempb + f < 25 ? tempb + f : 26;
                                    this.movelist.add(new int[]{tempa, tempa + s, tempb, out});
                                }
                            }
                            if (tempb == 0) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        if ((this.movelist.isEmpty()) && (!first.isEmpty())) {
            tempa = 100;
            if ((bar > -1) || (first.get(0) == 0)) {
                for (int m = 0; m < fbreak; m++) {
                    if (first.get(m) == tempa) {
                        tempa = 100;
                    } else {
                        tempa = first.get(m);
                        out = tempa + f < 25 ? tempa + f : 26;
                        this.movelist.add(new int[]{tempa, out});
                        single = true;
                    }
                    if (tempa == 0) {
                        break;
                    }
                }
            }
        }
        if ((this.movelist.isEmpty()) && (!second.isEmpty())) {
            tempa = 100;
            if ((bar > -1) || (second.get(0) == 0)) {
                for (int n = 0; n < sbreak; n++) {
                    if (second.get(n) == tempa) {
                        tempa = 100;
                    } else {
                        tempa = second.get(n);
                        out = tempa + s < 25 ? tempa + s : 26;
                        this.movelist.add(new int[]{tempa, out});
                        single = true;
                    }
                    if (tempa == 0) {
                        break;
                    }
                }
            }
        }
        //if (single) {
        //    singlesorter(false);
        //} else {
        //    listsorter(false);
        //}
    }       //Check this again on test cases

    public void listsorter(boolean white) {                        ///slows the movelister by factor 3, i.e. from 4 seconds / 10.000.000 boards to 12 seconds / 10.000.000 boards!!!
        if (white) {
            for (int o = 0; o < this.movelist.size(); o++) {
                if ((this.movelist.get(o)[0] < this.movelist.get(o)[2]) || ((this.movelist.get(o)[0] == this.movelist.get(o)[2]) && (this.movelist.get(o)[1] < this.movelist.get(o)[3]))) {
                    int tempa = this.movelist.get(o)[0];
                    int tempb = this.movelist.get(o)[1];
                    this.movelist.get(o)[0] = this.movelist.get(o)[2];
                    this.movelist.get(o)[1] = this.movelist.get(o)[3];
                    this.movelist.get(o)[2] = tempa;
                    this.movelist.get(o)[3] = tempb;
                }
            }
            this.movelist.sort(Comparator.comparingInt((int[] a) -> a[0]).thenComparingInt((int[] a) -> a[1]).thenComparingInt((int[] a) -> a[2]).reversed());
        } else {
            for (int o = 0; o < this.movelist.size(); o++) {
                if ((this.movelist.get(o)[0] > this.movelist.get(o)[2]) || ((this.movelist.get(o)[0] == this.movelist.get(o)[2]) && (this.movelist.get(o)[1] > this.movelist.get(o)[3]))) {
                    int tempa = this.movelist.get(o)[0];
                    int tempb = this.movelist.get(o)[1];
                    this.movelist.get(o)[0] = this.movelist.get(o)[2];
                    this.movelist.get(o)[1] = this.movelist.get(o)[3];
                    this.movelist.get(o)[2] = tempa;
                    this.movelist.get(o)[3] = tempb;
                }
            }
            this.movelist.sort(Comparator.comparingInt((int[] a) -> a[0]).thenComparingInt((int[] a) -> a[1]).thenComparingInt((int[] a) -> a[2]));
        }
    }

    public void singlesorter(boolean white) {
        if (white) {
            this.movelist.sort(Comparator.comparingInt((int[] a) -> a[0]).thenComparingInt((int[] a) -> a[1]).reversed());
        } else {
            this.movelist.sort(Comparator.comparingInt((int[] a) -> a[0]).thenComparingInt((int[] a) -> a[1]));
        }
    }

    public void doublesorter(boolean white) {
        if (!this.movelist.isEmpty()) {
            if (white) {
                if (this.movelist.get(0).length == 2) {
                    singlesorter(white);
                } else if (this.movelist.get(0).length == 4) {
                    listsorter(white);
                } else if (this.movelist.get(0).length == 6) {
                    for (int i = 0; i < this.movelist.size(); i++) {
                        if (this.movelist.get(i)[2] < this.movelist.get(i)[4]) {
                            swap(this.movelist.get(i), 2, 4);
                        }
                        if (this.movelist.get(i)[0] < this.movelist.get(i)[2]) {
                            swap(this.movelist.get(i), 0, 2);
                        }
                        if (this.movelist.get(i)[2] < this.movelist.get(i)[4]) {
                            swap(this.movelist.get(i), 2, 4);
                        }
                    }
                    this.movelist.sort(Comparator.comparingInt((int[] a) -> a[0]).thenComparingInt((int[] a) -> a[1]).thenComparingInt((int[] a) -> a[2]).thenComparingInt((int[] a) -> a[3]).thenComparingInt((int[] a) -> a[4]).reversed());
                } else if (this.movelist.get(0).length == 8) {
                    for (int i = 0; i < this.movelist.size(); i++) {
                        if (this.movelist.get(i)[0] < this.movelist.get(i)[2]) {
                            swap(this.movelist.get(i), 0, 2);
                        }
                        if (this.movelist.get(i)[4] < this.movelist.get(i)[6]) {
                            swap(this.movelist.get(i), 4, 6);
                        }
                        if (this.movelist.get(i)[0] < this.movelist.get(i)[4]) {
                            swap(this.movelist.get(i), 0, 4);
                        }
                        if (this.movelist.get(i)[2] < this.movelist.get(i)[6]) {
                            swap(this.movelist.get(i), 2, 6);
                        }
                        if (this.movelist.get(i)[2] < this.movelist.get(i)[4]) {
                            swap(this.movelist.get(i), 2, 4);
                        }
                    }
                    this.movelist.sort(Comparator.comparingInt((int[] a) -> a[0]).thenComparingInt((int[] a) -> a[1]).thenComparingInt((int[] a) -> a[2]).thenComparingInt((int[] a) -> a[3]).thenComparingInt((int[] a) -> a[4]).thenComparingInt((int[] a) -> a[5]).thenComparingInt((int[] a) -> a[6]).reversed());
                }
            } else {
                if (this.movelist.get(0).length == 2) {
                    singlesorter(white);
                } else if (this.movelist.get(0).length == 4) {
                    listsorter(white);
                } else if (this.movelist.get(0).length == 6) {
                    for (int i = 0; i < this.movelist.size(); i++) {
                        if (this.movelist.get(i)[2] > this.movelist.get(i)[4]) {
                            swap(this.movelist.get(i), 2, 4);
                        }
                        if (this.movelist.get(i)[0] > this.movelist.get(i)[2]) {
                            swap(this.movelist.get(i), 0, 2);
                        }
                        if (this.movelist.get(i)[2] > this.movelist.get(i)[4]) {
                            swap(this.movelist.get(i), 2, 4);
                        }
                    }
                    this.movelist.sort(Comparator.comparingInt((int[] a) -> a[0]).thenComparingInt((int[] a) -> a[1]).thenComparingInt((int[] a) -> a[2]).thenComparingInt((int[] a) -> a[3]).thenComparingInt((int[] a) -> a[4]));
                } else if (this.movelist.get(0).length == 8) {
                    for (int i = 0; i < this.movelist.size(); i++) {
                        if (this.movelist.get(i)[0] > this.movelist.get(i)[2]) {
                            swap(this.movelist.get(i), 0, 2);
                        }
                        if (this.movelist.get(i)[4] > this.movelist.get(i)[6]) {
                            swap(this.movelist.get(i), 4, 6);
                        }
                        if (this.movelist.get(i)[0] > this.movelist.get(i)[4]) {
                            swap(this.movelist.get(i), 0, 4);
                        }
                        if (this.movelist.get(i)[2] > this.movelist.get(i)[6]) {
                            swap(this.movelist.get(i), 2, 6);
                        }
                        if (this.movelist.get(i)[2] > this.movelist.get(i)[4]) {
                            swap(this.movelist.get(i), 2, 4);
                        }
                    }
                    this.movelist.sort(Comparator.comparingInt((int[] a) -> a[0]).thenComparingInt((int[] a) -> a[1]).thenComparingInt((int[] a) -> a[2]).thenComparingInt((int[] a) -> a[3]).thenComparingInt((int[] a) -> a[4]).thenComparingInt((int[] a) -> a[5]).thenComparingInt((int[] a) -> a[6]));
                }
            }
        }
    }

    public void swap(int[] array, int a, int b) {                  //only call this with a=even and b=uneven
        int[] temp = array.clone();
        array[a] = temp[b];
        array[a + 1] = temp[b + 1];
        array[b] = temp[a];
        array[b + 1] = temp[a + 1];
    }

    public int shorteval () {
        int sum = 0;
        for (int i = 0; i < 26; i++) {
            if (this.board.board[i] > 0) {
                sum = sum - (this.board.board[i] * i);
            } else if (this.board.board[i] < 0) {
                sum = sum - (this.board.board[i] * (25-i));
            }
        }
        return sum;
    }
    public double longeval () {
        double sum = this.shorteval();
        for (int i = 1; i < 25; i++) {
            if (this.white) {
                if (this.board.board[i] == 1) {
                    for (int j = 1; i - j > -1; j++) {
                        if (this.board.board[i-j] < 0) {
                            sum = sum - (risks[j-1] * (25-i));
                        }
                    }
                }
            } else {
                if (this.board.board[i] == -1) {
                    for (int j = 1; i + j < 26; j++) {
                        if (this.board.board[i+j] > 0) {
                            sum = sum + (risks[j-1] * i);
                        }
                    }
                }
            }
        }
        return sum;
    }
    
    public void stagefinder () {
        int points = 0;
        int lastw = 25;
        int lastb = 0;
        if (this.stage == 0) {
            if (this.white) {
                for (int i = 1; i < 25; i++) {
                    if (this.board.board[i] > 0) {
                        points++;
                    }
                }
            } else {
                for (int i = 1; i < 25; i++) {
                    if (this.board.board[i] < 0) {
                        points++;
                    }
                }
            }
            if (points > 7) {
                this.stage++;
            }
        } else if (this.stage == 1) {
            for (int i = 1; i < 26; i++) {
                if (this.board.board[i] > 0) {
                    lastw = i;
                }
            }
            for (int i = 24; i > -1; i--) {
                if (this.board.board[i] < 0) {
                    lastb = i;
                }
            }
            if (lastb > lastw) {
                this.stage++;
            }
        }
    }
}



/*
The bit I cut from movelisterb - might be the better solution, keeping this here to eventually go back to

if ((bear == 1) && (second.contains(lowest)) && (bar == 0)) {
            tempa = 100;
            for (int l = fbreak; l < first.size(); l++) {
                if (first.get(l) == tempa) {
                    tempa = 100;
                } else {
                    tempa = first.get(l);
                    this.movelist.add(new int[]{lowest, lowest + s, tempa, 26});
                }
            }
        } else {
            if ((bar > -1) || ((bar == -1) && (second.get(0) == 0))) {
                tempb = 100;
                for (int l = 0; l < sbreak; l++) {
                    if (second.get(l) == tempb) {
                        tempb = 100;
                    } else {
                        tempb = second.get(l);
                        if (((tempb + f + s < 25) && (!first.contains(tempb + s)) && (b.board[tempb + f + s] < 2)) || ((bear == 0) && (!first.contains(tempb + s)) && (tempb + s < 25) && (tempb + f + s > 24))) {
                            out = tempb + s + f < 25 ? tempb + s + f : 26;
                            this.movelist.add(new int[]{tempb, tempb + s, tempb + s, out});
                        }
                    }
                    if (tempb == 0) {
                        break;
                    }
                }
            }
        }
*/