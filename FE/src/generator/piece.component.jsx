import React, { Component } from 'react'
import Canvas from '../game/canvas.component';
import PieceGenerator from './piece-generator';
import RandomGenerator from './random-generator';
import UtilFunctions from './random-generator';

class PieceComponent extends Component {

    constructor(props) {
        var pg = new PieceGenerator(300, 400, "" + Math.random());
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
        const { symbol, selected, canMove } = this.props;
        let style = {};
        let str = symbol
        if ((selected || canMove)) {
            style = { color: 'green' };
            if (str === "") {
                //   str='X';
            }
        }
        return (<Canvas draw={this.drawMethod()} />);
    }
}

export default PieceComponent