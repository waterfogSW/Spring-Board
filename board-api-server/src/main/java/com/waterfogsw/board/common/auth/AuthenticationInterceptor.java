package com.waterfogsw.board.common.auth;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.waterfogsw.board.common.exception.AuthenticationException;
import com.waterfogsw.board.core.user.domain.Role;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

  private final AuthenticationTokenResolver authenticationTokenResolver;

  private static final String TOKEN_HEADER = "Authorization";

  @Override
  public boolean preHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler
  ) {
    if (!(handler instanceof HandlerMethod handlerMethod)) {
      return true;
    }

    if (isNeedsAuthorization(handlerMethod)) {
      return true;
    }

    String token = extractTokenFromHeader(request);

    if (!authenticationTokenResolver.validateToken(token)) {
      throw new AuthenticationException();
    }

    Authentication authentication = authenticationTokenResolver.getAuthentication(token);
    Role clientRole = authentication.role();
    Role handlerRole = getMethodRole(handlerMethod);

    if (!clientRole.hasAuthority(handlerRole)) {
      throw new AuthenticationException();
    }

    AuthenticationContextHolder.setAuthentication(authentication);

    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView
  ) {
    AuthenticationContextHolder.clearContext();
  }

  private Role getMethodRole(HandlerMethod handlerMethod) {
    return Objects.requireNonNull(handlerMethod.getMethodAnnotation(Auth.class))
                  .role();
  }

  private boolean isNeedsAuthorization(HandlerMethod handlerMethod) {
    return handlerMethod.getMethodAnnotation(Auth.class) == null;
  }

  private String extractTokenFromHeader(HttpServletRequest request) {
    String authorization = request.getHeader(TOKEN_HEADER);
    if (authorization == null) {
      throw new AuthenticationException();
    }

    try {
      return authorization.split(" ")[1];

    } catch (Exception e) {
      throw new AuthenticationException();
    }
  }

}
