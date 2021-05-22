package com.freechess.server.controller;


import com.freechess.game.pieces.PieceType;
import com.freechess.generators.piecegenerator.PieceTypeGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/")
public class GeneratorController {

    // get gameId data
    @GetMapping("piece/{pieceId}")
    public ResponseEntity<PieceType> getPiece(@PathVariable long pieceId){

        PieceType pieceType = new PieceTypeGenerator().generate(pieceId);
        //System.out.println(pieceType.getSymbol());
        return ResponseEntity.ok(pieceType);
    }

}