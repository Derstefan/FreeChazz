package com.freechess.generators.piece;

import com.freechess.game.pieces.PieceType;

public interface PieceTypeGenerator {


    public PieceType generate(long pieceId);

    public PieceType generate(int lvl,long pieceId);

}
