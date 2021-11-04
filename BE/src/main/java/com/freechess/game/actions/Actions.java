package com.freechess.game.actions;






import com.freechess.game.actions.acts.Acts;

import static com.freechess.game.actions.acts.Acts.MOVE_OR_ATTACK;
import static com.freechess.game.actions.conditions.Conditions.*;

public class Actions {
    // Mapping zu Json? vll enum mit konstruktoren verbunden?

    public static Action MOVE_TO_FREE_POSITION = new Action(FREE_POSITION, MOVE_OR_ATTACK,'F');

    public static Action MOVE_TO_ENEMY_POSITION = new Action(ENEMY_AT_POSITION, MOVE_OR_ATTACK,'E');

    public static Action MOVE_OR_ATTACK_ACTION = new Action(ENEMY_AT_POSITION.OR(FREE_POSITION), MOVE_OR_ATTACK,'X');

    public static Action WALK_AND_MOVE_OR_ATTACK = new Action(CLEAR_PATH, MOVE_OR_ATTACK,'M');


}
