import React, { Component } from 'react'
import mainService from '../services/main.service';
import Canvas from './canvas.component';
import PieceComponent from './piece.component';

class GameComponent extends Component {
    

    
    constructor(props) {
        super(props)
        this.state = {
            gameId: JSON.parse(localStorage.getItem("auth")).gameId,
            me:JSON.parse(localStorage.getItem("auth")).player,
            inviteLink: "http://localhost:3000/joingame/" + JSON.parse(localStorage.getItem("auth")).gameId,
            
            //game
            player1: {},
            player2: {},
            boardData: {}, // data from server
            boardView: {}, // symbol, playertype
            width:16,
            height:16,
            turn:"P1",
            round:0,

            //selection
            selectedField:{},
            possibleMoves:[],

            //consts
            squareSize: 25,
            boardTopx :25,
            boardTopy : 25,
            updateInterval: 1500
        }
        this.updateGameData();
        this.loadBoard();
        this.selectField = this.selectField.bind(this);
        this.clickOnCanvas = this.clickOnCanvas.bind(this);
        this.drawMethod = this.drawMethod.bind(this);
        this.play = this.play.bind(this);

        const loadTimer = setInterval(() => {
            this.updateGameData();
        }, this.state.updateInterval);
    }

componentDidMount(){

}

    updateGameData() {
        const { gameId,turn} = this.state;
        mainService.getGameData(gameId).then((res) => {
            this.setState({ player1: res.data.player1, player2: res.data.player2,turn:res.data.turn,round:res.data.round });
            
            //when other player made his turn
            if(turn!==res.data.turn){
                
                this.loadBoard();
            }

            //check game end ?
        });
    }

    

    loadBoard(){
        const { gameId } = this.state;
        mainService.getBoard(gameId).then((res) => {
            let bv=res.data.board;
            console.log(bv);
            for (let i = 0; i < bv.length; i++) {
                for (let j = 0; j < bv[0].length; j++) {
                    if(res.data.board[i][j]===null){
                        bv[i][j]={
                            symbol: "",
                            owner: "",
                            possibleMoves: []};
                    } else {
                        bv[i][j]={
                        symbol: bv[i][j].symbol,
                        owner: bv[i][j].owner,
                        possibleMoves: bv[i][j].possibleMoves};
                    }
                }
            }
            this.setState({ boardData:res.data,boardView:bv,width:bv[0].length,height:bv.length});

        });
    }

    selectField(x,y){
        const {boardView,selectedField,me,turn,possibleMoves} =this.state;
        const isPlayerTurn = me === turn;
        const isEmptyField = boardView[y][x].symbol==="";
        const sthSelected = JSON.stringify(selectedField)!=="{}";

        if(sthSelected){
            const isAlreadySelected = selectedField.x===x && selectedField.y===y;
            const isPossibleMove = possibleMoves.some(move => move.x === x && move.y===y);
            const isOwnSelected = me==boardView[selectedField.y][selectedField.x].owner;

            // move,unselect, another select ?

            if(isAlreadySelected){
                // unselect
                this.setState({possibleMoves:[],
                selectedField:{}});
            } else if(isOwnSelected && isPossibleMove && isPlayerTurn){
                // move
                const draw = {fromPos:{x:selectedField.x,y:selectedField.y},toPos:{x:x,y:y}}
                this.play(draw);
            } else if(isEmptyField){
                // unselect
                this.setState({possibleMoves:[],
                selectedField:{}});
            } else {
                // select new position
                this.setState({possibleMoves:boardView[y][x].possibleMoves,
                selectedField:{x:x,y:y}});
            }
        } else {
            if(!isEmptyField){
                // select new position
                this.setState({possibleMoves:boardView[y][x].possibleMoves,
                    selectedField:{x:x,y:y}});
            }
        }
    }

    play(draw){
        const {gameId,turn} = this.state;
        mainService.play(gameId,draw).then((res) => {
            console.log("played",turn);
            const nextTurn = (turn==="P1")?"P2":"P1";
            this.setState({possibleMoves:[],
                selectedField:{},
                turn:nextTurn});
                
                this.updateGameData();
            this.loadBoard();
        });
    }




