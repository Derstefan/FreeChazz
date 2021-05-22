import React, { Component } from 'react'
import Canvas from '../game/canvas.component';
import PieceGenerator from '../generator/piece-generator';
import mainService from '../services/main.service';

class PieceDataComponent extends Component {

    constructor(props) {

        super(props);
        var pg = new PieceGenerator(300, 400, "" + this.props.match.params.id);
        //console.log("id: ", this.props.match.params.id);
        this.state = {
            pieceId: this.props.match.params.id,
            width: 300,
            height: 400,
            pieceImage: pg.drawPieceCanvas("P1"),
            piece: {}
        }

        this.drawMoves = this.drawMoves.bind(this);

    }

    componentDidMount() {
        mainService.pieceData(this.props.match.params.id).then(res => {
            this.setState({ piece: res.data });
            console.log(res.data);

        }
        );
    }




    /**
     * Statt der Tabelle eher ein svg ??
     * @returns 
     */
    drawMoves() {
        const { piece } = this.state;
        var rows = "";
        if (piece.moves !== undefined) {
            rows = piece.moves.actions.map(function (item, i) {
                var entry = item.map(function (element, j) {
                    return (
                        <td key={j}> {element} </td>
                    );
                });
                return (
                    <tr key={i}> {entry} </tr>
                );
            });
        }

        return (
            <table className="table-hover table-striped table-bordered">
                <tbody>
                    {rows}
                </tbody>
            </table>
        );
    }

    drawMovesSVG() {
        const { piece } = this.state;
        var rows = "";
        if (piece.moves !== undefined) {
            rows = piece.moves.actions.map(function (item, i) {
                var entry = item.map(function (element, j) {
                    return (
                        <td key={j}> {element} </td>
                    );
                });
                return (
                    <tr key={i}> {entry} </tr>
                );
            });
        }

        return (
            <table className="table-hover table-striped table-bordered">
                <tbody>
                    {rows}
                </tbody>
            </table>
        );
    }





    drawCanvas() {
        const { pieceImage, width, height } = this.state;

        const draw = (ctx, frameCount) => {
            ctx.canvas.width = width
            ctx.canvas.height = height
            ctx.drawImage(pieceImage, 0, 0);
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
        return (<div>{this.drawMoves()} <Canvas draw={this.drawCanvas()} /></div>);
    }
}

export default PieceDataComponent