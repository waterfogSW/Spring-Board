package com.waterfogsw.board.board.dto;

import com.waterfogsw.board.core.board.domain.Board;
import com.waterfogsw.board.core.user.domain.User;

import lombok.Builder;

@Builder
public record BoardGetDetailResponse(
    long id,
    String title,
    String description,
    UserInfo creatorInfo,
    String createdAt
) {

  public static BoardGetDetailResponse from(Board board) {
    UserInfo creatorInfo = UserInfo.from(board.getCreator());

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

  @Builder
  public record UserInfo(
      String name,
      String imageUrl
  ) {

    public static UserInfo from(User user) {
      return UserInfo.builder()
                     .name(user.getName())
                     .imageUrl(user.getImageUrl())
                     .build();
    }

  }

}
