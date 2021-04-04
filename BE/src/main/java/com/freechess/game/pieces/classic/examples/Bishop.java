package com.freechess.game.pieces.classic.examples;

import com.freechess.game.core.Board;

import com.freechess.game.core.Position;
import com.freechess.game.pieces.classic.ClassicPieceType;

import java.util.ArrayList;

public class Bishop extends ClassicPieceType {
    @Override
    public ArrayList<Position> computePossibleMoves(Board board, Position pos) {
        return null;
    }
/*    public Bishop(Board board, Player owner, Position pos) {
        super(board, owner, 'B', pos);
    }

    @Override
    public void computePossibleMoves() {
        moveDirection(1,1);
        moveDirection(-1,1);
        moveDirection(-1,1);
        moveDirection(-1,-1);
    }

    private void moveDirection(int dx,int dy){
        Position move = new Position(getPos().getX(),getPos().getY());
        boolean stopped = false;
        while(!stopped){
            if(isFriendAt(move) || !isOnboard(move)){
                stopped = true;
            } else if(isEnemyAt(move)){
                addPossibleMove(move);
                stopped=true;
            } else {
                addPossibleMove(move);
                move.add(dx, dy);
            }
        }
    }*/

}
