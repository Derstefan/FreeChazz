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
            turn:"Player1",// TODO: sth better than String switching?

            //selection
            selectedField:{},
            possibleMoves:[],

            //consts
            squareSize: 25,
            boardTopx :25,
            boardTopy : 25
        }
        this.loadGameData();
        this.loadBoard();
        this.selectField = this.selectField.bind(this);
        this.clickOnCanvas = this.clickOnCanvas.bind(this);
        this.drawMethod = this.drawMethod.bind(this);
    }

componentDidMount(){

}

    loadGameData() {
        const { gameId } = this.state;
        mainService.getGameData(gameId).then((res) => {
            this.setState({ player1: res.data.player1, player2: res.data.player2 });
        });
    }

    

    loadBoard(){
        const { gameId } = this.state;
        mainService.getBoard(gameId).then((res) => {
            let bv=res.data.board;
            
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
        const {boardView,selectedField} =this.state;

        //if(playerturn)
        //if(own possible move)
        //if(figure)



        if(selectedField.x===x && selectedField.y===y){
            // unselect then --> (later maybe not, reflexive abilities)
            this.setState({possibleMoves:[],
                selectedField:{}});
        } else {
            if(boardView[y][x].symbol===""){
                // dont select/ unselect
                this.setState({possibleMoves:[],
                    selectedField:{}});                
            } else {
                //select field and show possible move of piece
                this.setState({possibleMoves:boardView[y][x].possibleMoves,
                    selectedField:{x:x,y:y}});
                }
        }

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
                ctx.fillStyle = ((i+j)%2==0) ? "lightgray":"gray";
                let xOffset = boardTopx + j*squareSize;
                let yOffset = boardTopy + i*squareSize;
                ctx.fillRect(xOffset, yOffset, squareSize, squareSize);
//                ctx.fillRect(Math.sin(i*0.2)*xOffset, Math.sin(j*frameCount*0.001)*yOffset, squareSize, squareSize);
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
                        let symbol = (boardView[j][i].owner==="Player1")?"♖":"♜";
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




    
    render() {
        const { inviteLink, player1, player2, boardView,selectedField } = this.state;
        var piece= "";
        if(boardView[0] && selectedField.x){
            piece = boardView[selectedField.y][selectedField.x];
            //console.log(piece);
        }

        // TODO: aufteilen in GameData und PieceData ?

        return (
            <div>
                {inviteLink}
                <div>
                here is the Game: Player1: {player1 && player1.name} {player2 && <>Player2: {player2.name}</>}
                </div>
            <Canvas draw={this.drawMethod()} onClick={this.clickOnCanvas} />
           {/* <div>{piece.symbol} {" "}{piece.owner}</div>*/}
            </div>
        )
    }
}

export default GameComponent