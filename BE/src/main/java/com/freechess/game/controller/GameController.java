package com.freechess.game.controller;


import com.freechess.game.core.Board;
import com.freechess.game.core.Position;
import com.freechess.game.security.JwtResponse;
import com.freechess.game.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/")
public class GameController {

    @Autowired
    JwtUtils jwtUtils;


    @GetMapping("newgame")
    public ResponseEntity<JwtResponse> newGame(){
        UUID gameId = UUID.randomUUID();
        UUID playerId = UUID.randomUUID();
        String jwt = jwtUtils.generateJwtToken(playerId,gameId);
        JwtResponse jwtResponse = new JwtResponse(gameId,playerId,jwt);

        return ResponseEntity.ok(jwtResponse);
    }

    // inviteLink
    @GetMapping("joingame/{gameId}")
    public ResponseEntity<JwtResponse> joinGame(@PathVariable UUID gameId){
        //Game game= Server.getGameById(gameId);
        //if(gameId exists){...
        UUID playerId = UUID.randomUUID();
        String jwt = jwtUtils.generateJwtToken(playerId,gameId);
        JwtResponse jwtResponse = new JwtResponse(gameId,playerId,jwt);

        return ResponseEntity.ok(jwtResponse);
    }

    // get gameId data
    @GetMapping("board/{gameId}")
    public ResponseEntity<Board> getBoard(@PathVariable UUID gameId){
        //check header ??
        //Game game= Server.getGameById(gameId);
        //Board board = game.getBoard();
        return ResponseEntity.ok(null);
    }

    // play gameid
    @GetMapping("board/{gameId}")
    public ResponseEntity<String> play(@PathVariable UUID gameId, Position fromPos,Position toPos){
        //check header

        //Game game= Server.getGameById(gameId);
        // Board board = game.getBoard();
        // Player
        return ResponseEntity.ok("");
    }
}
