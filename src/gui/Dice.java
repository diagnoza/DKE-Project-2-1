package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Dice extends JComponent{

	static  Random dice1 = new Random();
  static Random dice2 = new Random();

	static int stored1;
	static int stored2;

	public static int roll1()
	{
		stored1 = dice1.nextInt(6)+1;
		return stored1;
	}
	public static int roll2()
	{
		stored2 = dice2.nextInt(6)+1;
		return stored2;
	}

	public static int displayDice1()
	{
		return stored1;
	}
	public static int displayDice2()
	{
		return stored2;
	}

	public static String rollstr(){
		String roll1 =  Integer.toString(roll1());
		return roll1;
	}
	public static String rollstr2()
	{
		String roll2 =  Integer.toString(roll2());
		return roll2;
	}
	public void paintComponent(Graphics g)
	{
    	g.setColor(Color.black);
		g.fillRect(10,10, 50,50);

		g.setColor(Color.black);
		g.fillRect(120,10,50,50);


	}


	public static void diceframe(){
		JFrame window = new JFrame("Main Menu");
		window.setVisible(true);
		window.setSize(200,200);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(new Dice());

		window.setBackground(new Color(255,153,0));

		JLabel label1 = new JLabel();
		label1.setText(rollstr());
		label1.setBounds(20,15,20,20);
		label1.setFont(new Font("white", Font.BOLD, 20));
		label1.setForeground(Color.white);


		JLabel label2 = new JLabel();
		label2.setText(rollstr2());
		label2.setBounds(125,15,20,20);
		label2.setFont(new Font("white", Font.BOLD, 20));
		label2.setForeground(Color.white);


		window.getContentPane().add(label1);
		window.getContentPane().add(label2);

	}


}
