package com.freechess.game.actions.conditions.operations;

import com.freechess.game.actions.conditions.Condition;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;

public class TrivCondition extends Condition {

    @Override
    public  boolean check(Board board, Position pos1, Position pos2) {
        return true;
    }

}
