package com.freechess.game.core;

import com.freechess.game.pieces.Piece;

import java.util.ArrayList;

public class Board {

    private int width = 8;
    private int height = 8;
    private Piece[][] board = new Piece[width][height];

    public Board() {

    }

    public void removePiece(Position pos){
        Piece p = board[pos.getX()][pos.getY()];

        p.getOwner().addToGraveyard(p);
        board[pos.getX()][pos.getY()] = null;
    }

    public void addPiece(Piece piece,Position pos){
        board[pos.getX()][pos.getY()] = piece;
        piece.setPos(pos);
    }

    public String drawBoard(){
        String str = "";
        for(int j=0;j<board[0].length+2;j++){
            str+="-";
        }
        str+="\n";
        for(int i=0;i<board.length;i++){
            str+="|";
            for(int j=0;j<board[0].length;j++){
                str+=symbolAt(new Position(i,j));
            }
            str+="|\n";
        }
        for(int j=0;j<board[0].length+2;j++){
            str+="-";
        }
        return str;
    }


    public Piece pieceAt(Position p){
        return board[p.getX()][p.getY()];
    }

    public char symbolAt(Position p){
        if(pieceAt(p)!=null){
            return pieceAt(p).getSymbol();
        }
        return ' ';
    }
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isOnboard(Position pos){
        int x = pos.getX();
        int y = pos.getY();
        return x>=0 && x<width && y>=0 && y<height;
    }
}
