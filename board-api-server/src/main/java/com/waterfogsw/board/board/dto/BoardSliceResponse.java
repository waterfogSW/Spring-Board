package com.waterfogsw.board.board.dto;

import com.waterfogsw.board.core.board.domain.Board;
import com.waterfogsw.board.user.dto.UserInfoResponse;

public record BoardSliceResponse(
    long id,
    String title,
    String description,
    UserInfoResponse userInfo
) {

  public static BoardSliceResponse from(Board board) {
    UserInfoResponse userInfoResponse = UserInfoResponse.from(board.getCreator());
    return new BoardSliceResponse(board.getId(), board.getTitle(), board.getDescription(), userInfoResponse);
  }

}
