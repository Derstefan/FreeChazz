import React, { Component } from 'react'
import mainService from '../services/main.service';

class StartComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            name: "player1"
        }
        this.changeName = this.changeName.bind(this);
        this.startGame = this.startGame.bind(this);
    }

    componentDidMount() {
        // EmployeeService.getEmployeeById(this.state.id).then( res => {
        //     this.setState({employee: res.data});
        // })
    }


    startGame() {
        const {name} = this.state;
        const {history} = this.props;
        console.log("start game");
        mainService.startNewGame(name).then((res) => {
            console.log(res.data);
            localStorage.setItem("auth",JSON.stringify(res.data))
 
            history.push("/game");

 
        });
     }

    changeName(event) {
        this.setState({ name: event.target.value });
    }

    render() {
        const { name } = this.state;
        return (
            <div>
                <div className="m-5" >
                    <input value={name} onChange={this.changeName} />
                </div>
                <div className="m-5" >


                    <button type="button" className="btn btn primary" onClick={this.startGame}>new Game</button>
                   
                </div>
            </div>
        )
    }
}

export default StartComponent