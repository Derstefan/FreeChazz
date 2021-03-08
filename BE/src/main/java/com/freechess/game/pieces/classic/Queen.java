package com.freechess.game.pieces.classic;

import com.freechess.game.core.Board;
import com.freechess.game.core.Player;
import com.freechess.game.core.Position;

public class Queen extends ClassicPiece{
    public Queen(Board board, Player owner, Position pos) {
        super(board, owner, 'B', pos);
    }

    @Override
    public void computePossibleMoves() {
        moveDirection(0,-1);
        moveDirection(1,-1);
        moveDirection(1,0);
        moveDirection(1,1);
        moveDirection(0,1);
        moveDirection(-1,1);
        moveDirection(-1,0);
        moveDirection(-1,-1);
    }

    private void moveDirection(int dx,int dy){
        Position move = new Position(getPos().getX(),getPos().getY());
        boolean stopped = false;
        while(!stopped){
            if(isFriendAt(move) || !isOnboard(move)){
                stopped = true;
            }
            if(isEnemyAt(move)){
                addPossibleMove(move);
                stopped=true;
            }
            addPossibleMove(move);
            move.add(dx,dy);
        }
    }

}
