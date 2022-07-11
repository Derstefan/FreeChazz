package com.freechess.server.controller;


import com.freechess.game.pieces.Piece;
import com.freechess.game.player.EPlayer;
import com.freechess.game.Game;
import com.freechess.game.player.Player;
import com.freechess.generators.piece.PieceTypeGeneratorParam;
import com.freechess.generators.piece.impl.PieceTypeGenerator;
import com.freechess.server.DTO.DrawData;
import com.freechess.server.DTO.GameData;
import com.freechess.game.board.Board;
import com.freechess.server.DTO.GameParams;
import com.freechess.server.DTO.JwtResponse;
import com.freechess.server.security.JwtUtils;
import com.freechess.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = {"http://localhost:3000","http://192.168.0.135:3000"})
@RestController
@RequestMapping("api/")
public class GameController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private Server server;

    @GetMapping("newgame/{name}")
    public ResponseEntity<JwtResponse> newGame(@PathVariable String name){
        Game game = server.createGame();
        Player player1 = new Player(name, EPlayer.P1);
        game.join(player1);

        UUID playerId = player1.getPlayerId();
        UUID gameId = game.getGameId();

        //generate Security Token
        String jwt = jwtUtils.generateJwtToken(playerId,gameId);
        JwtResponse jwtResponse = new JwtResponse(gameId,playerId,jwt,EPlayer.P1);

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("newgame/{name}")
    public ResponseEntity<JwtResponse> newGame(@PathVariable String name,@RequestBody GameParams params){
        Game game = server.createGame(params);
        Player player1 = new Player(name, EPlayer.P1);
        game.join(player1);

        UUID playerId = player1.getPlayerId();
        UUID gameId = game.getGameId();

        //generate Security Token
        String jwt = jwtUtils.generateJwtToken(playerId,gameId);
        JwtResponse jwtResponse = new JwtResponse(gameId,playerId,jwt,EPlayer.P1);

        return ResponseEntity.ok(jwtResponse);
    }

    @GetMapping("joingame/{gameId}/{name}")
    public ResponseEntity<JwtResponse> joinGame(@PathVariable UUID gameId,@PathVariable String name){
        Game game = server.getGameById(gameId);
        if(game!=null){
            Player player2 = new Player(name, EPlayer.P2);
            boolean joined = game.join(player2);
            if(!joined){
                //already 2 player in this game
                return ResponseEntity.badRequest().body(null);
            }

            UUID playerId = player2.getPlayerId();
            String jwt = jwtUtils.generateJwtToken(playerId,gameId);
            JwtResponse jwtResponse = new JwtResponse(gameId,playerId,jwt,EPlayer.P2);
            return ResponseEntity.ok(jwtResponse);
        }
        return ResponseEntity.notFound().build();
    }

    // get some data for update check
    @GetMapping("gamedata/{gameId}")
    public ResponseEntity<GameData> getGameData(@PathVariable UUID gameId){
        Game game = server.getGameById(gameId);
        if(game!=null){

            return ResponseEntity.ok(new GameData(game));
        }
        return ResponseEntity.notFound().build();
    }

    // get full board
    @GetMapping("board/{gameId}")
    public ResponseEntity<Board> getBoard(@RequestHeader HttpHeaders headers, @PathVariable UUID gameId){
        if(validate(headers)){
            Game game = server.getGameById(gameId);
            if(game!=null){
                Board board = game.getBoard();
                //System.out.println(board.getBoard()[0][7].getId());
                return ResponseEntity.ok(board);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.status(401).body(null);
    }

    @GetMapping("pieceTest")
    public ResponseEntity<Piece> getPiece(){
        PieceTypeGenerator gen = new PieceTypeGenerator();
        return ResponseEntity.status(401).body(new Piece(EPlayer.P1,gen.generate(new PieceTypeGeneratorParam(1,12312))));
    }

    @GetMapping("BoardTest")
    public ResponseEntity<Board> getBoard(){
        Game game = server.createGame();
        Player player1 = new Player("blabla", EPlayer.P1);
        game.join(player1);

        UUID playerId = player1.getPlayerId();
        UUID gameId = game.getGameId();

        return ResponseEntity.ok(game.getBoard());
    }


    // play gameid
    @PostMapping("play/{gameId}")
    public ResponseEntity<String> play(@RequestHeader HttpHeaders headers, @PathVariable UUID gameId,@RequestBody DrawData draw){
        if(validate(headers)) {
            Game game= server.getGameById(gameId);
            game.play(draw.getFromPos(), draw.getToPos());
            return ResponseEntity.ok("");
        }
        return ResponseEntity.status(401).body(null);
    }


    private boolean validate(HttpHeaders headers){
        return jwtUtils.validateJwtToken(parseJwtToken(headers.get("Authorization").toString()));
    }

    private String parseJwtToken(String value){
        return value.substring(8,value.length()-1);
    }
}
