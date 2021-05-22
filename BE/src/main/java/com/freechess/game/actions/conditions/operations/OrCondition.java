package com.freechess.game.actions.conditions.operations;

import com.freechess.game.actions.conditions.Condition;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;

public class OrCondition extends Condition {

    private Condition cond1;
    private Condition cond2;

    public OrCondition(Condition cond1, Condition cond2){
        this.cond1 = cond1;
        this.cond2 = cond2;
    }

    @Override
    public boolean check(Board board, Position pos1, Position pos2) {
        return cond1.check(board,pos1,pos2) || cond2.check(board,pos1,pos2);
    }

    public Condition getCond1() {
        return cond1;
    }

    public Condition getCond2() {
        return cond2;
    }
}
