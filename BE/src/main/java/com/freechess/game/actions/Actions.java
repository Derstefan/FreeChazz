package com.freechess.game.actions;






import static com.freechess.game.actions.acts.Acts.*;
import static com.freechess.game.actions.conditions.Conditions.*;

public class Actions {

    public static Action MOVE_TO_FREE_POSITION = new Action(FREE_POSITION, MOVE_OR_ATTACK,'F');

    public static Action MOVE_TO_ENEMY_POSITION = new Action(ENEMY_AT_POSITION, MOVE_OR_ATTACK,'E');

    public static Action MOVE_OR_ATTACK_ACTION = new Action(ENEMY_AT_POSITION.OR(FREE_POSITION), MOVE_OR_ATTACK,'X');

    public static Action WALK_AND_MOVE_OR_ATTACK = new Action(CLEAR_PATH.AND(ENEMY_AT_POSITION.OR(FREE_POSITION)), MOVE_OR_ATTACK,'M');

    public static Action SWAP_POSITIONS_ACTION = new Action(FRIEND_AT_POSITION.AND(FREE_POSITION.NOT()), SWAP_POSITIONS,'S');

    public static Action RUSH_ACTION = new Action(ALWAYS, RUSH_ACT,'R');


    //TODO: automatic mapping from symbol to Action
    public static Action getActionBySymbol(char symbol){
        switch (symbol){
            case 'F':
                return MOVE_TO_FREE_POSITION;
            case 'E':
                return MOVE_TO_ENEMY_POSITION;
            case 'M':
                return WALK_AND_MOVE_OR_ATTACK;
            case 'S':
                return SWAP_POSITIONS_ACTION;
            case 'R':
                return RUSH_ACTION;
            default:
                return MOVE_OR_ATTACK_ACTION;
        }
    }
}
