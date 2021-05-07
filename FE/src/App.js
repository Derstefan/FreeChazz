import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import HeaderComponent from './components/header.component';
import StartComponent from './components/startGame.component';
import GameComponent from './game/game.component';
import JoinGameComponent from './components/joinGame.component';
import PieceComponent from './components/piece.component';
import PiecesComponent from './components/pieces.component';




function App() {
  return (
    <div className="App">
      <Router>
        <HeaderComponent />
        <div className="container">
          <Switch>
            <Route path="/" exact component={StartComponent}></Route>
            <Route path="/game" exact component={GameComponent}></Route>
            <Route path="/joingame/:id" exact component={JoinGameComponent}></Route>
            <Route path="/piece" exact component={PieceComponent}></Route>
            <Route path="/pieces" exact component={PiecesComponent}></Route>
            {/*new game/*}
                          {/*game/*}
                          {/*pieces/*}
                          {/* <Route path = "/update-employee/:id" component = {UpdateEmployeeComponent}></Route> */}
          </Switch>
        </div>

      </Router>
    </div>
  );
}

export default App;
