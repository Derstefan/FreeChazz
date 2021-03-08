package com.freechess.game.pieces.classic;

import com.freechess.game.core.Board;
import com.freechess.game.core.Player;
import com.freechess.game.core.Position;

public class King extends ClassicPiece {

    public King(Board board,Player owner,Position pos){
        super(board,owner,'K',pos);
    }

    @Override
    public void computePossibleMoves() {

        addMoves(-1,1);
        addMoves(0,1);
        addMoves(+1,1);
        addMoves(-1,0);
        addMoves(+1,0);
        addMoves(-1,-1);
        addMoves(0,-1);
        addMoves(+1,-1);
    }

    public void addMoves(int dx, int dy) {
        Position toPos = new Position(getPos().getX() + dx,getPos().getY() + dy);
        if(isOnboard(toPos) && !isFriendAt(toPos)){
            addPossibleMove(toPos);
        }
    }



}
