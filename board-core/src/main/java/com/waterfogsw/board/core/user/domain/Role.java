package com.waterfogsw.board.core.user.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

  ADMIN(1), USER(2), GUEST(3);

  private final int priority;

  public boolean hasAuthority(Role required) {
    return required.priority >= this.priority;
  }

}
