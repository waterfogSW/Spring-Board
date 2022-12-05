package com.waterfogsw.board.post.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.waterfogsw.board.common.auth.Auth;
import com.waterfogsw.board.core.user.domain.Role;
import com.waterfogsw.board.post.dto.PostCreateRequest;
import com.waterfogsw.board.post.dto.PostUpdateRequest;
import com.waterfogsw.board.post.service.PostCommandService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts")
public class PostRestController {

  private final PostCommandService postCommandService;

  @Auth(role = Role.USER)
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@RequestBody PostCreateRequest request) {
    postCommandService.create(request);
  }

  @Auth(role = Role.USER)
  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public void update(
      @PathVariable
      long id,
      @RequestBody
      PostUpdateRequest request
  ) {
    postCommandService.update(id, request);
  }

}
