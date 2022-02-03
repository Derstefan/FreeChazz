package com.freechess.game.pieces;

import com.freechess.game.board.Board;
import com.freechess.game.board.Position;

import java.util.ArrayList;

public interface IPieceType {


    public abstract void perform(Board board, Position fromPos, Position toPos) throws Exception;

    public abstract ArrayList<Position> computePossibleMoves(Board board, Position pos);

    public abstract String getSymbol();

    //TODO: maybe better "serilizer"?
    public abstract String getSerial();
}
