package com.waterfogsw.board.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waterfogsw.board.board.dto.BoardCreateRequest;
import com.waterfogsw.board.board.dto.BoardUpdateRequest;
import com.waterfogsw.board.core.common.exception.AuthenticationException;
import com.waterfogsw.board.core.common.exception.NotFoundException;
import com.waterfogsw.board.core.board.domain.Board;
import com.waterfogsw.board.core.board.repository.BoardRepository;
import com.waterfogsw.board.core.user.domain.User;
import com.waterfogsw.board.user.service.UserService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardCommandService {

  private final BoardRepository boardRepository;
  private final UserService userService;

  @Transactional
  public void create(@NonNull BoardCreateRequest request) {
    User user = userService.getCurrentUser();
    Board board = Board.builder()
                       .title(request.title())
                       .description(request.description())
                       .owner(user)
                       .build();

    boardRepository.save(board);
  }

  @Transactional
  public void update(
      long id,
      @NonNull
      BoardUpdateRequest request
  ) {
    User user = userService.getCurrentUser();
    Board board = boardRepository.findById(id)
                                 .orElseThrow(() -> new NotFoundException("Board not found"));

    checkBoardOwner(user, board);
    board.update(request.title(), request.description());
  }

  @Transactional
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
    if (!user.equals(board.getOwner())) {
      throw new AuthenticationException();
    }
  }

}
