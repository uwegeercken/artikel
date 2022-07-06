package com.datamelt.artikel.app.web.util;

import com.datamelt.artikel.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

public class Token
{
    private static String ISSUER = "artikel application";
    private static byte[] secret = Base64.getDecoder().decode("5YpQ9/akAv0NvvSfmBG6bDpSqLMmynAAixpP6cueJ/M=");

    public static String generateToken(User user, long tokenExpiresMinutes)
    {
       Instant now = Instant.now();

       return Jwts.builder()
               .setSubject(user.getName())
               .setIssuer(ISSUER)
               .setIssuedAt(Date.from(now))
               .setExpiration(Date.from(now.plus(tokenExpiresMinutes, ChronoUnit.MINUTES)))
               .signWith(Keys.hmacShaKeyFor(secret))
               .compact();
    }

    public static Jws<Claims> parseToken(String token) throws Exception
    {
        return Jwts.parser()
            .requireIssuer(ISSUER)
            .setSigningKey(secret)
            .parseClaimsJws(token);
    }
}
