import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class thirdwindow {

    public static JFrame frame;
   
    public static PieceStack[] ArrayCopy;
    public static Board board;
    public static PieceButton[] buttons = new PieceButton[40];


    private static JLabel rollScoreLabel1 = new JLabel("Dice #1: ");
    private static JLabel rollScoreLabel2 = new JLabel("Dice #2: ");
    private static JLabel currentTurnLabel = new JLabel("WHITE moves");
    private static Die dice1 = new Die();
    private static Die dice2 = new Die();
    private static JButton pickDice = new JButton("Dice #1");


    public static void components(JFrame window) {

        frame = new JFrame();
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.LIGHT_GRAY);
        frame.setSize(1300,880);
        frame.setResizable(false);
        try {
            board = new Board();
            ArrayCopy = board.getTriangles();
        } catch (IllegalColourException e) {
            e.printStackTrace();
        }

        //back button
        JButton back = new JButton();
        back.setBounds(1150, 650, 140, 70);
        back.setOpaque(true);
        back.setBorder(new LineBorder(Color.blue, 5));
        back.setText("Back");
        back.setBackground(Color.white);
        back.setFont(new Font("Helvetica", Font.BOLD, 36));
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                frame.dispose();
                window.setVisible(true);
            }
        });

        JButton rollButton = new JButton("Roll Dices");
        rollButton.setBounds(45, 660, 150, 50);
        rollButton.setBackground(Color.white);
        rollButton.setOpaque(true);
        rollButton.setBorder(new LineBorder(Color.BLUE, 5));
        rollButton.setFont(new Font("Helvetica", Font.BOLD, 26));
        rollButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                rollScoreLabel1.setText("DICE #1:  " + dice1.roll());
                rollScoreLabel2.setText("DICE #2:  " + dice2.roll());
                frame.repaint();
            }
        });

        rollScoreLabel1.setForeground(Color.WHITE);
        rollScoreLabel1.setBounds(45, 710, 200, 50);
        rollScoreLabel2.setForeground(Color.WHITE);
        rollScoreLabel2.setBounds(45, 730, 200, 50);

        pickDice.setBounds(45, 780, 150, 50);
        pickDice.setFont(new Font("Helvetica", Font.BOLD, 26));
        pickDice.setBackground(Color.white);
        pickDice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pickDice.getText().equals("Dice #1")) pickDice.setText("Dice #2");
                else if (pickDice.getText().equals("Dice #2")) pickDice.setText("Both");
                else pickDice.setText("Dice #1");
                frame.repaint();
            }
        });

        JButton endTurnButton = new JButton("End turn");
        endTurnButton.setBounds(780, 660, 150, 50);
        endTurnButton.setBackground(Color.white);
        endTurnButton.setOpaque(true);
        endTurnButton.setBorder(new LineBorder(Color.BLUE, 5));
        endTurnButton.setFont(new Font("Helvetica", Font.BOLD, 26));
        endTurnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                dice1 = new Die();
                dice2 = new Die();
                if (currentTurnLabel.getText().equals("WHITE moves")) currentTurnLabel.setText("BLACK moves");
                else currentTurnLabel.setText("WHITE moves");
                frame.repaint();
            }
        });

        currentTurnLabel.setForeground(Color.WHITE);
        currentTurnLabel.setBounds(780, 710, 200, 50);

        frame.setVisible(true);
        frame.setContentPane(new BackgammonBoard());
        frame.getContentPane().add(rollButton);
        frame.getContentPane().add(back);
        frame.getContentPane().add(rollScoreLabel1);
        frame.getContentPane().add(rollScoreLabel2);
        frame.getContentPane().add(pickDice);
        frame.getContentPane().add(endTurnButton);
        frame.getContentPane().add(currentTurnLabel);

    }


    public static void addPieces() {
        int totalPieces = 0;
        int i;
        for (i = 0; i < 24; i++) {
            int size = ArrayCopy[i].size();
            String colorStack = ArrayCopy[i].getColour();
            for (int x = 0; x < size; x++) {
                totalPieces = totalPieces + 1;

                buttons[totalPieces] = new PieceButton(board.getTriangles()[i].get(x),totalPieces);
                buttons[totalPieces].setBounds(buttons[totalPieces].piece.xPosition, buttons[totalPieces].piece.yPosition, 58, 17);
                buttons[totalPieces].setOpaque(true);
                buttons[totalPieces].setText("");
                if (colorStack.equals("white")) {
                    buttons[totalPieces].setBackground(Color.white);
                } else {
                    buttons[totalPieces].setBackground(Color.black);
                }

                buttons[totalPieces].piece.linkButton(totalPieces);

                final int totalPiecesTemp = totalPieces;

                buttons[totalPieces].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        if (buttons[totalPiecesTemp].piece.middle == true) {
                            int stackNumber1 = buttons[totalPiecesTemp].piece.stackNumber;
                            int height1 = buttons[totalPiecesTemp].piece.getHeight(board.getTriangles());
                            int sizex = board.getTriangles()[stackNumber1].size();
                            String colorz = buttons[totalPiecesTemp].piece.getColour();
                            if (pickDice.getText().equals("Dice #1") && !dice1.isUsed()) {
                                try {
                                    int value = board.movePieceFromMiddle(buttons[totalPiecesTemp].piece.colour, dice1.getScore());
                                    if (value == -20 || value == -70) {
                                        int x_Coordinate = buttons[totalPiecesTemp].piece.xPosition;
                                        int y_Coordinate = buttons[totalPiecesTemp].piece.yPosition;
                                        buttons[totalPiecesTemp].setBounds(x_Coordinate, y_Coordinate, 58, 17);
                                    } else {
                                        int x_CoordinateRemove = buttons[value].piece.xPosition;
                                        int y_CoordinateRemove = buttons[value].piece.yPosition;
                                        buttons[value].setBounds(x_CoordinateRemove, y_CoordinateRemove, 58, 17);
                                        int x_Coordinate = buttons[totalPiecesTemp].piece.xPosition;
                                        int y_Coordinate = buttons[totalPiecesTemp].piece.yPosition;
                                        buttons[totalPiecesTemp].setBounds(x_Coordinate, y_Coordinate, 58, 17);
                                    }
                                    if (isGameOver() == true) {
                                        showEnding(colorz);
                                    }
                                } catch (IllegalArgumentException e) {
                                    System.out.println("MOVE NOT ALLOWED!");
                                    dice1.resetMove();
                                }
                            } else if (pickDice.getText().equals("Dice #2") && !dice2.isUsed()) {
                                try {
                                    int value = board.movePieceFromMiddle(buttons[totalPiecesTemp].piece.colour, dice2.getScore());
                                    if (value == -20 || value == -70) {
                                        System.out.println("GOT HERE!!");
                                        int x_Coordinate = buttons[totalPiecesTemp].piece.xPosition;
                                        int y_Coordinate = buttons[totalPiecesTemp].piece.yPosition;
                                        buttons[totalPiecesTemp].setBounds(x_Coordinate, y_Coordinate, 58, 17);
                                    } else {
                                        int x_CoordinateRemove = buttons[value].piece.xPosition;
                                        int y_CoordinateRemove = buttons[value].piece.yPosition;
                                        buttons[value].setBounds(x_CoordinateRemove, y_CoordinateRemove, 58, 17);
                                        int x_Coordinate = buttons[totalPiecesTemp].piece.xPosition;
                                        int y_Coordinate = buttons[totalPiecesTemp].piece.yPosition;
                                        buttons[totalPiecesTemp].setBounds(x_Coordinate, y_Coordinate, 58, 17);
                                    }
                                    if (isGameOver() == true) {
                                        showEnding(colorz);
                                    }
                                } catch (IllegalArgumentException e) {
                                    System.out.println("MOVE NOT ALLOWED!");
                                    dice2.resetMove();
                                }

                            } else if (!dice1.isUsed() && !dice2.isUsed()) {
                                try {
                                    int value = board.movePieceFromMiddle(buttons[totalPiecesTemp].piece.colour, dice1.getScore() + dice2.getScore());
                                    if (value == -20 || value == -70) {
                                        System.out.println("GOT HERE!!");
                                        int x_Coordinate = buttons[totalPiecesTemp].piece.xPosition;
                                        int y_Coordinate = buttons[totalPiecesTemp].piece.yPosition;
                                        buttons[totalPiecesTemp].setBounds(x_Coordinate, y_Coordinate, 58, 17);
                                    } else {
                                        int x_CoordinateRemove = buttons[value].piece.xPosition;
                                        int y_CoordinateRemove = buttons[value].piece.yPosition;
                                        buttons[value].setBounds(x_CoordinateRemove, y_CoordinateRemove, 58, 17);
                                        int x_Coordinate = buttons[totalPiecesTemp].piece.xPosition;
                                        int y_Coordinate = buttons[totalPiecesTemp].piece.yPosition;
                                        buttons[totalPiecesTemp].setBounds(x_Coordinate, y_Coordinate, 58, 17);
                                    }
                                    if (isGameOver() == true) {
                                        showEnding(colorz);
                                    }
                                } catch (IllegalArgumentException e) {
                                    System.out.println("MOVE NOT ALLOWED!");
                                    dice1.resetMove();
                                    dice2.resetMove();
                                }
                            }
                        } else {
                            int stackNumber1 = buttons[totalPiecesTemp].piece.stackNumber;
                            int height1 = buttons[totalPiecesTemp].piece.getHeight(board.getTriangles());
                            int sizex = board.getTriangles()[stackNumber1].size();
                            String colorz = buttons[totalPiecesTemp].piece.getColour();
                            if (height1 + 1 == sizex && (currentTurnLabel.getText().equals("WHITE moves") || currentTurnLabel.getText().equals("BLACK moves"))) {
                                if (pickDice.getText().equals("Dice #1") && !dice1.isUsed()) {
                                    try {
                                        int value = board.movePiece(stackNumber1, dice1.getScore(), buttons[totalPiecesTemp].piece.colour);
                                        if (value == -20 || value == -70) {
                                            int x_Coordinate = buttons[totalPiecesTemp].piece.xPosition;
                                            int y_Coordinate = buttons[totalPiecesTemp].piece.yPosition;
                                            buttons[totalPiecesTemp].setBounds(x_Coordinate, y_Coordinate, 58, 17);
                                        } else {
                                            // PIECE THAT NEEDS TO BE REMOVED
                                            int x_CoordinateRemove = buttons[value].piece.xPosition;
                                            int y_CoordinateRemove = buttons[value].piece.yPosition;
                                            buttons[value].setBounds(x_CoordinateRemove, y_CoordinateRemove, 58, 17);
                                            int x_Coordinate = buttons[totalPiecesTemp].piece.xPosition;
                                            int y_Coordinate = buttons[totalPiecesTemp].piece.yPosition;
                                            buttons[totalPiecesTemp].setBounds(x_Coordinate, y_Coordinate, 58, 17);
                                        }
                                        if (isGameOver() == true) {
                                            showEnding(colorz);
                                        }
                                    } catch (IllegalArgumentException e) {
                                        System.out.println("MOVE NOT ALLOWED!");
                                        dice1.resetMove();
                                    }

                                } else if (pickDice.getText().equals("Dice #2") && !dice2.isUsed()) {
                                    try {
                                        int value = board.movePiece(stackNumber1, dice2.getScore(), buttons[totalPiecesTemp].piece.colour);
                                        if (value == -20 || value == -70) {
                                            int x_Coordinate = buttons[totalPiecesTemp].piece.xPosition;
                                            int y_Coordinate = buttons[totalPiecesTemp].piece.yPosition;
                                            buttons[totalPiecesTemp].setBounds(x_Coordinate, y_Coordinate, 58, 17);
                                        } else {
                                            // PIECE THAT NEEDS TO BE REMOVED
                                            int x_CoordinateRemove = buttons[value].piece.xPosition;
                                            int y_CoordinateRemove = buttons[value].piece.yPosition;
                                            buttons[value].setBounds(x_CoordinateRemove, y_CoordinateRemove, 58, 17);
                                            int x_Coordinate = buttons[totalPiecesTemp].piece.xPosition;
                                            int y_Coordinate = buttons[totalPiecesTemp].piece.yPosition;
                                            buttons[totalPiecesTemp].setBounds(x_Coordinate, y_Coordinate, 58, 17);
                                        }
                                        if (isGameOver() == true) {
                                            showEnding(colorz);
                                        }
                                    } catch (IllegalArgumentException e) {
                                        System.out.println("MOVE NOT ALLOWED!");
                                        dice2.resetMove();
                                    }
                                } else if (!dice1.isUsed() && !dice2.isUsed()) {
                                    try {
                                        int value = board.movePiece(stackNumber1, dice1.getScore() + dice2.getScore(), buttons[totalPiecesTemp].piece.colour);
                                        if (value == -20 || value == -70) {
                                            int x_Coordinate = buttons[totalPiecesTemp].piece.xPosition;
                                            int y_Coordinate = buttons[totalPiecesTemp].piece.yPosition;
                                            buttons[totalPiecesTemp].setBounds(x_Coordinate, y_Coordinate, 58, 17);
                                        } else {
                                            int x_CoordinateRemove = buttons[value].piece.xPosition;
                                            int y_CoordinateRemove = buttons[value].piece.yPosition;
                                            buttons[value].setBounds(x_CoordinateRemove, y_CoordinateRemove, 58, 17);
                                            int x_Coordinate = buttons[totalPiecesTemp].piece.xPosition;
                                            int y_Coordinate = buttons[totalPiecesTemp].piece.yPosition;
                                            buttons[totalPiecesTemp].setBounds(x_Coordinate, y_Coordinate, 58, 17);
                                        }
                                        if (isGameOver() == true) {
                                            showEnding(colorz);
                                        }
                                    } catch (IllegalArgumentException e) {
                                        System.out.println("MOVE NOT ALLOWED!");
                                        dice1.resetMove();
                                        dice2.resetMove();
                                    }
                                }
                            }
                        }
                    }
                });

                String colorButton = buttons[totalPieces].piece.getColour();
                if (colorButton == "white") {
                    buttons[totalPieces].setBorder(new LineBorder(Color.white, 1));
                } else {
                    buttons[totalPieces].setBorder(new LineBorder(Color.black, 1));
                }
            }

        }

        for (int t = 1; t <= totalPieces; t++) {
            frame.getContentPane().add(buttons[t]);
        }
    }

    public static void showEnding(String color) {
        String message = "GAME OVER "+ color + " WON";
        JOptionPane.showMessageDialog(null, message);
    }

    public static Boolean isGameOver(){
        return board.getSuccesfullyRemovedWhite().size() == 15 || board.getSuccesfullyRemovedBlack().size() == 15;
    }

    public static void labels() {
        /**LABELS*/

    final JLabel label1 = new JLabel("24");
        label1.setBounds(750, 618, 20, 15);
        label1.setForeground(Color.white);

        final JLabel label2 = new JLabel("23");
        label2.setBounds(690, 618, 20, 15);
        label2.setForeground(Color.white);

        final JLabel label3 = new JLabel("22");
        label3.setBounds(630, 618, 20, 15);
        label3.setForeground(Color.white);

        final JLabel label4 = new JLabel("21");
        label4.setBounds(570, 618, 20, 15);
        label4.setForeground(Color.white);

        final JLabel label5 = new JLabel("20");
        label5.setBounds(510, 618, 20, 15);
        label5.setForeground(Color.white);

        final JLabel label6 = new JLabel("19");
        label6.setBounds(455, 618, 20, 15);
        label6.setForeground(Color.white);

        final JLabel label7 = new JLabel("18");
        label7.setBounds(370, 618, 20, 15);
        label7.setForeground(Color.white);

        final JLabel label8 = new JLabel("17");
        label8.setBounds(310, 618, 20, 15);
        label8.setForeground(Color.white);

        final JLabel label9 = new JLabel("16");
        label9.setBounds(250, 618, 20, 15);
        label9.setForeground(Color.white);

        final JLabel label10 = new JLabel("15");
        label10.setBounds(190, 618, 20, 15);
        label10.setForeground(Color.white);

        final JLabel label11 = new JLabel("14");
        label11.setBounds(130, 618, 20, 15);
        label11.setForeground(Color.white);

        final JLabel label12 = new JLabel("13");
        label12.setBounds(75, 618, 20, 15);
        label12.setForeground(Color.white);

        final JLabel label13 = new JLabel("12");
        label13.setBounds(75, 32, 20, 15);
        label13.setForeground(Color.white);

        final JLabel label14 = new JLabel("11");
        label14.setBounds(130, 32, 20, 15);
        label14.setForeground(Color.white);

        final JLabel label15 = new JLabel("10");
        label15.setBounds(190, 32, 20, 15);
        label15.setForeground(Color.white);

        final JLabel label16 = new JLabel("9");
        label16.setBounds(250, 32, 20, 15);
        label16.setForeground(Color.white);

        final JLabel label17 = new JLabel("8");
        label17.setBounds(310, 32, 20, 15);
        label17.setForeground(Color.white);

        final JLabel label18 = new JLabel("7");
        label18.setBounds(370, 32, 20, 15);
        label18.setForeground(Color.white);

        final JLabel label19 = new JLabel("6");
        label19.setBounds(455, 32, 20, 15);
        label19.setForeground(Color.white);

        final JLabel label20 = new JLabel("5");
        label20.setBounds(510, 32, 20, 15);
        label20.setForeground(Color.white);

        final JLabel label21 = new JLabel("4");
        label21.setBounds(570, 32, 20, 15);
        label21.setForeground(Color.white);

        final JLabel label22 = new JLabel("3");
        label22.setBounds(630, 32, 20, 15);
        label22.setForeground(Color.white);

        final JLabel label23 = new JLabel("2");
        label23.setBounds(690, 32, 20, 15);
        label23.setForeground(Color.white);

        final JLabel label24 = new JLabel("1");
        label24.setBounds(750, 32, 20, 15);
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
