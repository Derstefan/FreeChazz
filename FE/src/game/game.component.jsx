import React, { Component } from 'react'
import mainService from '../services/main.service';
import Canvas from '../components/canvas.component';
import PieceGenerator from '../components/generator/piece-generator';
import PieceCardComponent from './piece-card.component';
import Config from "./config.json";


class GameComponent extends Component {

    constructor(props) {
        super(props)
        this.state = {
            gameId: JSON.parse(localStorage.getItem("auth")).gameId,
            me: JSON.parse(localStorage.getItem("auth")).player,
            inviteLink: "http://192.168.0.135:3000/joingame/" + JSON.parse(localStorage.getItem("auth")).gameId,

            //updater
            isInited: false,

            //game consts
            player1: {},
            player2: {},
            width: 16,
            height: 16,
            pieces: {}, // images of pieces

            // game state
            boardData: {}, // data from server
            boardView: {}, // symbol, playertype
            turn: "P1",
            round: 0,
            winner: null,

            //selection
            selectedField: {},
            possibleMoves: [],
            pieceId: "",
        }
        this.selectField = this.selectField.bind(this);
        this.clickOnCanvas = this.clickOnCanvas.bind(this);
        this.drawMethod = this.drawMethod.bind(this);
        this.play = this.play.bind(this);
        this.loadBoard = this.loadBoard.bind(this);
        this.loadPieceData = this.loadPieceData.bind(this);

    }

    componentDidMount() {
        const { isInited } = this.state;

        //Init function 
        if (!isInited) {
            this.loadPieceData();
        }

        //start updater
        const loadTimer = setInterval(() => {
            this.updateGameData();

        }, Config.updateInterval);
    }

    //check for ugameupdate
    updateGameData() {
        const { gameId, turn } = this.state;
        mainService.getGameData(gameId).then((res) => {



            this.setState({ player1: res.data.player1, player2: res.data.player2, turn: res.data.turn, round: res.data.round, winner: res.data.winner });
            //when other player made his turn
            if (turn !== res.data.turn) {

                this.loadBoard();
            }

            //check game end ?
        });
    }


    // initial creating piece graphics
    loadPieceData() {
        const { gameId } = this.state;
        mainService.getBoard(gameId).then((res) => {
            let bv = this.createBoard(res.data.board);
            var pieces = new Map();
            for (let i = 0; i < bv.length; i++) {
                for (let j = 0; j < bv[0].length; j++) {
                    if (bv[i][j].symbol !== "" && pieces.get(bv[i][j].symbol) === undefined) {
                        var pg = new PieceGenerator(Config.squareSize * 0.8, Config.squareSize * 0.95, bv[i][j].symbol);
                        pieces.set(bv[i][j].symbol, pg.drawPieceCanvas(bv[i][j].owner));

                    }
                }
            }

            this.setState({ pieces: pieces, isInited: true, boardView: bv, width: bv[0].length, height: bv.length });
        });
    }

    //update Board
    loadBoard() {
        const { gameId } = this.state;
        mainService.getBoard(gameId).then((res) => {
            let bv = this.createBoard(res.data.board);
            this.setState({ boardData: res.data, boardView: bv, width: bv[0].length, height: bv.length });
        });
    }


    // save Board data
    createBoard(board) {
        let bv = board;
        console.log(bv);
        for (let i = 0; i < bv.length; i++) {
            for (let j = 0; j < bv[0].length; j++) {
                if (board[i][j] === null) {
                    bv[i][j] = {
                        symbol: "",
                        owner: "",
                        possibleMoves: []
                    };
                } else {
                    bv[i][j] = {
                        symbol: bv[i][j].symbol,
                        owner: bv[i][j].owner,
                        possibleMoves: bv[i][j].possibleMoves
                    };
                }
            }
        }
        return bv;
    }


    selectField(x, y) {
        const { boardView, selectedField, me, turn, possibleMoves } = this.state;
        const isPlayerTurn = me === turn;
        const isEmptyField = boardView[y][x].symbol === "";
        const sthSelected = JSON.stringify(selectedField) !== "{}";

        if (sthSelected) {
            const isAlreadySelected = selectedField.x === x && selectedField.y === y;
            const isPossibleMove = possibleMoves.some(move => move.x === x && move.y === y);
            const isOwnSelected = me === boardView[selectedField.y][selectedField.x].owner;

            // move,unselect, another select ?

            if (isAlreadySelected) {
                // unselect
                this.setState({
                    possibleMoves: [],
                    selectedField: {},
                    pieceId: ""
                });
            } else if (isOwnSelected && isPossibleMove && isPlayerTurn) {
                // move
                const draw = { fromPos: { x: selectedField.x, y: selectedField.y }, toPos: { x: x, y: y } }
                this.play(draw);
            } else if (isEmptyField) {
                // unselect
                this.setState({
                    possibleMoves: [],
                    selectedField: {},
                    pieceId: ""
                });
            } else {
                // select new position
                this.setState({
                    possibleMoves: boardView[y][x].possibleMoves,
                    selectedField: { x: x, y: y },
                    pieceId: boardView[y][x].symbol
                });
            }
        } else {
            if (!isEmptyField) {
                // select new position
                this.setState({
                    possibleMoves: boardView[y][x].possibleMoves,
                    selectedField: { x: x, y: y },
                    pieceId: boardView[y][x].symbol
                });
            }
        }
    }

