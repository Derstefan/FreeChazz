package com.freechess.game.pieces;

import com.freechess.game.exception.FailedParsingPieceTypeException;
import com.freechess.game.pieces.impl.PieceType;
import com.freechess.game.player.EPlayer;
import org.junit.jupiter.api.Test;

import static com.freechess.game.pieces.PieceTypeHelper.CROSS1;
import static org.assertj.core.api.Assertions.assertThat;

public class PieceTest {



    @Test
    void generalPieceTypeAndPieceTest(){
        Piece p = new Piece(EPlayer.P1,CROSS1);

        assertThat(CROSS1.getSymbol()).isEqualTo("M");
        assertThat(CROSS1.getActionMap().size()).isEqualTo(4);
        assertThat(p.getOwner()).isEqualTo(EPlayer.P1);
        assertThat(p.getSymbol()).isEqualTo("M");
        //as long as it's not refered to a board
        assertThat(p.getPossibleMoves().isEmpty()).isTrue();
    }





    @Test
    void testPieceTypeById() throws FailedParsingPieceTypeException {

        String id =  CROSS1.getSerial();
        PieceType type1 = PieceType.getInstance(id);
        assertThat(CROSS1.equals(type1)).isTrue();


        PieceType type2 = PieceType.getInstance(PieceTypeHelper.PIECETYPE_ID);
     //   assertThat(PieceTypeHelper.PIECETYPE_ID).isEqualTo(type2.getSerial());
    }
}
