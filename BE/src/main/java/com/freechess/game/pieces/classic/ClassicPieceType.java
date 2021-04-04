package com.freechess.game.pieces.classic;

import com.freechess.game.core.*;
import com.freechess.game.pieces.PieceType;

public abstract class ClassicPieceType extends PieceType {


    /**
     * Classical pieces walk or jump to a free position or enemy.
     */
    public ClassicPieceType() {
        super();
    }

    @Override
    public void perform(Board board, Position fromPos,Position toPos) {
        Piece piece = board.pieceAt(fromPos);
        EPlayer owner = piece.getOwner();

        Piece targetPiece = board.pieceAt(toPos);

        if (targetPiece != null) {
            if (targetPiece.getOwner().equals(owner)) {
                // TODO: the same player -> error
                // or switch tower and king
            }
            if (!targetPiece.getOwner().equals(owner)) {
                // attack enemy
                board.removePiece(toPos);
            }
        }
        board.removePiece(fromPos);
        board.addPiece(piece,toPos);
    }



}
