import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class secondwindow {
	

	public static void components()
	{
		
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(1000,1000);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.black);	
			
		//Select label
		JLabel label = new JLabel(); 
		label.setText("Please select");
		label.setFont(new Font("Courier New", Font.ITALIC, 40));
		label.setForeground(Color.white);
		label.setBounds(300,80,1000,200);
		
		//Human vs Human button
		 JButton HH = new JButton("Human vs Human");
		HH.setBounds(370,200,300,100);				
		HH.setBackground(Color.white);
		HH.setFont(new Font("Batang", Font.BOLD, 26));
		HH.setBorder(new LineBorder(Color.blue,5));
		HH.addMouseListener(new MouseListener() { 
			public void mouseEntered(MouseEvent e) {
				
			}
			public void mouseExited(MouseEvent e) {
				
			}
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
			
				thirdwindow.components();
				thirdwindow.labels();			
				thirdwindow.addPieces();		
			}		
	});
		
		//Human vs Computer button
		JButton HC = new JButton("Human vs Computer");
		HC.setBounds(370,350,300,100);
		HC.setBorder(new LineBorder(Color.blue,5));
		HC.setBackground(Color.white);
		HC.setFont(new Font("Batang", Font.BOLD, 26));
		
		//Back button
		JButton back = new JButton(); 
		back.setBounds(800,600,140,70); 
		//back.setOpaque(true);
		//back.setBorderPainted(false);				
		back.setText("Back");
		back.setBorder(new LineBorder(Color.blue,5));
		back.setBackground(Color.white);
		back.setFont(new Font("Batang", Font.BOLD, 26));
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				frame.dispose();
				
			}
		});
		
		
		
		panel.add(back);
		panel.add(HC);
		panel.add(HH);
		panel.add(label);
		frame.add(panel);
		
	}

}