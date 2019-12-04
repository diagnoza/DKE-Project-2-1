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
    
    @Override
    public void run() {
        //System.out.println("Thread " +id+ " started!");
        long end = System.currentTimeMillis() + time;
        threadtree = new Tree(new Node(state));
        while (System.currentTimeMillis() < end) {
            Node current = selectnode(threadtree.root);
            current.expandnode();
            totalnodesexpanded++;
            current.evalchildren(earlydefense, earlyblitz, earlyprime, earlyanchor, middefense, midblitz, midprime, midanchor);
            current.prunenode();
            Node rollout = current;
            if (!current.children.isEmpty()) {
                rollout = current.getRandomChild();
            }
            int result = randomplay(rollout);
            backpropagate(rollout, result);
        }
        //System.out.println("Thread " +id+ " finished with " +totalnodesexpanded+ " Nodes expanded in total!");
    }
    
    public MultiMCTS(State s, int timelimit, int i) {
        this.state = new State(s);
        this.time = timelimit;
        this.id = i;
    }
    
}
