package GameLogic;
import java.util.Random;

public class Die {


    /**
     *
     *
     *
     * @return Random number between 1 and 6
     */
    public static int roll(){

        Random random = new Random(System.currentTimeMillis());

        return random.nextInt(6) + 1;
    }
}
