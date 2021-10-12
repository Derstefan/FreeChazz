package com.freechess.generators.board;

import com.freechess.game.board.Board;

public interface BoardGenerator {

    public Board generate();

    public Board generate(int width, int height);

}
