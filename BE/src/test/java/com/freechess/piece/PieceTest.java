package com.freechess.piece;

import com.freechess.game.actions.Action;
import com.freechess.game.actions.Actions;
import com.freechess.game.board.Position;
import com.freechess.game.pieces.ActionMap;
import com.freechess.game.pieces.DefaultPieceType;
import com.freechess.game.pieces.Piece;
import com.freechess.game.player.EPlayer;
import org.junit.jupiter.api.Test;

public class PieceTest {



    @Test
    void test1(){
        DefaultPieceType type = new DefaultPieceType();
        Action action = Actions.MOVE_OR_ATTACK_ACTION;
        ActionMap map = new ActionMap();
        map.put(new Position(2,0),action);
        map.put(new Position(0,2),action);
        map.put(new Position(-2,0),action);
        map.put(new Position(0,-2),action);
        type.setActions(map);
        Piece p = new Piece(EPlayer.P1,type);

        // schöner -> Builder DefaultPieceType... .giveAction().giveAction().giveAction()...



    }
}
