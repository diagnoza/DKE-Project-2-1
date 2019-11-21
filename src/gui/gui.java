import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.LineBorder;


public class gui  {
	
	public static void main(String[] args) {
		//main window
		JFrame window = new JFrame("Main Menu");
		window.setVisible(true);
		window.setSize(1000,1000);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);				
		panel.setBackground(Color.black);
		window.add(panel);
		
		
		// Backgammon game 
		JLabel gamelabel = new JLabel(); 
		gamelabel.setText("BACKGAMMON GAME");
		gamelabel.setFont(new Font("Algerian", Font.BOLD, 65));
		gamelabel.setForeground(Color.white);
		gamelabel.setBounds(200,10,1000,200);
		
		//Play button
		final JButton play = new JButton();
		play.setBounds(370,200,300,100);
		play.setText("NEW GAME");
		play.setBorder(new LineBorder(Color.blue,5));
		play.setBackground(Color.white);		
		play.setFont(new Font("Batang", Font.BOLD, 30));
		
		
		//exit button
		final JButton quit = new JButton();
		quit.setBounds(370,450,300,100); 
		quit.setText("EXIT");				
		quit.setBackground(Color.white);
		quit.setFont(new Font("Batang", Font.BOLD, 30));
		quit.setBorder(new LineBorder(Color.blue,5));
		
	
		
		//play button
		play.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent e) {
				
				
			}
			public void mouseExited(MouseEvent e)
			{
				
				
			}
			public void mouseReleased(MouseEvent e)
			{
				
			}
			public void mousePressed(MouseEvent e)
			{
				
			}
			public void mouseClicked(MouseEvent e)
			{			
				secondwindow.components();				
			}
						
		
	});
		
		quit.addMouseListener(new MouseListener() { 
			
			public void mouseEntered(MouseEvent e) {
				
			}
			public void mouseExited(MouseEvent e) {
				
			}
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
	});
		panel.add(gamelabel);
		panel.add(play);
		panel.add(quit);
	
	}
}
