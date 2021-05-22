package com.freechess.generators.piecegenerator;

import com.freechess.game.pieces.DefaultPieceType;
import com.freechess.game.pieces.PieceType;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class PieceTypeGenerator {

    private Random rand;
    private long pieceId;

    public PieceType generate(long pieceId){
        pieceId = Math.abs(pieceId); // fixes the minus

        this.pieceId = pieceId;
        this.rand=new Random(pieceId);

        DefaultPieceTypeGenerator gen = new DefaultPieceTypeGenerator();
        DefaultPieceType pieceType = gen.generate(pieceId);

        //or other generators

        //String symbol = generateRandomString(10);
        String symbol = "" + pieceId;
        pieceType.setSymbol(symbol);
        return pieceType;
    }

    public PieceType generate(int lvl,long seed){
        long pieceId = Long.valueOf("" + lvl + Math.abs(seed));
        return generate(pieceId);
    }





}
