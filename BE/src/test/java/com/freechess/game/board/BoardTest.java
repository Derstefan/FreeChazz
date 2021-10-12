package com.freechess.game.board;

import com.freechess.game.pieces.DefaultPieceType;
import com.freechess.game.pieces.Piece;
import com.freechess.game.player.EPlayer;
import com.freechess.generators.board.impl.SymmetricBoardGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {


    @Test
    public void whenBoardGenerated_areEnemysMethodWorks() {
        SymmetricBoardGenerator gen = new SymmetricBoardGenerator(1);
        Board board = gen.generate(12,12);
        Piece piece1 = getFirstPieceOf(EPlayer.P1,board);
        Piece piece2 = getFirstPieceOf(EPlayer.P2,board);
        System.out.println(board.toString());
        System.out.println(piece1.getPossibleMoves().size());
        System.out.println(((DefaultPieceType)piece1.getPieceType()).getMoves().size());
        assertEquals(false, board.areEnemys(piece1, piece1));
        assertEquals(true, board.areEnemys(piece1, piece2));
    }
    
    
    
    private Piece getFirstPieceOf(EPlayer player, Board board){
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                Position pos = new Position(i,j);
                if(board.pieceAt(pos)!=null){
                    if(board.pieceAt(pos).getOwner()==player){
                        return board.pieceAt(pos);

                    }
                 }
            }
        }
        return null;
    }
}
