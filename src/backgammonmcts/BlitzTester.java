package backgammonmcts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.io.*; 
import java.util.*; 

public class BlitzTester {
	
	int genNum = 0; //Current generation number
	int maxGenNum = 5; //max generation
	int AImodeW;
	int AImodeB;
	int numGames = 10;
	
	public void test(double[][] blitzVar, double[] blitzVarB, int AImodeW, int AImodeB) throws IOException, ClassNotFoundException, InterruptedException {
        
		//
        double[][] population = new double[100][11];
        this.AImodeW = AImodeW;
        this.AImodeB = AImodeB;
        
        //Creating timestamp
        Date date= new Date();
   	 	long time = date.getTime();
   	 	Timestamp ts = new Timestamp(time);
        
        //Creating a file that stores the test data
        File file = new File("test.txt");

        //Creating writer to write data to file
        PrintWriter writer = new PrintWriter(new FileWriter(file, true));  //Does not rewrite file, adds to existing data 

        //Loop that iterates over the players
        for(int k=0;k<100;k++) {
        	//Creating variables for the player
            double numWhiteWon = 0;
            double numBlackWon = 0;
            boolean first = true;
        	int x = 0;
        	
        	//Bentley is for slightly adjusting input to create random population
        	double[] bentley = new double[10];
        	
        	if(blitzVar!=null) {
        		if(genNum!=0 && k<10) {
        			bentley[0] = blitzVar[k][1];
	        		bentley[1] = blitzVar[k][2];
	        		bentley[2] = blitzVar[k][3];
	        		bentley[3] = blitzVar[k][4];
	        		bentley[4] = blitzVar[k][5];
	        		bentley[5] = blitzVar[k][6];
	        		bentley[6] = blitzVar[k][7];
	        		bentley[7] = blitzVar[k][8];
	        		bentley[8] = blitzVar[k][9];
	        		bentley[9] = blitzVar[k][10];
        		}
        		else if(genNum!=0 && k>9) {
        			if(k==19||k==29||k==39||k==49||k==59||k==69||k==79||k==89||k==99) {
        				bentley[0] = blitzVar[0][1]+Math.random()-Math.random();
    	        		bentley[1] = blitzVar[0][2]+Math.random()-Math.random();
    	        		bentley[2] = blitzVar[0][3]+Math.random()-Math.random();
    	        		bentley[3] = blitzVar[0][4]+Math.random()-Math.random();
    	        		bentley[4] = blitzVar[0][5]+Math.random()-Math.random();
    	        		bentley[5] = blitzVar[0][6]+Math.random()-Math.random();
    	        		bentley[6] = blitzVar[0][7]+Math.random()-Math.random();
    	        		bentley[7] = blitzVar[0][8]+Math.random()-Math.random();
    	        		bentley[8] = blitzVar[0][9]+Math.random()-Math.random();
    	        		bentley[9] = blitzVar[0][10]+Math.random()-Math.random();
        			}
        			else {
	        			bentley[0] = blitzVar[k/10][1]+Math.random()-Math.random();
		        		bentley[1] = blitzVar[k/10][2]+Math.random()-Math.random();
		        		bentley[2] = blitzVar[k/10][3]+Math.random()-Math.random();
		        		bentley[3] = blitzVar[k/10][4]+Math.random()-Math.random();
		        		bentley[4] = blitzVar[k/10][5]+Math.random()-Math.random();
		        		bentley[5] = blitzVar[k/10][6]+Math.random()-Math.random();
		        		bentley[6] = blitzVar[k/10][7]+Math.random()-Math.random();
		        		bentley[7] = blitzVar[k/10][8]+Math.random()-Math.random();
		        		bentley[8] = blitzVar[k/10][9]+Math.random()-Math.random();
		        		bentley[9] = blitzVar[k/10][10]+Math.random()-Math.random();
        			}
        		}
        		/*else {
	        		bentley[0] = blitzVar[0][1]+Math.random()*2-Math.random()*2;
	        		bentley[1] = blitzVar[0][2]+Math.random()*2-Math.random()*2;
	        		bentley[2] = blitzVar[0][3]+Math.random()*8-Math.random()*8;
	        		bentley[3] = blitzVar[0][4]+Math.random()*6-Math.random()*6;
	        		bentley[4] = blitzVar[0][5]+Math.random()*14-Math.random()*14;
	        		bentley[5] = blitzVar[0][6]+Math.random()*10-Math.random()*10;
	        		bentley[6] = blitzVar[0][7]+Math.random()*8-Math.random()*8;
	        		bentley[7] = blitzVar[0][8]+Math.random()*6-Math.random()*6;
	        		bentley[8] = blitzVar[0][9]+Math.random()*4-Math.random()*4;
	        		bentley[9] = blitzVar[0][10]+Math.random()*2-Math.random()*2;
        		}*/
        	}
        	//Sly is for first time only
        	double[] sly = new double[10];
        	
        	/*if(blitzVar == null) {
	        	sly[0] = Math.random()*2;
	        	sly[1] = Math.random()*2;
	        	sly[2] = Math.random()*8;
	        	sly[3] = Math.random()*6;
	        	sly[4] = Math.random()*14;
	        	sly[5] = Math.random()*10;
	        	sly[6] = Math.random()*8;
	        	sly[7] = Math.random()*6;
	        	sly[8] = Math.random()*4;
	        	sly[9] = Math.random()*2;
        	}*/
        	
        	/*if(blitzVar == null) {
	        	sly[0] = 2;
	        	sly[1] = 2;
	        	sly[2] = 8;
	        	sly[3] = 6;
	        	sly[4] = 14;
	        	sly[5] = 10;
	        	sly[6] = 8;
	        	sly[7] = 6;
	        	sly[8] = 4;
	        	sly[9] = 2;
        	}*/
	        
        	if(blitzVar == null) {
	        	sly[0] = 0.11526573538241869;
	        	sly[1] = 0.5936466883524378;
	        	sly[2] = -0.9741008222267975;
	        	sly[3] = -1.1434114484624203;
	        	sly[4] = 0.6085679373798933;
	        	sly[5] = -0.7222640775713567;
	        	sly[6] = 1.4006812380151201;
	        	sly[7] = 1.2662092801833666;
	        	sly[8] = 1.3748327701324374;
	        	sly[9] = -0.30752241189789364;
        	}
        	
	        //Loop that iterates over the games of one player
	        while(x < numGames) {
	        	
	        	//Creating a board
	            Board test = new Board();
	            //Creating a random dice roll
	            Roll r = new Roll();
	            while (r.steps[0] == r.steps[1]) {
	                r = new Roll();
	            }
	            
	            //Determines who starts the game
	            //*In RandomVersusRandom the color who starts has a 0.441% higher chance of winning*
	            boolean white = /*false;//*/(r.steps[0] > r.steps[1]);
	            
	            State start = new State(test, white, 0, 0, 0, r);
	            MCTS w = new MCTS();
	            MCTS b = new MCTS();
	            //Use setblitz if there is an input array
	            if(blitzVar != null) {
	            	w.setBlitz(bentley[0], bentley[1],bentley[2],
	            			bentley[3], bentley[4], bentley[5],
	            			bentley[6], bentley[7],
	            			bentley[8], bentley[9]);
	            }
	            if(blitzVar == null) {
	            	w.setBlitz(sly[0], sly[1], sly[2], sly[3], sly[4], sly[5], sly[6], sly[7], sly[8], sly[9]);
	            }
	            if(blitzVarB != null) {
	            	b.setBlitz(blitzVarB[0], blitzVarB[1], blitzVarB[2], blitzVarB[3], blitzVarB[4], blitzVarB[5], blitzVarB[6], blitzVarB[7],
	            			blitzVarB[8], blitzVarB[9]);
	            }
	            first = true;
	            State next = new State();
	            
	            while (start.wincheck() == 0) {
	                if (start.white) {
	                	//Random
	                	if(AImodeW == 2) {
	                    	next = new State(start, w.Random(start));
	                    	start = next;
	                    }
	                	//Blitz
	                	else if(AImodeW == 3) {
	                		//If first move of the game, use standard move
	                		if(first) {
	                    		int[] move = FirstMove.opening(r, white);
	                            next = new State(start, move);
	                            first = false;
	                            start = next;
	                    	}
	                		//Else use Blitz
	                		else {
	                			next = new State(start, w.Blitz(start));
	                			start = next;
	                		}
	                    }
	                } else {
	                	//Random
	                	if(AImodeB == 2) {
	                		next = new State(start, b.Random(start));
	                    	start = next;
	                	}
	                	//Blitz
	                	else if(AImodeB == 3) {
	                		//If first move of the game, use standard move
	                		if(first) {
	                    		int[] move = FirstMove.opening(r, white);
	                            next = new State(start, move);
	                            first = false;
	                            start = next;
	                    	}
	                		//Else use blitz
	                		else {
	                			next = new State(start, b.Blitz(start));
	                    		start = next;
	                		}
	                	}
	                }
	            }
	            //Adding a point to the winning color
	            if (start.wincheck() > 0) {
	                numWhiteWon++;
	            } else {
	                numBlackWon++;
	            }
	            
	            //System.out.println("Total nodes expanded for White: " +w.totalnodesexpanded);
	            //System.out.println("Rollouts completed for White: " +w.totalrollouts);
	            //System.out.println("Total nodes expanded for Black: " +b.totalnodesexpanded);
	            //System.out.println("Rollouts completed for Black: " +b.totalrollouts);
	            
	            if(x==0) {
	            	for(int l=0;l<10;l++) {
	            		population[k][l+1] = w.getBlitz()[l];
	            	}
	            }
	            
	            x++;
	            System.out.println("Generation: " + genNum + " Player: " + k + " Game# " + x + "/" + numGames + " W: " + numWhiteWon + " B: " + numBlackWon);
	        }
	        population[k][0] = numWhiteWon;
	        
        }
        
        if(genNum < maxGenNum) {
        	genNum++;
        	secondGenCalc(population);
        }
        
        double max = 0;
        double[] result = new double[11]; 
        //Loop that stores the best child of the last population in the result array
        for(int q=0;q<100;q++) {
        	if(population[q][0]>max) {
        		for(int o=0;o<11;o++) {
        			result[o] = population[q][o];
        		}
        		max = population[q][0];
        	}
        }
        
        System.out.println(Arrays.toString(result));
        
        //Writing data into file
        writer.println("NEW blitzTEST " + ts);
        writer.println(Arrays.toString(result));
        writer.println("");
        writer.close();
        
        System.out.println("Test complete! Data stored in test.txt");
    }
	
