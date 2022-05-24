package com.freechess.game.pieces;

import com.freechess.game.actions.Actions;
import com.freechess.game.pieces.impl.PieceType;
import com.freechess.game.pieces.impl.PieceTypeBuilder;

public class PieceTypeHelper {



    public static PieceType CROSS1 = new PieceTypeBuilder().symbol("M")
            .action(2,0, Actions.MOVE_OR_ATTACK_ACTION)
            .action(0,2,Actions.MOVE_OR_ATTACK_ACTION)
            .action(-2,0,Actions.MOVE_OR_ATTACK_ACTION)
            .action(0,-2,Actions.MOVE_OR_ATTACK_ACTION)
            .build();

    public static PieceType WALK1 = new PieceTypeBuilder().symbol("M")
            .action(0,1,Actions.WALK_AND_MOVE_OR_ATTACK)
            .action(0,2,Actions.WALK_AND_MOVE_OR_ATTACK)
            .action(0,3,Actions.WALK_AND_MOVE_OR_ATTACK)
            .build();

    public static String PIECETYPE_ID = "P;X,2,0;X,-2,0;X,2,2;X,-2,2;";
}
