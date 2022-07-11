package com.freechess.game.actions.conditions.binary;

import com.freechess.game.actions.conditions.Condition;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;


/**
 * Checks if the path between two positions is free (exluding start- and endpoint)
 */
public class ClearPathCondition extends Condition {

    @Override
    public boolean check(Board board, Position pos1, Position pos2) {

        // check equal pos
        if(pos1.equals(pos2)){
            throw new IllegalArgumentException();
        }
        // check diagonal

        int dy = (pos1.getY()-pos2.getY());
        int dx = (pos1.getX()-pos2.getX());
        int l = Math.max(Math.abs(dy),Math.abs(dx));

        int x = pos1.getX();
        int y = pos1.getY();

        for(int i=1;i<l;i++){
            x-=dx/l;
            y-=dy/l; //HERE is the minus!!!
            if(!board.isFree(x,y)){
                return false;
            }
        }
        return true;
    }
}
