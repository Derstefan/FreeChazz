package com.freechess.game.actions.acts.binary;

import com.freechess.game.actions.acts.Act;
import com.freechess.game.board.Board;
import com.freechess.game.exception.MovementException;
import com.freechess.game.player.EPlayer;
import com.freechess.game.pieces.Piece;
import com.freechess.game.board.Position;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoveOrAttackAct extends Act {

/*    public MoveOrAttackAct() {
        this.type = "MoveOrAttackAct";
    }*/

    @Override
    public void perform(Board board, Position pos1,Position pos2) throws MovementException{
        Piece piece = board.pieceAt(pos1);
        EPlayer owner = piece.getOwner();

        Piece targetPiece = board.pieceAt(pos2);

        if (targetPiece != null) {
            if (targetPiece.getOwner().equals(owner)) {
                log.warn("Can not attack own piece.");
                throw new MovementException();
            }
            if (!targetPiece.getOwner().equals(owner)) {
                // attack enemy
                board.takePiece(pos2);
            }
        }
        board.removePiece(pos1);

        board.addPiece(piece,pos2);
    }

}
