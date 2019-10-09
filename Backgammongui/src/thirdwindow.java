import GameLogic.Board;
import GameLogic.IllegalColourException;
import GameLogic.PieceStack;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class thirdwindow {

	public static JFrame frame;
	public static PieceStack ArrayCopy[];
	public static Board board;
	public static JButton[] buttons = new JButton[40];

	// A method that maps the pieces onto the field from the array
	// When dice is rolled and a user presses on a piece, the available pieces light up
	// If pieces and user presses on correct piece, piece in moved in array and relevant graphics are repainted
	// Change turns \\

	public static void components(){
		frame = new JFrame();
		frame.setSize(2000,2000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.BLACK);

		try {
			 board = new Board();
			ArrayCopy = board.getTriangles();
		}
		catch(IllegalColourException e){
			e.printStackTrace();
		}


		//Whitelabel
		   JLabel whiteLabel = new JLabel("White player");
		   whiteLabel.setBounds(1030,100,150,30);
		   whiteLabel.setForeground(Color.WHITE);
		   whiteLabel.setFont(new Font("Batang", Font.BOLD, 24));

			//score of white player label
			 JLabel score1 = new JLabel("Score :");
			score1.setBounds(1030,150,150,30);
			score1.setForeground(Color.WHITE);
			 score1.setFont(new Font("Batang", Font.BOLD, 24));
			 

			 //BlackLabel
			JLabel  blackLabel = new JLabel("Black player");
			   blackLabel.setBounds(1030,405,150,30);
			   blackLabel.setFont(new Font("Batang", Font.BOLD, 24));
			   blackLabel.setForeground(Color.BLACK);

			//Score of black player label
				JLabel score2 = new JLabel("Score :");
				score2.setBounds(1030,455,150,30);
				 score2.setFont(new Font("Batang", Font.BOLD, 24));
				 score2.setForeground(Color.BLACK);
				 

				 //back button
				JButton back = new JButton();
				back.setBounds(1150,650,140,70);
				back.setOpaque(true);
			//	back.setBorderPainted(false);
				back.setBorder(new LineBorder(Color.blue,5));
				back.setText("Back");
				back.setBackground(Color.white);
				back.setFont(new Font("Batang", Font.BOLD, 36));
				back.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {

						frame.dispose();

					}
				});

				JButton roll = new JButton("Roll Dices");
			    roll.setBounds(45, 660, 150, 50);
			    //roll.setForeground(Color.BLUE);
			    roll.setBackground(Color.white);
			    roll.setOpaque(true);
			 //   roll.setBorderPainted(false);
			    roll.setBorder(new LineBorder(Color.BLUE,5));
			   // roll.setBackground(Color.white);
			    roll.setFont(new Font("Batang", Font.BOLD, 26));
				roll.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						Dice.diceframe();
					System.out.println(Dice.displayDice1());
					System.out.println(Dice.displayDice2());
						System.out.println("SIZE: "+ArrayCopy[12].size());
						System.out.println("COLOUR: "+ArrayCopy[12].colour);

