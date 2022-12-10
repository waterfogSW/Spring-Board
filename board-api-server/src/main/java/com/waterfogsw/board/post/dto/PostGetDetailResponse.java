package com.waterfogsw.board.post.dto;

import com.waterfogsw.board.core.post.entity.Post;
import com.waterfogsw.board.user.dto.UserInfoDTO;

import lombok.Builder;

@Builder
public record PostGetDetailResponse(
    long id,
    String title,
    String content,
    UserInfoDTO writer,
    String createdAt,
    String updatedAt
) {

  public static PostGetDetailResponse from(Post post) {
    UserInfoDTO writer = UserInfoDTO.from(post.getWriter());
    return PostGetDetailResponse.builder()
                                .id(post.getId())
                                .title(post.getTitle())
                                .content(post.getContent())
                                .writer(writer)
                                .createdAt(
                                    post.getCreatedAt()
                                        .toString()
                                )
                                .updatedAt(
                                    post.getUpdatedAt()
                                        .toString()
                                )
                                .build();
  }

}
