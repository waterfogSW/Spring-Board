package com.waterfogsw.board.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
      String description,
      User creator
  ) {
    return Board.builder()
                .title(title)
                .description(description)
                .creator(creator)
                .build();
  }

  public static List<User> getRandomUsers(int num) {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      String name = getRandomStringByUUID(10);
      String email = getRandomStringByUUID(10) + "@" + getRandomStringByUUID(10);
      users.add(getUser(name, email));
    }

    return users;
  }

  public static List<Board> getRandomBoards(
      int num,
      User creator
  ) {
    List<Board> boards = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      String title = getRandomStringByUUID(10);
      String description = getRandomStringByUUID(30);
      boards.add(getBoard(title, description, creator));
    }

    return boards;
  }

  public static String getRandomStringByUUID(int length) {
    if (length > 36 || length <= 0) {
      throw new IllegalArgumentException();
    }

    return UUID.randomUUID()
               .toString()
               .substring(0, length);
  }

}