package com.freechess.game.controller;


import com.freechess.game.DTO.GameData;
import com.freechess.game.DTO.JwtResponse;
import com.freechess.game.DTO.ServerData;
import com.freechess.game.core.Board;
import com.freechess.game.core.Game;
import com.freechess.game.core.Player;
import com.freechess.game.core.Position;
import com.freechess.game.server.JwtUtils;
import com.freechess.game.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/")
public class ServerController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private Server server;

    // get gameId data
    @GetMapping("serverdata")
    public ResponseEntity<ServerData> getServerData(){
        return ResponseEntity.ok(new ServerData(server));
    }
}