    play(draw) {
        const { gameId, turn, winner } = this.state;
        if (winner === null) {
            mainService.play(gameId, draw).then((res) => {
                //            console.log("played", turn);
                const nextTurn = (turn === "P1") ? "P2" : "P1";
                this.setState({
                    possibleMoves: [],
                    selectedField: {},
                    turn: nextTurn
                });

                this.updateGameData();
                this.loadBoard();
            });
        }
    }




    clickOnCanvas(event) {
        const rect = event.target.getBoundingClientRect();
        const x = event.clientX - rect.left - Config.boardTopx;
        const y = event.clientY - rect.top - Config.boardTopy;
        this.selectField((x - x % Config.squareSize) / (Config.squareSize), (y - y % Config.squareSize) / (Config.squareSize));
    }

    drawMethod() {
        const { width, height, boardView, possibleMoves, selectedField, me, pieces, isInited, winner } = this.state;


        const draw = (ctx, frameCount) => {
            if (boardView) {
                const squareSize = Config.squareSize;
                const boardTopx = Config.boardTopx;
                const boardTopy = Config.boardTopy;

                ctx.canvas.width = squareSize * (width + 1)
                ctx.canvas.height = squareSize * (height + 1)

                //draw board
                for (let i = 0; i < width; i++) {
                    for (let j = 0; j < height; j++) {
                        ctx.fillStyle = ((i + j) % 2 === 0) ? "#D2B48C" : "PeachPuff";
                        let xOffset = boardTopx + j * squareSize;
                        let yOffset = boardTopy + i * squareSize;
                        ctx.fillRect(xOffset, yOffset, squareSize, squareSize);
                    }
                }

                if (JSON.stringify(selectedField) !== "{}") {
                    // draw moves
                    ctx.globalAlpha = 0.45;
                    ctx.fillStyle = (me === boardView[selectedField.y][selectedField.x].owner) ? "lightgreen" : "red";
                    for (let k = 0; k < possibleMoves.length; k++) {
                        let xOffset = boardTopx + possibleMoves[k].x * squareSize;
                        let yOffset = boardTopy + possibleMoves[k].y * squareSize;

                        ctx.fillRect(xOffset, yOffset, squareSize, squareSize);
                    }

                    //draw selected
                    ctx.fillStyle = (me === boardView[selectedField.y][selectedField.x].owner) ? "green" : "darkred";
                    ctx.fillRect(boardTopx + selectedField.x * squareSize, boardTopy + selectedField.y * squareSize, squareSize, squareSize);
                    ctx.globalAlpha = 1;
                }

                // draw pieces
                if (boardView[0] && isInited) {
                    ctx.fillStyle = "black";
                    ctx.font = "20px Arial";
                    for (let i = 0; i < width; i++) {
                        for (let j = 0; j < height; j++) {
                            if (boardView[j][i].symbol !== "") {

                                let xOffset = boardTopx + (i + 0.115) * squareSize;
                                let yOffset = boardTopy + (j + 0.05) * squareSize;
                                if (pieces.length !== 0) {
                                    //  console.log(pieces);

                                    ctx.drawImage(pieces.get(boardView[j][i].symbol), xOffset, yOffset);
                                }
                                //}


                            }
                        }
                    }
                }

                // draw the border around the chessboard
                ctx.strokeStyle = "black";
                ctx.strokeRect(boardTopx, boardTopy, squareSize * width, squareSize * height)


                // draw winner
                if (winner !== null) {
                    ctx.fillStyle = "rgba(100,100,100,0.8)"
                    ctx.fillRect(boardTopx, boardTopy, squareSize * width, squareSize * height)

                    ctx.fillStyle = "red"
                    ctx.font = '30px serif';
                    ctx.fillText(winner.name + ' wins!', squareSize * width / 3, squareSize * height / 2);
                }

            }
        }
        return draw;

    }

    drawGameText() {
        const { me, turn } = this.state;
        if (me === turn) {
            return "Your turn!";
        }
        return "Wait for opponents turn...";
    }


    render() {
        const { inviteLink, player1, player2, round, isInited, winner } = this.state;
        // console.log("pieceId (game component) :", this.state.pieceId);
        if (isInited) {
            return (
                <div>
                    <div>{this.drawGameText()} {" "} {winner !== null && "The Winner is" + winner.name}</div>
                    <div className="row">
                        <div class="mb-3 mt-5">
                            <div className="card">
                                {this.state.pieceId !== "" && <PieceCardComponent pieceId={this.state.pieceId}></PieceCardComponent>}
                            </div>
                        </div>
                        <div class="mb-5">
                            <Canvas draw={this.drawMethod()} onClick={this.clickOnCanvas} />

                            <div>{inviteLink}</div>
                            <div>
                                Player1: {player1 && player1.name} {player2 && <>Player2: {player2.name}</>} {" round:"}{round}
                            </div>
                        </div>
                    </div >
                </div>
            )
        }
        return "";
    }
}

export default GameComponent