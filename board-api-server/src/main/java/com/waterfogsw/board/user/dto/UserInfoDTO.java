package com.waterfogsw.board.user.dto;

import com.waterfogsw.board.core.user.domain.User;

public record UserInfoDTO(
    long id,
    String name,
    String imageUrl
) {

  public static UserInfoDTO from(User user) {
    return new UserInfoDTO(user.getId(), user.getName(), user.getImageUrl());
  }

}
