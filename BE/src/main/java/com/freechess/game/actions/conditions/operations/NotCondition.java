package com.freechess.game.actions.conditions.operations;

import com.freechess.game.actions.conditions.Condition;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;

public class NotCondition extends Condition {

    private Condition cond;

    public NotCondition(Condition cond){
        this.cond = cond;

    }

    @Override
    public boolean check(Board board, Position pos1, Position pos2) {

        return !cond.check(board,pos1,pos2);
    }

    public Condition getCond() {
        return cond;
    }
}
