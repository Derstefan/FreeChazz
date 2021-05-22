package com.freechess.game.actions.acts;

import com.freechess.game.board.Board;
import com.freechess.game.board.Position;

public abstract class Act {

    //TODO: sollte wieder raus und besser für rest api seriealisieren?
/*    protected String type;*/

    public abstract void perform(Board board, Position pos1, Position pos2);

  /*  public String getType() {
        return type;
    }*/
}
