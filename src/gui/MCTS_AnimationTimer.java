package gui;

import backgammonmcts.MCTS;
import backgammonmcts.State;
import javafx.animation.AnimationTimer;

import static gui.AIvAI.setupPieces;

class MCTS_AnimationTimer extends AnimationTimer {

    State s;
    MCTS w;
    MCTS b;
    String variant1 = Settings.player1;
    String variant2 = Settings.player2;
    /**
     *
     * @param s State of the game at time of creation
     * @param w First MCTS object playing white
     * @param b Second MCTS object playing black
     * @param variant The type of MCTS used to determine the next move
     */
    MCTS_AnimationTimer(State s, MCTS w, MCTS b, String variant){
        this.s = s;
        this.w = w;
        this.b = b;
//        this.variant = variant;
    }

    /**
     * This method makes sure that the GUI is constantly updat.
     *      * @param now The timestamp of the current frame given in nanoseconds.
     *      *           This valueed after a move has been made by the AI, by re-rendering
     * the scene up to 60 times per second will be the same for all AnimationTimers called during one frame.
     */
    @Override
    public void handle(long now) {
        if(s.getwhite()){
            System.out.println("[");
            for(int u =0; u<s.board.board.length;u++){
                System.out.print(s.board.board[u]+" ");
            }
            System.out.println("]");
            s = w.doStep(s, variant1);
        } else {
            System.out.println("[");
            for(int u =0; u<s.board.board.length;u++){
                System.out.print(s.board.board[u]+" ");
            }
            System.out.println("]");
            s  = b.doStep(s, variant2);
        }
        if(s.wincheck() != 0) this.stop();
        setupPieces(s.getboard().board);
    }
}
