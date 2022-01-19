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
}
