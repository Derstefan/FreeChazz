import React, { Component } from 'react'
import Canvas from '../components/canvas.component';
import PieceGenerator from '../components/generator/piece-generator';
import mainService from '../services/main.service';
import PieceCard from './piece-card';
import Config from "./config.json";

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
            pieceCard: new PieceCard(),

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
                mainService.generatePiece(pieceId).then(res => {
                    var pg = new PieceGenerator(100, 120, "" + pieceId);
                    this.setState({
                        piece: res.data,
                        pieceImage: pg.drawPieceCanvas("P1")
                    });
                    console.log(res.data);
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
        const { pieceCard, piece, pieceImage } = this.state;

        const draw = (ctx, frameCount) => {
            ctx.canvas.width = Config.card.width;
            ctx.canvas.height = Config.card.height;
            ctx.drawImage(pieceCard.drawPieceCard(piece, pieceImage), 0, 0);
        }
        return draw;
    }




    render() {
        const { piece, pieceImage } = this.state;
        if (piece && pieceImage) {
            return (<div><Canvas draw={this.drawCanvas()} /> </div>);
        }
        return "";
    }
}

export default PieceCardComponent