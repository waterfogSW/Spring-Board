package com.waterfogsw.board.board.dto;

import com.waterfogsw.board.core.board.domain.Board;

public record BoardSearchResponse(
    long id,
    String title,
    String description
) {

  public static BoardSearchResponse from(Board board) {
    return new BoardSearchResponse(board.getId(), board.getTitle(), board.getDescription());
  }

}
