package com.freechess.generators.boardgenerator;


import com.freechess.game.board.Board;
import com.freechess.game.player.EPlayer;
import com.freechess.game.pieces.Piece;
import com.freechess.game.board.Position;
import com.freechess.game.pieces.PieceType;
import com.freechess.generators.piecegenerator.PieceTypeGenerator;

import java.util.ArrayList;
import java.util.Random;

public class BoardGenerator {

    private ArrayList<PieceType> lvl_1_pieces = new ArrayList<>();
    private ArrayList<PieceType> lvl_2_pieces = new ArrayList<>();
    private ArrayList<PieceType> lvl_3_pieces = new ArrayList<>();
    private ArrayList<PieceType> lvl_4_pieces = new ArrayList<>();
    private ArrayList<PieceType> lvl_5_pieces = new ArrayList<>();

    private Random rand;
    private long seed;

    public BoardGenerator() {
        this.seed = (long) (Math.random() * Long.MAX_VALUE);
        rand = new Random(seed);
    }

    public BoardGenerator(long seed) {
        this.seed = seed;
        rand = new Random(seed);
    }


    public Board generate(int width,int height) {
        Board board = new Board(width, height);
        init(board);
        board.computePossibleMoves();
        return board;
    }


    private void init(Board board) {

        PieceTypeGenerator gen = new PieceTypeGenerator();
        //Generate Types
        PieceType king1Type = gen.generate(2,rand.nextLong()/10);
        PieceType king2Type = gen.generate(2,rand.nextLong()/10);

        for (int i = 0; i < 10; i++) {
            lvl_1_pieces.add(gen.generate(1,rand.nextLong()/10));
            lvl_2_pieces.add(gen.generate(2,rand.nextLong()/10));
            lvl_3_pieces.add(gen.generate(3,rand.nextLong()/10));
            lvl_4_pieces.add(gen.generate(4,rand.nextLong()/10));
            lvl_5_pieces.add(gen.generate(5,rand.nextLong()/10));
        }


        // Generate Pieces
        Piece king1 = new Piece(EPlayer.P1, king1Type);
        Piece king2 = new Piece(EPlayer.P2, king2Type);
        board.setKing1(king1);
        board.setKing2(king2);
        board.addPiece(king1, new Position(board.getWidth() / 2, 0));
        board.addPiece(king2, new Position(board.getWidth() / 2, board.getHeight() - 1));



        addPiecesToBoard(lvl_1_pieces,randomPositions(board,2,1),true,board);
        addPiecesToBoard(lvl_2_pieces,randomPositions(board,2,2),true,board);
        addPiecesToBoard(lvl_3_pieces,randomPositions(board,2,3),true,board);


    }


    // TODO: addPiecesToBoard(pieces,num)

    private ArrayList<Position> randomPositions(Board board, int number, int line) {
        if ((line != 1 && line != 2 && line != 3) || number <= 0) {
            return new ArrayList<Position>();
        }
        return getRandomPosOfArea(0, board.getWidth() - 1,  1/2*board.getHeight() + line, 1/2*board.getHeight() + 1 + line, number);


    }

    private void addPiecesToBoard(ArrayList<PieceType> pieces, ArrayList<Position> positions, boolean mirrored, Board board) {

        for (int i = 0; i < positions.size(); i++) {
            PieceType eType = getRandomEntryOf(pieces);

            Piece p = new Piece(EPlayer.P1, eType);
            board.addPiece(p, positions.get(i));
            if (mirrored) {
                Piece p2 = new Piece(EPlayer.P2, eType);
                Position inverse = new Position(board.getWidth() - 1 - positions.get(i).getX(), board.getHeight() - 1 - positions.get(i).getY());
                board.addPiece(p2, inverse);
            }
        }
    }


    /**
     * Without repeatation
     */
    private ArrayList<Position> getRandomPosOfArea(int minX, int maxX, int minY, int maxY, int number) {
        ArrayList<Position> allPositions = new ArrayList<>();
        ArrayList<Position> positions = new ArrayList<>();

        for (int i = minX; i < maxX + 1; i++) {
            for (int j = minY; j < maxY + 1; j++) {
                allPositions.add(new Position(i, j));
            }
        }
        if (allPositions.size() < number) {
            number = allPositions.size();
        }

        for (int i = 0; i < number; i++) {
            int k = (int) Math.round(rand.nextDouble() * (allPositions.size() - 1) - 0.5);
            positions.add(allPositions.get(k));
            allPositions.remove(k);
        }
        return positions;
    }

    private PieceType getRandomEntryOf(ArrayList<PieceType> pieceTypes) {
        return pieceTypes.get((int) (rand.nextDouble() * pieceTypes.size()));
    }
}
