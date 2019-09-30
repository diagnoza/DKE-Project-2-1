import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class thirdwindow {

	public static JFrame frame;
	public static  void components()
	{
		 frame = new JFrame();
		frame.setSize(2000,2000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.gray);
		
		
		
		
	    //Whitelabel
		   JLabel whiteLabel = new JLabel("White Player");
		   whiteLabel.setBounds(1030,100,150,30);
		   whiteLabel.setForeground(Color.black);				   
		   whiteLabel.setFont(new Font("white", Font.BOLD, 24));
		   
			//score of white player label			
			 JLabel score1 = new JLabel("Score :");
			score1.setBounds(1030,120,150,30);
			score1.setForeground(Color.black);
			 score1.setFont(new Font("White", Font.BOLD, 24));
			
			 
			 //BlackLabel
			JLabel  blackLabel = new JLabel("Black Player ");
			   blackLabel.setBounds(1030,405,150,30);
			   blackLabel.setFont(new Font("White", Font.BOLD, 24));
			   blackLabel.setForeground(Color.white);
			
			//Score of black player label
				JLabel score2 = new JLabel("Score :");
				score2.setBounds(1030,425,150,30);
				 score2.setFont(new Font("White", Font.BOLD, 24));
				 score2.setForeground(Color.white);
				 
				 //back button
				JButton back = new JButton(); 
				back.setBounds(1200,650,140,70); 
				back.setOpaque(true);
			//	back.setBorderPainted(false);	
				back.setBorder(new LineBorder(Color.blue));
				back.setText("Back");
				back.setBackground(Color.white);
				back.setFont(back.getFont().deriveFont(36.0f));
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
			    roll.setBorder(new LineBorder(Color.BLUE));
			   // roll.setBackground(Color.white);
			    roll.setFont(roll.getFont().deriveFont(20.0f));
				roll.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						
						Dice.diceframe();
						
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
	
	public static void labels()
	{
		/**LABELS*/
		final JLabel label1 = new JLabel("1");
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

}
