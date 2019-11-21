package backgammonmcts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
*
* @author Rudy
*/

public class Tester {
	
	public static void main(String[] args) throws IOException {
		
		//Variables
		int numberOfTests = 10;
		int x = 0;
		double numWhiteWon = 0;
		double numBlackWon = 0;
		double WhiteBlackPercentage;
		
		//Creating file to store data
		File file = new File("Test.txt");
		
		//Creating writer to write data to file
		PrintWriter writer = new PrintWriter(new FileWriter(file, true));  //Does not rewrite file, adds to existing data 
		
		//Loop that plays numberOfTests times games and adds +1 to winning team variable
		while(x<numberOfTests) {
			if(BackgammonMCTS.main2()) {
				numWhiteWon = numWhiteWon + 1;
			}
			else {
				numBlackWon = numBlackWon + 1;
			}
			x++;
		}
		
		//Calculating winning percentage of white team
		WhiteBlackPercentage = (numWhiteWon/(numberOfTests)) * 100;
		
		//Writing data into file
		writer.println("NEW TEST");
		writer.println("White prune number: " + BackgammonMCTS.pruneNumberW + " , Thinking time: " + BackgammonMCTS.thinkTimeW);
		writer.println("Black prune number: " + BackgammonMCTS.pruneNumberB + " , Thinking time: " + BackgammonMCTS.thinkTimeB);
		writer.println("Number of games is: " + numberOfTests);
		writer.println("White won: " + numWhiteWon + " times");
		writer.println("Black won: " + numBlackWon + " times");
		writer.println("WhiteBlack percentage is: " + WhiteBlackPercentage + "%");
		writer.println("BlackWhite percentage is: " + (100 - WhiteBlackPercentage) + "%");
		writer.println("");
		writer.close();
	}
}
