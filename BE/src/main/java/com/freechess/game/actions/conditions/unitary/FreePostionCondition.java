package com.freechess.game.actions.conditions.unitary;

import com.freechess.game.actions.conditions.UnitaryCondition;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;

import java.util.Objects;

public class FreePostionCondition  extends UnitaryCondition {

    @Override
    public boolean check(Board board, Position pos) {
        Objects.requireNonNull(board);
        Objects.requireNonNull(pos);
        return board.pieceAt(pos)==null;
    }

}
