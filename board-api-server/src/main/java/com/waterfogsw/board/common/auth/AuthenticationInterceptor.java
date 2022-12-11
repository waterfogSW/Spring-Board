package com.waterfogsw.board.common.auth;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.waterfogsw.board.core.common.exception.AuthenticationException;
import com.waterfogsw.board.core.user.domain.Role;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

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

    Authentication authentication = AuthenticationContextHolder.getAuthentication();
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

}
