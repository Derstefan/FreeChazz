package com.freechess.game.pieces.generators;

import com.freechess.game.core.Piece;
import com.freechess.game.pieces.PieceType;
import com.freechess.game.pieces.classic.JumpingPieceType;

import java.util.ArrayList;

public class PieceTypeGenerator {


    ArrayList<String> symbols;


    public PieceType generate(int lvl){
        long seed = (long)(Math.random()*12378324);
        JumpingPieceGenerator jumpGen = new JumpingPieceGenerator(seed);
        JumpingPieceType pieceType = jumpGen.generate(lvl);

        //or other generators
        pieceType.setSymbol(String.valueOf((char)(Math.random()*50+50)));
        return pieceType;
    }

}
