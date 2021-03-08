import React, { Component } from 'react'
import MainService from './services/main.service';

class MainComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            inviteLink: "",
            numberOfGames: 0,
            gameIds:[]
        }
        this.startnewGame=this.startnewGame.bind(this);
        this.showGames=this.showGames.bind(this);
        this.showMap=this.showMap.bind(this);
    }

    componentDidMount(){
        const {gameIds} = this.state;
        
        MainService.getNumberOfGames().then((res)=>{
            this.setState({numberOfGames: res.data});
        },(error)=>{});
        MainService.getGameIds().then((res)=>{
            this.setState({gameIds: res.data});
        },(error)=>{});
    }


    startnewGame(){
        const {gameIds} = this.state;
        MainService.startNewGame().then((res)=>{
            if(res.data!==""){
            this.setState({inviteLink: res.data});
            }
            MainService.getNumberOfGames().then((res)=>{
                this.setState({numberOfGames: res.data});
            },(error)=>{});
            MainService.getGameIds().then((res)=>{
                this.setState({gameIds: res.data});
            },(error)=>{});
        },(error)=>{});

        
        
    }

    showGames(){
        const{gameIds} = this.state;
        console.log("show",gameIds);
        if(gameIds.length>0){
        return (gameIds.map((id)=>(<div><button type="button" onClick={()=>this.showMap(id)} className="btn btn-secondary mx-3">{id}</button></div>)));
    }
    return "";
    }

    showMap(id){
        const {history} = this.props;
        history.push(`/map/${id}`);
    }



    render() {
        const {numberOfGames} = this.state;
        return (
            <div>
                <br></br>
                <button type="button" className="btn btn-secondary" onClick={this.startnewGame}>new Game</button>
                <div className="card">
                    <br/><div className="row">{this.showGames()}</div>
                    <br/>
                    Number of Games {numberOfGames}
                </div>
            </div>
        )
    }
}

export default MainComponent