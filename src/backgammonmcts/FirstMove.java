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
 */
public class FirstMove {
    public static int[] opening (Roll r, boolean white) {
        int a = r.steps[0];
        int b = r.steps[1];
        if (a > b) {
            a = b;
            b = r.steps[0];
        }
        int x = ThreadLocalRandom.current().nextInt(0, 101);
        int[] answer = null;
        if (white) {
            if (a == 1) {
                if (b == 2) {
                    if (x < 3) {
                        answer = new int[]{24, 23, 23, 21};
                    } else if (x < 5) {
                        answer = new int[]{13, 12, 12, 10};
                    } else if (x < 41) {
                        answer = new int[]{13, 11, 6, 5};
                    } else {
                        answer = new int[]{24, 23, 13, 11};
                    }
                } else if (b == 3) {
                    answer = new int[]{8, 5, 6, 5};
                } else if (b == 4) {
                    if (x < 3) {
                        answer = new int[]{24, 23, 24, 20};
                    } else if (x < 5) {
                        answer = new int[]{13, 12, 12, 8};
                    } else if (x < 25) {
                        answer = new int[]{13, 9, 6, 5};
                    } else {
                        answer = new int[]{24, 23, 13, 9};
                    }
                } else if (b == 5) {
                    if (x < 4) {
                        answer = new int[]{24, 23, 23, 18};
                    } else if (x < 28) {
                        answer = new int[]{13, 8, 6, 5};
                    } else {
                        answer = new int[]{24, 23, 13, 8};
                    }
                } else if (b == 6) {
                    answer = new int[]{13, 7, 8, 7};
                }
            } else if (a == 2) {
                if (b == 3) {
                    if (x < 7) {
                        answer = new int[]{24, 22, 13, 10};
                    } else if (x < 48) {
                        answer = new int[]{13, 11, 13, 10};
                    } else {
                        answer = new int[]{24, 21, 13, 11};
                    }
                } else if (b == 4) {
                    answer = new int[]{8, 4, 6, 4};
                } else if (b == 5) {
                    if (x < 2) {
                        answer = new int[]{13, 8, 6, 4};
                    } else if (x < 45) {
                        answer = new int[]{24, 22, 13, 8};
                    } else {
                        answer = new int[]{13, 11, 13, 8};
                    }
                } else if (b == 6) {
                    if (x < 2) {
                        answer = new int[]{13, 11, 13, 7};
                    } else if (x < 8) {
                        answer = new int[]{13, 11, 11, 5};
                    } else if (x < 18) {
                        answer = new int[]{24, 22, 22, 16};
                    } else {
                        answer = new int[]{24, 18, 13, 11};
                    }
                }
            } else if (a == 3) {
                if (b == 4) {
                    if (x < 4) {
                        answer = new int[]{24, 21, 24, 20};
                    } else if (x < 29) {
                        answer = new int[]{24, 21, 13, 9};
                    } else if (x < 61) {
                        answer = new int[]{13, 10, 13, 9};
                    } else {
                        answer = new int[]{24, 20, 13, 10};
                    }
                } else if (b == 5) {
                    if (x < 2) {
                        answer = new int[]{13, 10, 13, 8};
                    } else {
                        answer = new int[]{8, 3, 6, 3};
                    }
                } else if (b == 6) {
                    if (x < 2) {
                        answer = new int[]{13, 10, 13, 7};
                    } else if (x < 23) {
                        answer = new int[]{24, 21, 21, 15};
                    } else {
                        answer = new int[]{24, 18, 13, 10};
                    }
                }
            } else if (a == 4) {
                if (b == 5) {
                    if (x < 7) {
                        answer = new int[]{24, 20, 20, 15};
                    } else if (x < 38) {
                        answer = new int[]{13, 9, 13, 8};
                    } else {
                        answer = new int[]{24, 20, 13, 8};
                    }
                } else if (b == 6) {
                    if (x < 28) {
                        answer = new int[]{8, 2, 6, 2};
                    } else if (x < 63) {
                        answer = new int[]{24, 20, 20, 14};
                    } else {
                        answer = new int[]{24, 18, 13, 9};
                    }
                }
            } else if (a == 5) {
                answer = new int[]{24, 19, 19, 13};
            }
        } else {
            if (a == 1) {
                if (b == 2) {
                    if (x < 3) {
                        answer = new int[]{1, 2, 2, 4};
                    } else if (x < 5) {
                        answer = new int[]{12, 13, 13, 15};
                    } else if (x < 41) {
                        answer = new int[]{12, 14, 19, 20};
                    } else {
                        answer = new int[]{1, 2, 12, 14};
                    }
                } else if (b == 3) {
                    answer = new int[]{17, 20, 19, 20};
                } else if (b == 4) {
                    if (x < 3) {
                        answer = new int[]{1, 2, 1, 5};
                    } else if (x < 5) {
                        answer = new int[]{12, 13, 13, 17};
                    } else if (x < 25) {
                        answer = new int[]{12, 16, 19, 20};
                    } else {
                        answer = new int[]{1, 2, 12, 16};
                    }
                } else if (b == 5) {
                    if (x < 4) {
                        answer = new int[]{1, 2, 2, 7};
                    } else if (x < 28) {
                        answer = new int[]{12, 17, 19, 20};
                    } else {
                        answer = new int[]{1, 2, 12, 17};
                    }
                } else if (b == 6) {
                    answer = new int[]{12, 18, 17, 18};
                }
            } else if (a == 2) {
                if (b == 3) {
                    if (x < 7) {
                        answer = new int[]{1, 3, 12, 15};
                    } else if (x < 48) {
                        answer = new int[]{12, 14, 12, 15};
                    } else {
                        answer = new int[]{1, 4, 12, 14};
                    }
                } else if (b == 4) {
                    answer = new int[]{17, 21, 19, 21};
                } else if (b == 5) {
                    if (x < 2) {
                        answer = new int[]{12, 17, 19, 21};
                    } else if (x < 45) {
                        answer = new int[]{1, 3, 12, 17};
                    } else {
                        answer = new int[]{12, 14, 12, 17};
                    }
                } else if (b == 6) {
                    if (x < 2) {
                        answer = new int[]{12, 14, 12, 18};
                    } else if (x < 8) {
                        answer = new int[]{12, 14, 14, 20};
                    } else if (x < 18) {
                        answer = new int[]{1, 3, 3, 9};
                    } else {
                        answer = new int[]{1, 7, 12, 14};
                    }
                }
            } else if (a == 3) {
                if (b == 4) {
                    if (x < 4) {
                        answer = new int[]{1, 4, 1, 5};
                    } else if (x < 29) {
                        answer = new int[]{1, 4, 12, 16};
                    } else if (x < 61) {
                        answer = new int[]{12, 15, 12, 16};
                    } else {
                        answer = new int[]{1, 5, 12, 15};
                    }
                } else if (b == 5) {
                    if (x < 2) {
                        answer = new int[]{12, 15, 12, 17};
                    } else {
                        answer = new int[]{17, 22, 19, 22};
                    }
                } else if (b == 6) {
                    if (x < 2) {
                        answer = new int[]{12, 15, 12, 18};
                    } else if (x < 23) {
                        answer = new int[]{1, 4, 4, 10};
                    } else {
                        answer = new int[]{1, 7, 12, 15};
                    }
                }
            } else if (a == 4) {
                if (b == 5) {
                    if (x < 7) {
                        answer = new int[]{1, 5, 5, 10};
                    } else if (x < 38) {
                        answer = new int[]{12, 16, 12, 17};
                    } else {
                        answer = new int[]{1, 5, 12, 17};
                    }
                } else if (b == 6) {
                    if (x < 28) {
                        answer = new int[]{17, 23, 19, 23};
                    } else if (x < 63) {
                        answer = new int[]{1, 5, 5, 11};
                    } else {
                        answer = new int[]{1, 7, 12, 16};
                    }
                }
            } else if (a == 5) {
                answer = new int[]{1, 6, 6, 12};
            }
        }
        return answer;
    }
}
