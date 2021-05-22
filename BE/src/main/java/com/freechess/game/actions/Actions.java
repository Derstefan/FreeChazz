package com.freechess.game.actions;

import com.freechess.game.actions.acts.binary.MoveOrAttackAct;
import com.freechess.game.actions.conditions.binary.EnemyAtPostionCondition;
import com.freechess.game.actions.conditions.binary.FriendAtPostionCondition;
import com.freechess.game.actions.conditions.unitary.FreePostionCondition;

public class Actions {

    public static Action MOVE_TO_FREE_POSITION(){
        FreePostionCondition cond = new FreePostionCondition();
        MoveOrAttackAct act = new MoveOrAttackAct();
        Action moveToFreePosition  = new Action(cond,act);
        moveToFreePosition.setSymbol('F');
        return moveToFreePosition;
    }

    public static Action MOVE_TO_ENEMY_POSITION(){
        EnemyAtPostionCondition cond = new EnemyAtPostionCondition();
        MoveOrAttackAct act = new MoveOrAttackAct();
        Action moveToEnemyPosition  = new Action(cond,act);
        moveToEnemyPosition.setSymbol('E');
        return moveToEnemyPosition;
    }

    public static Action MOVE_OR_ATTACK(){
        EnemyAtPostionCondition cond1 = new EnemyAtPostionCondition();
        FreePostionCondition cond2 = new FreePostionCondition();
        MoveOrAttackAct act = new MoveOrAttackAct();

        Action moveOrAttack  = new Action(cond1.OR(cond2),act);
        moveOrAttack.setSymbol('X');
        return moveOrAttack;
    }
}
