package com.freechess.generators.piece.impl;

import com.freechess.game.pieces.IPieceType;
import com.freechess.game.pieces.impl.PieceType;
import com.freechess.generators.piece.IPieceTypeGenerator;

import java.util.Random;


/**
 * This generator coonsist of many generators for pieceTypes. "Ochestrierer"
 */
public class PieceTypeGeneratorPool implements IPieceTypeGenerator {

    private Random rand;
    private long pieceId;

    public IPieceType generate(long pieceId){
        pieceId = Math.abs(pieceId); // fixes the minus

        this.pieceId = pieceId;
        this.rand=new Random(pieceId);

        PieceTypeGenerator gen = new PieceTypeGenerator();
        PieceType pieceType = gen.generate(pieceId);

        //or other generators

        //String symbol = generateRandomString(10);
        String symbol = "" + pieceId;
        pieceType.setSymbol(symbol);
        return pieceType;
    }

    public IPieceType generate(int lvl, long seed){
        long pieceId = Long.valueOf("" + lvl + Math.abs(seed));
        return generate(pieceId);
    }

}
