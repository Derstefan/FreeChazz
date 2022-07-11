package com.freechess.game.actions.conditions;

import com.freechess.game.board.Board;
import com.freechess.game.board.BoardBuilder;
import com.freechess.game.board.Position;
import com.freechess.game.pieces.Piece;
import com.freechess.game.player.EPlayer;
import org.junit.jupiter.api.Test;

import static com.freechess.game.pieces.PieceTypeHelper.WALK1;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FriendAtPostitionCondition {

/*    @Test
    public void simpleBoardTest(){

        Piece king1 = new Piece(EPlayer.P1,WALK1);
        Piece p1 = new Piece(EPlayer.P1,WALK1);
        Piece king2 = new Piece(EPlayer.P2,WALK1);
        Board board = new BoardBuilder(15,15)
                .putKing(EPlayer.P1,king1,new Position(4,8))
                .putKing(EPlayer.P2, king2,new Position(4,6))

                .build();

        assertThat(king1.canMoveTo(new Position(4,7))).isFalse();
    }*/
}
