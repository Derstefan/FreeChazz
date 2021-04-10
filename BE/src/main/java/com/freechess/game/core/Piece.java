package com.freechess.game.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freechess.game.pieces.PieceType;

import java.util.ArrayList;


public class Piece {


    private String symbol = "P";
    private PieceType pieceType;

    private EPlayer owner;
    private ArrayList<Position> possibleMoves = new ArrayList<>();


    public Piece(EPlayer owner, PieceType pieceType){
        this.owner = owner;
        this.pieceType = pieceType;
    }

    public void addPossibleMove(Position move){
        possibleMoves.add(move);
    }

    public void clearPossibleMoves(){
        possibleMoves.clear();;
    }

/*    public boolean isEnemyAt(Position pos) {
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
    }*/




    public EPlayer getOwner(){
        return owner;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonIgnore
    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public ArrayList<Position> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(ArrayList<Position> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public boolean canMoveTo(Position posTo ){
        for (int i = 0; i < possibleMoves.size(); i++) {
            if(possibleMoves.get(i).getX()==posTo.getX() && possibleMoves.get(i).getY()==posTo.getY()){
                return true;
            }
        }
        return false;
    }
}
