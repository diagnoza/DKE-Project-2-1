import java.awt.event.*;
import java.util.Random;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class BackgammonBoard extends JComponent{
	
	/**public static void main(String[] args)
	{
		final JFrame frame = new JFrame();
		frame.setSize(2000,2000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.gray);
		
		//Roll Button
	
	    //Whitelabel
	   JLabel whiteLabel = new JLabel("White Player");
	   whiteLabel.setBounds(1030,100,150,30);
	   whiteLabel.setForeground(Color.black);
	   
	   whiteLabel.setFont(new Font("white", Font.BOLD, 24));
	   //TextField
		
		
		JLabel score1 = new JLabel("Score :");
		score1.setBounds(1030,120,150,30);
		score1.setForeground(Color.black);
		 score1.setFont(new Font("White", Font.BOLD, 24));
		
		 
		 //BlackLabel
		 JLabel blackLabel = new JLabel("Black Player ");
		   blackLabel.setBounds(1030,405,150,30);
		   blackLabel.setFont(new Font("White", Font.BOLD, 24));
		   blackLabel.setForeground(Color.white);
		
		//Score 2
			JLabel score2 = new JLabel("Score :");
			score2.setBounds(1030,425,150,30);
			 score2.setFont(new Font("White", Font.BOLD, 24));
			 score2.setForeground(Color.white);
			 
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
			
			final JLabel label12 = new JLabel("12");//13
			label12.setBounds(75,618,15,15);
			label12.setForeground(Color.white);
			
			JButton roll = new JButton("Roll Dices");
		    roll.setBounds(1075, 320, 150, 30);
		    //roll.setForeground(Color.BLUE);
		    roll.setBackground(new Color(255,153,0));
		    roll.setOpaque(true);
		 //   roll.setBorderPainted(false);
		    roll.setBorder(new LineBorder(Color.BLUE));
		   // roll.setBackground(Color.white);
		    roll.setFont(roll.getFont().deriveFont(20.0f));
		    roll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
				
				       JFrame frameOne = new JFrame("Dice Roller");
				        frameOne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				        frameOne.setSize(500,500);
				        frameOne.setVisible(true);
				        ResultGUI resultGUI = new ResultGUI();
				        frameOne.add(resultGUI);

					
				}
			});
		   
			
		frame.setContentPane(new BackgammonBoard());
		frame.getContentPane().add(roll); 
		//frame.getContentPane().add(textfield);
		frame.getContentPane().add(whiteLabel);
		//frame.getContentPane().add(textfield2);
		frame.getContentPane().add(blackLabel);
		frame.getContentPane().add(score1);
		frame.getContentPane().add(score2);
		frame.getContentPane().add(back);
		
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
		//frame.getContentPane().add(label13);
		frame.setVisible(true);
		
	}*/
	
	public void paintComponent(Graphics g)
	{
		//Main Rect
		g.setColor(Color.black);
		g.fillRect(30,30, 900,610);
		
		g.setColor(new Color(255,153,0));
		g.fillRect(45, 45, 355,570);
		
		g.setColor(new Color(255,153,0)); 
		g.fillRect(425, 45, 355,570);
		
		g.drawRect(795, 45, 100, 285);
		g.drawRect(795, 330, 100, 285);
		
		int[] xPoints1 = {45,72,100};
		int[] yPoints1 = {45,300,45};		
		g.setColor(Color.white);
		g.drawPolygon(xPoints1,yPoints1,3);
		g.setColor(Color.white);		
		g.fillPolygon(xPoints1,yPoints1,3);//diff1 = 55,diff 2 = 55,diff 3 = 60
		//xpoint1 +27 = xpoint2
		
		int[] xPoints2 = {100,127,160};
		int[] yPoints2 = {45,300,45};		
		g.setColor(Color.blue);
		g.drawPolygon(xPoints2,yPoints2,3);
		g.setColor(Color.blue);		
		g.fillPolygon(xPoints2,yPoints2,3);
		
		int[] xPoints3 = {160,187,220};
		int[] yPoints3 = {45,300,45};		
		g.setColor(Color.white);
		g.drawPolygon(xPoints3,yPoints3,3);
		g.setColor(Color.white);		
		g.fillPolygon(xPoints3,yPoints3,3);

		int[] xPoints4 = {220,247,280};
		int[] yPoints4 = {45,300,45};		
		g.setColor(Color.blue);
		g.drawPolygon(xPoints4,yPoints4,3);
		g.setColor(Color.blue);		
		g.fillPolygon(xPoints4,yPoints4,3);

		int[] xPoints5 = {280,307,340};
		int[] yPoints5 = {45,300,45};		
		g.setColor(Color.white);
		g.drawPolygon(xPoints5,yPoints5,3);
		g.setColor(Color.white);		
		g.fillPolygon(xPoints5,yPoints5,3);

		int[] xPoints6 = {340,367,400};
		int[] yPoints6 = {45,300,45};		
		g.setColor(Color.blue);
		g.drawPolygon(xPoints6,yPoints6,3);
		g.setColor(Color.blue);		
		g.fillPolygon(xPoints6,yPoints6,3);

				int[] xPoints7 = {425,452,480};
				int[] yPoints7 = {45,300,45};		
				g.setColor(Color.white);
				g.drawPolygon(xPoints7,yPoints7,3);
				g.setColor(Color.white);		
				g.fillPolygon(xPoints7,yPoints7,3);
				
				int[] xPoints8 = {480,507,540};
				int[] yPoints8 = {45,300,45};		
				g.setColor(Color.blue);
				g.drawPolygon(xPoints8,yPoints8,3);
				g.setColor(Color.blue);		
				g.fillPolygon(xPoints8,yPoints8,3);
				
				int[] xPoints9 = {540,567,600};
				int[] yPoints9 = {45,300,45};		
				g.setColor(Color.white);
				g.drawPolygon(xPoints9,yPoints9,3);
				g.setColor(Color.white);		
				g.fillPolygon(xPoints9,yPoints9,3);
				
				int[] xPoints10 = {600,627,660};
				int[] yPoints10 = {45,300,45};		
				g.setColor(Color.blue);
				g.drawPolygon(xPoints10,yPoints10,3);
				g.setColor(Color.blue);		
				g.fillPolygon(xPoints10,yPoints10,3);
				
				int[] xPoints11 = {660,687,720};
				int[] yPoints11 = {45,300,45};		
				g.setColor(Color.white);
				g.drawPolygon(xPoints11,yPoints11,3);
				g.setColor(Color.white);		
				g.fillPolygon(xPoints11,yPoints11,3);
				
				int[] xPoints12 = {720,747,780};
				int[] yPoints12 = {45,300,45};		
				g.setColor(Color.blue);
				g.drawPolygon(xPoints12,yPoints12,3);
				g.setColor(Color.blue);		
				g.fillPolygon(xPoints12,yPoints12,3);				
				
				int[] xPoints13 = {45,72,100};
				int[] yPoints13 = {615,330,615};		
				g.setColor(Color.blue);
				g.drawPolygon(xPoints13,yPoints13,3);
				g.setColor(Color.blue);		
				g.fillPolygon(xPoints13,yPoints13,3);
				
				int[] xPoints14 = {100,127,160};
				int[] yPoints14 = {615,330,615};		
				g.setColor(Color.white);
				g.drawPolygon(xPoints14,yPoints14,3);
				g.setColor(Color.white);		
				g.fillPolygon(xPoints14,yPoints14,3);
				
				int[] xPoints15 = {160,187,220};
				int[] yPoints15 = {615,330,615};		
				g.setColor(Color.blue);
				g.drawPolygon(xPoints15,yPoints15,3);
				g.setColor(Color.blue);		
				g.fillPolygon(xPoints15,yPoints15,3);
				
				int[] xPoints16 = {220,247,280};
				int[] yPoints16 = {615,330,615};		
				g.setColor(Color.white);
				g.drawPolygon(xPoints16,yPoints16,3);
				g.setColor(Color.white);		
				g.fillPolygon(xPoints16,yPoints16,3);
				
				int[] xPoints17 = {280,307,340};
				int[] yPoints17 = {615,330,615};		
				g.setColor(Color.blue);
				g.drawPolygon(xPoints17,yPoints17,3);
				g.setColor(Color.blue);		
				g.fillPolygon(xPoints17,yPoints17,3);
				
				int[] xPoints18 = {340,367,400};
				int[] yPoints18 = {615,330,615};		
				g.setColor(Color.white);
				g.drawPolygon(xPoints18,yPoints18,3);
				g.setColor(Color.white);		
				g.fillPolygon(xPoints18,yPoints18,3);
				
				int[] xPoints19 = {425,452,480};
				int[] yPoints19 = {615,330,615};		
				g.setColor(Color.blue);
				g.drawPolygon(xPoints19,yPoints19,3);
				g.setColor(Color.blue);		
				g.fillPolygon(xPoints19,yPoints19,3);
				
				//Triangle 20
				int[] xPoints20 = {480,507,540};
				int[] yPoints20 = {615,330,615};		
				g.setColor(Color.white);
				g.drawPolygon(xPoints20,yPoints20,3);
				g.setColor(Color.white);		
				g.fillPolygon(xPoints20,yPoints20,3);		
							
				//Triangle 21
				int[] xPoints21 = {540,567,600};
				int[] yPoints21    = {615,330,615};		
				g.setColor(Color.blue);
				g.drawPolygon(xPoints21,yPoints21,3);
				g.setColor(Color.blue);		
				g.fillPolygon(xPoints21,yPoints21,3);
				
				
				//Triangle 22
				int[] xPoints22 = {600,627,660};
				int[] yPoints22 = {615,330,615};		
				g.setColor(Color.white);
				g.drawPolygon(xPoints22,yPoints22,3);
				g.setColor(Color.white);		
				g.fillPolygon(xPoints22,yPoints22,3);
				
				
				//Triangle 23
				int[] xPoints23 = {660,687,720};
				int[] yPoints23 = {615,330,615};		
				g.setColor(Color.blue);
				g.drawPolygon(xPoints23,yPoints23,3);
				g.setColor(Color.blue);		
				g.fillPolygon(xPoints23,yPoints23,3);
				
				
				//Triangle 24
				int[] xPoints24 = {720,747,780};
				int[] yPoints24 = {615,330,615};		
				g.setColor(Color.white);
				g.drawPolygon(xPoints24,yPoints24,3);
				g.setColor(Color.white);		
				g.fillPolygon(xPoints24,yPoints24,3);
										
				g.setColor(Color.WHITE);
				g.fillRect(1000,30, 300,305);			
				
				
			g.setColor(Color.BLACK);
			g.fillRect(1000,335, 300,305);	
		
	}
	}