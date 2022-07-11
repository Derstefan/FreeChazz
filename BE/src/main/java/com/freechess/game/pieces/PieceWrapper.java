package com.freechess.game.pieces;

public class PieceWrapper {
    private Piece piece;

    public PieceWrapper(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
