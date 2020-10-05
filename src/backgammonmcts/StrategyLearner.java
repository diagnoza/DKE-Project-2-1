package backgammonmcts;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class StrategyLearner {

    private double lambda;
    private Random random = new Random(System.currentTimeMillis()); // Random number generator for random MCTSs


    /**
     * @param lambda learning rate
     */
    public StrategyLearner(double lambda){
        this.lambda = lambda;
    }


    /**
     * Generates one random strategy for the simulation. Should include threads and firstmoves later.
     * @param max Maximum value the pruning parameters can be set to.
     * @return Randomly generated play strategy
     */
    private MCTS_Strategy generateRandomStrategy(double max){
        MCTS mcts = new MCTS();
        double defense = random.nextDouble()*max;
        double blitz = random.nextDouble()*max;
        double prime = random.nextDouble()*max;
        double anchor = random.nextDouble()*max;
        mcts.setpruningprofile(defense, blitz, prime, anchor, defense, blitz, prime, anchor);
        return new MCTS_Strategy(mcts, random.nextDouble()>=0.5, 1);
    }

    /**
     * Generates a population of random strategies
     * @param max Maximum value the pruning parameters can be set to.
     * @return strategies: a population of random strategies
     */
    private MCTS_Strategy[] generateRandomStrategies(double max, int numStrategies){
        MCTS_Strategy[] strategies = new MCTS_Strategy[numStrategies];
        for(int i=0; i<numStrategies; i++){
            strategies[i] = this.generateRandomStrategy(max);
        }
        return strategies;
    }

    /**
     * Creates an offspring of two MCTS_Strategies
     * @param strategy1 Parent strategy 1
     * @param strategy2 Parent strategy 2
     * @param tP Target parameter to optimize
     * @return child
     */
    public MCTS_Strategy recombine(MCTS_Strategy strategy1, MCTS_Strategy strategy2, int tP){
        MCTS_Strategy childStrategy = strategy1.copy();
        return this.adjustParam(strategy2, childStrategy, tP);
    }

    /**
     * This function will randomly alter the settings of one strategy, by adjusting it towards a random strategy.
     * @param strategy Strategy to be adjusted
     * @param mutationRate Chance of the random adjustment happening
     * @param tP Target Parameter to optimize
     * @param max maximum Value for any setting
     * @return strategy, possibly mutated
     */
    public MCTS_Strategy mutate (MCTS_Strategy strategy, double mutationRate, int tP, int max){
        assert(mutationRate >= 0 && mutationRate <=1);
        if(random.nextDouble() < mutationRate)
            strategy = this.adjustParam(this.generateRandomStrategy(max), strategy, tP);
        return strategy;
    }

    /**
     * This function will mutate a population of strategies
     * @param strategies
     * @param mutationRate
     * @param tP
     * @param max
     * @return
     */
    public MCTS_Strategy[] mutate(MCTS_Strategy[] strategies, double mutationRate, int tP, int max){
        for(int i=0; i<strategies.length; i++){
            strategies[i] = mutate(strategies[i], mutationRate, tP, max);
        }
        return strategies;
    }

    /**
     * This method adjusts the parameters of the losing strategy to fit the winning strategy
     * @param winningStrategy MCTS_Strategy that won the last game
     * @param losingStrategy MCTS_Strategy that lost the last game
     * @param tP Target Parameter to optimize
     * @return Updated losingStrategy
     */
     MCTS_Strategy adjustParam(MCTS_Strategy winningStrategy, MCTS_Strategy losingStrategy, int tP){
        if(tP > 13)
            tP = random.nextInt((13 - 10) + 1) + 10;
        if(tP == -1){
            losingStrategy.mcts.earlydefense = losingStrategy.mcts.earlydefense +
                    (winningStrategy.mcts.earlydefense-losingStrategy.mcts.earlydefense)*this.lambda;
            losingStrategy.mcts.earlyblitz = losingStrategy.mcts.earlyblitz +
                    (winningStrategy.mcts.earlyblitz-losingStrategy.mcts.earlyblitz)*this.lambda;
            losingStrategy.mcts.earlyprime = losingStrategy.mcts.earlyprime +
                    (winningStrategy.mcts.earlyprime-losingStrategy.mcts.earlyprime)*this.lambda;
            losingStrategy.mcts.earlyanchor = losingStrategy.mcts.earlyanchor +
                    (winningStrategy.mcts.earlyanchor-losingStrategy.mcts.earlyanchor)*this.lambda;
            losingStrategy.mcts.middefense = losingStrategy.mcts.middefense +
                    (winningStrategy.mcts.middefense-losingStrategy.mcts.middefense)*this.lambda;
            losingStrategy.mcts.midblitz = losingStrategy.mcts.midblitz +
                    (winningStrategy.mcts.midblitz-losingStrategy.mcts.midblitz)*this.lambda;
            losingStrategy.mcts.midprime = losingStrategy.mcts.midprime+
                    (winningStrategy.mcts.midprime-losingStrategy.mcts.midprime)*this.lambda;
            losingStrategy.mcts.midanchor = losingStrategy.mcts.midanchor +
                    (winningStrategy.mcts.midanchor-losingStrategy.mcts.midanchor)*this.lambda;
            losingStrategy.firstMove = winningStrategy.firstMove;
            losingStrategy.threadCount = (int)Math.round(losingStrategy.threadCount +
                    (winningStrategy.threadCount-losingStrategy.threadCount)*this.lambda);

        }
        else if(tP == 0){ // adjusts early defense
            losingStrategy.mcts.earlydefense = losingStrategy.mcts.earlydefense +
                    (winningStrategy.mcts.earlydefense-losingStrategy.mcts.earlydefense)*this.lambda;
        }
        else if(tP == 1){ //adjusts early blitz
            losingStrategy.mcts.earlyblitz = losingStrategy.mcts.earlyblitz +
                    (winningStrategy.mcts.earlyblitz-losingStrategy.mcts.earlyblitz)*this.lambda;
        }
        else if(tP == 2){ // adjusts early prime
            losingStrategy.mcts.earlyprime = losingStrategy.mcts.earlyprime +
                    (winningStrategy.mcts.earlyprime-losingStrategy.mcts.earlyprime)*this.lambda;
        }
        else if(tP == 3){ // adjusts early anchor
            losingStrategy.mcts.earlyanchor = losingStrategy.mcts.earlyanchor +
                    (winningStrategy.mcts.earlyanchor-losingStrategy.mcts.earlyanchor)*this.lambda;
        }
        else if(tP == 4){ //adjusts mid defense
            losingStrategy.mcts.middefense = losingStrategy.mcts.middefense +
                    (winningStrategy.mcts.middefense-losingStrategy.mcts.middefense)*this.lambda;
        }
        else if(tP == 5){ //adjusts mid blitz
            losingStrategy.mcts.midblitz = losingStrategy.mcts.midblitz +
                    (winningStrategy.mcts.midblitz-losingStrategy.mcts.midblitz)*this.lambda;
        }
        else if(tP == 6){ //adjusts mid prime
            losingStrategy.mcts.midprime = losingStrategy.mcts.midprime+
                    (winningStrategy.mcts.midprime-losingStrategy.mcts.midprime)*this.lambda;
        }
        else if(tP == 7){ //adjusts mid anchor
            losingStrategy.mcts.midanchor = losingStrategy.mcts.midanchor +
                    (winningStrategy.mcts.midanchor-losingStrategy.mcts.midanchor)*this.lambda;
        }
        else if(tP == 8){ //adjusts firstMove
            losingStrategy.firstMove = winningStrategy.firstMove;
        }
        else if(tP == 9){ // adjusts thread count
            losingStrategy.threadCount = (int)Math.round(losingStrategy.threadCount +
                    (winningStrategy.threadCount-losingStrategy.threadCount)*this.lambda);
        }
        else if(tP == 10){ // adjusts defense: both early defense and mid defense
            losingStrategy.mcts.earlydefense = losingStrategy.mcts.earlydefense +
                    (winningStrategy.mcts.earlydefense-losingStrategy.mcts.earlydefense)*this.lambda;
            losingStrategy.mcts.middefense = losingStrategy.mcts.middefense +
                    (winningStrategy.mcts.middefense-losingStrategy.mcts.middefense)*this.lambda;
        }
        else if(tP == 11){ // adjusts blitz: both early blitz and mid blitz
            losingStrategy.mcts.earlyblitz = losingStrategy.mcts.earlyblitz +
                    (winningStrategy.mcts.earlyblitz-losingStrategy.mcts.earlyblitz)*this.lambda;
            losingStrategy.mcts.midblitz = losingStrategy.mcts.midblitz +
                    (winningStrategy.mcts.midblitz-losingStrategy.mcts.midblitz)*this.lambda;

        }
        else if(tP == 12){ // adjusts prime: both early prime and mid prime
            losingStrategy.mcts.earlyprime = losingStrategy.mcts.earlyprime +
                    (winningStrategy.mcts.earlyprime-losingStrategy.mcts.earlyprime)*this.lambda;
            losingStrategy.mcts.midprime = losingStrategy.mcts.midprime+
                    (winningStrategy.mcts.midprime-losingStrategy.mcts.midprime)*this.lambda;
        }
        else if(tP == 13) { //adjusts anchor: both early anchor and mid anchor
            losingStrategy.mcts.earlyanchor = losingStrategy.mcts.earlyanchor +
                    (winningStrategy.mcts.earlyanchor-losingStrategy.mcts.earlyanchor)*this.lambda;
            losingStrategy.mcts.midanchor = losingStrategy.mcts.midanchor +
                    (winningStrategy.mcts.midanchor-losingStrategy.mcts.midanchor)*this.lambda;
        }
        return losingStrategy;
    }

    /**
     * Repopulates the array of possible strategies
     * Resets wincount, to make competition in next generation fair
     * @param strategies Partially filled array, containing best strategies from last generation
     * @return Repopulated array strategies
     */
    private MCTS_Strategy[] recombine(MCTS_Strategy[] strategies, int tP){
         int notNullStrategies = 1;
        for(int i=0; i<strategies.length; i++){
            if(strategies[i] != null)
                notNullStrategies++;
            else
                strategies[i] = recombine(strategies[random.nextInt(notNullStrategies)],
                        strategies[random.nextInt(notNullStrategies)], tP);
            strategies[i].winCount = 0;
        }
        return strategies;
    }

    /**
     * Uses genetic algorithm with tournament selection to optimize strategy
     * @param strategies An array of predefined strategies
     * @param epochs number of games to be played during one tournament
     * @param generations number of generations through which the algorithm iterates
     * @param numTournaments number of Tournaments in one generation
     * @param keepWinner lets the winner of a match play again
     * @param numMatches number of matches to decide a winner
     * @param tP Target Parameter to optimize
     * @return Optimized strategies, sorted from worst to best
     * @throws InterruptedException
     */
    private MCTS_Strategy[] runGA(MCTS_Strategy[] strategies, int epochs, int generations, int numTournaments, boolean keepWinner, int numMatches, int tP) throws InterruptedException{
        int slice = strategies.length/numTournaments;
        for(int gen=0; gen<generations; gen++){
            MCTS_Strategy[] topStrategies = new MCTS_Strategy[strategies.length];
            for(int t=0; t<numTournaments; t++) {
                MCTS_Strategy[] tournamentStrategies = Arrays.copyOfRange(strategies, t*slice, (t+1)*slice);
                if(t==numTournaments-1) tournamentStrategies = Arrays.copyOfRange(strategies, t*slice, strategies.length);
                tournamentStrategies = this.runSimulation(tournamentStrategies, epochs, keepWinner, false, numMatches, tP);
                Arrays.sort(tournamentStrategies);
                topStrategies[t] = tournamentStrategies[tournamentStrategies.length-1];
            }
            strategies = this.recombine(topStrategies, tP);

        }
        Arrays.sort(strategies);
        return strategies;
    }

    /**
     *
     * @param strategies array of strategies that will compete
     * @param epochs number of games that will be played
     * @param keepWinner Decides if the winner gets to play another match
     * @param numMatches Number of matches to play to decide the winner of one round.
     * @param tP Target Parameter to optimize
     * @return stategies with adjusted parameters
     */
    private MCTS_Strategy[] runSimulation(MCTS_Strategy[] strategies, int epochs, boolean keepWinner, boolean adjust, int numMatches, int tP) throws InterruptedException{
        Playtest playtest = new Playtest();
        int winDex = random.nextInt(strategies.length);
        for(int i=0; i<epochs; i++){
            // Let to randomly selected strategies play against each other
            System.out.println("Match number " + i + " started");
            double startTime = System.currentTimeMillis();
            int index1 = random.nextInt(strategies.length);
            int index2 = random.nextInt(strategies.length);
            if(keepWinner) index1 = winDex;
            System.out.println(index1);
            System.out.println(index2);
            boolean win =  playtest.play(strategies[index1], strategies[index2], numMatches); // True if strategy1 won
            if (win) {
                if (adjust) strategies[index2] = this.adjustParam(strategies[index1], strategies[index2], tP);
                strategies[index1].winCount++;
                winDex = index1;
            }
             else {
                if (adjust) strategies[index1] = this.adjustParam(strategies[index2], strategies[index1], tP);
                strategies[index2].winCount++;
                winDex = index2;
            }
            System.out.println("Match number " + i + " finished in " + (System.currentTimeMillis()-startTime) + " Milliseconds");
            System.out.println("==================================");
        }
        return strategies;
    }

    public MCTS_Strategy[] advanceTournament(String[] files, int batchSize, int cutOff, double mutationRate, int tP){
        MCTS_Strategy[][] strategies = new MCTS_Strategy[files.length][batchSize];
        MCTS_Strategy[] topStrategies = new MCTS_Strategy[batchSize*strategies.length];

        // Read out strategies
        for(int i=0; i<files.length; i++){
            strategies[i] = this.readStrategies(files[i]);
            Arrays.sort(strategies[i]);
            System.arraycopy(strategies[i], strategies[i].length-cutOff, topStrategies, i*cutOff, cutOff);
        }

        topStrategies = this.recombine(topStrategies, tP);
        topStrategies = this.mutate(topStrategies, mutationRate, tP, 10);

        return topStrategies;

    }

    /**
     * Reads a serialized array of MCTS_strategies
     * @param file Name of the .ser file to be read
     * @return MCTS_strategies that have previously been run through the algorithm
     */
    private MCTS_Strategy[] readStrategies(String file){
        try// If this doesnt work throw an exception
        {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            MCTS_Strategy[] strategies = (MCTS_Strategy[]) in.readObject();
            in.close();
            fileIn.close();
            return strategies;
        }catch(IOException i)
        {
            i.printStackTrace();
            return null;
        }catch(ClassNotFoundException c)
        {
            System.out.println("Error");
            c.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args){
        StrategyLearner s = new StrategyLearner(0.5);
        String file = "RecombinedStrategies1.ser";  // Put the name of your recombined strategies file here
        MCTS_Strategy[] strategies = s.readStrategies(file);
        try {
            strategies = s.runSimulation(strategies, 500, false, false, 1, 14); // change number of total matches in epochs
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        try
        {
            FileOutputStream fileOut = new FileOutputStream("strategiesEsra.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(strategies);
            out.close();
            fileOut.close();
        }catch(IOException i)
        {
            i.printStackTrace();
        }

//        String[] files ={"C:\\Users\\Esra\\Documents\\Uni\\Data Science\\Year 2\\Project 1\\Second Phase\\Strategies\\strategiesRoy2.ser",
//                        "C:\\Users\\Esra\\Documents\\Uni\\Data Science\\Year 2\\Project 1\\Second Phase\\Strategies\\strategiesEsra.ser",
//                        "C:\\Users\\Esra\\Documents\\Uni\\Data Science\\Year 2\\Project 1\\Second Phase\\Strategies\\strategiesRoy1.ser"};
//
//        MCTS_Strategy strategies[] = s.advanceTournament(files, 50, 10, 0.05, 15);
//
//        for(int i=1; i<=3; i++) {
//
//            try {
//                FileOutputStream fileOut = new FileOutputStream("RecombinedStrategies" + i + ".ser");
//                ObjectOutputStream out = new ObjectOutputStream(fileOut);
//                MCTS_Strategy [] arr = new MCTS_Strategy[50];
//                System.arraycopy(strategies, (i-1)*50, arr, 0, 50);
//                out.writeObject(arr)
//                ;
//                out.close();
//                fileOut.close();
//            } catch (IOException io) {
//                io.printStackTrace();
//            }
//        }

    }
}
