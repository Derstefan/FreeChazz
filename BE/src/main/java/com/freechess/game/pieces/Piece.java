package com.freechess.game.pieces;

import com.freechess.game.core.Board;
import com.freechess.game.core.Player;
import com.freechess.game.core.Position;

import java.util.ArrayList;


public abstract class Piece {

    private Player owner;
    private char symbol;
    private Board board;
    private Position pos;
    private ArrayList<Position> possibleMoves = new ArrayList<>();

    public Piece(Board board, Player owner,char symbol,Position pos){
        this.owner = owner;
        this.symbol = symbol;
        this.board = board;
        this.pos = pos;
    }
    public abstract void computePossibleMoves();

    public abstract void perform(Position toPos);


    public ArrayList<Position> getPossibleMoves(){
        clearPossibleMoves();
        computePossibleMoves();
        return possibleMoves;

    }



    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public Board getBoard() {
        return board;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public void addPossibleMove(Position move){
        possibleMoves.add(move);
    }

    public void clearPossibleMoves(){
        possibleMoves.clear();;
    }


    public boolean isEnemyAt(Position pos) {
        if(board.pieceAt(pos)!=null){
            return !board.pieceAt(pos).getOwner().equals(this.getOwner());
        }
    return false;
    }

    public boolean isFriendAt(Position pos) {
        if(board.pieceAt(pos)!=null){
            return board.pieceAt(pos).getOwner().equals(this.getOwner());
        }
        return false;
    }

    public boolean isEmptyAt(Position pos){
        return !isEnemyAt(pos) && !isFriendAt(pos);
    }

    public boolean isOnboard(Position pos) {
     return board.isOnboard(pos);
    }
}
