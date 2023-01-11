package com.waterfogsw.board.board.dto;

import com.waterfogsw.board.core.board.domain.Board;
import com.waterfogsw.board.user.dto.UserInfo;

public record BoardSliceResponse(
    long id,
    String title,
    String description,
    UserInfo userInfo
) {

  public static BoardSliceResponse from(Board board) {
    UserInfo userInfo = UserInfo.from(board.getOwner());
    return new BoardSliceResponse(board.getId(), board.getTitle(), board.getDescription(), userInfo);
  }

}
