import React, { Component } from 'react'

class PlanetComponent extends Component {


    render() {
        const {x,y}=this.props;
        const style = {
            top: y+"px",
            left: x+"px",
            backgroundColor: "green",
            
            width: "7px",
            height: "7px",
            position: "relative",
            borderRadius: "50%",          
        }
        return (
            <div style={style}>
            </div>
        )
    }
}

export default PlanetComponent