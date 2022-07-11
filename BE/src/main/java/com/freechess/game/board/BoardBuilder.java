package com.freechess.game.board;


import com.freechess.game.pieces.Piece;
import com.freechess.game.player.EPlayer;
import java.util.HashMap;

public class BoardBuilder {

    private Board board;
    private int width;
    private int height;
    //for validatation
    private boolean king1Set = false;
    private boolean king2Set = false;
    private int idCounter = 0;



    public BoardBuilder(int width,int height){
        //TODO:validate width and height
        this.width = width;
        this.height = height;
        this.board = Board.getInstance(width,height);
    }

    public BoardBuilder putPiece(Piece piece, Position pos){
        piece.setId(idCounter);
        idCounter++;
        board.addPiece(piece,pos);
        return this;
    }

    public void putPieces(HashMap<Position, Piece> pieces){
        for(Position pos:pieces.keySet()){
            putPiece(pieces.get(pos),pos);
        }
    }

    public BoardBuilder putKing(EPlayer player, Piece king, Position pos){
        putPiece(king, pos);
        if(EPlayer.P1.equals(player)){
            board.setKing1(king);
            king1Set = true;
            return this;
        }
        board.setKing2(king);
        king2Set = true;
        return this;
    }

    // with null return or with exception?
    public Board build() {
        if(validate()){
            board.computePossibleMoves();
            return board;
        }
        return null;
    }

    private boolean validate() {
        if(!king1Set || !king2Set){
            return false;
        }
        return true;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
