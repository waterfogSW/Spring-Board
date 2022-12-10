package com.waterfogsw.board.board.dto;

import com.waterfogsw.board.core.board.domain.Board;
import com.waterfogsw.board.user.dto.UserInfoDTO;

public record BoardSliceResponse(
    long id,
    String title,
    String description,
    UserInfoDTO userInfo
) {

  public static BoardSliceResponse from(Board board) {
    UserInfoDTO userInfoDTO = UserInfoDTO.from(board.getCreator());
    return new BoardSliceResponse(board.getId(), board.getTitle(), board.getDescription(), userInfoDTO);
  }

}
