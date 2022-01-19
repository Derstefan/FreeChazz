package com.freechess.game.actions.conditions.binary;

import com.freechess.game.actions.conditions.Condition;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;

import java.util.Objects;

public class EnemyAtPostionCondition extends Condition {

    @Override
    public boolean check(Board board, Position pos1, Position pos2) {
        Objects.requireNonNull(board);
        Objects.requireNonNull(pos1);
        Objects.requireNonNull(pos2);
        return board.areEnemys(board.pieceAt(pos1),board.pieceAt(pos2));
    }

}
