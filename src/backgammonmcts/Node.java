/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonmcts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Laurin
 * This represents the individual Nodes on the Tree. Each Node has a parent (except the root), a list of children, and contains one particular State.
 */
public class Node {
    State state;
    Node parent;
    int siblingnumber;
    List<Node> children;
    public Node () {                                //constructors
    }
    public Node (State s) {
        this.state = s;
        this.children = new ArrayList<>();
    }
    public Node (State s, Node p, int n) {
        this.state = s;
        this.parent = p;
        this.siblingnumber = n;
        this.children = new ArrayList<>();
    }
    public Node (State s, Node p, List<Node> c, int n) {
        this.state = s;
        this.parent = p;
        this.siblingnumber = n;
        this.children = c;
    }
    public State getstate () {                      //getters and setters
        return this.state;
    }
    public Node getparent () {
        return this.parent;
    }
    public int getsiblingnumber () {
        return this.siblingnumber;
    }
    public List<Node> getchildren () {
        return this.children;
    }
    public void setstate (State s) {
        this.state = s;
    }
    public void setparent (Node p) {
        this.parent = p;
    }
    public void setsiblingnumber (int n) {
        this.siblingnumber = n;
    }
    public void setchildren (List<Node> c) {
        this.children = c;
    }
    public void expandnode() {
        if ((this.state.movelist.isEmpty()) && (!this.state.checked)) {
            this.state.movehelper(this.state.board, this.state.roll, this.state.white);
        } 
        if (this.children.isEmpty()) {
            if (this.state.movelist.isEmpty()) {
                this.children.add(new Node(new State(this.state, new int[]{}), this, 0));
            } else {
                for (int i = 0; i < this.state.movelist.size(); i++) {
                    this.children.add(new Node(new State(this.state, this.state.movelist.get(i)), this, i));
                }
            }
        }
    }
    public void evalchildren(double a, double b, double c, double d, double e, double f, double g, double h) {
        for (int i = 0; i < this.children.size(); i++) {
            switch (this.children.get(i).state.stage) {
                case 0:
                    this.children.get(i).state.eval = Evals.earlyeval(this.children.get(i).state, a);
                    if (b != 0) {
                        this.children.get(i).state.eval += Evals.blitzeval(this.children.get(i).state, b);
                    }   if (c != 0) {
                        this.children.get(i).state.eval += Evals.primeeval(this.children.get(i).state, c);
                    }   if (d != 0) {
                        this.children.get(i).state.eval += Evals.anchoreval(this.children.get(i).state, d);
                    }   break;
                case 1:
                    this.children.get(i).state.eval = Evals.longeval(this.children.get(i).state, e);
                    if (f != 0) {
                        this.children.get(i).state.eval += Evals.blitzeval(this.children.get(i).state, f);
                    }   if (g != 0) {
                        this.children.get(i).state.eval += Evals.primeeval(this.children.get(i).state, g);
                    }   if (h != 0) {
                        this.children.get(i).state.eval += Evals.anchoreval(this.children.get(i).state, h);
                    }   break;
                default:
                    this.children.get(i).state.eval = Evals.shorteval(this.children.get(i).state);
                    break;
            }
        }
    }
    public void prunenode() {
        if (this.children.size() > MCTS.pruningfactor) {
            if (this.state.white) {
                this.children.sort(Comparator.comparingDouble(a -> a.state.eval));
            } else {
                this.children.sort(Comparator.comparingDouble((Node a) -> a.state.eval).reversed());
            }
            this.children.subList(MCTS.pruningfactor, this.children.size()).clear();
        }
    }
    public void prunewithrandomness() {
        if (this.children.size() > MCTS.pruningfactor + MCTS.randomfactor) {
            if (this.state.white) {
                this.children.sort(Comparator.comparingDouble(a -> a.state.eval));
            } else {
                this.children.sort(Comparator.comparingDouble((Node a) -> a.state.eval).reversed());
            }
            Collections.shuffle(this.children.subList(MCTS.pruningfactor, MCTS.pruningfactor + MCTS.randomfactor));
            this.children.subList(MCTS.pruningfactor + MCTS.randomfactor, this.children.size()).clear();
        }
    }
    public Node getRandomChild() {
        int x = ThreadLocalRandom.current().nextInt(0, this.children.size());
        return this.children.get(x);
    }

    
}
