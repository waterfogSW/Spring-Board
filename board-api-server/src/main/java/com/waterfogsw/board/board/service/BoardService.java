package com.waterfogsw.board.board.service;

import org.springframework.stereotype.Service;

import com.waterfogsw.board.board.dto.BoardCreateRequest;
import com.waterfogsw.board.board.repository.BoardRepository;
import com.waterfogsw.board.core.board.domain.Board;
import com.waterfogsw.board.core.user.domain.User;
import com.waterfogsw.board.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;
  private final UserService userService;

  public void create(BoardCreateRequest request) {
    User user = userService.getCurrentUser();

    Board board = Board.builder()
                       .title(request.title())
                       .description(request.description())
                       .creator(user)
                       .build();

    boardRepository.save(board);
  }

}
