package com.freechess.game.actions.acts.binary;

import com.freechess.game.actions.acts.Act;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;
import com.freechess.game.pieces.Piece;

/**
 * Attacks all pieces (enemy and friends) in a line
 */
public class RushAct extends Act {
    @Override
    public void perform(Board board, Position pos1, Position pos2) throws Exception {

        Piece piece = board.pieceAt(pos1);
        // check equal pos
        if(pos1.equals(pos2)){
            throw new IllegalArgumentException();
        }
        // check diagonal

        int dy = (pos1.getY()-pos2.getY());
        int dx = (pos1.getX()-pos2.getX());
        int l = Math.max(Math.abs(dy),Math.abs(dx));

        int x = pos1.getX();
        int y = pos1.getY();

        for(int i=1;i<=l;i++){
            x-=dx/l;
            y-=dy/l; //HERE is the minus!!!
            if(!board.isFree(x,y)){
                board.takePiece(new Position(x,y));
            }
        }
        board.removePiece(pos1);
        board.addPiece(piece,pos2);
    }
}
