import React, { Component } from 'react'
import Canvas from '../../canvas.component';
import ChessPieceGenerator from './chess-piece-generator';


class ChessPiecesComponent extends Component {

    constructor(props) {

        super(props);
        this.state = {
            width: 50,
            height: 60,
            pieces: []
        }

        for (var i = 0; i < 110; i++) {
            var pg = new ChessPieceGenerator(this.state.width, this.state.height, "" + Math.random());
            this.state.pieces.push(pg.drawPieceCanvas("P1"));
        }

    }

    drawMethod(piece) {
        const { width, height } = this.state;

        const draw = (ctx, frameCount) => {
            ctx.canvas.width = width
            ctx.canvas.height = height
            ctx.drawImage(piece, 0, 0);
        }
        return draw;
    }

    drawPieces() {
        const { pieces } = this.state;
        return pieces.map((p) => <Canvas draw={this.drawMethod(p)} />)
    }

    render() {
        return this.drawPieces();
    }
}

export default ChessPiecesComponent