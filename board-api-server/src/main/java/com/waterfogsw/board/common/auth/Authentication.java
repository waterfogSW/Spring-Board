package com.waterfogsw.board.common.auth;

import com.waterfogsw.board.core.user.domain.Role;
import com.waterfogsw.board.core.user.domain.User;

public record Authentication(
    long userId,
    Role role
) {

  public static Authentication from(User user) {
    return new Authentication(user.getId(), user.getRole());
  }

}
