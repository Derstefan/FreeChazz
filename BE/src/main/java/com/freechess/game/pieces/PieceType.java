package com.freechess.game.pieces;

import com.freechess.game.core.Board;
import com.freechess.game.core.Piece;
import com.freechess.game.core.Position;

import java.util.ArrayList;

public abstract class PieceType {

    private String symbol;

    public PieceType(){

    }

    public abstract void perform(Board board,Position fromPos, Position toPos);

    public abstract ArrayList<Position> computePossibleMoves(Board board, Position pos);
}
