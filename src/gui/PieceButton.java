import javax.swing.*;

public class PieceButton extends JButton {
    public Piece piece;
    public int number;

    public PieceButton(Piece piece, int value) {
        super();
        this.piece = piece;
        this.number = value;
    }
}