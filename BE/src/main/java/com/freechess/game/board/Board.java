package com.freechess.game.board;

import com.freechess.game.pieces.IPieceType;
import com.freechess.game.pieces.Piece;
import com.freechess.game.pieces.PieceWrapper;
import com.freechess.game.pieces.impl.PieceType;
import com.freechess.game.player.EPlayer;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
public class Board {

    private int width;
    private int height;

    private Piece king1;
    private Piece king2;

    private Optional<EPlayer> winner = Optional.empty();

    private ArrayList<Piece> graveyard = new ArrayList<>();


    private Piece[][] board;

    private Board(int width, int height) {
        this.width = width;
        this.height = height;
        board = new Piece[height][width];
    }

    // ----------------------------------------- Board actions


    public void perform(Position fromPos, Position toPos){
        if(getWinner().isPresent()){
            return;
        }
        Piece piece = pieceAt(fromPos);
        try{
            piece.getPieceType().perform(this,fromPos,toPos);
        } catch (Exception e){
            log.warn("There was an Error performing this draw.");
        }
     }


    public void takePiece(Position pos){
        if(getWinner().isPresent()){
            return;
        }
        Piece p = pieceAt(pos);
        if(p.equals(king1)){
            setWinner(king2.getOwner());
        } else if(p.equals(king2)){
            setWinner(king1.getOwner());
        }
        graveyard.add(p);

        removePiece(pos);
    }

    public void removePiece(Position pos){
        Piece p = board[pos.getX()][pos.getY()];
        board[pos.getY()][pos.getX()] = null;
    }

    public void addPiece(Piece piece,Position pos){
        piece.setPosition(pos);
        board[pos.getY()][pos.getX()] = piece;
    }

    public void swapPieces(Position pos1,Position pos2){
        Piece p1 = pieceAt(pos1);
        Piece p2 = pieceAt(pos2);
        addPiece(p1,pos2);
        addPiece(p2,pos1);
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
                    IPieceType type = piece.getPieceType();
                    piece.setPossibleMoves(type.computePossibleMoves(this,pos));
                }
            }
        }
    }







    public Piece pieceAt(Position p){
        if(p.getX()<0 || p.getX()>=width || p.getY()<0 || p.getY()>=height){
            return null;
        }
        return board[p.getY()][p.getX()];
    }

    public boolean isFree(Position p){
        if(board[p.getY()][p.getX()]==null){
            return true;
        }
        return false;
    }

    public boolean isFree(int x,int y){
        if(!isOnboard(new Position(x,y)))return true;
        if(board[y][x]==null){
            return true;
        }
        return false;
    }




    public String symbolAt(Position p){
        if(pieceAt(p)!=null){
            return " " +pieceAt(p).getSymbol() + " ";
        }
        return " - ";
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public Optional<EPlayer> getWinner() {
        return winner;
    }

    public void setWinner(EPlayer winner) {
        this.winner = Optional.of(winner);
    }

    public Piece getKing1() {
        return king1;
    }

    public void setKing1(Piece king1) {
        king1.setKing("1");
        this.king1 = king1;
    }

    public Piece getKing2() {
        return king2;
    }

    public void setKing2(Piece king2) {
        king2.setKing("1");
        this.king2 = king2;
    }

    public ArrayList<Piece> getGraveyard() {
        return graveyard;
    }

    public int countPieces(){
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j]!= null){
                    count++;
                }
            }
        }
        return count;
    }


    public String toString(){
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

    public static Board getInstance(int width, int height){
        return new Board(width,height);
    }

}
