package com.freechess.server.controller;


import com.freechess.server.DTO.ServerData;
import com.freechess.server.security.JwtUtils;
import com.freechess.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000","http://192.168.178.136:3000"})
@RestController
@RequestMapping("api/")
public class ServerController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private Server server;

    // get some data from server TODO: Maybe better metrics
    @GetMapping("serverdata")
    public ResponseEntity<ServerData> getServerData(){
        return ResponseEntity.ok(new ServerData(server));
    }
}
