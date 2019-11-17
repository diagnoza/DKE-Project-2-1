package GameLogic;
import java.util.Random;

public class Die {

    Random random;

    public Die(){
        random = new Random(System.currentTimeMillis());
    }

    /**
     *
     *
     *
     * @return Random number between 1 and 6
     */
    public int roll(){
        return random.nextInt(6) + 1;
    }
}
