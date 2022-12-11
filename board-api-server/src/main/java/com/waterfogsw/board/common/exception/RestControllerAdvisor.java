package com.waterfogsw.board.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.waterfogsw.board.core.common.exception.AuthenticationException;
import com.waterfogsw.board.core.common.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestControllerAdvisor {

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(AuthenticationException.class)
  public void forbidden(AuthenticationException e) {
    log.debug("exception={}, message={}", e.getClass(), e.getMessage());
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public void notfound(NotFoundException e) {
    log.debug("exception={}, message={}", e.getClass(), e.getMessage());
  }


}
