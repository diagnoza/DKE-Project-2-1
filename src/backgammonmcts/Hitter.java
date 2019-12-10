package backgammonmcts;

import java.util.HashSet;


/**
 * class that represents one piece on the board that might hit a blot.
 */
class Hitter {

    private static int doubleHits;
    private static HashSet<Integer> oneDieDistances;

    static {
        HashSet<Integer> oneDieDistances = new HashSet<>();
        int doubleHits = 0;
    }

    private HashSet<HashSet<Integer>> combinations;
    private int hitterIndex;
    private int blotIndex;
    private boolean usesSingleDie;
    private State state;


    /**
     * @param hitterIndex position of the hitter
     * @param blotIndex   position of the blot
     */
    Hitter(int hitterIndex, int blotIndex, State state) {
        this.hitterIndex = hitterIndex;
        this.blotIndex = blotIndex;
        this.state = state;
        this.usesSingleDie = false;
        this.combinations = new HashSet<>();
        this.computeCombinations();
    }

    /**
     * This method resets doubleHits and oneDieDistances, because these have to be unique for every iteration of
     * blotDangerCalculation in EvalBKG.
     *
     * @return number of ways that more than one blot can be hit.
     */
    static int getDoubleHits() {
        int d = doubleHits;
        doubleHits = 0;
        oneDieDistances = new HashSet<>();
        return d;
    }

    HashSet<HashSet<Integer>> getCombinations() {
        return combinations;
    }

    boolean isUsesSingleDie() {
        return usesSingleDie;
    }

    int getHitterIndex() {
        return hitterIndex;
    }

    private void addCombination(int i, int j) {
        HashSet<Integer> combination = new HashSet<>();
        combination.add(i);
        combination.add(j);
        this.combinations.add(combination);
    }

    /**
     * This is a helper function to determine possible combinations of die rolls for each distance
     */
    private void computeCombinations() {
        boolean jIn = false;
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 6; j++) {
                int distance = Math.abs(this.blotIndex - this.hitterIndex);
                if (distance == i) {
                    usesSingleDie = true;
                    oneDieDistances.add(i);
                    addCombination(i, j);
                    if (oneDieDistances.contains(j) && !jIn)
                        doubleHits++;
                } else if (distance == j) {
                    usesSingleDie = true;
                    oneDieDistances.add(j);
                    jIn = true;
                    addCombination(i, j);
                    if (oneDieDistances.contains(i))
                        doubleHits++;
                } else if (distance == i + j) {
                    if (state.white
                            && state.board.board[this.blotIndex - i] > -2
                            && state.board.board[this.blotIndex - j] > -2) {
                        addCombination(i, j);
                    }
                } else if (i == j) {
                    if (state.white
                            && state.board.board[this.blotIndex - i] > -2
                            && state.board.board[this.blotIndex - 2 * i] > -2) {
                        if (distance == 3 * i)
                            addCombination(i, j);
                        else if (distance == 4 * i && state.board.board[this.blotIndex - 3 * i] > -2)
                            addCombination(i, j);
                    }
                    // add black
                }

            }
        }

    }

}