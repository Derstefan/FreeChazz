package com.freechess.generators.board.impl;


import com.freechess.game.board.Board;
import com.freechess.game.board.BoardBuilder;
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

    private static final int DEFAULT_WIDTH = 15;
    private static final int DEFAULT_HEIGHT = 15;

    private Random rand;
    private long seed;
    private HashMap<Integer, Set<IPieceType>> piecePool = new HashMap<>();
    private BoardBuilder builder;


    public SymmetricBoardGenerator() {
        this.seed = (long) (Math.random() * Long.MAX_VALUE);
        rand = new Random(seed);
        generator = new PieceTypeGeneratorPool();
    }

    public SymmetricBoardGenerator(long seed) {
        this.seed = seed;
        rand = new Random(seed);
        generator = new PieceTypeGeneratorPool();
    }

    public Board generate(){
        return generate(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }

    public Board generate(int width,int height)
    {
        generatePiecePool();
        builder = new BoardBuilder(width,height);

        putKing(EPlayer.P1);
        putKing(EPlayer.P2);
        putPieces();

        Board board = builder.build();
        board.computePossibleMoves();
        return board;

    }

    private PieceTypeGeneratorPool generator;


    private void generatePiecePool() {
        for(int j =1;j<=MAX_LVL;j++){
            piecePool.put(j, new HashSet<>());
            for (int i = 0; i < POOL_SIZE; i++) {
                piecePool.get(j).add(generateRandomPieceType(j));
            }
        }
    }

    private void putKing(EPlayer player) {
        IPieceType kingType = generateRandomPieceType(2);
        Piece king = new Piece(player, kingType);
        Position pos;
        if(player.equals(EPlayer.P1)){
            pos = new Position(builder.getWidth() / 2, 0);
        } else {
            pos =new Position(builder.getWidth() / 2, builder.getHeight() - 1);
        }
            builder.putKing(player,king,pos);
    }

    //TODO: better implementation - more dynamic for different board sizes ?
    public void putPieces() {
        addPiecesToBoard(piecePool.get(1),randomPositions(8,1),true);
        addPiecesToBoard(piecePool.get(2),randomPositions(10,2),true);
        addPiecesToBoard(piecePool.get(3),randomPositions(4,3),true);
    }


    private ArrayList<Position> randomPositions(int number, int line) {
        if ((line != 1 && line != 2 && line != 3) || number <= 0) {
            return new ArrayList<Position>();
        }
        return GenUtil.getRandomPosOfArea(0, builder.getWidth() - 1,  1/2*builder.getHeight() + line, 1/2*builder.getHeight() + 1 + line, number,this.rand);
    }

    private void addPiecesToBoard(Set<IPieceType> pieces, ArrayList<Position> positions, boolean mirrored) {

        for (int i = 0; i < positions.size(); i++) {
            IPieceType eType = GenUtil.getRandomEntryOf(pieces,this.rand);

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
