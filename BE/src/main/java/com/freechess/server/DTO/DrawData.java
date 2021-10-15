package com.freechess.server.DTO;

import com.freechess.game.board.Position;

public class DrawData {
    Position fromPos;
    Position toPos;

    public DrawData(Position fromPos, Position toPos) {
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
