package com.freechess.game.board;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PositionTest {

    public static Set<Position> positions = new HashSet<Position>(Arrays.asList(new Position(0,1),new Position(-2,0),new Position(0,-2),new Position(1,2)));
    public static Position posIn = new Position(0,1);
    public static Position posNotIn = new Position(0,6);
    public static Position pos = new Position(-1,5);
    public static Position posEquals = new Position(-1,5);

    @Test
    public void testPositionFunctionalities(){
        assertThat(pos.equals(posEquals)).isTrue();
        assertThat(pos.equals(posIn)).isFalse();
        assertThat(posIn.isIn(positions)).isTrue();
        assertThat(posNotIn.isIn(positions)).isFalse();

    }
}
