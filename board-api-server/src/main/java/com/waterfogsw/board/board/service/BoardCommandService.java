package com.waterfogsw.board.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waterfogsw.board.board.dto.BoardCreateRequest;
import com.waterfogsw.board.board.dto.BoardUpdateRequest;
import com.waterfogsw.board.common.exception.AuthenticationException;
import com.waterfogsw.board.common.exception.NotFoundException;
import com.waterfogsw.board.core.board.domain.Board;
import com.waterfogsw.board.core.board.repository.BoardRepository;
import com.waterfogsw.board.core.user.domain.User;
import com.waterfogsw.board.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardCommandService {

  private final BoardRepository boardRepository;
  private final UserService userService;

  @Transactional
  public void create(BoardCreateRequest request) {
    User user = userService.getCurrentUser();

    Board board = Board.builder()
                       .title(request.title())
                       .description(request.description())
                       .creator(user)
                       .build();

    boardRepository.save(board);
  }

  @Transactional
  public void update(
      long id,
      BoardUpdateRequest request
  ) {
    User user = userService.getCurrentUser();
    Board board = boardRepository.findById(id)
                                 .orElseThrow(() -> new NotFoundException("Board not found"));

    checkBoardOwner(user, board);
    board.update(request.title(), request.description());
  }

  public void delete(long id) {
    User user = userService.getCurrentUser();
    Board board = boardRepository.findById(id)
                                 .orElseThrow(() -> new NotFoundException("Board not found"));

    checkBoardOwner(user, board);
    boardRepository.delete(board);
  }

  private void checkBoardOwner(
      User user,
      Board board
  ) {
    if (!user.isMyBoard(board)) {
      throw new AuthenticationException();
    }
  }

}
