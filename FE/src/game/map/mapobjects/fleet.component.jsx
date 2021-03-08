import React, { Component } from 'react'

class FleetComponent extends Component {


    render() {
        const { x, y,rot } = this.props;
        const style = {
            top: y + "px",
            left: x + "px",
            transform: "rotate("+rot+"deg)",
            background: "blue",

            position: "relative",
            display: "block",
            width: "7px",
            height: "7px",
            clipPath: "polygon(50% 0%, 0% 100%, 100% 100%)"
        }
        return (
            <div style={style}>
            </div>
        )
    }
}

export default FleetComponent