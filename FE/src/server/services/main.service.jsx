import axios from 'axios';

const MAIN_API_BASE_URL = "http://localhost:8080/main";

class MainService {


    startNewGame(){
        return axios.get(MAIN_API_BASE_URL + "/newgame")
    }

    getNumberOfGames(){
        return axios.get(MAIN_API_BASE_URL + "/numberofgames")
    }

    getGameIds(){
        return axios.get(MAIN_API_BASE_URL + "/gameids")
    }

    // getEmployees(){
    //     return axios.get(EMPLOYEE_API_BASE_URL);
    // }

    // createEmployee(employee){
    //     return axios.post(EMPLOYEE_API_BASE_URL, employee);
    // }

    // getEmployeeById(employeeId){
    //     return axios.get(EMPLOYEE_API_BASE_URL + '/' + employeeId);
    // }

    // updateEmployee(employee, employeeId){
    //     return axios.put(EMPLOYEE_API_BASE_URL + '/' + employeeId, employee);
    // }

    // deleteEmployee(employeeId){
    //     return axios.delete(EMPLOYEE_API_BASE_URL + '/' + employeeId);
    // }
}

export default new MainService()