package backgammonmcts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

// TODO: Check bar
/**
 * This class implements the evaluation function found in this paper
 * https://bkgm.com/articles/Berliner/BKG-AProgramThatPlaysBackgammon/#-sec-IV-E
 */

public class EvalBKG {

    private double heuristicPoints;
    private State state;


    EvalBKG(State state) {
        this.state = state;
    }

    private int nChooseK(int n, int k){
        return factorial(n)/(factorial(k)*factorial(n-k));
    }

    private int factorial(int num){
        if(num <= 1)
            return 1;
        else
            return num*factorial(num-1);
    }

    double getHeuristicPoints() {
        return heuristicPoints;
    }

    /**
     *
     */
    private int[] blotDangerCalculation() {

        // Compute Piploss
        int piploss = 0;
        HashSet<HashSet<Integer>> distinctCombinations = new HashSet<>();
        int p2 = 0;
        if(this.state.white){
            for(int i=24; i>0; i--){
                if(this.state.board.board[i] == 1) {
                    List<Hitter> hitters = computeHits(i);
                    int oneDieHitters = 0;
                    for(Hitter h: hitters) {
                        piploss += (25 - i) * h.getCombinations().size();
                        distinctCombinations.addAll(h.getCombinations());
                        if(h.isUsesSingleDie()) {
                            oneDieHitters++;
                            if (state.board.board[h.getHitterIndex()] <= -2)
                                p2++;
                        }
                        for(HashSet<Integer> comb: h.getCombinations()){
                            for(int el: comb){
                                System.out.print(el + ", ");
                            }
                            System.out.println();
                        }
                    }
                    p2 += nChooseK(oneDieHitters, 2);
                }
            }
        }
        // add black
        int[] P = new int[]{piploss, distinctCombinations.size(), p2 + Hitter.getDoubleHits()};
        heuristicPoints = -(double)(P[0] + 3*P[1] + 2*P[2]);
        return P;
    }

    private List<Hitter> computeHits(int pos) {
        int[] possibleHits = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 15, 16, 18, 20, 24};
        ArrayList<Hitter> hitters = new ArrayList<Hitter>();
        if (this.state.white) {
            for (int hit : possibleHits) {
                 int distance = pos - hit;
                 if (distance > 1 && this.state.board.board[pos - hit] < 0)
                    hitters.add(new Hitter(pos - hit, pos, state));
            }
        }
        // add black

        return hitters;
    }

    private static void testBlotDanger(State stat){
        EvalBKG eval = new EvalBKG(stat);
        int [] blotDanger = eval.blotDangerCalculation();

        for(int num: blotDanger){
            System.out.println(num);
        }
        System.out.println("Heuristic Points: " + eval.getHeuristicPoints());
    }

//public static void main(String[] args){
//
//    // Test for beginning of the game. Should return [0,0,0]
//    System.out.println("Test 1:");
//    Board board = new Board();
//    State stat = new State();
//    stat.board = board;
//    stat.white = true;
//    testBlotDanger(stat);
//    System.out.println("====================================");
//
//    // Test for one blot at 21 and one hitter at 17. Should return [36,9,0]
//    System.out.println("Test 2:");
//    board.setboard(new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,0,0,0,1,0,0,0,0});
//    State stat2 = new State();
//    stat2.board = board;
//    stat2.white = true;
//    testBlotDanger(stat2);
//    System.out.println("====================================");
//
//    // Test for one blot at 21 and 2 hitters at 17. Should return [36,9,1]
//    System.out.println("Test 3:");
//    board.setboard(new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-2,0,0,0,1,0,0,0,0});
//    State stat3 = new State();
//    stat3.board = board;
//    stat3.white = true;
//    testBlotDanger(stat3);
//    System.out.println("====================================");
//
//    // Test for one blot at 21 and 2 hitters at 17. Should return [36,9,1]
//    System.out.println("Test 4:");
//    board.setboard(new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-3,0,0,0,1,0,0,0,0});
//    State stat4 = new State();
//    stat4.board = board;
//    stat4.white = true;
//    testBlotDanger(stat4);
//    System.out.println("====================================");
//
//    // Test for two whites at 21 and 2 hitters at 17. Should return [0,0,0]
//    System.out.println("Test 5:");
//    board.setboard(new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-3,0,0,0,2,0,0,0,0});
//    State stat5 = new State();
//    stat5.board = board;
//    stat5.white = true;
//    testBlotDanger(stat5);
//    System.out.println("====================================");
//
//    // Test for blots at 20, 21 and 1 hitters at 17. Should return [0,0,0]
//    System.out.println("Test 6:");
//    board.setboard(new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-3,0,0,1,1,0,0,0,0});
//    State stat6 = new State();
//    stat6.board = board;
//    stat6.white = true;
//    testBlotDanger(stat6);
//    System.out.println("===================================="); // TODO: It counts 3,4 as a way of hitting both. That is wrong.
//}



}
