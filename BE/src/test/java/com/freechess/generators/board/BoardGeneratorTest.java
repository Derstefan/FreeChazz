package com.freechess.generators.board;

import com.freechess.game.board.Board;
import com.freechess.generators.board.impl.SymmetricBoardGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BoardGeneratorTest {


    @Test
    public void whenGenerateWithSymmetricBoardGenerator_aCorrectBoardWillBeGenenerated(){
        SymmetricBoardGenerator gen = new SymmetricBoardGenerator();
        Board board = gen.generate();

        assertThat(board.countPieces()).isGreaterThan(2);
        assertThat(board.getKing1()).isNotNull();
        assertThat(board.getKing2()).isNotNull();
        // ...
    }
}
