package com.blogapi.service.impl;

import com.blogapi.entity.RefreshToken;
import com.blogapi.entity.Role;
import com.blogapi.entity.User;
import com.blogapi.repository.UserRepository;
import com.blogapi.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;

@Service

public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access-expiration}")
    private long accessExpiration;
    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;


    private String generateToken(User user,long expiration){
        // get roles

        List<String>roles = user.getRoles()
                .stream().map(Role::getName)
                .toList();
        // create claims
        Map<String,Object>claims = new HashMap<>();
        claims.put("roles",roles);

        Date now = new Date();
        Date expiry = new Date(System.currentTimeMillis()+expiration);

        // build jwt
        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(getSigningKey())
                .compact();
        // return token
    }

     private Key getSigningKey(){
        byte[] KeySecret = secret.getBytes(StandardCharsets.UTF_8);
         return Keys.hmacShaKeyFor(KeySecret);
    }

    @Override
    public String generateAccessToken(User user) {

        return generateToken(user,accessExpiration);
    }

    @Override
    public String generateRefreshToken(User user) {

        return generateToken(user,refreshExpiration);
    }

   // token varification for the currect user

    public Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith((SecretKey)getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
     @Override
    public String extractEmail(String token){
        return extractClaims(token).getSubject();
    }
    @Override
    public Date extractExpiration(String token){
        return extractClaims(token).getExpiration();
    }
    @Override
    public boolean  isTokenValid (String token,User user){
           boolean isMatch = extractEmail(token).equals(user.getEmail());

           boolean isExpired = !extractExpiration(token).before(new Date());

           return isMatch && isExpired;
    }



}