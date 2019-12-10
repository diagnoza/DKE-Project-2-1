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
public class UCT {
    static final double exfactor = 1.41;
    public static double UCTvalue (boolean viewpoint, int totalvisits, double score, int visits) {
        if (visits == 0) {
            return Integer.MAX_VALUE;
        }
        double ex = (score / (double) visits);
        if (viewpoint) {
            return ex + exfactor *(Math.sqrt((Math.log(totalvisits)) / (double) visits));
        } else {
            return (1-ex) + exfactor *(Math.sqrt((Math.log(totalvisits)) / (double) visits));
        }
    }
    
    public static Node findbestnode (Node n) {
        int parentvisits = n.state.visits;
        boolean viewpoint = n.state.white;
        return Collections.max(n.children, Comparator.comparing(c -> UCTvalue(viewpoint, parentvisits, c.state.score, c.state.visits)));
    }

    
}
