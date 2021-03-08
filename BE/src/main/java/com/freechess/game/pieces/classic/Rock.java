package com.freechess.game.pieces.classic;

import com.freechess.game.core.Board;
import com.freechess.game.core.Player;
import com.freechess.game.core.Position;

public class Rock extends ClassicPiece{
    public Rock(Board board, Player owner, Position pos) {
        super(board, owner, 'R', pos);
    }

    @Override
    public void computePossibleMoves() {
        moveDirection(1,0);
        moveDirection(0,1);
        moveDirection(-1,0);
        moveDirection(0,-1);
    }

    private void moveDirection(int dx,int dy){
        Position move = new Position(getPos().getX()+dx,getPos().getY()+dy);
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