// Dice values
					}
				});




			  frame.setVisible(true);
			  frame.setContentPane(new BackgammonBoard());
			  frame.getContentPane().add(roll);
			  frame.getContentPane().add(whiteLabel);
			  frame.getContentPane().add(blackLabel);
			  frame.getContentPane().add(score1);
			  frame.getContentPane().add(score2);
			  frame.getContentPane().add(back);
	}

	public static void labels(){
		/**LABELS*/
		final JLabel label1 = new JLabel("1s");
		label1.setBounds(750,618,15,15);
		label1.setForeground(Color.white);

		final JLabel label2 = new JLabel("2");
		label2.setBounds(690,618,15,15);
		label2.setForeground(Color.white);

		final JLabel label3 = new JLabel("3");
		label3.setBounds(630,618,15,15);
		label3.setForeground(Color.white);

		final JLabel label4 = new JLabel("4");
		label4.setBounds(570,618,15,15);
		label4.setForeground(Color.white);

		final JLabel label5 = new JLabel("5");
		label5.setBounds(510,618,15,15);
		label5.setForeground(Color.white);

		final JLabel label6 = new JLabel("6");
		label6.setBounds(455,618,15,15);
		label6.setForeground(Color.white);

		final JLabel label7 = new JLabel("7");
		label7.setBounds(370,618,15,15);
		label7.setForeground(Color.white);

		final JLabel label8 = new JLabel("8");
		label8.setBounds(310,618,15,15);
		label8.setForeground(Color.white);

		final JLabel label9 = new JLabel("9");
		label9.setBounds(250,618,15,15);
		label9.setForeground(Color.white);

		final JLabel label10 = new JLabel("10");
		label10.setBounds(190,618,15,15);
		label10.setForeground(Color.white);

		final JLabel label11 = new JLabel("11");
		label11.setBounds(130,618,15,15);
		label11.setForeground(Color.white);

		final JLabel label12 = new JLabel("12");
		label12.setBounds(75,618,15,15);
		label12.setForeground(Color.white);

		final JLabel label13 = new JLabel("13");
		label13.setBounds(75,32,15,15);
		label13.setForeground(Color.white);

		final JLabel label14 = new JLabel("14");
		label14.setBounds(130,32,15,15);
		label14.setForeground(Color.white);

		final JLabel label15 = new JLabel("15");
		label15.setBounds(190,32,15,15);
		label15.setForeground(Color.white);

		final JLabel label16 = new JLabel("16");
		label16.setBounds(250,32,15,15);
		label16.setForeground(Color.white);

		final JLabel label17 = new JLabel("17");
		label17.setBounds(310,32,15,15);
		label17.setForeground(Color.white);

		final JLabel label18 = new JLabel("18");
		label18.setBounds(370,32,15,15);
		label18.setForeground(Color.white);

		final JLabel label19 = new JLabel("19");
		label19.setBounds(455,32,15,15);
		label19.setForeground(Color.white);

		final JLabel label20 = new JLabel("20");
		label20.setBounds(510,32,15,15);
		label20.setForeground(Color.white);

		final JLabel label21 = new JLabel("21");
		label21.setBounds(570,32,15,15);
		label21.setForeground(Color.white);

		final JLabel label22 = new JLabel("22");
		label22.setBounds(630,32,15,15);
		label22.setForeground(Color.white);

		final JLabel label23 = new JLabel("23");
		label23.setBounds(690,32,15,15);
		label23.setForeground(Color.white);

		final JLabel label24 = new JLabel("24");
		label24.setBounds(750,32,15,15);
		label24.setForeground(Color.white);

		frame.getContentPane().add(label1);
		frame.getContentPane().add(label2);
		frame.getContentPane().add(label3);
		frame.getContentPane().add(label4);
		frame.getContentPane().add(label5);
		frame.getContentPane().add(label6);
		frame.getContentPane().add(label7);
		frame.getContentPane().add(label8);
		frame.getContentPane().add(label9);
		frame.getContentPane().add(label10);
		frame.getContentPane().add(label11);
		frame.getContentPane().add(label12);
		frame.getContentPane().add(label13);
		frame.getContentPane().add(label14);
		frame.getContentPane().add(label15);
		frame.getContentPane().add(label16);
		frame.getContentPane().add(label17);
		frame.getContentPane().add(label18);
		frame.getContentPane().add(label19);
		frame.getContentPane().add(label20);
		frame.getContentPane().add(label21);
		frame.getContentPane().add(label22);
		frame.getContentPane().add(label23);
		frame.getContentPane().add(label24);

	}
	



	public static void addPieces(){


			int totalPieces =0;
				for (int i = 0; i < 24; i++) {
					int size = ArrayCopy[i].size();
					String colorStack = ArrayCopy[i].getColour();
					if (size > 0) {
						for (int x = 1; x <= size; x++) {
							totalPieces = totalPieces + 1;
							int[] coordinates = calculateCoordinates(i, x);
							int x_coordinate = coordinates[0];
							int y_coordinate = coordinates[1];
							buttons[totalPieces] = new JButton((String.valueOf(totalPieces)));
							buttons[totalPieces].setBounds(x_coordinate, y_coordinate, 58, 22);
							buttons[totalPieces].setOpaque(true);
							buttons[totalPieces].setText("");

							if (colorStack == "white") {
								buttons[totalPieces].setBackground(Color.white);
							} else {
								buttons[totalPieces].setBackground(Color.black);
							}

//							if (x == size) {
								final int totalPiecesTemp = totalPieces;
								buttons[totalPieces].addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent event) {
//										if (x == size) {
//										// LET IT BE PRESSED!
//										board.movePiece(i, 1);
//										buttons[totalPiecesTemp].setBounds(calculateCoordinates(i+1, ArrayCopy[6].size()+1)[0], calculateCoordinates(i+1, ArrayCopy[6].size()+1)[1], 58, 22);
//										}
										buttons[totalPiecesTemp].setBackground(Color.red);
									}
								});
//							}
							if (i % 2 == 0) {
								// EVEN
								buttons[totalPieces].setBorder(new LineBorder(Color.white, 1));
							} else {
								// ODD
								buttons[totalPieces].setBorder(new LineBorder(Color.black, 1));
							}
						}
					}
				}

				for (int t =1; t<=totalPieces;t++){
					frame.getContentPane().add(buttons[t]);
				}
	}

	public static int [] calculateCoordinates(int stackNumber, int height){
		int x = 0;
		int y = 0;
		if (stackNumber >= 0 && stackNumber <=5){
			x= 721 - (stackNumber*60);
			y = 46 + ((height-1) * 24);
		} else if (stackNumber >= 6 && stackNumber <=11){
			x= 341 - ((stackNumber-6)*60);
			y = 46 + ((height-1) * 24);
		} else if (stackNumber >= 12 && stackNumber <=17){
			x= 41 + ((stackNumber-12)*60);
			y = 591 - ((height-1) * 24);
		} else if (stackNumber >= 18 && stackNumber <=23){
			x= 421 + ((stackNumber-18)*60);
			y = 591 - ((height-1) * 24);
		}
		int [] arr = new int [2];
		arr[0]= x;
		arr[1] = y;
		return arr;
	}
	// REPAINT

}
