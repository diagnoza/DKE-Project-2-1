package backgammonmcts;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the evaluation function found in this paper
 * https://bkgm.com/articles/Berliner/BKG-AProgramThatPlaysBackgammon/#-sec-IV-E
 */

public class EvalBKG {

    private double heuristicPoints;
    private Board board;
    private State state;


    public EvalBKG() {


    }

    /**
     *
     */
    private int blotDangerCalculation() {

        // Compute Piploss
        int piploss = 0;
        if(this.state.white){
            for(int i=25; i>0; i--){
                if(this.board.board[i] == 1) {
                    List<hitter> hitters = computeHits(i);

                }
            }
        }

        return piploss;

    }

    private List<hitter> computeHits(int pos) {
        int[] possibleHits = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 15, 16, 18, 20, 24};
        ArrayList<hitter> hitters = new ArrayList<hitter>();
        if (this.state.white) {
            for (int hit : possibleHits) {
                if (this.board.board[pos - hit] < 0)
                    hitters.add(new hitter(pos - hit, pos));
            }
        }

        return hitters;
    }


    /**
     * local class that represents one piece on the board that might hit a blot.
     */
    class hitter {


        private int hitterIndex;
        private int blotIndex;
        private ArrayList<int[]> combinations;

        /**
         * @param hitterIndex  position of the hitter
         * @param blotIndex position of the blot
         */
        hitter(int hitterIndex, int blotIndex) {
            this.hitterIndex = hitterIndex;
            this.blotIndex = blotIndex;
            this.computeCombinations();
        }

        /**
         * This is a helper function to determine possible combinations of dice rolls for each distance
         */
        private void computeCombinations(){
            for(int i=1; i<=6; i++){
                for(int j=1; j<=6; j++) {
                    int distance = this.blotIndex - this.hitterIndex;
                    if(distance == i+j || distance == i || distance == j){
                        if (state.white && board.board[i] > -2)
                            this.combinations.add(new int[] {i, j});
                        else if(!state.white && board.board[i] < 2)
                            this.combinations.add(new int[] {i, j});
                    }
                }

            }

        }

    }


}
