package com.freechess.generators.board;

import com.freechess.game.board.Board;
import com.freechess.game.board.ESize;

public interface BoardGenerator {

    public Board generate();

    public Board generate(ESize eSize);

}
