package com.waterfogsw.board.common.auth;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.waterfogsw.board.common.property.JwtProperties;
import com.waterfogsw.board.core.user.domain.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationTokenResolver {

  private final JwtProperties properties;
  private static final String ROLE_CLAIM_NAME = "role";

  public Authentication getAuthentication(String token) {
    Claims claims = getClaims(token);

    String subject = claims.getSubject();
    long userId = Long.parseLong(subject);

    String roleStr = claims.get(ROLE_CLAIM_NAME)
                           .toString();
    Role role = Role.valueOf(roleStr);

    return new Authentication(userId, role);
  }

  public boolean isTokenNotExpired(String token) {
    return getClaims(token).getExpiration()
                           .before(new Date());
  }

  private Claims getClaims(String token) {
    return Jwts.parser()
               .setSigningKey(properties.clientSecret())
               .parseClaimsJws(token)
               .getBody();
  }

}
