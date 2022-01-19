package com.freechess.server.controller;


import com.freechess.game.pieces.IPieceType;
import com.freechess.game.pieces.impl.PieceType;
import com.freechess.generators.piece.impl.PieceTypeGeneratorPool;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000","http://192.168.0.136:3000"})
@RestController
@RequestMapping("api/")
public class GeneratorController {

    // generate piece
    @GetMapping("piece/{pieceId}")
    public ResponseEntity<IPieceType> getPiece(@PathVariable long pieceId){

        IPieceType pieceType = new PieceTypeGeneratorPool().generate(pieceId);
        return ResponseEntity.ok(pieceType);
    }

}
