package br.com.bookmark.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Integer expiration;

    public String generateToken(String username){
        return Jwts
                .builder()
                .setSubject(username)
                .setExpiration(getExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean isTokenValid(String token){
        Claims claims = getClaims(token);
        if(!claims.isEmpty())
            return isCredentialsValid(claims);
        return false;
    }

    private boolean isCredentialsValid(Claims claims){
        String username = claims.getSubject();
        Date expirationDate = claims.getExpiration();
        if(!username.isEmpty() && expirationDate != null && new Date().before(expirationDate))
            return true;
        return false;
    }

    private Date getExpirationDate() {
        return new Date(System.currentTimeMillis() + (expiration * 1000));
    }

    private Claims getClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token){
        return getClaims(token).getSubject();
    }

}
