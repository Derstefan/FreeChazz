import React, { Component } from 'react'
import MainService from '../server/services/main.service';

class DashboardComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            inviteLink: "",
            numberOfGames: 0,
        }
        this.startnewGame=this.startnewGame.bind(this);
    }

    componentDidMount(){
        MainService.getNumberOfGames().then((res)=>{
            this.setState({numberOfGames: res.data});
        });
    }


    startnewGame(){
        const {inviteLink,numberOfGames} = this.state;
        MainService.startNewGame().then((res)=>{
            if(res.data!==""){
            this.setState({inviteLink: res.data});
            }
            MainService.getNumberOfGames().then((res)=>{
                this.setState({numberOfGames: res.data});
            });
        });
    }

    render() {
        const {inviteLink,numberOfGames} = this.state;
        return (
            <div>
                <br></br>
                <button type="button" className="btn btn-secondary" onClick={this.startnewGame}>new Game</button>
                <div className="card">
                    InviteLink:<br></br>
                    <a href={inviteLink}>{inviteLink}</a>
                    <br/><br/>
                    Number of Games {numberOfGames}
                </div>
            </div>
        )
    }
}

export default DashboardComponent