import React, { Component } from 'react'
import PieceCardComponent from '../../game/piece-card.component';

class PieceDataComponent extends Component {

    constructor(props) {

        super(props);

        this.state = {
            pieceId: this.props.match.params.id,
        }

    }


    render() {
        return (<div> <PieceCardComponent pieceId={this.props.match.params.id}></PieceCardComponent></div>);


    }
}

export default PieceDataComponent