import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import MainComponent from './server/main.component';
import HeaderComponent from './game/header.component';
import MapComponent from './game/map/map.component';



function App() {
  return (
    <div className="App">
      <Router>
      <HeaderComponent/>
                <div className="container">
                    <Switch> 
                          <Route path = "/" exact component = {MainComponent}></Route>
                          <Route path = "/map/:id" exact component = {MapComponent}></Route>
                          {/* <Route path = "/update-employee/:id" component = {UpdateEmployeeComponent}></Route> */}
                    </Switch>
                </div>
              
        </Router>
    </div>
  );
}

export default App;
