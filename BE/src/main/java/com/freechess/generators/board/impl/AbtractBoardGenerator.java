package com.freechess.generators.board.impl;

import com.freechess.game.board.Board;
import com.freechess.game.board.Position;
import com.freechess.game.pieces.Piece;
import com.freechess.game.pieces.PieceType;
import com.freechess.game.player.EPlayer;
import com.freechess.generators.board.BoardGenerator;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public abstract class AbtractBoardGenerator implements BoardGenerator {

    protected static final int DEFAULT_WIDTH = 14;
    protected static final int DEFAULT_HEIGHT = 14;

    protected Random rand;
    protected long seed;

    protected HashMap<Integer, Set<PieceType>> piecePool = new HashMap<>();


    public AbtractBoardGenerator() {
        this.seed = (long) (Math.random() * Long.MAX_VALUE);
        rand = new Random(seed);
    }

    public AbtractBoardGenerator(long seed) {
        this.seed = seed;
        rand = new Random(seed);
    }

    public Board generate(){
       return generate(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }

    public Board generate(int width,int height)
    {
        Board board = new Board(width,height);
        generatePiecePool();


        putKing(board, EPlayer.P1);
        putKing(board, EPlayer.P2);
        putPieces(board);

        board.computePossibleMoves();
        return board;

    }

    public abstract void generatePiecePool();

    public abstract void putKing(Board board,EPlayer player);

    public abstract void putPieces(Board board);


}
