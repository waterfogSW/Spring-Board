package com.waterfogsw.board.common.auth;

import com.waterfogsw.board.core.user.domain.Role;

public record Authentication(
    long userId,
    Role role
) {

  public static Authentication createEmptyAuthentication() {
    return new Authentication(-1L, Role.GUEST);
  }

}
