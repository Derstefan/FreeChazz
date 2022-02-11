import React, { Component } from 'react'
import mainService from '../services/main.service';
import Canvas from '../components/canvas.component';
import PieceGenerator from '../components/generator/piece-generator';
import PieceCard from './piece-card';
import Config from "./config.json";
import serverConfig from "../services/server-config.json";


class GameComponent extends Component {

    constructor(props) {
        super(props)
        this.state = {
            gameId: JSON.parse(localStorage.getItem("auth")).gameId,
            me: JSON.parse(localStorage.getItem("auth")).player,
            inviteLink: "http://" + serverConfig.host + ":3000/joingame/" + JSON.parse(localStorage.getItem("auth")).gameId,

            //updater
            isInited: false,

            //game consts
            player1: {},
            player2: {},
            width: 16,
            height: 16,
            pieceImagesSmall: {}, // images of pieces

            //pieceData for cards
            pieceImages: {}, //cardImages of pieces
            actions: {}, // actions of pieces
            pieceCard: new PieceCard(),

            // game state
            boardData: {}, // data from server
            boardView: {}, // symbol, playertype
            turn: "undef",
            round: 0,
            winner: null,

            //selection
            selectedField: {},
            possibleMoves: [],
            selectedPiece: {},
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
            var pieceImagesSmall = new Map();
            var actions = new Map();
            var pieceImages = new Map();
            for (let i = 0; i < bv.length; i++) {
                for (let j = 0; j < bv[0].length; j++) {
                    if (bv[i][j].symbol !== "" && pieceImagesSmall.get(bv[i][j].symbol) === undefined) {
                        var pg = new PieceGenerator(Config.squareSize * 0.8, Config.squareSize * 0.95, bv[i][j].seed);
                        pieceImagesSmall.set(bv[i][j].symbol, pg.drawPieceCanvas(bv[i][j].owner));

                        mainService.generatePiece(bv[i][j].seed).then(res2 => {
                            var pg = new PieceGenerator(100, 120, "" + bv[i][j].seed);
                            pieceImages.set(bv[i][j].symbol, pg.drawPieceCanvas(bv[i][j].owner))
                            actions.set(bv[i][j].symbol, res2.data.actionMap.actions);
                        });
                    }
                }
            }
            //TODO: alternativer RestRequest um gleich alle pieceData zu bekommen

            this.setState({ pieceImagesSmall: pieceImagesSmall, actions: actions, pieceImages: pieceImages, isInited: true, boardView: bv, width: bv[0].length, height: bv.length });
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

        for (let i = 0; i < bv.length; i++) {
            for (let j = 0; j < bv[0].length; j++) {
                if (board[i][j] === null) {
                    bv[i][j] = {
                        symbol: "",
                        owner: "",
                        possibleMoves: [],
                        serial: ""
                    };
                } else {
                    bv[i][j] = {
                        symbol: bv[i][j].symbol,
                        owner: bv[i][j].owner,
                        possibleMoves: bv[i][j].possibleMoves,
                        serial: bv[i][j].serial,
                        seed: bv[i][j].seed
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
                    selectedPiece: boardView[y][x],
                    pieceId: boardView[y][x].symbol //TODO: statt symbol pieceId
                });
            }
        } else {
            if (!isEmptyField) {
                // select new position
                this.setState({
                    possibleMoves: boardView[y][x].possibleMoves,
                    selectedField: { x: x, y: y },
                    selectedPiece: boardView[y][x],
                    pieceId: boardView[y][x].symbol//TODO: statt symbol pieceId
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
        const { width, height, boardView, possibleMoves, selectedField, me, pieceImagesSmall, isInited, winner, pieceId, selectedPiece, pieceCard, actions, pieceImages } = this.state;


        const draw = (ctx, frameCount) => {
            if (boardView) {
                const squareSize = Config.squareSize;
                const boardTopx = Config.boardTopx;
                const boardTopy = Config.boardTopy;

                ctx.canvas.width = squareSize * (width + 1) + Config.card.width;
                ctx.canvas.height = squareSize * (height + 1);

                //draw card

                if (pieceId !== "") {
                    ctx.drawImage(pieceCard.drawPieceCard(actions.get(pieceId), pieceImages.get(pieceId), selectedPiece.owner), squareSize * (width + 1), 0);
                }

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
                                if (pieceImagesSmall.length !== 0) {
                                    //  console.log(pieces);

                                    ctx.drawImage(pieceImagesSmall.get(boardView[j][i].symbol), xOffset, yOffset);
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

        if (isInited) {
            return (
                <div>
                    <div>{this.drawGameText()} {" "} {winner !== null && "The Winner is" + winner.name}</div>
                    <div className="row">
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