	public void secondGenCalc(double[][] population) throws ClassNotFoundException, IOException, InterruptedException {
		double min = 0; 
        int S = 0; //Counter to keep track of the children
        double[][] secG = new double[100][11]; //Matrix containing the second generation based on the best children of the population
        
        //filling the second generation with the 10 best resulting children of the population
        for(int p=0;p<100;p++) {
        	//If there is less than 10 children picked yet
        	if((population[p][0]>min) && S<10) {
        		//Loop to copy info
        		for(int o=0;o<11;o++) {
        			secG[S][o] = population[p][o];
        		}
        		min = population[p][0];
        		S++;
        	}
        	//If the second generation is full, overwrite the weakest child
        	else if((population[p][0]>min && S>=10)) {
        		boolean firstChild = false; //Boolean to prevent duplicate children (if two children both are min)
        		int k = 0;
        		int l = 0;
        		//Loop that checks over children
        		for(int r=0;r<10;r++) {
        			if((secG[r][0]==min)) {
        				if(firstChild==false) {
        				//Loop to copy info
        					for(int o=0;o<11;o++) {
        						secG[r][o] = population[p][o];
        						firstChild = true;
        						l = r;
        					}
        				}
        				k++;
        				if(k>1) {
        					min = secG[r][0];
        				}
        				else if(r==9 && k==1) {
        					min = secG[l][0];
        				}
        			}
        		}
        	}
        }
        test(secG, null, AImodeW, AImodeB);
	}
}
