package com.waterfogsw.board.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.waterfogsw.board.board.dto.BoardCreateRequest;
import com.waterfogsw.board.board.service.BoardService;
import com.waterfogsw.board.common.auth.Auth;
import com.waterfogsw.board.core.user.domain.Role;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/boards")
public class BoardRestController {

  private final BoardService boardService;

  @Auth(role = Role.USER)
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@RequestBody BoardCreateRequest request) {
    boardService.create(request);
  }

}
