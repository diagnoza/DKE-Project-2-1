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
 */
public class MultiMCTS extends MCTS implements Runnable {           
    State state;
    int time;
    int id;
    Tree threadtree;
    int pruningfactor;
    int treepruning;
    
    @Override
    public void run() {
        //System.out.println("Thread " +id+ " started!");
        long end = System.currentTimeMillis() + time;
        threadtree = new Tree(new Node(state));
        do {
            Node current = selectnode(threadtree.root);
            current.expandnode();
            totalnodesexpanded++;
            if (treepruning > 0) {
                current.evalchildren(earlydefense, earlyblitz, earlyprime, earlyanchor, middefense, midblitz, midprime, midanchor);
                current.prunenode(pruningfactor);
            }
            Node rollout = current;
            if (!current.children.isEmpty()) {
                rollout = current.getRandomChild();
            }
            int result = randomplay(rollout);
            backpropagate(rollout, result);
        } while (System.currentTimeMillis() < end);
        //System.out.println("Thread " +id+ " finished with " +totalnodesexpanded+ " Nodes expanded in total!");
    }
    
    public MultiMCTS(State s, int timelimit, int i, int n, int m) {
        this.state = new State(s);
        this.time = timelimit;
        this.id = i;
        this.pruningfactor = n;
        this.treepruning = m;
    }
    
}
