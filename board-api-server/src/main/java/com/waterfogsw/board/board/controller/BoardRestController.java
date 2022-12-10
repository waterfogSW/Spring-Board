package com.waterfogsw.board.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.waterfogsw.board.board.dto.BoardCreateRequest;
import com.waterfogsw.board.board.dto.BoardGetDetailResponse;
import com.waterfogsw.board.board.dto.BoardSliceRequest;
import com.waterfogsw.board.board.dto.BoardSliceResponse;
import com.waterfogsw.board.board.dto.BoardUpdateRequest;
import com.waterfogsw.board.board.service.BoardCommandService;
import com.waterfogsw.board.board.service.BoardQueryService;
import com.waterfogsw.board.common.auth.Auth;
import com.waterfogsw.board.core.user.domain.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/boards")
public class BoardRestController {

  private final BoardCommandService boardCommandService;
  private final BoardQueryService boardQueryService;

  @Auth(role = Role.USER)
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@RequestBody BoardCreateRequest request) {
    boardCommandService.create(request);
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public BoardGetDetailResponse getDetail(@PathVariable long id) {
    return boardQueryService.getDetail(id);
  }

  @Auth(role = Role.USER)
  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public void update(
      @PathVariable
      long id,
      @RequestBody
      BoardUpdateRequest request
  ) {
    boardCommandService.update(id, request);
  }

  @Auth(role = Role.USER)
  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable long id) {
    boardCommandService.delete(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<BoardSliceResponse> getSliceBoard(BoardSliceRequest request) {
    return boardQueryService.getSliceOfBoard(request);
  }

}
