import axios from 'axios';

const MAIN_API_BASE_URL = "http://localhost:8080/api";

class MainService {



    startNewGame(name){
        return axios.get(MAIN_API_BASE_URL + "/newgame/" + name)
    }

    joinGame(gameId,name){
        return axios.get(MAIN_API_BASE_URL + "/joingame/" + gameId + "/" + name)
    }

    getGameData(gameId){
        return axios.get(MAIN_API_BASE_URL + "/gamedata/" + gameId,{headers: this.authHeader()})
    }

    getBoard(gameId){
        return axios.get(MAIN_API_BASE_URL + "/board/" + gameId,{headers: this.authHeader()})
    }

    play(gameId,draw){
        return axios.post(MAIN_API_BASE_URL + "/play/" + gameId,draw,{headers: this.authHeader()})
    }

    authHeader(){
        const user = JSON.parse(localStorage.getItem("auth"));

        if(user && user.accessToken){
            return {Authorization: "Bearer " +user.accessToken};
        }
        return {};
    }
}

export default new MainService()