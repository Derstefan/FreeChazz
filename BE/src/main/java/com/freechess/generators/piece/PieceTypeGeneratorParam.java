package com.freechess.generators.piece;

import com.freechess.generators.piece.impl.PieceTypeGeneratorPool;

import java.util.Scanner;

public class PieceTypeGeneratorParam {

    private static final String DELIMITER = ",";

    private Integer lvl;
    private Long seed;

    public PieceTypeGeneratorParam(String params){
        Scanner s = new Scanner(params);
        s.useDelimiter(DELIMITER);

        if(s.hasNext()){
            lvl= s.nextInt();
        }
        if(s.hasNext()){
            seed= s.nextLong();
        }
    }

    public PieceTypeGeneratorParam(int lvl, long seed){
        this.lvl = Integer.valueOf(lvl);
        this.seed = Long.valueOf(seed);
    }

    public PieceTypeGeneratorParam(long seed){
        this.seed = seed;
    }

    public PieceTypeGeneratorParam(int lvl){
        this.lvl = lvl;
    }

    public PieceTypeGeneratorParam(){

    }

    public String toString(){
        return  lvl + DELIMITER + seed;
    }

    public Integer getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public Long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }
}
