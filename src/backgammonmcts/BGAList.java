/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonmcts;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Laurin
 * Extending ArrayList so we can easily sort the list of positions for black and white. Index 0 is not included in the sorting method because it contains the number of positions.
 */
public class BGAList extends ArrayList {
    public void whitesort () {
        Collections.sort(this.subList(1, this.size()), Collections.reverseOrder());
    }
    public void blacksort () {
        Collections.sort(this.subList(1, this.size()));
    }
}
