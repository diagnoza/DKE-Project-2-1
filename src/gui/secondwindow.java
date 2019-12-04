import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class secondwindow {


    public static void components(JFrame window) {

    	JFrame frame = new JFrame("Main Menu");
        frame.setVisible(true);
        frame.setSize(1000,701);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setContentPane(new JLabel(new ImageIcon("C:\\Users\\Vaishnavi Sirul\\eclipse-workspace\\Backgammon!\\src\\Img.jpg")));
		frame.setSize(1000,700);

        //Select label
        JLabel label = new JLabel();
        label.setText("PLEASE SELECT");
        label.setFont(new Font("Times New Roman", Font.BOLD, 60));
        label.setForeground(Color.white);
        label.setBounds(300, 5, 1000, 200);

        //Human vs Human button
        JButton HH = new JButton("Human vs Human");
        HH.setBounds(370, 150, 300, 90);
        HH.setBackground(Color.LIGHT_GRAY);
        HH.setFont(new Font("Times New Roman", Font.BOLD, 26));
        HH.setBorder(new LineBorder(Color.BLACK, 2));
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

                thirdwindow.components(window);
                thirdwindow.labels();
                thirdwindow.addPieces();
                frame.dispose();

            }
        });

        //Human vs Computer button
        JButton HC = new JButton("Human vs Computer");
        HC.setBounds(370, 300, 300, 90);
        HC.setBorder(new LineBorder(Color.BLACK, 2));
        HC.setBackground(Color.LIGHT_GRAY);
        HC.setFont(new Font("Times New Roman", Font.BOLD, 26));
        
        //AI vs AI button
        JButton CC = new JButton("Computer vs Computer");
        CC.setBounds(370, 450, 300, 90);
        CC.setBorder(new LineBorder(Color.BLACK, 2));
        CC.setBackground(Color.LIGHT_GRAY);
        CC.setFont(new Font("Times New Roman", Font.BOLD, 26));

        //Back button
        JButton back = new JButton();
        back.setBounds(830, 600, 140, 50);
        back.setText("Back");
        back.setBorder(new LineBorder(Color.BLACK, 2));
        back.setBackground(Color.LIGHT_GRAY);
        back.setFont(new Font("Times New Roman", Font.BOLD, 26));
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                window.setVisible(true);
                frame.dispose();

            }
        });

        frame.getContentPane().add(label);
        frame.getContentPane().add(HC);
        frame.getContentPane().add(HH);
        frame.getContentPane().add(back);
        frame.getContentPane().add(CC);
    }
}
