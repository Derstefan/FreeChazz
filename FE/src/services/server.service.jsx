import axios from 'axios';

const MAIN_API_BASE_URL = "http://192.168.0.135:8080/api";

class ServerService {



    getServerData() {
        return axios.get(MAIN_API_BASE_URL + "/serverdata")
    }

}

export default new ServerService()