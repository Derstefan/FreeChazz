package com.freechess.game.pieces.generators;

import com.freechess.game.core.Piece;
import com.freechess.game.pieces.PieceType;
import com.freechess.game.pieces.classic.JumpingPieceType;

public class PieceGenerator {





    public static PieceType generate(){
        long seed = (long)(Math.random()*12378324);
        JumpingPieceType pieceType = (new JumpingPieceGenerator(seed)).generate();

        //or other generators

        return pieceType;
    }
}
