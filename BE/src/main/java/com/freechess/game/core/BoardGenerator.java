package com.freechess.game.core;


import com.freechess.game.pieces.PieceType;
import com.freechess.game.pieces.generators.PieceTypeGenerator;

import java.util.ArrayList;

public class BoardGenerator {

    private ArrayList<PieceType> lvl_1_pieces = new ArrayList<>();
    private ArrayList<PieceType> lvl_2_pieces = new ArrayList<>();
    private ArrayList<PieceType> lvl_3_pieces = new ArrayList<>();
    private ArrayList<PieceType> lvl_4_pieces = new ArrayList<>();
    private ArrayList<PieceType> lvl_5_pieces = new ArrayList<>();

    public Board generate(){
        Board board = new Board(15,15);
        init(board);
        board.computePossibleMoves();
        return board;
    }


    private void init(Board board){

        PieceTypeGenerator gen = new PieceTypeGenerator();
        //Generate Types
        PieceType king1Type = gen.generate(2);
        PieceType king2Type = gen.generate(2);



        for (int i = 0; i < 5; i++) {
            lvl_1_pieces.add(gen.generate(1));
            lvl_2_pieces.add(gen.generate(2));
            lvl_3_pieces.add(gen.generate(3));
            lvl_4_pieces.add(gen.generate(4));
            lvl_5_pieces.add(gen.generate(5));
        }


        // Generate Pieces
        Piece king1 = new Piece(EPlayer.P1, king1Type);
        Piece king2 = new Piece(EPlayer.P2, king2Type);
        board.setKing1(king1);
        board.setKing2(king2);
        board.addPiece(king2,new Position(board.getWidth()/2, 0));
        board.addPiece(king1,new Position(board.getWidth()/2, board.getHeight()-1));

        ArrayList<Position> firstLine = getRandomPosOfArea(0,board.getWidth()-1,board.getHeight()/2+1,3*board.getHeight()/4,20);
        for (int i = 0; i < 15; i++) {
            PieceType eType = getRandomEntryOf(lvl_1_pieces);

            Piece p = new Piece(EPlayer.P1,eType);
            board.addPiece(p, firstLine.get(i));

            Piece p2 = new Piece(EPlayer.P2,eType);
            Position inverse = new Position(board.getWidth()-1-firstLine.get(i).getX(),board.getHeight()-1-firstLine.get(i).getY());
            board.addPiece(p2,inverse);
        }

        ArrayList<Position> secondLine = getRandomPosOfArea(0, board.getWidth(), 3*board.getHeight()/4+1,5*board.getHeight()/6,10);
        for (int i = 0; i < 10; i++) {
            PieceType eType = getRandomEntryOf(lvl_2_pieces);

            Piece p = new Piece(EPlayer.P1,eType);
            board.addPiece(p, secondLine.get(i));

            Piece p2 = new Piece(EPlayer.P2,eType);
            Position inverse = new Position(board.getWidth()-1-secondLine.get(i).getX(),board.getHeight()-1-secondLine.get(i).getY());
            board.addPiece(p2,inverse);
        }

        ArrayList<Position> thirdLine = getRandomPosOfArea(0,board.getWidth()-1,5*board.getHeight()/6+1,board.getHeight()-1,6);
        for (int i = 0; i < 3; i++) {
            PieceType eType = getRandomEntryOf(lvl_3_pieces);

            Piece p = new Piece(EPlayer.P1,eType);
            board.addPiece(p, thirdLine.get(i));

            Piece p2 = new Piece(EPlayer.P2,eType);
            Position inverse = new Position(board.getWidth()-1-thirdLine.get(i).getX(), board.getHeight()-1-thirdLine.get(i).getY());
            board.addPiece(p2,inverse);
        }



/*        board.addPiece(new Piece(EPlayer.P2, PieceTypeGenerator.generate()),new Position(17,1));
        board.addPiece(new Piece(EPlayer.P1, PieceTypeGenerator.generate()),new Position(5,12));*/
    }

    /**
     * Without repeatation
     */
    private ArrayList<Position> getRandomPosOfArea(int minX, int maxX, int minY, int maxY, int number){
        ArrayList<Position> allPositions= new ArrayList<>();
        ArrayList<Position> positions= new ArrayList<>();

        for (int i=minX;i<maxX+1;i++) {
            for (int j = minY; j < maxY+1; j++) {
                allPositions.add(new Position(i,j));
            }
        }
        if(allPositions.size()<number){
            number= allPositions.size();
        }

        for (int i = 0; i < number; i++) {
            int k = (int) Math.round(Math.random()*(allPositions.size()-1)-0.5);
            positions.add(allPositions.get(k));
            allPositions.remove(k);
        }
        return positions;
    }

    private PieceType getRandomEntryOf(ArrayList<PieceType> pieceTypes){
        return pieceTypes.get((int)(Math.random()* pieceTypes.size()));
    }
}
