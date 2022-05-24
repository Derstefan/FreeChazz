package com.freechess.game.board;

import com.freechess.game.pieces.Piece;
import com.freechess.game.player.EPlayer;

import static com.freechess.game.pieces.PieceTypeHelper.CROSS1;
import static com.freechess.game.pieces.PieceTypeHelper.WALK1;

public class BoardHelper {

    public static Board exampleBoard(){
            Piece king1 = new Piece(EPlayer.P1,CROSS1);
            Piece king2 = new Piece(EPlayer.P2,CROSS1);
            Board board = new BoardBuilder(15,15)
                    .putKing(EPlayer.P1,king1,new Position(0,6))
                    .putKing(EPlayer.P2, king2,new Position(14,6))
                    .build();
         return board;
    }

    public static Board exampleBoard2(){
        Piece king1 = new Piece(EPlayer.P1,WALK1);
        Piece king2 = new Piece(EPlayer.P2,WALK1);
        Board board = new BoardBuilder(15,15)
                .putKing(EPlayer.P1,king1,new Position(4,6))
                .putKing(EPlayer.P2, king2,new Position(4,8))
                .build();
        return board;
    }
}
