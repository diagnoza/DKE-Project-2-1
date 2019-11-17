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
public class MCTS {

    static int pruningfactor = 100;
    static int randomfactor = pruningfactor;
    int totalnodesexpanded = 0;
    int totalrollouts = 0;
    Tree tree;
    
    
    public MCTS() {
        tree = new Tree();
    }
    
    public int MCTSmostvisited (State s) {
        long end = System.currentTimeMillis() + 12000;
        tree = new Tree(new Node(s));
        //int debugb = 0;
        while (System.currentTimeMillis() < end) {
            Node current = selectnode(tree.root);
            current.expandnode();
            Node rollout = current;
            if (!current.children.isEmpty()) {
                rollout = current.getRandomChild();
            }
            int result = randomplay(rollout);
            //debugb++;
            //System.out.println("Finished rollout number " +debugb+ ", winner is " + (result == 1 ? "White" : "Black") + ".");
            backpropagate(rollout, result);
        }
        Node winner = Collections.max(tree.root.children, Comparator.comparing(c -> c.state.visits));
        //System.out.println("The winning node was visited " +winner.state.visits+ " times, and has a score of " +winner.state.score +".");
        return winner.siblingnumber;
    }
    
    public int MCTSmostvisitedpruned (State s, int n, int time) {
        pruningfactor = n;
        long end = System.currentTimeMillis() + time;
        tree = new Tree(new Node(s));
        //int debugb = 0;
        while (System.currentTimeMillis() < end) {
            Node current = selectnode(tree.root);
            current.expandnode();
            totalnodesexpanded++;
            current.prunenode();
            Node rollout = current;
            if (!current.children.isEmpty()) {
                rollout = current.getRandomChild();
            }
            int result = randomplay(rollout);
            //debugb++;
            //System.out.println("Finished rollout number " +debugb+ ", winner is " + (result == 1 ? "White" : "Black") + ".");
            backpropagate(rollout, result);
        }
        Node winner = Collections.max(tree.root.children, Comparator.comparing(c -> c.state.visits));   
        //System.out.println("The winning node was visited " +winner.state.visits+ " times, and has a score of " +winner.state.score +".");
        return winner.siblingnumber;
    }
    
    public double MCTShighestscorepruned (State s, int n) {
        pruningfactor = n;
        long end = System.currentTimeMillis() + 3000;
        tree = new Tree(new Node(s));
        //int debugb = 0;
        while (System.currentTimeMillis() < end) {
            Node current = selectnode(tree.root);
            current.expandnode();
            current.prunenode();
            Node rollout = current;
            if (!current.children.isEmpty()) {
                rollout = current.getRandomChild();
            }
            int result = randomplay(rollout);
            //debugb++;
            //System.out.println("Finished rollout number " +debugb+ ", winner is " + (result == 1 ? "White" : "Black") + ".");
            backpropagate(rollout, result);
        }
        Node winner = Collections.max(tree.root.children, Comparator.comparing(c -> c.state.score));   
        //System.out.println("The winning node was visited " +winner.state.visits+ " times, and has a score of " +winner.state.score +".");
        return winner.siblingnumber;
    }

    public Node selectnode(Node n) {
        Node node = n;
        while (!node.children.isEmpty()) {
            node = UCT.findbestnode(node);
        }
        return node;
    }
    
    public void backpropagate(Node n, int r) {
        Node temp = n;
        while (temp != null) {
            temp.state.visits++;
            if ((r > 0) && (temp.state.white)) {
                temp.state.score++;
            }
            if ((r < 0) && (!temp.state.white)) {
                temp.state.score++;
            }
            temp = temp.parent;
        }
    }
    
    public int randomplay(Node n) {
        Node temp = n;
        while (temp.state.winner == 0) {
            temp.expandnode();
            totalnodesexpanded++;
            temp.prunenode();
            temp = temp.getRandomChild();
        }
        totalrollouts++;
        return temp.state.winner;
    }
    
    




}
