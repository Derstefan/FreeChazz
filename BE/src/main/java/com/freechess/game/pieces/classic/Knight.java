package com.freechess.game.pieces.classic;

import com.freechess.game.core.Board;
import com.freechess.game.core.Player;
import com.freechess.game.core.Position;

public class Knight extends ClassicPiece{
    public Knight(Board board, Player owner, Position pos) {
        super(board, owner, 'H', pos);
    }

    @Override
    public void computePossibleMoves() {
        addMoves(-2,1);
        addMoves(-2,-1);
        addMoves(-1,2);
        addMoves(-1,-2);
        addMoves(1,2);
        addMoves(1,-2);
        addMoves(2,1);
        addMoves(2,-1);
    }

    public void addMoves(int dx, int dy) {
        Position toPos = new Position(getPos().getX() + dx,getPos().getY() + dy);
        if(isOnboard(toPos) && !isFriendAt(toPos)){
            addPossibleMove(toPos);
        }
    }
}
