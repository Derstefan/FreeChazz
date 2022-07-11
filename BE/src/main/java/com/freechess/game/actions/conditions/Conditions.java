package com.freechess.game.actions.conditions;

import com.freechess.game.actions.conditions.binary.ClearPathCondition;
import com.freechess.game.actions.conditions.binary.EnemyAtPostionCondition;
import com.freechess.game.actions.conditions.binary.FriendAtPostionCondition;
import com.freechess.game.actions.conditions.operations.TrivCondition;
import com.freechess.game.actions.conditions.unitary.FreePostionCondition;

public class Conditions {

    public final static FreePostionCondition FREE_POSITION = new FreePostionCondition();

    public final static EnemyAtPostionCondition ENEMY_AT_POSITION = new EnemyAtPostionCondition();

    public final static FriendAtPostionCondition FRIEND_AT_POSITION = new FriendAtPostionCondition();

    public final static ClearPathCondition CLEAR_PATH = new ClearPathCondition();

    public final static TrivCondition ALWAYS = new TrivCondition();

}
