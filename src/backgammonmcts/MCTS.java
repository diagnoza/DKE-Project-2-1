/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonmcts;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Laurin
 * semi- @author Rudy
 */
public class MCTS {

    int pruningfactor = 100;
    int randomfactor = pruningfactor;
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
    
    public int MCTSmostvisited (State s, int time) {
        long end = System.currentTimeMillis() + time;
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
    
    public int MCTShighestscore (State s, int time) {
        long end = System.currentTimeMillis() + time;
        tree = new Tree(new Node(s));
        Node winner;
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
        if (s.white) {
            winner = Collections.max(tree.root.children, Comparator.comparing(c -> c.state.score));
        } else {
            winner = Collections.min(tree.root.children, Comparator.comparing(c -> c.state.score));
        }
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
            current.prunenode(n);
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
    
    public int MCTShighestscorepruned (State s, int n, int time) {
        pruningfactor = n;
        long end = System.currentTimeMillis() + time;
        tree = new Tree(new Node(s));
        Node winner;
        //int debugb = 0;
        while (System.currentTimeMillis() < end) {
            Node current = selectnode(tree.root);
            current.expandnode();
            current.evalchildren(earlydefense, earlyblitz, earlyprime, earlyanchor, middefense, midblitz, midprime, midanchor);
            current.prunenode(n);
            Node rollout = current;
            if (!current.children.isEmpty()) {
                rollout = current.getRandomChild();
            }
            int result = randomplay(rollout);
            //debugb++;
            //System.out.println("Finished rollout number " +debugb+ ", winner is " + (result == 1 ? "White" : "Black") + ".");
            backpropagate(rollout, result);
        }
        if (s.white) {
            winner = Collections.max(tree.root.children, Comparator.comparing(c -> c.state.score));
        } else {
            winner = Collections.min(tree.root.children, Comparator.comparing(c -> c.state.score));
        } 
        //System.out.println("The winning node was visited " +winner.state.visits+ " times, and has a score of " +winner.state.score +".");
        return winner.siblingnumber;
    }
    
    public int MCTS_hsp_rootparallel (State s, int n, int time) throws InterruptedException {
        pruningfactor = n;
        tree = new Tree(new Node(s));
        Node winner;
        MultiMCTS mmctsa = new MultiMCTS(s, time, 1, n);
        MultiMCTS mmctsb = new MultiMCTS(s, time, 2, n);
        MultiMCTS mmctsc = new MultiMCTS(s, time, 3, n);
        MultiMCTS mmctsd = new MultiMCTS(s, time, 4, n);
        MultiMCTS mmctse = new MultiMCTS(s, time, 5, n);
        MultiMCTS mmctsf = new MultiMCTS(s, time, 6, n);
        MultiMCTS mmctsg = new MultiMCTS(s, time, 7, n);
        MultiMCTS mmctsh = new MultiMCTS(s, time, 8, n);
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
            tree.root.children.get(i).state.score =
                    mmctsa.threadtree.root.children.get(i).state.score +  
                    mmctsb.threadtree.root.children.get(i).state.score +
                    mmctsc.threadtree.root.children.get(i).state.score +
                    mmctsd.threadtree.root.children.get(i).state.score +
                    mmctse.threadtree.root.children.get(i).state.score +
                    mmctsf.threadtree.root.children.get(i).state.score +
                    mmctsg.threadtree.root.children.get(i).state.score +
                    mmctsh.threadtree.root.children.get(i).state.score;
        }
        if (s.white) {
            winner = Collections.max(tree.root.children, Comparator.comparing(c -> c.state.score));
        } else {
            winner = Collections.min(tree.root.children, Comparator.comparing(c -> c.state.score));
        }
        return winner.siblingnumber;
    }
    
    public int MCTS_mvp_vcrp (State s, int n, int t, int time) throws InterruptedException {
        pruningfactor = n;
        tree = new Tree(new Node(s));
        ExecutorService threadPool = Executors.newFixedThreadPool(t);
        ArrayList<MultiMCTS> list = new ArrayList<>();
        for (int i = 0; i < t; i++) {
            list.add(new MultiMCTS(s, time, i, n));
        }
        for (int i = 0; i < t; i++) {
            threadPool.submit(list.get(i));
        }
        threadPool.shutdown();
        threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        tree.root.expandnode();
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < (pruningfactor < tree.root.children.size() ? pruningfactor : tree.root.children.size()); j++) {
                tree.root.children.get(j).state.visits += list.get(i).threadtree.root.children.get(j).state.visits;
            }
        }
        Node winner = Collections.max(tree.root.children, Comparator.comparing(c -> c.state.visits));
        return winner.siblingnumber;
    }
    
    public int MCTS_hsp_vcrp (State s, int n, int t, int time) throws InterruptedException {
        pruningfactor = n;
        tree = new Tree(new Node(s));
        Node winner;
        ExecutorService threadPool = Executors.newFixedThreadPool(t);
        ArrayList<MultiMCTS> list = new ArrayList<>();
        for (int i = 0; i < t; i++) {
            list.add(new MultiMCTS(s, time, i, n));
        }
        for (int i = 0; i < t; i++) {
            threadPool.submit(list.get(i));
        }
        threadPool.shutdown();
        threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        tree.root.expandnode();
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < (pruningfactor < tree.root.children.size() ? pruningfactor : tree.root.children.size()); j++) {
                tree.root.children.get(j).state.score += list.get(i).threadtree.root.children.get(j).state.score;
            }
        }
        if (s.white) {
            winner = Collections.max(tree.root.children, Comparator.comparing(c -> c.state.score));
        } else {
            winner = Collections.min(tree.root.children, Comparator.comparing(c -> c.state.score));
        }
        return winner.siblingnumber;
    }
    
    public int fullrandom (State s) {
        tree = new Tree(new Node(s));
        Node current = tree.root;
        current.expandnode();
        int n = ThreadLocalRandom.current().nextInt(0, tree.root.children.size());
        return n;
    }
    
    public int MCTS_mvp_rootparallel (State s, int n, int time) throws InterruptedException {
        pruningfactor = n;
        tree = new Tree(new Node(s));
        MultiMCTS mmctsa = new MultiMCTS(s, time, 1, n);
        MultiMCTS mmctsb = new MultiMCTS(s, time, 2, n);
        MultiMCTS mmctsc = new MultiMCTS(s, time, 3, n);
        MultiMCTS mmctsd = new MultiMCTS(s, time, 4, n);
        MultiMCTS mmctse = new MultiMCTS(s, time, 5, n);
        MultiMCTS mmctsf = new MultiMCTS(s, time, 6, n);
        MultiMCTS mmctsg = new MultiMCTS(s, time, 7, n);
        MultiMCTS mmctsh = new MultiMCTS(s, time, 8, n);
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
        //System.out.println("The root of the tree in thread one has " +mmctsa.threadtree.root.children.size()+ " children.");
        //System.out.println("They have " + mmctsa.threadtree.root.children.get(0).state.visits + " and " + mmctsa.threadtree.root.children.get(1).state.visits + " visits, respectively.");
        //System.out.println("The root of the tree in thread two has " +mmctsb.threadtree.root.children.size()+ " children.");
        //System.out.println("They have " + mmctsb.threadtree.root.children.get(0).state.visits + " and " + mmctsb.threadtree.root.children.get(1).state.visits + " visits, respectively.");
        //System.out.println("The root of the tree in thread three has " +mmctsc.threadtree.root.children.size()+ " children.");
        //System.out.println("They have " + mmctsc.threadtree.root.children.get(0).state.visits + " and " + mmctsc.threadtree.root.children.get(1).state.visits + " visits, respectively.");
        //System.out.println("The root of the tree in thread four has " +mmctsd.threadtree.root.children.size()+ " children.");
        //System.out.println("They have " + mmctsd.threadtree.root.children.get(0).state.visits + " and " + mmctsd.threadtree.root.children.get(1).state.visits + " visits, respectively.");
        //System.out.println("The root of the tree in thread five has " +mmctse.threadtree.root.children.size()+ " children.");
        //System.out.println("They have " + mmctse.threadtree.root.children.get(0).state.visits + " and " + mmctse.threadtree.root.children.get(1).state.visits + " visits, respectively.");
        //System.out.println("The root of the tree in thread six has " +mmctsf.threadtree.root.children.size()+ " children.");
        //System.out.println("They have " + mmctsf.threadtree.root.children.get(0).state.visits + " and " + mmctsf.threadtree.root.children.get(1).state.visits + " visits, respectively.");
        //System.out.println("The root of the tree in thread seven has " +mmctsg.threadtree.root.children.size()+ " children.");
        //System.out.println("They have " + mmctsg.threadtree.root.children.get(0).state.visits + " and " + mmctsg.threadtree.root.children.get(1).state.visits + " visits, respectively.");
        //System.out.println("The root of the tree in thread eight has " +mmctsh.threadtree.root.children.size()+ " children.");
        //System.out.println("They have " + mmctsh.threadtree.root.children.get(0).state.visits + " and " + mmctsh.threadtree.root.children.get(1).state.visits + " visits, respectively.");
        
        //int x = 0;
        //int y = 0;
        //for (int i = 0; i < mmctsa.threadtree.root.children.size(); i++) {
        //    tree.root.children.get(mmctsa.threadtree.root.children.get(i).siblingnumber).state.visits += mmctsa.threadtree.root.children.get(i).state.visits;
        //}
        //for (int i = 0; i < mmctsb.threadtree.root.children.size(); i++) {
        //    tree.root.children.get(mmctsb.threadtree.root.children.get(i).siblingnumber).state.visits += mmctsb.threadtree.root.children.get(i).state.visits;
        //}
        //for (int i = 0; i < mmctsc.threadtree.root.children.size(); i++) {
        //   tree.root.children.get(mmctsc.threadtree.root.children.get(i).siblingnumber).state.visits += mmctsc.threadtree.root.children.get(i).state.visits;
        //}
        //for (int i = 0; i < mmctsd.threadtree.root.children.size(); i++) {
        //    tree.root.children.get(mmctsd.threadtree.root.children.get(i).siblingnumber).state.visits += mmctsd.threadtree.root.children.get(i).state.visits;
        //}
        //for (int i = 0; i < mmctse.threadtree.root.children.size(); i++) {
        //    tree.root.children.get(mmctse.threadtree.root.children.get(i).siblingnumber).state.visits += mmctse.threadtree.root.children.get(i).state.visits;
        //}
        //for (int i = 0; i < mmctsf.threadtree.root.children.size(); i++) {
        //    tree.root.children.get(mmctsf.threadtree.root.children.get(i).siblingnumber).state.visits += mmctsf.threadtree.root.children.get(i).state.visits;
        //}
        //for (int i = 0; i < mmctsg.threadtree.root.children.size(); i++) {
        //    tree.root.children.get(mmctsg.threadtree.root.children.get(i).siblingnumber).state.visits += mmctsg.threadtree.root.children.get(i).state.visits;
        //}
        //for (int i = 0; i < mmctsh.threadtree.root.children.size(); i++) {
        //    tree.root.children.get(mmctsh.threadtree.root.children.get(i).siblingnumber).state.visits += mmctsh.threadtree.root.children.get(i).state.visits;
        //}
        //System.out.println("The roots of the threadtrees from threads one through eight have " +mmctsa.threadtree.root.children.size()+ ", " +mmctsb.threadtree.root.children.size()+ ", " +mmctsc.threadtree.root.children.size()+ ", " +mmctsd.threadtree.root.children.size()+ ", " +mmctse.threadtree.root.children.size()+ ", " +mmctsf.threadtree.root.children.size()+ ", " +mmctsg.threadtree.root.children.size()+ ", " +mmctsh.threadtree.root.children.size()+ " children.");
        //if ((mmctsa.threadtree.root.children.size() == 0) || (mmctsb.threadtree.root.children.size() == 0) || (mmctsc.threadtree.root.children.size() == 0) || (mmctsd.threadtree.root.children.size() == 0) || (mmctse.threadtree.root.children.size() == 0) || (mmctsf.threadtree.root.children.size() == 0) || (mmctsg.threadtree.root.children.size() == 0) || (mmctsh.threadtree.root.children.size() == 0)) {
        //    System.out.println("The Boards for thread one through eight are:");
        //    System.out.println(Arrays.toString(mmctsa.threadtree.root.state.board.board));
        //    System.out.println(Arrays.toString(mmctsb.threadtree.root.state.board.board));
        //    System.out.println(Arrays.toString(mmctsc.threadtree.root.state.board.board));
        //    System.out.println(Arrays.toString(mmctsd.threadtree.root.state.board.board));
        //    System.out.println(Arrays.toString(mmctse.threadtree.root.state.board.board));
        //    System.out.println(Arrays.toString(mmctsf.threadtree.root.state.board.board));
        //    System.out.println(Arrays.toString(mmctsg.threadtree.root.state.board.board));
        //    System.out.println(Arrays.toString(mmctsh.threadtree.root.state.board.board));
        //    System.out.println("The White booleans for thread one through eight are:");
        //    if (mmctsa.threadtree.root.state.white) {
        //        System.out.println("True");
        //    } else {
        //        System.out.println("False");
        //    }
        //    if (mmctsb.threadtree.root.state.white) {
        //        System.out.println("True");
        //    } else {
        //        System.out.println("False");
        //    }
        //    if (mmctsc.threadtree.root.state.white) {
        //        System.out.println("True");
        //    } else {
        //        System.out.println("False");
        //    }
        //    if (mmctsd.threadtree.root.state.white) {
        //        System.out.println("True");
        //    } else {
        //        System.out.println("False");
        //    }
        //    if (mmctse.threadtree.root.state.white) {
        //        System.out.println("True");
        //    } else {
        //        System.out.println("False");
        //    }
        //    if (mmctsf.threadtree.root.state.white) {
        //        System.out.println("True");
        //    } else {
        //        System.out.println("False");
        //    }
        //    if (mmctsg.threadtree.root.state.white) {
        //        System.out.println("True");
        //    } else {
        //        System.out.println("False");
        //    }
        //    if (mmctsh.threadtree.root.state.white) {
        //        System.out.println("True");
        //    } else {
        //        System.out.println("False");
        //    }
        //}
            
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
        Node winner = Collections.max(tree.root.children, Comparator.comparing(c -> c.state.visits));
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
            temp.state.visits += Math.abs(r);
            if ((r > 0) && (temp.state.white)) {
                temp.state.score += r;
            }
            if ((r < 0) && (!temp.state.white)) {
                temp.state.score -= r;
            }
            temp = temp.parent;
        }
    }
    
    public int lightrollout(Node n) {
        Node temp = n;
        while (temp.state.winner == 0) {
            temp.expandnode();
            temp = temp.getRandomChild();
        }
        return temp.state.winner;
    }
    
    public int randomplay(Node n) {
        Node temp = n;
        while (temp.state.winner == 0) {
            temp.expandnode();
            totalnodesexpanded++;
            temp.evalchildren(earlydefense, earlyblitz, earlyprime, earlyanchor, middefense, midblitz, midprime, midanchor);
            temp.prunenode(pruningfactor);
            temp = temp.getRandomChild();
        }
        totalrollouts++;
        return temp.state.winner;
    }
    
    




}
