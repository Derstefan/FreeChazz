import axios from 'axios';

const API_BASE_URL = "http://localhost:8080/";

class PlanetService {


    getPlanet(gameId,planetId){
        return axios.get(API_BASE_URL + gameId + "/map/planets")
    }

}

export default new PlanetService()