import React, { Component } from 'react'
import mainService from '../services/main.service';

class JoinGameComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            gameId: this.props.match.params.id,
            name: "player2"
        }
        this.joinGame = this.joinGame.bind(this);
        this.changeName = this.changeName.bind(this);
    }

    componentDidMount(){
        // EmployeeService.getEmployeeById(this.state.id).then( res => {
        //     this.setState({employee: res.data});
        // })
    }

    joinGame(){
        const {gameId,name} = this.state;
        const {history} = this.props;
        console.log("join game");
        mainService.joinGame(gameId,name).then((res) => {
            console.log(res.data);
            localStorage.setItem("auth",JSON.stringify(res.data))
 
            history.push("/game");

 
        });
    }

    changeName(event) {
        this.setState({ name: event.target.value });
    }

    render() {
        const {name} = this.state;
        return (
            <div>
                <div className="m-5" >
                    <input value={name} onChange={this.changeName} />
                    <button type="button" className="btn btn primary" onClick={this.joinGame}>join Game</button>
                </div>
            </div>
        )
    }
}

export default JoinGameComponent