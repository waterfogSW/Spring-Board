package com.waterfogsw.board.board.dto;

import com.waterfogsw.board.core.board.domain.Board;
import com.waterfogsw.board.user.dto.UserInfoDTO;

import lombok.Builder;

@Builder
public record BoardGetDetailResponse(
    long id,
    String title,
    String description,
    UserInfoDTO creatorInfo,
    String createdAt
) {

  public static BoardGetDetailResponse from(Board board) {
    UserInfoDTO creatorInfo = UserInfoDTO.from(board.getCreator());

    return BoardGetDetailResponse.builder()
                                 .id(board.getId())
                                 .title(board.getTitle())
                                 .description(board.getDescription())
                                 .creatorInfo(creatorInfo)
                                 .createdAt(
                                     board.getCreatedAt()
                                          .toLocalDate()
                                          .toString()
                                 )
                                 .build();

  }

}
