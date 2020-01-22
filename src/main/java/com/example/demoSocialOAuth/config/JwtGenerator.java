package com.example.demoSocialOAuth.config;

import com.example.demoSocialOAuth.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {

    @Value("${jwt.secret}")
    private String secret;


    public UserEntity parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            UserEntity u = new UserEntity();
            u.setUserName(body.getSubject());
            u.setUserId(((String) body.get("userId")));


            return u;

        } catch (JwtException | ClassCastException e) {
            e.printStackTrace();
            return  null;
        }
    }


    public String generateToken(UserEntity u) {
        Claims claims = Jwts.claims().setSubject(u.getUserName());
        claims.put("userId", u.getUserId() + "");

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}


