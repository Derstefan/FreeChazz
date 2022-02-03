package com.freechess.game.pieces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freechess.game.board.Position;
import com.freechess.game.player.EPlayer;

import java.util.ArrayList;


public class Piece {


    private String symbol;
    private IPieceType pieceType;

    private EPlayer owner;
    private ArrayList<Position> possibleMoves = new ArrayList<>();


    public Piece(EPlayer owner, IPieceType pieceType){
        this.owner = owner;
        this.pieceType = pieceType;
        this.symbol = pieceType.getSymbol();
    }

    public void addPossibleMove(Position move){
        possibleMoves.add(move);
    }

    public void clearPossibleMoves(){
        possibleMoves.clear();;
    }

    public EPlayer getOwner(){
        return owner;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPieceId() {
        return pieceType.getSerial();
    }

    @JsonIgnore
    public IPieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(IPieceType pieceType) {
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
