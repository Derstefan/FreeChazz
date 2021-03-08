import axios from 'axios';

const API_BASE_URL = "http://localhost:8080/";

class MapService {


    getPlanetsMap(gameId){
        return axios.get(API_BASE_URL + gameId + "/map/planets")
    }

    getFleetsMap(gameId){
        return axios.get(API_BASE_URL + gameId + "/map/fleets")
    }

    getBattlesMap(gameId){
        return axios.get(API_BASE_URL + gameId + "/map/battles")
    }

    getPlanetData(gameId,planetId){

    }

    getFleetData(gameId,FleetId){

    }

    getBattleData(gameId,BattleId){
        
    }


}

export default new MapService()