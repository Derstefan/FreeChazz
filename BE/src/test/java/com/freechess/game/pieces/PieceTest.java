package com.freechess.game.pieces;

import com.freechess.game.actions.Actions;
import com.freechess.game.board.Position;
import com.freechess.game.pieces.impl.PieceType;
import com.freechess.game.pieces.Piece;
import com.freechess.game.pieces.impl.PieceTypeBuilder;
import com.freechess.game.player.EPlayer;
import org.junit.jupiter.api.Test;

import static com.freechess.game.pieces.PieceTypeHelper.CROSS1;
import static org.assertj.core.api.Assertions.assertThat;

public class PieceTest {



    @Test
    void generalPieceTypeAndPieceTest(){
        Piece p = new Piece(EPlayer.P1,CROSS1);

        assertThat(CROSS1.getSymbol()).isEqualTo("M");
        assertThat(CROSS1.getActions().size()).isEqualTo(4);
        assertThat(p.getOwner()).isEqualTo(EPlayer.P1);
        assertThat(p.getSymbol()).isEqualTo("M");
        //as long as it's not refered to a board
        assertThat(p.getPossibleMoves().isEmpty()).isTrue();
    }
}
