package com.freechess.game.board;

import com.freechess.game.pieces.DefaultPieceType;
import com.freechess.game.pieces.Piece;
import com.freechess.generators.boardgenerator.BoardGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {


    @Test
    public void whenBoardGenerated_areEnemysMethodWorks() {
        BoardGenerator gen = new BoardGenerator(1);
        Board board = gen.generate(12,12);
        Piece piece1 = board.pieceAt(new Position(0, 1));
        Piece piece2 = board.pieceAt(new Position(15, 14));
        System.out.println(board.toString());
        System.out.println(piece1.getPossibleMoves().size());
        System.out.println(((DefaultPieceType)piece1.getPieceType()).getMoves().size());
        assertEquals(false, board.areEnemys(piece1, piece1));
        assertEquals(true, board.areEnemys(piece1, piece2));
    }
}
