import React, { Component } from 'react'
import mainService from '../services/main.service';
import Canvas from '../components/canvas.component';
import PieceGenerator from '../components/generator/piece-generator';
import PieceCard from './piece-card';
import Config from "./config.json";
import RenderFunctions from "./render-functions.js";
import serverConfig from "../services/server-config.json";
import { Engine, Runner, Composite, Bodies, Body } from 'matter-js';


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
            graveyard: [],

            //selection
            selectedField: {},
            selectedPiece: {},
            pieceId: "",


            //animation with matter.js
            engine: Engine.create(),
            matterBodies: [],
            showAnimation: false,


        }

        //matter.js init
        //start matter.js runner
        Runner.run(Runner.create(), this.state.engine);

        //this.state.engine.gravity.scale = 0.00;
        this.state.engine.gravity.y = 0.6;
        this.state.engine.timing.timeScale = 0.8;

        this.selectField = this.selectField.bind(this);
        this.clickOnCanvas = this.clickOnCanvas.bind(this);
        this.drawMethod = this.drawMethod.bind(this);
        this.play = this.play.bind(this);
        this.loadBoard = this.loadBoard.bind(this);
        this.loadPieceData = this.loadPieceData.bind(this);
        this.createMatterBodies = this.createMatterBodies.bind(this);

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
                        var pg = new PieceGenerator(Config.squareSize * Config.board.smallImage.wScale, Config.squareSize * Config.board.smallImage.hScale, bv[i][j].seed);
                        pieceImagesSmall.set(bv[i][j].symbol, pg.drawPieceCanvas(bv[i][j].owner));

                        mainService.generatePiece(bv[i][j].seed).then(res2 => {
                            var pg = new PieceGenerator(Config.card.imageWidth, Config.card.imageHeight, "" + bv[i][j].seed);
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
        const { gameId, graveyard, engine, matterBodies, showAnimation } = this.state;
        var matterBodiesUpdate = matterBodies;
        mainService.getBoard(gameId).then((res) => {
            //List of deleted pieces TODO: what is if page reloaded and graveyard at first empty -> many animations at start ?

            let deletedPieces = res.data.graveyard.slice(graveyard.length, res.data.graveyard.size);

            //matter bodies create, add forces and composite
            if (showAnimation) {
                if (deletedPieces.length !== 0) {
                    this.createMatterBodies(deletedPieces).forEach(b => {
                        Composite.add(engine.world, b.body);
                        matterBodiesUpdate.push(b);
                    });
                }
            } else {
                this.setState({ showAnimation: true });
            }


            //  let bodyList = this.createMatterBodies(deletedPieces);
            //            console.log(bodyList.length);
            // console.log(Composite.allBodies(engine.world));



            let bv = this.createBoard(res.data.board);
            this.setState({ boardData: res.data, boardView: bv, graveyard: res.data.graveyard, matterBodies: matterBodiesUpdate, width: bv[0].length, height: bv.length });
        });
    }

    createMatterBodies(deletedPieces) {
        const { width, engine } = this.state;
        var matterBodies = [];
        deletedPieces.forEach(piece => {
            //create body
            var pg = new PieceGenerator(Config.squareSize * Config.board.smallImage.wScale, Config.squareSize * Config.board.smallImage.hScale, piece.seed);
            let xOffsetPic = Config.boardTopx + (piece.position.x + Config.board.smallImage.xOffset) * Config.squareSize;
            let yOffsetPic = Config.boardTopy + (piece.position.y + Config.board.smallImage.yOffset) * Config.squareSize;

            pg.getMatterBodies().forEach(b => {
                Body.translate(b.body, { x: xOffsetPic, y: yOffsetPic });
                matterBodies.push(b);
            });
            var ground = Bodies.rectangle(0, yOffsetPic + Config.squareSize * 0.7, 21300, 2, { isStatic: true });
            matterBodies.push({ body: ground, color: "black", alpha: 1.0, visible: false });
        });

        return matterBodies;

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
        const { boardView, selectedField, me, turn, selectedPiece } = this.state;
        const isPlayerTurn = me === turn;
        const isEmptyField = boardView[y][x].symbol === "";
        const sthSelected = JSON.stringify(selectedField) !== "{}";

        if (sthSelected) {
            const isAlreadySelected = selectedField.x === x && selectedField.y === y;
            const isPossibleMove = selectedPiece.possibleMoves.some(move => move.x === x && move.y === y);
            const isOwnSelected = me === boardView[selectedField.y][selectedField.x].owner;

            // move,unselect, another select ?

            if (isAlreadySelected) {
                // unselect
                this.setState({
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
                    selectedField: {},
                    pieceId: ""
                });
            } else {
                // select new position
                this.setState({
                    selectedField: { x: x, y: y },
                    selectedPiece: boardView[y][x],
                    pieceId: boardView[y][x].symbol //TODO: statt symbol pieceId
                });
            }
        } else {
            if (!isEmptyField) {
                // select new position
                this.setState({
                    selectedField: { x: x, y: y },
                    selectedPiece: boardView[y][x],
                    pieceId: boardView[y][x].symbol //TODO: statt symbol pieceId
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
        const { width, height, boardView, selectedField, me, pieceImagesSmall, isInited, winner, pieceId, selectedPiece, pieceCard, actions, pieceImages, engine, matterBodies } = this.state;


        const draw = (ctx, frameCount) => {
            if (boardView) {
                const squareSize = Config.squareSize;
                const boardTopx = Config.boardTopx;
                const boardTopy = Config.boardTopy;

                ctx.canvas.width = squareSize * (width + 1) + Config.card.width;
                ctx.canvas.height = squareSize * (height + 1);

                //draw card
                if (pieceId !== "") {
                    var cardPicsize = Config.card.width;
                    var grd = ctx.createRadialGradient(squareSize * (width + 1) + cardPicsize / 2, 0 + cardPicsize * 0.625, 2, squareSize * (width + 1) + cardPicsize / 2, 0 + cardPicsize * 0.625, cardPicsize * 0.5);
                    grd.addColorStop(0, "black");
                    if (selectedPiece.owner === me) {
                        grd.addColorStop(1, "rgba(0,100,0,0.1)");
                    } else {
                        grd.addColorStop(1, "rgba(100,0,0,0.1)");
                    }
                    // Fill with gradient
                    ctx.fillStyle = grd;
                    ctx.fillRect(squareSize * (width + 1), + cardPicsize * 0.125, cardPicsize, cardPicsize);

                    ctx.drawImage(pieceCard.drawPieceCard(actions.get(pieceId), pieceImages.get(pieceId), selectedPiece.owner), squareSize * (width + 1), 0);
                }

                //draw board
                for (let i = 0; i < width; i++) {
                    for (let j = 0; j < height; j++) {
                        ctx.fillStyle = ((i + j) % 2 === 0) ? Config.board.color1 : Config.board.color2;
                        let xOffset = boardTopx + j * squareSize;
                        let yOffset = boardTopy + i * squareSize;
                        ctx.fillRect(xOffset, yOffset, squareSize, squareSize);
                    }
                }
                // draw the border around the chessboard
                ctx.strokeStyle = "black";
                ctx.strokeRect(boardTopx, boardTopy, squareSize * width, squareSize * height)


                if (JSON.stringify(selectedField) !== "{}") {
                    // draw moves
                    ctx.globalAlpha = 0.45;
                    ctx.fillStyle = (me === boardView[selectedField.y][selectedField.x].owner) ? "lightgreen" : "red";
                    for (let k = 0; k < selectedPiece.possibleMoves.length; k++) {
                        let xOffset = boardTopx + selectedPiece.possibleMoves[k].x * squareSize;
                        let yOffset = boardTopy + selectedPiece.possibleMoves[k].y * squareSize;

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

                    for (let i = 0; i < width; i++) {
                        for (let j = 0; j < height; j++) {
                            if (boardView[j][i].symbol !== "") {

                                let xOffset = boardTopx + i * squareSize;
                                let yOffset = boardTopy + j * squareSize;
                                let xOffsetPic = boardTopx + (i + Config.board.smallImage.xOffset) * squareSize;
                                let yOffsetPic = boardTopy + (j + Config.board.smallImage.yOffset) * squareSize;

                                if (pieceImagesSmall.length !== 0) {
                                    //  console.log(pieces);


                                    //green or red shadow
                                    var grd = ctx.createRadialGradient(xOffset + squareSize / 2, yOffset + squareSize * 0.6, 2, xOffset + squareSize / 2, yOffset + squareSize * 0.6, squareSize * 0.5);
                                    grd.addColorStop(0, "black");
                                    if (boardView[j][i].owner === me) {
                                        grd.addColorStop(1, "rgba(0,100,0,0.1)");
                                    } else {
                                        grd.addColorStop(1, "rgba(100,0,0,0.1)");
                                    }
                                    // Fill with gradient
                                    ctx.fillStyle = grd;
                                    ctx.fillRect(xOffset, yOffset, squareSize, squareSize);

                                    ctx.drawImage(pieceImagesSmall.get(boardView[j][i].symbol), xOffsetPic, yOffsetPic);
                                }
                                //}


                            }
                        }
                    }
                }
                //animation
                //console.log("active bodies:", matterBodies.length);
                if (matterBodies.length != 0) {
                    RenderFunctions.renderMatterAnimation(ctx, matterBodies, frameCount);
                    matterBodies.forEach(b => {
                        if (b.alpha <= 0) {
                            Composite.remove(engine.world, b.body);
                            matterBodies.splice(matterBodies.indexOf(b), 1);
                        }
                    })
                }



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