package com.freechess.game.pieces.classic;

import com.freechess.game.core.Board;
import com.freechess.game.core.Player;
import com.freechess.game.core.Position;
import com.freechess.game.pieces.Piece;

public abstract class ClassicPiece extends Piece {

    public ClassicPiece(Board board,Player owner, char symbol,Position pos) {
        super(board, owner, symbol,pos);
    }

    @Override
    public void perform(Position toPos) {
        Board board = getBoard();
        Position pos = getPos();

        Piece piece = board.pieceAt(pos);
        Piece targetPiece = board.pieceAt(toPos);

        if (targetPiece != null) {
            if (targetPiece.getOwner().equals(this.getOwner())) {
                // TODO: the same player -> error
                // or switch tower and king
            }
            if (!targetPiece.getOwner().equals(this.getOwner())) {
                // attack enemy
                board.removePiece(toPos);
            }
        }
        board.removePiece(pos);
        board.addPiece(this,toPos);
    }



}
