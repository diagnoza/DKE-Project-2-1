/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonmcts;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Laurin
 * This is the current dice result that is part of a State and determines the possible moves. Calling it with the empty constructor gives a random result. Either constructor automatically doubles the steps
 * if a pair is rolled. When creating the list of possible moves from a State all indices of steps are considered.
 */
public class Roll {
    int[] steps;
    public Roll () {                                                    //constructors
        int a = ThreadLocalRandom.current().nextInt(1, 7);
        int b = ThreadLocalRandom.current().nextInt(1, 7);
        if (a == b) {
            steps = new int[4];
            steps[0] = a; steps[1] = a; steps[2] = a; steps[3] = a;
        } else {
            steps = new int[2];
            steps[0] = a; steps[1] = b;
        }
    }
    public Roll (int a, int b) {
        if (a == b) {
            steps = new int[4];
            steps[0] = a; steps[1] = a; steps[2] = a; steps[3] = a;
        } else {
            steps = new int[2];
            steps[0] = a; steps[1] = b;
        }
    }
    public int[] getsteps () {                                          //getters and setters
        return this.steps;
    }
    public void setsteps (int[] s) {
        this.steps = s;
    }
}
