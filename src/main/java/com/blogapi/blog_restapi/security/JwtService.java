package com.blogapi.blog_restapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtService {
    /**
     * The secret key. This should be in a file not in the code. This is just for demo purposes. The secret key should be at least 256 bits long.
     */
    public static final String SECRET = "413F4428472B4B6250655368566D597133743677397A244326452948404D6351";

    /**
     * The validity of the token in milliseconds. This is just for demo purposes. The validity should be at least 15 minutes.
     * 15 minutes = 900000 milliseconds
     * 1 hour = 3600000 milliseconds
     * 1 day = 86400000 milliseconds
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken(String email) {
        Map<String, Objects> claim = new HashMap<>();
        return createToken(claim, email);
    }

    private String createToken(Map<String, Objects> claim, String email) {

        return Jwts.builder()
                .setClaims(claim)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
