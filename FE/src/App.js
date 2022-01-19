import './App.css';
import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import HeaderComponent from './components/header.component';
import StartComponent from './components/startGame.component';
import GameComponent from './game/game.component';
import JoinGameComponent from './components/joinGame.component';
import PieceComponent from './components/generator/piece.component';
import ChessPieceComponent from './components/generator/silhouettes/chess-piece.component';
import PiecesComponent from './components/generator/pieces.component';
import PieceDataComponent from './components/tools/piece-data.component';
import PiecesDataComponent from './components/tools/pieces-data.component';
import ChessPiecesComponent from './components/generator/silhouettes/chess-pieces.component';




function App() {
  return (
    <div className="App">
      <Router>
        <HeaderComponent />
        <div className="container">
          <Switch>
            <Route path="/" exact component={StartComponent}></Route>
            <Route path="/game" exact component={GameComponent}></Route>
            <Route path="/joingame/:id" component={JoinGameComponent}></Route>
            <Route path="/piece/:id" component={PieceDataComponent}></Route>
            <Route path="/pieces" component={PiecesDataComponent}></Route>
            <Route path="/piecesymbol" component={PieceComponent}></Route>
            <Route path="/piecesymbols" component={PiecesComponent}></Route>
            <Route path="/chesspiecesymbol" component={ChessPieceComponent}></Route>
            <Route path="/chesspiecessymbol" component={ChessPiecesComponent}></Route>
          </Switch>
        </div>

      </Router>
    </div>
  );
}

export default App;
