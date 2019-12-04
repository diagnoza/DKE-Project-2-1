/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonmcts;

/**
 *
 * @author Laurin
 */
public class Evals {
    
    static double[] risks = new double[]{0.305, 0.319, 0.347, 0.361, 0.361, 0.388, 0.083, 0.083, 0.069, 0.041, 0.027, 0.041, 0.0, 0.0, 0.013, 0.013, 0.0, 0.013, 0.0, 0.013, 0.0, 0.0, 0.0, 0.013};
    
    public static int shorteval (State s) {
        int sum = 0;
        for (int i = 0; i < 26; i++) {
            if (s.board.board[i] > 0) {
                sum = sum - (s.board.board[i] * i);
            } else if (s.board.board[i] < 0) {
                sum = sum - (s.board.board[i] * (25-i));
            }
        }
        return sum;
    }
    
    public static double longeval (State s, double n) {
        double sum = s.shorteval();
        for (int i = 1; i < 25; i++) {
            if (s.white) {
                if (s.board.board[i] == 1) {
                    for (int j = 1; i - j > -1; j++) {
                        if (s.board.board[i-j] < 0) {
                            sum = sum - (risks[j-1] * (25-i) * n);
                        }
                    }
                }
            } else {
                if (s.board.board[i] == -1) {
                    for (int j = 1; i + j < 26; j++) {
                        if (s.board.board[i+j] > 0) {
                            sum = sum + (risks[j-1] * i * n);
                        }
                    }
                }
            }
        }
        return sum;
    }
    
    public static double earlyeval (State s, double n) {
        double sum = s.shorteval();
        for (int i = 1; i < 25; i++) {
            if (s.white) {
                if ((s.board.board[i] == 1) && ((i > 12) || (i < 7))) {
                    for (int j = 1; i - j > -1; j++) {
                        if (s.board.board[i-j] < 0) {
                            sum = sum - (risks[j-1] * (25-i) * n);
                        }
                    }
                }
            } else {
                if ((s.board.board[i] == -1) && ((i < 13) || (i > 18))) {
                    for (int j = 1; i + j < 26; j++) {
                        if (s.board.board[i+j] > 0) {
                            sum = sum + (risks[j-1] * i * n);
                        }
                    }
                }
            }
        }
        return sum;
    }    
    
    public static double blitzeval (State s, double n) {
        if (s.white) {
            return s.board.board[0]* -n;
        } else {
            return s.board.board[25]* -n;
        }
    }
    
    public static double primeeval (State s, double n) {
        double total = 0;
        double current = 0;
        int row = 0;
        if (s.white) {
            for (int i = 1; i < 25; i++) {
                if (s.board.board[i] > 1) {
                    if (i < 7) {
                        current += 1.5;
                        row++;
                    } else {
                        current++;
                        row++;
                    }
                } else {
                    total += current*row;
                    current = 0;
                    row = 0;
                }
            }
            return total*n;
        } else {
            for (int i = 1; i < 25; i++) {
                if (s.board.board[i] < -1) {
                    if (i > 18) {
                        current += 1.5;
                        row++;
                    } else {
                        current++;
                        row++;
                    }
                } else {
                    total += current*row;
                    current = 0;
                    row = 0;
                }
            }
            return -total*n;
        }
    }
    
    public static double anchoreval (State s, double n) {
        double total = 0;
        double current = 0;
        int row = 0;
        if (s.white) {
            for (int i = 19; i < 25; i++) {
                if (s.board.board[i] > 1) {
                    current++;
                    row++;
                } else {
                    total += current*row;
                    current = 0;
                    row = 0;
                }
            }
            return total*n;
        } else {
            for (int i = 1; i < 7; i++) {
                if (s.board.board[i] < -1) {
                    current++;
                    row++;
                } else {
                    total += current*row;
                    current = 0;
                    row = 0;
                }
            }
            return -total*n;
        }
    }
    
    
    
}
