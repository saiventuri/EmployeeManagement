package com.example.employeemanagement.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtUtil implements Serializable {

    private static final String TOKEN_ISSUER = "SAI_PRAVEEN";
    private static final String TOKEN_ID = "A5266";
    @Value("${jwt.secret}")
    private String secret;

    //1. Generate Token
    public String generateToken(String subject) {
        return Jwts.builder()
                .setId(TOKEN_ID)
                .setSubject(subject)
                .setIssuer(TOKEN_ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)))
                .signWith(SignatureAlgorithm.HS256,
                        Base64.getEncoder().encode(this.secret.getBytes()))
                .compact();
    }


    // 2. Read Claims (token)
    public Claims getTokenClaims(String token) {
        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encode(this.secret.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }

    //3. Read expiry date for token
    public Date getTokenExpirationDate(String token) {
        return getTokenClaims(token).getExpiration();
    }

    //4. Read username/subject
    public String getTokenUserName(String token) {
        return getTokenClaims(token).getSubject();
    }

    //5. validate expiration date of token
    public boolean isTokenExpired(String token) {
        return getTokenExpirationDate(token).before(new Date());
    }

    //6. Validate the token from database user name.
    public boolean validateToken(String token, String userName) {
        return (userName.equals(getTokenUserName(token)) && !isTokenExpired(token));
    }
}
