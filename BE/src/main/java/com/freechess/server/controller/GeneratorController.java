package com.freechess.server.controller;


import com.freechess.game.pieces.PieceType;
import com.freechess.generators.piece.impl.PieceTypeGeneratorPool;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000","http://192.168.178.136:3000"})
@RestController
@RequestMapping("api/")
public class GeneratorController {

    // generate piece
    @GetMapping("piece/{pieceId}")
    public ResponseEntity<PieceType> getPiece(@PathVariable long pieceId){

        PieceType pieceType = new PieceTypeGeneratorPool().generate(pieceId);
        return ResponseEntity.ok(pieceType);
    }

}
