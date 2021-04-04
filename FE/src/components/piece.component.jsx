import React, { Component } from 'react'

class PieceComponent extends Component {
    constructor(props) {
        super(props)
    }

    render() {
        const {symbol,selected,canMove} = this.props;
        let style = {};
        let str = symbol
        if((selected || canMove)){
            style = {color: 'green'};
            if(str===""){
             //   str='X';
            }
        }


        return (
            <div style={style}>
                {str}
            </div>
        )
    }
}

export default PieceComponent