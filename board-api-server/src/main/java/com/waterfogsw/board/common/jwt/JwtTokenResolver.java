package com.waterfogsw.board.common.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.waterfogsw.board.common.property.JwtProperties;
import com.waterfogsw.board.core.user.domain.Role;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenResolver {

  private final JwtProperties properties;
  private static final String ROLE_CLAIM_NAME = "role";

  public long getUserId(String token) {
    String subject = Jwts.parser()
                         .setSigningKey(properties.clientSecret())
                         .parseClaimsJws(token)
                         .getBody()
                         .getSubject();

    return Long.parseLong(subject);
  }

  public Role getRole(String token) {
    String roleStr = Jwts.parser()
                         .setSigningKey(properties.clientSecret())
                         .parseClaimsJws(token)
                         .getBody()
                         .get(ROLE_CLAIM_NAME)
                         .toString();

    return Role.valueOf(roleStr);
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
