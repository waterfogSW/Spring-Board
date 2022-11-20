package com.watefogsw.board.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.watefogsw.board.common.property.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  private final JwtProperties properties;

  public String createAccessToken(String payload) {
    return createToken(payload, properties.accessTokenExpirySeconds());
  }

  public String createRefreshToken(String payload) {
    return createToken(payload, properties.refreshTokenExpirySeconds());
  }

  private String createToken(
      String payload,
      long expirySeconds
  ) {
    Claims claims = Jwts.claims()
                        .setSubject(payload);
    Date now = new Date();
    Date validity = new Date(now.getTime() + expirySeconds);

    return Jwts.builder()
               .setClaims(claims)
               .setIssuedAt(now)
               .setExpiration(validity)
               .signWith(SignatureAlgorithm.HS256, properties.clientSecret())
               .compact();
  }

  public String getPayload(String token) {
    try {
      return Jwts.parser()
                 .setSigningKey(properties.clientSecret())
                 .parseClaimsJws(token)
                 .getBody()
                 .getSubject();
    } catch (ExpiredJwtException e) {
      return e.getClaims()
              .getSubject();
    } catch (JwtException e) {
      throw new IllegalArgumentException("Invalid token");
    }
  }

  public boolean validateToken(String token) {
    try {
      return Jwts.parser()
                 .setSigningKey(properties.clientSecret())
                 .parseClaimsJws(token)
                 .getBody()
                 .getExpiration()
                 .after(new Date());

    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

}
