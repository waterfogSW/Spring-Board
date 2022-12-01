package com.waterfogsw.board.board.dto;

import com.waterfogsw.board.core.board.domain.Board;

public record BoardPageResponse(
    long id,
    String title,
    String description
) {

  public static BoardPageResponse from(Board board) {
    return new BoardPageResponse(board.getId(), board.getTitle(), board.getDescription());
  }

}
