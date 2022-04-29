package com.freechess.server.controller;


import com.freechess.game.pieces.IPieceType;
import com.freechess.game.pieces.impl.PieceType;
import com.freechess.generators.piece.PieceTypeGeneratorParam;
import com.freechess.generators.piece.impl.PieceTypeGeneratorPool;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000","http://192.168.0.136:3000"})
@RestController
@RequestMapping("api/")
public class GeneratorController {

    // generate piece
    @GetMapping("generatepiece/{seed}")
    public ResponseEntity<IPieceType> getPiece(@PathVariable String seed){

        IPieceType pieceType = new PieceTypeGeneratorPool().generate(new PieceTypeGeneratorParam(seed));
        return ResponseEntity.ok(pieceType);
    }

    @GetMapping("findpiece/{pieceSerial}")
    public ResponseEntity<IPieceType> getPieceBySerial(@PathVariable String pieceSerial){
        try{
            IPieceType pieceType = PieceType.getInstance(pieceSerial);
            return ResponseEntity.ok(pieceType);
        } catch(Exception e){

        }
        return (ResponseEntity<IPieceType>) ResponseEntity.badRequest();

    }
}
