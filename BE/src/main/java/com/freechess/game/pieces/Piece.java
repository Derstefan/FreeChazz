package com.freechess.game.pieces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freechess.game.board.Position;
import com.freechess.game.player.EPlayer;

import java.util.ArrayList;
import java.util.UUID;


public class Piece {

    private int id; //for piece


    private IPieceType pieceType;

    private Position position;

    private String king;

    private EPlayer owner;
    private ArrayList<Position> possibleMoves = new ArrayList<>();




    public Piece(EPlayer owner, IPieceType pieceType){
        this.owner = owner;
        this.pieceType = pieceType;
        this.king = "0";
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return pieceType.getSymbol();
    }

    public String getSeed(){
        return  pieceType.getSeed();
    }

    public String getSerial() {
        return pieceType.getSerial();
    }

    public String getKing() {
        return king;
    }

    public void setKing(String king) {
        if(king=="1"){
            this.king = "1";
        } else {
            this.king = "0";
        }

    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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
