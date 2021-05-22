package com.freechess.game.actions.acts;

import com.freechess.game.board.Board;
import com.freechess.game.board.Position;

public abstract class UnitaryAct extends Act {
    public abstract void perform(Board board, Position pos);

    public void perform(Board board,Position pos1,Position pos2){
        perform(board,pos2);
    }
}
