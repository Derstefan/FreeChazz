import axios from 'axios';
import serverConfig from "../services/server-config.json";

const MAIN_API_BASE_URL = "http://" + serverConfig.host + ":8080/api";

class MainService {



    startNewGame(name) {
        return axios.get(MAIN_API_BASE_URL + "/newgame/" + name)
    }

    startNewGameWithParams(name, params) {
        return axios.post(MAIN_API_BASE_URL + "/newgame/" + name, params)
    }

    joinGame(gameId, name) {
        return axios.get(MAIN_API_BASE_URL + "/joingame/" + gameId + "/" + name)
    }

    getGameData(gameId) {
        return axios.get(MAIN_API_BASE_URL + "/gamedata/" + gameId, { headers: this.authHeader() })
    }

    getBoard(gameId) {
        return axios.get(MAIN_API_BASE_URL + "/board/" + gameId, { headers: this.authHeader() })
    }

    play(gameId, draw) {
        return axios.post(MAIN_API_BASE_URL + "/play/" + gameId, draw, { headers: this.authHeader() })
    }

    generatePiece(seed) {
        return axios.get(MAIN_API_BASE_URL + "/generatepiece/" + seed);
    }

    findPiece(serial) {
        return axios.get(MAIN_API_BASE_URL + "/findpiece/" + serial);
    }

    authHeader() {
        const user = JSON.parse(localStorage.getItem("auth"));

        if (user && user.accessToken) {
            return { Authorization: "Bearer " + user.accessToken };
        }
        return {};
    }


}

export default new MainService()