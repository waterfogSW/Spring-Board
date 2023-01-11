package com.waterfogsw.board.user.dto;

import com.waterfogsw.board.core.user.domain.User;

public record UserInfo(
    long id,
    String name,
    String imageUrl
) {

  public static UserInfo from(User user) {
    return new UserInfo(user.getId(), user.getName(), user.getImageUrl());
  }

}
