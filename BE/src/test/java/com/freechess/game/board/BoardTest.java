package com.freechess.game.board;

import com.freechess.game.pieces.Piece;
import com.freechess.game.player.EPlayer;
import org.junit.jupiter.api.Test;

import static com.freechess.game.pieces.PieceTypeHelper.CROSS1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {



    @Test
    public void simpleBoardTest(){
        Piece king1 = new Piece(EPlayer.P1,CROSS1);
        Piece king2 = new Piece(EPlayer.P2,CROSS1);
        Board board = new BoardBuilder(15,15)
                .putKing(EPlayer.P1,king1,new Position(0,6))
                .putKing(EPlayer.P2, king2,new Position(14,6))
                .build();
        assertThat(board).isNotNull();
        assertThat(board.areEnemys(king1,king1)).isFalse();
        assertThat(board.areEnemys(king1,king2)).isTrue();
    }


    @Test
    public void whenBoardisGeneratedWithoutKings_thenBoardisNull(){
        Board board = new BoardBuilder(15,15)
                .build();
        assertThat(board).isNull();
    }





}
