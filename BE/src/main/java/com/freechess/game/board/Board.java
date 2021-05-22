package com.freechess.game.board;

import com.freechess.game.pieces.Piece;
import com.freechess.game.pieces.PieceType;
import com.freechess.game.player.EPlayer;

import java.util.ArrayList;

public class Board {

    private int width = 8;
    private int height = 8;

    private Piece king1;
    private Piece king2;

    private EPlayer winner = null;

    private ArrayList<Piece> graveyard = new ArrayList<>();


    private Piece[][] board;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        board = new Piece[height][width];
    }



    /**
     * Compute possible moves of all pieces.
     */
    public void computePossibleMoves(){
        for(int i =0;i< board.length;i++){
            for(int j =0;j< board[0].length;j++){
                Position pos = new Position(i,j);
                Piece piece = pieceAt(pos);
                if(piece!=null){
                    PieceType type = piece.getPieceType();
                    piece.setPossibleMoves(type.computePossibleMoves(this,pos));
                }
            }
        }
    }

    public void perform(Position fromPos, Position toPos){
        Piece piece = pieceAt(fromPos);
        piece.getPieceType().perform(this,fromPos,toPos);
    }


    public void takePiece(Position pos){
        Piece p = board[pos.getX()][pos.getY()];
        if(p.equals(king1)){
            winner = king1.getOwner();
        } else if(p.equals(king2)){
            winner = king2.getOwner();
        }
        graveyard.add(p);

        board[pos.getY()][pos.getX()] = null;
    }

    public void removePiece(Position pos){
        Piece p = board[pos.getX()][pos.getY()];
        board[pos.getY()][pos.getX()] = null;
    }

    public void addPiece(Piece piece,Position pos){
        board[pos.getY()][pos.getX()] = piece;
    }

    public String drawBoard(){
        String str = "";
        for(int j=0;j<board[0].length+2;j++){
            str+=" - ";
        }
        str+="\n";
        for(int i=0;i<board.length;i++){
            str+="|";
            for(int j=0;j<board[0].length;j++){
                str+=symbolAt(new Position(j,i));
            }
            str+="|\n";
        }
        for(int j=0;j<board[0].length+2;j++){
            str+=" - ";
        }
        return str;
    }


    public Piece pieceAt(Position p){
        if(p.getX()<0 || p.getX()>=width || p.getY()<0 || p.getY()>=height){
            return null;
        }
        return board[p.getY()][p.getX()];
    }

    public String symbolAt(Position p){
        if(pieceAt(p)!=null){
            return " " +pieceAt(p).getSymbol() + " ";
        }
        return " - ";
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isOnboard(Position pos){
        int x = pos.getX();
        int y = pos.getY();
        return x>=0 && x<width && y>=0 && y<height;
    }

    public boolean areEnemys(Piece p1,Piece p2){
        if(p1==null || p2==null){
            return false;
        }
        return p1.getOwner()!=p2.getOwner();
    }

    public Piece[][] getBoard() {
        return board;
    }

    public EPlayer getWinner() {
        return winner;
    }

    public Piece getKing1() {
        return king1;
    }

    public void setKing1(Piece king1) {
        this.king1 = king1;
    }

    public Piece getKing2() {
        return king2;
    }

    public void setKing2(Piece king2) {
        this.king2 = king2;
    }

    public ArrayList<Piece> getGraveyard() {
        return graveyard;
    }



}
