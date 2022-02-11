package com.freechess.game.pieces.impl;

import com.freechess.game.actions.Action;
import com.freechess.game.board.Position;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PieceTypeBuilder {

    private PieceType pieceType;

    public PieceTypeBuilder(){
        this.pieceType = PieceType.getInstance();
    }

    public PieceTypeBuilder symbol(final String symbol){
        pieceType.setSymbol(symbol);
        return this;
    }

    public PieceTypeBuilder action(final Position pos,final Action action){
        pieceType.getActionMap().put(pos,action);
        return this;
    }

    public PieceTypeBuilder action(final int x,final int y,final Action action){
        action(new Position(x,y),action);
        return this;
    }

    public PieceTypeBuilder actions(final ActionMap actionMap){
        pieceType.setActionMap(actionMap);
        return this;
    }

    public PieceType build(){
        validate();
        return pieceType;
    }

    private void validate(){
        //blabla
        //exception?
    }

}
