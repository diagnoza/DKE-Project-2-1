import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class gui {
	public static void main(String[] args) {
        //main window
        JFrame window = new JFrame("Main Menu");
        window.setVisible(true);
        window.setSize(1000,701);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setContentPane(new JLabel(new ImageIcon("C:\\Users\\Vaishnavi Sirul\\eclipse-workspace\\Backgammon!\\src\\Img.jpg")));
		window.setSize(1000,700);
		
		   	JLabel gamelabel = new JLabel();
	        gamelabel.setText("BACKGAMMON GAME");
	        gamelabel.setFont(new Font("Times New Roman", Font.BOLD, 60));
	        gamelabel.setForeground(Color.WHITE);
	        gamelabel.setBounds(200,1,700,200);
	        
	      //Play button
	        JButton play = new JButton();
	        play.setVisible(true);
	        play.setBounds(400, 250, 250, 100);
	        play.setText("NEW GAME");
	        play.setBorder(new LineBorder(Color.BLACK, 2));
	        play.setBackground(Color.LIGHT_GRAY);
	        play.setFont(new Font("Times New Roman", Font.BOLD, 30));
	        
	        //Exit button
	        JButton quit = new JButton();
	        quit.setVisible(true);
	        quit.setBounds(400, 450, 250, 100);
	        quit.setText("EXIT");
	        quit.setBackground(Color.LIGHT_GRAY);
	        quit.setFont(new Font("Times New Roman", Font.BOLD, 30));
	        quit.setBorder(new LineBorder(Color.BLACK, 2));
	    

	        //play button
	        play.addMouseListener(new MouseListener() {
	            public void mouseEntered(MouseEvent e) {


	            }

	            public void mouseExited(MouseEvent e) {


	            }

	            public void mouseReleased(MouseEvent e) {

	            }

	            public void mousePressed(MouseEvent e) {

	            }

	            public void mouseClicked(MouseEvent e) {

	                secondwindow.components(window);
	                window.setVisible(false);
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
	      
		
	        
	        window.getContentPane().add(gamelabel);
	        window.getContentPane().add(play);
	        window.getContentPane().add(quit);
	}

}