    clickOnCanvas(event){
        const {squareSize,boardTopy,boardTopx} = this.state;
        const rect = event.target.getBoundingClientRect();
        const x = event.clientX - rect.left-boardTopx;
        const y = event.clientY - rect.top- boardTopy;
        //console.log("x: " + x + " y: " + y);
        this.selectField((x - x % squareSize)/(squareSize),(y - y % squareSize)/(squareSize));
    }

    drawMethod(){
        const {width,height,squareSize,boardView,possibleMoves,boardTopy,boardTopx,selectedField,me} = this.state;
        var ctx = null;

        const draw = (ctx, frameCount) => {

        if(boardView){
    
            ctx.canvas.width=squareSize*(width+1)
            ctx.canvas.height=squareSize*(height+1)

            //draw board
            for(let i=0; i<width; i++) {
              for(let j=0; j<height; j++) {
                ctx.fillStyle = ((i+j)%2==0) ? "#D2B48C":"PeachPuff";
                let xOffset = boardTopx + j*squareSize;
                let yOffset = boardTopy + i*squareSize;
                ctx.fillRect(xOffset, yOffset, squareSize, squareSize);
              }
            }
            if(JSON.stringify(selectedField)!=="{}"){
            // draw moves
            ctx.globalAlpha = 0.45;
            ctx.fillStyle=(me===boardView[selectedField.y][selectedField.x].owner)?"lightgreen":"red";
            for(let k=0; k<possibleMoves.length; k++) {
                let xOffset = boardTopx + possibleMoves[k].x*squareSize;
                let yOffset = boardTopy + possibleMoves[k].y*squareSize;
                
                ctx.fillRect(xOffset, yOffset, squareSize, squareSize);
            }

            //draw selected
            ctx.fillStyle=(me===boardView[selectedField.y][selectedField.x].owner)?"green":"darkred";
            ctx.fillRect(boardTopx + selectedField.x*squareSize, boardTopy + selectedField.y*squareSize, squareSize, squareSize);
            ctx.globalAlpha = 1;
        }

            // draw pieces
            if(boardView[0]){
            ctx.fillStyle = "black";
            ctx.font = "20px Arial";
            for(let i=0; i<width; i++) {
                for(let j=0; j<height; j++) {
                    if(boardView[j][i].symbol!==""){
                        let symbol = this.drawPiece(123,boardView[j][i].owner);
                        let xOffset = boardTopx + (i+0.12)*squareSize;
                        let yOffset = boardTopy + (j+0.8)*squareSize;
                        ctx.fillText(symbol,xOffset,yOffset);
                    }
                }
            }
        }

            // draw the border around the chessboard
            ctx.strokeStyle = "black";
            ctx.strokeRect(boardTopx, boardTopy, squareSize*width, squareSize*height)
    
    }
    }
    return draw;
    }

    drawPiece(pieceCode,player){
        if(player==="P1"){
            return "♖";
        }
        return "♜";
    }

    drawGameText(){
        const {me,turn} = this.state;
        if(me===turn){
            return "Your turn!";
        }
        return "Wait for opponents turn...";
    }

    
    render() {
        const { inviteLink, player1, player2, boardView,selectedField ,turn,me,round} = this.state;
        var piece= "";
        if(boardView[0] && selectedField.x){
            piece = boardView[selectedField.y][selectedField.x];
            //console.log(piece);
        }
        // TODO: aufteilen in GameData und PieceData ?
        return (

            <div>
            {this.drawGameText()}
            <Canvas draw={this.drawMethod()} onClick={this.clickOnCanvas} />
           {/* <div>{piece.symbol} {" "}{piece.owner}</div>*/}
           <div>
           {inviteLink}
           </div>
                <div>
                Player1: {player1 && player1.name} {player2 && <>Player2: {player2.name}</>} {" round:"}{round}
                </div>
            </div>
        )
    }
}

export default GameComponent