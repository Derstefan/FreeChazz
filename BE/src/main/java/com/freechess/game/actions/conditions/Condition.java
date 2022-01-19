package com.freechess.game.actions.conditions;

import com.freechess.game.actions.conditions.operations.AndCondition;
import com.freechess.game.actions.conditions.operations.NotCondition;
import com.freechess.game.actions.conditions.operations.OrCondition;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;

public abstract class Condition {

    public abstract boolean check(Board board, Position pos1, Position pos2);

    public AndCondition AND(Condition cond2){
        return new AndCondition(this,cond2);
    }

    public OrCondition OR(Condition cond2){
        return new OrCondition(this,cond2);
    }

    public NotCondition NOT(){
        return new NotCondition(this);
    }
}
