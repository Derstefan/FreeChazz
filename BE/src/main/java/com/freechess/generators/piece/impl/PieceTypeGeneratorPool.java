package com.freechess.generators.piece.impl;

import com.freechess.game.pieces.IPieceType;
import com.freechess.game.pieces.impl.PieceType;
import com.freechess.generators.piece.IPieceTypeGenerator;
import com.freechess.generators.piece.PieceTypeGeneratorParam;

import java.util.Random;


/**
 * This generator coonsist of many generators for pieceTypes. "Ochestrierer"
 */
public class PieceTypeGeneratorPool implements IPieceTypeGenerator {

    //private Random rand;
    //private long seed;
    private int symbolCounter = 100; //ascii start

/*    public IPieceType generate(long seed){

        this.seed = Math.abs(seed);
        this.rand=new Random(seed);

        PieceTypeGenerator gen = new PieceTypeGenerator();
        PieceType pieceType = gen.generate(seed);

        //or other generators

        //String symbol = generateRandomString(10);
//        String symbol = "" + seed;
//        pieceType.setSymbol(symbol);
        pieceType.setSeed(seed);
        pieceType.setSymbol(""+(char) symbolCounter);
        symbolCounter++; //TODO: waht is over 255?

        return pieceType;
    }

    public IPieceType generate(int lvl, long seed){
        long effectiveSeed = Long.valueOf("" + lvl + Math.abs(seed));

        return generate(effectiveSeed);
    }*/

    public IPieceType generate(PieceTypeGeneratorParam param){

        PieceTypeGenerator gen = new PieceTypeGenerator();
        PieceType pieceType = gen.generate(param);

        //or other generators

        pieceType.setSeed(param.toString());

        //TODO: Georg/Björn impl
        pieceType.setSymbol(""+(char) symbolCounter);
        symbolCounter++;
        //TODO:to here

        return pieceType;
    }
}
