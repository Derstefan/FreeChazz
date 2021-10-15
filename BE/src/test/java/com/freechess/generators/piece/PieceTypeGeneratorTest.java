package com.freechess.generators.piece;

import com.freechess.game.pieces.PieceType;
import com.freechess.generators.piece.impl.PieceTypeGeneratorPool;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PieceTypeGeneratorTest {

    @Test
    public void shouldGeneratePieceType(){
        PieceTypeGeneratorPool gen = new PieceTypeGeneratorPool();
        Random rand = new Random();
        PieceType p1 = gen.generate(1,rand.nextLong()/10);
        PieceType p2 = gen.generate(rand.nextLong());

        assertThat(p1).isNotNull();
        assertThat(p2).isNotNull();

    }
}
