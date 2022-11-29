package com.waterfogsw.board.auth.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.waterfogsw.board.auth.common.property.JwtProperties;
import com.waterfogsw.board.core.user.domain.Role;
import com.waterfogsw.board.core.user.domain.User;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  private final JwtProperties properties;

  private static final String ROLE_CLAIM_NAME = "role";

  public String createAccessToken(User user) {
    Long userId = user.getId();
    Role userRole = user.getRole();

    long expirySeconds = properties.accessTokenExpirySeconds();
    Date now = new Date();
    Date validity = new Date(now.getTime() + expirySeconds);

    return Jwts.builder()
               .claim(ROLE_CLAIM_NAME, userRole.name())
               .setSubject(userId.toString())
               .setIssuedAt(now)
               .setExpiration(validity)
               .signWith(SignatureAlgorithm.HS256, properties.clientSecret())
               .compact();

  }

  public String createRefreshToken(String payload) {
    long expirySeconds = properties.refreshTokenExpirySeconds();

    Date now = new Date();
    Date validity = new Date(now.getTime() + expirySeconds);

    return Jwts.builder()
               .setSubject(payload)
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

}
