package com.freechess.generators.board.impl;


import com.freechess.game.board.Board;
import com.freechess.game.player.EPlayer;
import com.freechess.game.pieces.Piece;
import com.freechess.game.board.Position;
import com.freechess.game.pieces.PieceType;
import com.freechess.generators.piece.PieceTypeGenerator;
import com.freechess.generators.piece.impl.PieceTypeGeneratorPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SymmetricBoardGenerator extends AbtractBoardGenerator {

    private static final int MAX_LVL = 5;
    private static final int POOL_SIZE = 5;


    private PieceTypeGeneratorPool generator;

    public SymmetricBoardGenerator() {
        super();
        generator = new PieceTypeGeneratorPool();
    }

    public SymmetricBoardGenerator(long seed) {
        super(seed);
        generator = new PieceTypeGeneratorPool();
    }

    @Override
    public void generatePiecePool() {
        for(int j =1;j<=MAX_LVL;j++){
            piecePool.put(j, new HashSet<>());
            for (int i = 0; i < POOL_SIZE; i++) {
                piecePool.get(j).add(generateRandomPieceType(j));
            }
        }
    }

    @Override
    public void putKing(Board board, EPlayer player) {
        PieceType kingType = generateRandomPieceType(2);
        Piece king = new Piece(player, kingType);
        if(player.equals(EPlayer.P1)){
            board.setKing1(king);
            board.addPiece(king,new Position(board.getWidth() / 2, 0));
        } else {
            board.setKing2(king);
            board.addPiece(king,new Position(board.getWidth() / 2, board.getHeight() - 1));
        }
    }

    //TODO: better implementation - more dynamic for different board sizes ?
    @Override
    public void putPieces(Board board) {
        addPiecesToBoard(piecePool.get(1),randomPositions(board,6,1),true,board);
        addPiecesToBoard(piecePool.get(2),randomPositions(board,6,2),true,board);
        addPiecesToBoard(piecePool.get(3),randomPositions(board,2,3),true,board);
    }


    private ArrayList<Position> randomPositions(Board board, int number, int line) {
        if ((line != 1 && line != 2 && line != 3) || number <= 0) {
            return new ArrayList<Position>();
        }
        return GenUtil.getRandomPosOfArea(0, board.getWidth() - 1,  1/2*board.getHeight() + line, 1/2*board.getHeight() + 1 + line, number,this.rand);


    }

    private void addPiecesToBoard(Set<PieceType> pieces, ArrayList<Position> positions, boolean mirrored, Board board) {

        for (int i = 0; i < positions.size(); i++) {
            PieceType eType = GenUtil.getRandomEntryOf(pieces,this.rand);

            Piece p = new Piece(EPlayer.P1, eType);
            board.addPiece(p, positions.get(i));
            if (mirrored) {
                Piece p2 = new Piece(EPlayer.P2, eType);
                Position inverse = new Position(board.getWidth() - 1 - positions.get(i).getX(), board.getHeight() - 1 - positions.get(i).getY());
                board.addPiece(p2, inverse);
            }
        }
    }

    private PieceType generateRandomPieceType(int lvl){
        return generator.generate(lvl,rand.nextLong()/10);
    }

}
