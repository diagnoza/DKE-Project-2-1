package backgammonmcts;

import java.io.Serializable;

/**
 * Local class to represent a specific strategy. This is necessary because firstMove and threadcount are not
 * parameters of the MCTS class.
 */
public class MCTS_Strategy implements Serializable, Comparable<MCTS_Strategy> { // TODO: Rewrite to not include MCTS object

    MCTS mcts;
    boolean firstMove;
    int threadCount;
    int winCount;

    /**
     *
     * @param mcts mcts object containing pruning parameters
     * @param firstMove This field determines if the first move of the game is retrieved from a known table.
     * @param threadCount This field sets the number of threads. Might be better to not do this manually. (Not used.
     *                    Threads are determined automatically.)
     */
    MCTS_Strategy(MCTS mcts, boolean firstMove, int threadCount){
        this.mcts = mcts;
        this.firstMove = firstMove;
        this.threadCount = threadCount;
        this.winCount = 0;
    }

    MCTS_Strategy(MCTS mcts, boolean firstMove, int threadCount, int winCount){
        this.mcts = mcts;
        this.firstMove = firstMove;
        this.threadCount = threadCount;
        this.winCount = winCount;
    }

    @Override
    public int compareTo(MCTS_Strategy o){
        return Integer.compare(this.winCount, o.winCount);
    }

    /**
     *
     * @return a deep copy of the MCTS_Strategy
     */
     MCTS_Strategy copy() {
        MCTS mcts = new MCTS();
        mcts.setpruningprofile(this.mcts.earlydefense, this.mcts.earlyblitz, this.mcts.earlyprime, this.mcts.earlyanchor,
                this.mcts.middefense, this.mcts.midblitz, this.mcts.midprime, this.mcts.midanchor);
        return new MCTS_Strategy(mcts, this.firstMove, this.threadCount, this.winCount);
    }
}
