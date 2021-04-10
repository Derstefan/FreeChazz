package com.freechess.game.core;

import com.freechess.game.pieces.generators.PieceTypeGenerator;

public class BoardGenerator {


    public Board generate(){
        Board board = new Board(30,30);
        init(board);
        board.computePossibleMoves();
        return board;
    }


    private void init(Board board){
        Piece king1 = new Piece(EPlayer.P1, PieceTypeGenerator.generate());
        Piece king2 = new Piece(EPlayer.P2, PieceTypeGenerator.generate());
        board.setKing1(king1);
        board.setKing2(king2);
        board.addPiece(king1,new Position(1,3));
        board.addPiece(king2,new Position(4,2));


        board.addPiece(new Piece(EPlayer.P2, PieceTypeGenerator.generate()),new Position(0,0));
        board.addPiece(new Piece(EPlayer.P2, PieceTypeGenerator.generate()),new Position(5,2));
        board.addPiece(new Piece(EPlayer.P2, PieceTypeGenerator.generate()),new Position(2,4));
        board.addPiece(new Piece(EPlayer.P2, PieceTypeGenerator.generate()),new Position(13,4));
        board.addPiece(new Piece(EPlayer.P2, PieceTypeGenerator.generate()),new Position(10,2));
        board.addPiece(new Piece(EPlayer.P2, PieceTypeGenerator.generate()),new Position(15,0));
        board.addPiece(new Piece(EPlayer.P2, PieceTypeGenerator.generate()),new Position(15,2));
        board.addPiece(new Piece(EPlayer.P2, PieceTypeGenerator.generate()),new Position(16,4));
        board.addPiece(new Piece(EPlayer.P2, PieceTypeGenerator.generate()),new Position(17,2));
        board.addPiece(new Piece(EPlayer.P2, PieceTypeGenerator.generate()),new Position(17,1));
        board.addPiece(new Piece(EPlayer.P1, PieceTypeGenerator.generate()),new Position(5,12));
        board.addPiece(new Piece(EPlayer.P1, PieceTypeGenerator.generate()),new Position(2,14));
        board.addPiece(new Piece(EPlayer.P1, PieceTypeGenerator.generate()),new Position(13,14));
        board.addPiece(new Piece(EPlayer.P1, PieceTypeGenerator.generate()),new Position(10,10));
        board.addPiece(new Piece(EPlayer.P1, PieceTypeGenerator.generate()),new Position(15,18));
        board.addPiece(new Piece(EPlayer.P1, PieceTypeGenerator.generate()),new Position(12,19));
        board.addPiece(new Piece(EPlayer.P1, PieceTypeGenerator.generate()),new Position(13,17));
        board.addPiece(new Piece(EPlayer.P1, PieceTypeGenerator.generate()),new Position(10,20));
    }

}
