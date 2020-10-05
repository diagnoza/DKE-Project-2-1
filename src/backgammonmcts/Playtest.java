/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonmcts;

/**
 *
 * @author Laurin
 */
public class Playtest {
    public boolean play(MCTS_Strategy wStrategy, MCTS_Strategy bStrategy, int numberOfMatches) throws InterruptedException{
        MCTS w = wStrategy.mcts;
        MCTS b = bStrategy.mcts;
        Board test;
        Roll r;
        State start;
        State next;
        boolean white;
        int whitewins = 0;
        int blackwins = 0;
        int n;
        int x1 = 100;                                         // this is the pruningfactor
        int y1 = 1;                                         // this is the tree-pruningfactor
        int x2 = 100;                                         // and again for player 2
        int y2 = 1;                                         // and again
        assert (numberOfMatches % 1 != 0);              // this uses an assertion to verify that the number of matches is uneven. Assertions are disabled in Java by default, so you could enable them, or you could rewrite this to throw an exception if numberOfMatches is even, or you could just never call the method with an uneven numberOfMatches.
        int z = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < numberOfMatches; i++) {
            test = new Board();
            r = new Roll();
            while (r.steps[0] == r.steps[1]) {
                r = new Roll();
            }
            white = (r.steps[0] > r.steps[1]);
            start = new State(test, white, 0, 0, 0, r);
            n = 0;
            next = new State();
            while (start.wincheck() == 0) {
                if (start.white) {
                    n = w.MCTS_mvp_vcrp(start, x1, y1, z, 1000);
                    if (n == -1) {
                        next = new State(start, new int[]{});
                    } else {
                        next = new State(start, w.tree.root.state.movelist.get(n));
                    }
                    start = next;
                } else {
                    n = b.MCTS_mvp_vcrp(start, x2, y2, z, 1000);
                    if (n == -1) {
                        next = new State(start, new int[]{});
                    } else {
                        next = new State(start, b.tree.root.state.movelist.get(n));
                    }
                    start = next;
                }
            }
            if (start.wincheck() > 0) {
                whitewins++;
            } else {
                blackwins++;
            }
    
        }
        if (whitewins > blackwins) {
            return true;
        } else {
            return false;
        }
    }
}
