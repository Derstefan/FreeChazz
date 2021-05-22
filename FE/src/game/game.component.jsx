import React, { Component } from 'react'
import mainService from '../services/main.service';
import Canvas from './canvas.component';
import PieceComponent from '../components/piece.component';
import PieceGeneratorComponent from '../generator/piece-generator.component';
import PieceGenerator from '../generator/piece-generator';




class GameComponent extends Component {



    constructor(props) {
        super(props)
        this.state = {
            gameId: JSON.parse(localStorage.getItem("auth")).gameId,
            me: JSON.parse(localStorage.getItem("auth")).player,
            inviteLink: "http://localhost:3000/joingame/" + JSON.parse(localStorage.getItem("auth")).gameId,

            //game
            player1: {},
            player2: {},
            boardData: {}, // data from server
            boardView: {}, // symbol, playertype
            width: 16,
            height: 16,
            turn: "P1",
            round: 0,


            //selection
            selectedField: {},
            possibleMoves: [],

            //pieces
            pieces: {},

            //updater
            loadTimer: undefined,
            isInited: false,


            //consts
            squareSize: 45,
            boardTopx: 30,
            boardTopy: 30,
            updateInterval: 2000
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
        this.updateGameData();

        if (!isInited) {
            this.loadBoard(true);
        }
        const loadTimer = setInterval(() => {
            this.updateGameData();

        }, this.state.updateInterval);
    }

    loadPieceData(bv) {
        const { squareSize } = this.state;

        // console.log("is inside", inited);
        var pieces = new Map();

        // fill pieces canvas buffer
        for (let i = 0; i < bv.length; i++) {
            for (let j = 0; j < bv[0].length; j++) {
                // TODO: P1 und P2 unterscheidung?
                console.log(bv[i][j].symbol);

                if (bv[i][j].symbol !== "" && pieces.get(bv[i][j].symbol) === undefined) {

                    var pg = new PieceGenerator(squareSize * 0.8, squareSize * 0.95, bv[i][j].symbol);//TODO: owner!!!
                    pieces.set(bv[i][j].symbol, pg.drawPieceCanvas(bv[i][j].owner));
                    // pieces[bv[i][j].symbol] = PieceGeneratorComponent.drawPieceCanvas(squareSize * 0.8, squareSize * 0.95, bv[i][j].symbol, bv[i][j].owner);

                }
            }
        }
        //        console.log(str);
        //        console.log(pieces);
        //console.log("size", pieces);
        this.setState({ pieces: pieces, isInited: true, boardView: bv, width: bv[0].length, height: bv.length });
    }



    updateGameData() {
        const { gameId, turn } = this.state;
        mainService.getGameData(gameId).then((res) => {
            this.setState({ player1: res.data.player1, player2: res.data.player2, turn: res.data.turn, round: res.data.round });

            //when other player made his turn
            if (turn !== res.data.turn) {

                this.loadBoard(false);
            }

            //check game end ?
        });
    }



    loadBoard(init) {
        const { gameId, squareSize } = this.state;
        mainService.getBoard(gameId).then((res) => {
            let bv = res.data.board;
            console.log(bv);
            for (let i = 0; i < bv.length; i++) {
                for (let j = 0; j < bv[0].length; j++) {
                    if (res.data.board[i][j] === null) {
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
            if (init) {
                this.loadPieceData(bv);
            } else {
                this.setState({ boardData: res.data, boardView: bv, width: bv[0].length, height: bv.length });
            }
        });
    }

    selectField(x, y) {
        const { boardView, selectedField, me, turn, possibleMoves } = this.state;
        const isPlayerTurn = me === turn;
        const isEmptyField = boardView[y][x].symbol === "";
        const sthSelected = JSON.stringify(selectedField) !== "{}";

        if (sthSelected) {
            const isAlreadySelected = selectedField.x === x && selectedField.y === y;
            const isPossibleMove = possibleMoves.some(move => move.x === x && move.y === y);
            const isOwnSelected = me == boardView[selectedField.y][selectedField.x].owner;

            // move,unselect, another select ?

            if (isAlreadySelected) {
                // unselect
                this.setState({
                    possibleMoves: [],
                    selectedField: {}
                });
            } else if (isOwnSelected && isPossibleMove && isPlayerTurn) {
                // move
                const draw = { fromPos: { x: selectedField.x, y: selectedField.y }, toPos: { x: x, y: y } }
                this.play(draw);
            } else if (isEmptyField) {
                // unselect
                this.setState({
                    possibleMoves: [],
                    selectedField: {}
                });
            } else {
                // select new position
                this.setState({
                    possibleMoves: boardView[y][x].possibleMoves,
                    selectedField: { x: x, y: y }
                });
            }
        } else {
            if (!isEmptyField) {
                // select new position
                this.setState({
                    possibleMoves: boardView[y][x].possibleMoves,
                    selectedField: { x: x, y: y }
                });
            }
        }
    }

    play(draw) {
        const { gameId, turn } = this.state;
        mainService.play(gameId, draw).then((res) => {
            console.log("played", turn);
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




    clickOnCanvas(event) {
        const { squareSize, boardTopy, boardTopx } = this.state;
        const rect = event.target.getBoundingClientRect();
        const x = event.clientX - rect.left - boardTopx;
        const y = event.clientY - rect.top - boardTopy;
        //console.log("x: " + x + " y: " + y);
        this.selectField((x - x % squareSize) / (squareSize), (y - y % squareSize) / (squareSize));
    }

    drawMethod() {
        const { width, height, squareSize, boardView, possibleMoves, boardTopy, boardTopx, selectedField, me, pieces, isInited } = this.state;


        const draw = (ctx, frameCount) => {
            if (boardView) {

                ctx.canvas.width = squareSize * (width + 1)
                ctx.canvas.height = squareSize * (height + 1)

                //draw board
                for (let i = 0; i < width; i++) {
                    for (let j = 0; j < height; j++) {
                        ctx.fillStyle = ((i + j) % 2 == 0) ? "#D2B48C" : "PeachPuff";
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

            }
        }
        return draw;

    }

    drawPiece(pieceCode, player) {
        if (player === "P1") {
            //return "♖";

        }
        return pieceCode;
        //        return "♜";
    }

    drawGameText() {
        const { me, turn } = this.state;
        if (me === turn) {
            return "Your turn!";
        }
        return "Wait for opponents turn...";
    }


    render() {
        const { inviteLink, player1, player2, boardView, selectedField, turn, me, round, isInited } = this.state;
        var piece = "";
        if (boardView[0] && selectedField.x) {
            piece = boardView[selectedField.y][selectedField.x];
            //console.log(piece);
        }


        // TODO: aufteilen in GameData und PieceData ?
        if (isInited) {
            return (

                <div>
                    {/* <img id="scream" width="220" height="277" src="https://filesamples.com/samples/image/svg/sample_640%C3%97426.svg" alt="The Scream"></img> */}


                    <div>            {this.drawGameText()}</div>
                    <Canvas draw={this.drawMethod()} onClick={this.clickOnCanvas} />
                    {/* <div>{piece.symbol} {" "}{piece.owner}</div>*/}
                    <div>
                        {inviteLink}
                    </div>
                    <div>
                        Player1: {player1 && player1.name} {player2 && <>Player2: {player2.name}</>} {" round:"}{round}
                    </div>

                </div >
            )
        }
        return "";
    }
}

export default GameComponent