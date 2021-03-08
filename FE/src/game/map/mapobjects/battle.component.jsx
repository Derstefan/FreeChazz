import React, { Component } from 'react'

class BattleComponent extends Component {


    render() {
        const {x,y}=this.props;
        const style = {
            top: y+"px",
            left: x+"px",
            backgroundColor: "red",  

            width: "7px",
            height: "7px",
            position: "relative",
        }
        return (
            <div style={style}>
            </div>
        )
    }
}

export default BattleComponent