package com.datamelt.artikel.app.web.util;

import com.datamelt.artikel.app.web.WebApplication;
import com.datamelt.artikel.model.User;
import com.datamelt.artikel.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Token
{
    public static String generateToken(User user, long tokenExpiresMinutes)
    {
       Instant now = Instant.now();

       return Jwts.builder()
               .setSubject(user.getName())
               .claim("role", user.getRole())
               .claim("name", user.getName())
               .claim("id", user.getId())
               .setIssuer(Constants.USERTOKEN_ISSUER)
               .setIssuedAt(Date.from(now))
               .setExpiration(Date.from(now.plus(tokenExpiresMinutes, ChronoUnit.MINUTES)))
               .signWith(Keys.hmacShaKeyFor(WebApplication.getSecretKey().getEncoded()))
               .compact();
    }

    public static Jws<Claims> parseToken(String token)
    {
        return Jwts.parser()
            .requireIssuer(Constants.USERTOKEN_ISSUER)
            .setSigningKey(WebApplication.getSecretKey().getEncoded())
            .parseClaimsJws(token);
    }
}
