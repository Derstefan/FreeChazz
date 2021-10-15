import React, { Component } from 'react'
import PieceCardComponent from '../../game/piece-card.component';

class PiecesDataComponent extends Component {

    constructor(props) {

        super(props);

        this.state = {
        }

    }


    render() {
        return (<div className="row">
            <PieceCardComponent pieceId={"" + Math.round(Math.random() * 123232189)}></PieceCardComponent>
            <PieceCardComponent pieceId={"" + Math.round(Math.random() * 123232189)}></PieceCardComponent>
            <PieceCardComponent pieceId={"" + Math.round(Math.random() * 123232189)}></PieceCardComponent>
            <PieceCardComponent pieceId={"" + Math.round(Math.random() * 123232189)}></PieceCardComponent>
            <PieceCardComponent pieceId={"" + Math.round(Math.random() * 123232189)}></PieceCardComponent>
            <PieceCardComponent pieceId={"" + Math.round(Math.random() * 123232189)}></PieceCardComponent>
            <PieceCardComponent pieceId={"" + Math.round(Math.random() * 123232189)}></PieceCardComponent>

            <PieceCardComponent pieceId={"" + Math.round(Math.random() * 123232189)}></PieceCardComponent>
            <PieceCardComponent pieceId={"" + Math.round(Math.random() * 123232189)}></PieceCardComponent>
            <PieceCardComponent pieceId={"" + Math.round(Math.random() * 123232189)}></PieceCardComponent>
            <PieceCardComponent pieceId={"" + Math.round(Math.random() * 123232189)}></PieceCardComponent>
            <PieceCardComponent pieceId={"" + Math.round(Math.random() * 123232189)}></PieceCardComponent>
            <PieceCardComponent pieceId={"" + Math.round(Math.random() * 123232189)}></PieceCardComponent>
            <PieceCardComponent pieceId={"" + Math.round(Math.random() * 123232189)}></PieceCardComponent>
        </div>);


    }
}

export default PiecesDataComponent