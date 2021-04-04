package com.freechess.game.controller;


import com.freechess.game.DTO.Draw;
import com.freechess.game.DTO.GameData;
import com.freechess.game.core.*;
import com.freechess.game.server.Server;
import com.freechess.game.DTO.JwtResponse;
import com.freechess.game.server.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
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
        Player player1 = new Player(name, EPlayer.Player1);
        game.join(player1);

        UUID playerId = player1.getPlayerId();
        UUID gameId = game.getGameId();

        //generate Security Token
        String jwt = jwtUtils.generateJwtToken(playerId,gameId);
        JwtResponse jwtResponse = new JwtResponse(gameId,playerId,jwt,EPlayer.Player1);

        return ResponseEntity.ok(jwtResponse);
    }

    @GetMapping("joingame/{gameId}/{name}")
    public ResponseEntity<JwtResponse> joinGame(@PathVariable UUID gameId,@PathVariable String name){
        Game game = server.getGameById(gameId);
        if(game!=null){
            Player player2 = new Player(name, EPlayer.Player2);
            boolean joined = game.join(player2);
            if(!joined){
                //already 2 player in this game
                return ResponseEntity.badRequest().body(null);
            }

            UUID playerId = player2.getPlayerId();
            String jwt = jwtUtils.generateJwtToken(playerId,gameId);
            JwtResponse jwtResponse = new JwtResponse(gameId,playerId,jwt,EPlayer.Player2);
            return ResponseEntity.ok(jwtResponse);
        }
        return ResponseEntity.notFound().build();
    }

    // just gets some metaData of the game, maybe just for tests
    @GetMapping("gamedata/{gameId}")
    public ResponseEntity<GameData> getGameData(@PathVariable UUID gameId){
        Game game = server.getGameById(gameId);
        if(game!=null){

            return ResponseEntity.ok(new GameData(game));
        }
        return ResponseEntity.notFound().build();
    }

    // get gameId data
    @GetMapping("board/{gameId}")
    public ResponseEntity<Board> getBoard(@RequestHeader HttpHeaders headers, @PathVariable UUID gameId){
        if(validate(headers)){
            Board board = server.getGameById(gameId).getBoard();
           // System.out.println(server.getGameById(gameId).getGameId());
            //System.out.println(gameId);

            return ResponseEntity.ok(board);
        }
        return ResponseEntity.status(401).body(null);
    }


    // play gameid
    @PostMapping("play/{gameId}")
    public ResponseEntity<String> play(@RequestHeader HttpHeaders headers, @PathVariable UUID gameId, Draw draw){
        if(validate(headers)) {
            Game game= server.getGameById(gameId);
            game.play(draw.getFromPos(), draw.getToPos());
            return ResponseEntity.ok("");
        }
        return ResponseEntity.status(401).body(null);
    }


    private boolean validate(HttpHeaders headers){
        //System.out.println(  "->" +parseJwtToken(headers.get("Authorization").toString()));
        return jwtUtils.validateJwtToken(parseJwtToken(headers.get("Authorization").toString()));
    }

    private String parseJwtToken(String value){
        return value.substring(8,value.length()-1);
    }
}
