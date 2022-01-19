package com.freechess.game.actions.acts;

import com.freechess.game.board.Board;
import com.freechess.game.board.Position;

public abstract class Act {

    public abstract void perform(Board board, Position pos1, Position pos2) throws Exception;

}
