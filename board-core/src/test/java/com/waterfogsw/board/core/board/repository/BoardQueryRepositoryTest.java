package com.waterfogsw.board.core.board.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.waterfogsw.board.core.board.domain.Board;
import com.waterfogsw.board.core.user.domain.User;
import com.waterfogsw.board.core.user.repository.UserRepository;
import com.waterfogsw.board.core.util.BaseRepositoryTest;
import com.waterfogsw.board.core.util.TestDataGenerator;

class BoardQueryRepositoryTest extends BaseRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  BoardRepository boardRepository;

  @Autowired
  BoardQueryRepository boardQueryRepository;

  @AfterEach
  void tearDown() {
    boardRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Nested
  @DisplayName("getDetail 메서드는")
  class DescribeGetDetail {

    @Nested
    @DisplayName("존재하지 않는 id를 조회하면")
    class ContextWithNotExistsId {

      @Test
      @DisplayName("빈 Optional값을 반환한다")
      void ItReturnEmptyOptionalValue() {
        //when, then
        Optional<Board> foundBoard = boardQueryRepository.getDetail(1L);

        Assertions.assertThat(foundBoard.isEmpty())
                  .isTrue();
      }

    }

    @Nested
    @DisplayName("존재하는 id를 조회하면")
    class ContextWithExitsId {

      @Test
      @DisplayName("엔티티가 들어있는 Optional 값을 반환한다")
      void ItReturnEntityAsOptionalValue() {
        //given
        User user = TestDataGenerator.getUser("alisong", "alisong@naver.com");
        userRepository.save(user);

        Board board = TestDataGenerator.getBoard("meet", user);
        boardRepository.save(board);

        //when, then
        Optional<Board> foundBoard = boardQueryRepository.getDetail(1L);
        Assertions.assertThat(foundBoard.isPresent())
                  .isTrue();
      }

    }

  }

}
