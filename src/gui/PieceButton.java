package gui;

import javafx.scene.shape.Rectangle;

public class PieceButton extends Rectangle {
    public Piece piece;
    public int number;

    public PieceButton(Piece piece, int value) {
        super();
        this.piece = piece;
        this.number = value;
    }

}
