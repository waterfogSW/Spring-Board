package com.waterfogsw.board.common.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

  private static final String TOKEN_HEADER = "Authorization";
  private final AuthenticationTokenResolver tokenResolver;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {

    try {
      String token = extractTokenFromHeader(request);
      if (tokenResolver.isTokenNotExpired(token)) {
        throw new JwtException("Invalid token exception");
      }
      Authentication authentication = tokenResolver.getAuthentication(token);
      log.info("test={}", authentication.toString());
      AuthenticationContextHolder.setAuthentication(authentication);
    } catch (Exception e) {
      log.debug(e.getMessage());
    }

    doFilter(request, response, filterChain);
    AuthenticationContextHolder.clearContext();
  }

  private String extractTokenFromHeader(HttpServletRequest request) {
    String authorization = request.getHeader(TOKEN_HEADER);
    if (authorization == null) {
      throw new IllegalArgumentException("Token not found");
    }
    try {
      return authorization.split(" ")[1];
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid token format");
    }
  }

}
