package com.freechess.generators.board.impl;


import com.freechess.game.board.Board;
import com.freechess.game.board.BoardBuilder;
import com.freechess.game.board.ESize;
import com.freechess.game.board.Position;
import com.freechess.game.pieces.IPieceType;
import com.freechess.game.pieces.Piece;
import com.freechess.game.player.EPlayer;
import com.freechess.generators.board.BoardGenerator;
import com.freechess.generators.piece.PieceTypeGeneratorParam;
import com.freechess.generators.piece.impl.PieceTypeGeneratorPool;

import java.util.*;

public class SymmetricBoardGenerator implements BoardGenerator {

    private static final int MAX_LVL = 5;
    private static final int POOL_SIZE = 5;

    private Random rand;
    private long seed;

    private ESize eSize;
    private HashMap<Integer, ArrayList<IPieceType>> piecePool = new HashMap<>();
    private BoardBuilder builder;
    private PieceTypeGeneratorPool generator;


    public SymmetricBoardGenerator() {
        this.seed = (long) (Math.random() * Long.MAX_VALUE);
        rand = new Random(seed);
        generator = new PieceTypeGeneratorPool();
    }

    public SymmetricBoardGenerator(long seed) {
        this.seed = seed;
        rand = new Random(seed);
        generator = new PieceTypeGeneratorPool();
        System.out.println("generate: " +seed);
    }

    public Board generate(){
        return generate(ESize.small);
    }

    public Board generate(ESize eSize)
    {
        this.eSize = eSize;
        generatePiecePool();
        builder = new BoardBuilder(eSize.getWidth(),eSize.getHeight());

        putKing(EPlayer.P1);
        putKing(EPlayer.P2);
        putPieces();

        Board board = builder.build();
        board.computePossibleMoves();
        return board;

    }



    private void generatePiecePool() {
        for(int j =1;j<=MAX_LVL;j++){
            piecePool.put(j, new ArrayList<>());
            for (int i = 0; i < POOL_SIZE; i++) {
                piecePool.get(j).add(generateRandomPieceType(j));
            }
        }
    }

    private void putKing(EPlayer player) {
        IPieceType kingType;
        if(ESize.big.equals(eSize)){
            kingType = generateRandomPieceType(5);
        } else {
            kingType = generateRandomPieceType(4);
        }

        Piece king = new Piece(player, kingType);
        Position pos;
        if(player.equals(EPlayer.P1)){
            pos =new Position(builder.getWidth() / 2, builder.getHeight() - 1);
        } else {
            pos = new Position(builder.getWidth() / 2, 0);
        }
            builder.putKing(player,king,pos);
    }

    //TODO: better implementation - more dynamic for different board sizes ?
    public void putPieces() {
        if(ESize.big.equals(eSize)){
            addPiecesToBoard(piecePool.get(1),randomPositions(27,9),true);
            addPiecesToBoard(piecePool.get(2),randomPositions(22,8),true);
            addPiecesToBoard(piecePool.get(3),randomPositions(14,7),true);
            addPiecesToBoard(piecePool.get(4),randomPositions(8,5),true);
            addPiecesToBoard(piecePool.get(5),randomPositions(4,3),true);

        } else if(ESize.medium.equals(eSize)) {
            addPiecesToBoard(piecePool.get(1),randomPositions(14,6),true);
            addPiecesToBoard(piecePool.get(2),randomPositions(17,5),true);
            addPiecesToBoard(piecePool.get(3),randomPositions(8,4),true);
            addPiecesToBoard(piecePool.get(4),randomPositions(4,2),true);
        } else{
            addPiecesToBoard(piecePool.get(1),randomPositions(8,3),true);
            addPiecesToBoard(piecePool.get(2),randomPositions(10,2),true);
            addPiecesToBoard(piecePool.get(3),randomPositions(4,1),true);
        }
    }


    private ArrayList<Position> randomPositions(int number, int line) {
        if ((line>=eSize.getHeight()/2) || number <= 0) {
            return new ArrayList<Position>();
        }
        return GenUtil.getRandomPosOfArea(
                0,
                builder.getWidth() - 1,
                1/2*builder.getHeight() + line,
                1/2*builder.getHeight() + 1 + line, number,rand);
    }

    private void addPiecesToBoard(ArrayList<IPieceType> pieces, ArrayList<Position> positions, boolean mirrored) {

        for (int i = 0; i < positions.size(); i++) {
            IPieceType eType = GenUtil.getRandomEntryOf(pieces,rand);

            Piece p = new Piece(EPlayer.P2, eType);
            builder.putPiece(p, positions.get(i));
            if (mirrored) {
                Piece p2 = new Piece(EPlayer.P1, eType);
                Position inverse = new Position(builder.getWidth() - 1 - positions.get(i).getX(), builder.getHeight() - 1 - positions.get(i).getY());
                builder.putPiece(p2, inverse);
            }
        }
    }

    private IPieceType generateRandomPieceType(int lvl){
        IPieceType pieceType = generator.generate(new PieceTypeGeneratorParam(lvl,rand.nextLong()));
        return pieceType;
    }

}
