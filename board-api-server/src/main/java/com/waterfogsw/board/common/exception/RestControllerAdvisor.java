package com.waterfogsw.board.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestControllerAdvisor {

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(AuthenticationException.class)
  public void forbidden(Exception e) {
    log.debug("exception={}, message={}", e.getClass(), e.getMessage());
  }

}
