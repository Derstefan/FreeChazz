import React, { Component } from 'react'
import Canvas from '../components/canvas.component';
import PieceGenerator from '../components/generator/piece-generator';
import mainService from '../services/main.service';

/**
 * 
 */
class PieceCardComponent extends Component {

    constructor(props) {

        super(props);


        this.state = {
            //    pieceId: this.props.pieceId,
            width: 160,
            height: 300,
            pieceImage: "",

            piece: {},

            //consts
            actionsSize: 9,
            actionsOffsetX: 13,
            actionsOffsetY: 160,

            imageOffsetX: 32,
            imageOffsetY: 10
        }

    }

    componentDidMount() {
        const { pieceId } = this.props;

        console.log("pieceId ", pieceId);
        if (pieceId) {
            if (pieceId !== "") {
                mainService.pieceData(pieceId).then(res => {
                    var pg = new PieceGenerator(100, 120, "" + pieceId);
                    this.setState({
                        piece: res.data,
                        pieceImage: pg.drawPieceCanvas("P1")
                    });
                    //      console.log(res.data);

                }
                );
            }
        }
    }


    // static getDerivedStateFromProps(props, state) {
    //     if (props.pieceId !== state.pieceId) {
    //         //Change in props
    //         console.log(props.pieceId);
    //         return {
    //             pieceId: props.pieceId
    //         };
    //     }
    //     return null; // No change to state
    // }



    drawCanvas() {
        const { piece, pieceImage, width, height, actionsSize, actionsOffsetX, actionsOffsetY } = this.state;

        const draw = (ctx, frameCount) => {
            ctx.canvas.width = width
            ctx.canvas.height = height
            if (piece.moves !== undefined) {
                ctx.drawImage(pieceImage, 32, 10);
                // draw actions

                for (var i = 0; i < piece.moves.actions.length; i++) {
                    for (var j = 0; j < piece.moves.actions[0].length; j++) {
                        if (piece.moves.actions[i][j] !== "-") {
                            if (piece.moves.actions[i][j] === "P") {
                                ctx.fillStyle = "#11AA11";
                                ctx.fillRect(actionsOffsetX + i * actionsSize, actionsOffsetY + j * actionsSize, actionsSize, actionsSize);
                            } else if (piece.moves.actions[i][j] === "E") {
                                ctx.fillStyle = "#BB1111";
                                ctx.fillRect(actionsOffsetX + i * actionsSize, actionsOffsetY + j * actionsSize, actionsSize, actionsSize);
                            } else if (piece.moves.actions[i][j] === "F") {
                                ctx.fillStyle = "#1111BB";
                                ctx.fillRect(actionsOffsetX + i * actionsSize, actionsOffsetY + j * actionsSize, actionsSize, actionsSize);
                            } else {
                                ctx.fillStyle = "#666666";
                                ctx.fillRect(actionsOffsetX + i * actionsSize, actionsOffsetY + j * actionsSize, actionsSize, actionsSize);
                            }
                        }
                    }
                }

                ctx.lineWidth = 0.5;
                ctx.strokeStyle = "#AAAAAA";
                for (i = 0; i < piece.moves.actions.length + 1; i++) {
                    //  ctx.drawline(actionsOffsetX + i * actionsSize, actionsOffsetY, actionsOffsetX + i * actionsSize, actionsOffsetX + piece.moves.actions.length + 1 * actionsSize);


                    ctx.beginPath();
                    ctx.moveTo(actionsOffsetX + i * actionsSize, actionsOffsetY);
                    ctx.lineTo(actionsOffsetX + i * actionsSize, actionsOffsetY + (piece.moves.actions.length) * actionsSize);
                    ctx.stroke();

                    ctx.beginPath();
                    ctx.moveTo(actionsOffsetX, actionsOffsetY + i * actionsSize);
                    ctx.lineTo(actionsOffsetX + (piece.moves.actions.length) * actionsSize, actionsOffsetY + i * actionsSize);
                    ctx.stroke();

                }
            }




        }
        return draw;
    }




    render() {

        return (<div><Canvas draw={this.drawCanvas()} /> </div>);
    }
}

export default PieceCardComponent