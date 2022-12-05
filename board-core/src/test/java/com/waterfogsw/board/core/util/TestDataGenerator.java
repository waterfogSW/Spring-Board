package com.waterfogsw.board.core.util;

import com.waterfogsw.board.core.board.domain.Board;
import com.waterfogsw.board.core.user.domain.Role;
import com.waterfogsw.board.core.user.domain.User;

public class TestDataGenerator {

  public static User getUser(
      String name,
      String email
  ) {
    return User.builder()
               .name(name)
               .email(email)
               .role(Role.USER)
               .build();
  }

  public static Board getBoard(
      String title,
      User creator
  ) {
    return Board.builder()
                .title(title)
                .creator(creator)
                .build();
  }

}
