package com.freechess.game.pieces;

import com.freechess.game.board.Board;
import com.freechess.game.board.Position;

import java.util.ArrayList;

public abstract class PieceType {

    private String symbol;

    public PieceType(){

    }

    public abstract void perform(Board board,Position fromPos, Position toPos);

    public abstract ArrayList<Position> computePossibleMoves(Board board, Position pos);

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
