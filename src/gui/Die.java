import java.util.Random;

public class Die {

    private static final Random random = new Random();
    private int score;
    private boolean used = false;

    public Die() {
    }

    public int roll() {
        score = random.nextInt(6) + 1;
        return score;
    }

    public void resetMove(){
      used = false;
    }

    public int getScore() {
//        used = true;
        return score;
    }

    public int getScore2() {
        return score;
    }

    public boolean isUsed() {
        return used;
    }
}
