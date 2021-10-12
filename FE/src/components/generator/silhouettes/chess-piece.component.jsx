import React, { Component } from 'react'
import Canvas from '../../canvas.component';
import ChessPieceGenerator from './chess-piece-generator';


class ChessPieceComponent extends Component {

    constructor(props) {
        var pg = new ChessPieceGenerator(300, 400, "" + Math.random());
        super(props);
        this.state = {
            width: 300,
            height: 400,
            piece: pg.drawPieceCanvas("P1")
        }

    }

    drawMethod() {
        const { piece, width, height } = this.state;

        const draw = (ctx, frameCount) => {
            ctx.canvas.width = width
            ctx.canvas.height = height
            ctx.drawImage(piece, 0, 0);
        }
        return draw;
    }



    render() {
        return (<Canvas draw={this.drawMethod()} />);
    }
}

export default ChessPieceComponent