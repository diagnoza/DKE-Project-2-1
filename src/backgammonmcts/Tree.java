/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonmcts;

/**
 *
 * @author Laurin
 * This is the Tree class. When you call it with a Node, you create a (sub)tree with that Node as its root.
 */
public class Tree {
    Node root;
    public Tree () {                                //constructors
    }
    public Tree (Node r) {
        this.root = r;
    }
    public Node getroot () {                        //getters and setters
        return this.root;
    }
    public void setroot (Node r) {
        this.root = r;
    }
}
