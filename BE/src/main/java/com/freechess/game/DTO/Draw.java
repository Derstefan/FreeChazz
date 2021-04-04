package com.freechess.game.DTO;

import com.freechess.game.core.Position;

public class Draw {
    Position fromPos;
    Position toPos;

    public Draw(Position fromPos, Position toPos) {
        this.fromPos = fromPos;
        this.toPos = toPos;
    }

    public Position getFromPos() {
        return fromPos;
    }

    public void setFromPos(Position fromPos) {
        this.fromPos = fromPos;
    }

    public Position getToPos() {
        return toPos;
    }

    public void setToPos(Position toPos) {
        this.toPos = toPos;
    }
}
