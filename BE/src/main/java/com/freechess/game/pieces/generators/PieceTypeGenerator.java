package com.freechess.game.pieces.generators;

import com.freechess.game.core.Piece;
import com.freechess.game.pieces.PieceType;
import com.freechess.game.pieces.classic.JumpingPieceType;

public class PieceTypeGenerator {





    public static PieceType generate(){
        long seed = (long)(Math.random()*12378324);
        JumpingPieceGenerator jumpGen = new JumpingPieceGenerator(seed);
        JumpingPieceType pieceType = jumpGen.generate();

        //or other generators

        return pieceType;
    }


    public static PieceType generate(int lvl){
        long seed = (long)(Math.random()*12378324);
        JumpingPieceGenerator jumpGen = new JumpingPieceGenerator(seed);
        JumpingPieceType pieceType = jumpGen.generate();

        //or other generators

        return pieceType;
    }

}
