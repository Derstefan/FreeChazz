package com.freechess.generators.piece;

import com.freechess.game.pieces.IPieceType;

public interface IPieceTypeGenerator {



    public IPieceType generate(PieceTypeGeneratorParam param);

/*
    public IPieceType generate(long pieceId);

    public IPieceType generate(int lvl, long pieceId);
*/

}
