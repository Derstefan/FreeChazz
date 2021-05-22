package com.freechess.server.security;


import java.util.Date;
import java.util.UUID;


import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


//import io.jsonwebtoken.*;


@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);


    @Value("${freechess.app.jwtSecret}")
    private String jwtSecret;


   // @Value("${freechess.app.jwtExpirationMs}")
    private int jwtExpirationMs = 86400000;


    public String generateJwtToken(UUID playerId,UUID gameId) {

        return Jwts.builder()
             //   .setId(playerId.toString())
                .setSubject((gameId.toString()))
                .setIssuedAt(new Date())
                //.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))//-> timeout
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public String getPlayerIdFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getId();
    }

    public String getGameIdFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}