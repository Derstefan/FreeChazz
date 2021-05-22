package com.freechess.game.actions.conditions;

import com.freechess.game.board.Board;
import com.freechess.game.board.Position;

public abstract class UnitaryCondition extends Condition {
    protected abstract boolean check(Board board, Position pos);

    @Override
    public boolean check(Board board, Position pos1, Position pos2){
        return check(board,pos2);
    }
}
