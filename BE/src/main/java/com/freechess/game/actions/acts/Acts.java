package com.freechess.game.actions.acts;

import com.freechess.game.actions.acts.binary.MoveOrAttackAct;
import com.freechess.game.actions.acts.binary.RushAct;
import com.freechess.game.actions.acts.binary.SwapPositionsAct;

public class Acts {

    public static final MoveOrAttackAct MOVE_OR_ATTACK = new MoveOrAttackAct();

    public static final SwapPositionsAct SWAP_POSITIONS = new SwapPositionsAct();

    public static final RushAct RUSH_ACT = new RushAct();

}
