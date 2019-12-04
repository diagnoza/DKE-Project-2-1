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
 * semi- @author Rudy
 */
public class MCTS {

    static int pruningfactor = 100;
    static int randomfactor = pruningfactor;
    int totalnodesexpanded = 0;
    int totalrollouts = 0;
    Tree tree;
    double earlydefense = 0;
    double earlyblitz = 0;
    double earlyprime = 0;
    double earlyanchor = 0;
    double middefense = 0;
    double midblitz = 0;
    double midprime = 0;
    double midanchor = 0;
    
    
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
            current.evalchildren(earlydefense, earlyblitz, earlyprime, earlyanchor, middefense, midblitz, midprime, midanchor);
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
    
    public int MCTShighestscorepruned (State s, int n) {
        pruningfactor = n;
        long end = System.currentTimeMillis() + 3000;
        tree = new Tree(new Node(s));
        //int debugb = 0;
        while (System.currentTimeMillis() < end) {
            Node current = selectnode(tree.root);
            current.expandnode();
            current.evalchildren(earlydefense, earlyblitz, earlyprime, earlyanchor, middefense, midblitz, midprime, midanchor);
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
    
    public int MCTS_hsp_rootparallel (State s, int n, int time) throws InterruptedException {
        pruningfactor = n;
        tree = new Tree(new Node(s));
        MultiMCTS mmctsa = new MultiMCTS(s, time, 1);
        MultiMCTS mmctsb = new MultiMCTS(s, time, 2);
        MultiMCTS mmctsc = new MultiMCTS(s, time, 3);
        MultiMCTS mmctsd = new MultiMCTS(s, time, 4);
        MultiMCTS mmctse = new MultiMCTS(s, time, 5);
        MultiMCTS mmctsf = new MultiMCTS(s, time, 6);
        MultiMCTS mmctsg = new MultiMCTS(s, time, 7);
        MultiMCTS mmctsh = new MultiMCTS(s, time, 8);
        Thread one = new Thread(mmctsa);
        Thread two = new Thread(mmctsb);
        Thread three = new Thread(mmctsc);
        Thread four = new Thread(mmctsd);
        Thread five = new Thread(mmctse);
        Thread six = new Thread(mmctsf);
        Thread seven = new Thread(mmctsg);
        Thread eight = new Thread(mmctsh);
        one.start();
        two.start();
        three.start();
        four.start();
        five.start();
        six.start();
        seven.start();
        eight.start();
        one.join();
        two.join();
        three.join();
        four.join();
        five.join();
        six.join();
        seven.join();
        eight.join();
        tree.root.expandnode();
        for (int i = 0; i < (pruningfactor < tree.root.children.size() ? pruningfactor : tree.root.children.size()) ; i++) {
            tree.root.children.get(i).state.visits =
                    mmctsa.threadtree.root.children.get(i).state.visits +  
                    mmctsb.threadtree.root.children.get(i).state.visits +
                    mmctsc.threadtree.root.children.get(i).state.visits +
                    mmctsd.threadtree.root.children.get(i).state.visits +
                    mmctse.threadtree.root.children.get(i).state.visits +
                    mmctsf.threadtree.root.children.get(i).state.visits +
                    mmctsg.threadtree.root.children.get(i).state.visits +
                    mmctsh.threadtree.root.children.get(i).state.visits;
        }
        Node winner = Collections.max(tree.root.children, Comparator.comparing(c -> c.state.score));
        return winner.siblingnumber;
    }
    
    public void setpruningprofile(double a, double b, double c, double d, double e, double f, double g, double h) {
        this.earlydefense = a;
        this.earlyblitz = b;
        this.earlyprime = c;
        this.earlyanchor = d;
        this.middefense = e;
        this.midblitz = f;
        this.midprime = g;
        this.midanchor = h;
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
            temp.evalchildren(earlydefense, earlyblitz, earlyprime, earlyanchor, middefense, midblitz, midprime, midanchor);
            temp.prunenode();
            temp = temp.getRandomChild();
        }
        totalrollouts++;
        return temp.state.winner;
    }
    
    




}
