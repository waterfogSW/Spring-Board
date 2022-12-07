package com.waterfogsw.board.core.board.repository;

import java.util.List;
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
        Optional<Board> foundBoard = boardQueryRepository.getDetail(-1L);

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

        Board board = TestDataGenerator.getBoard("meet", "direct", user);
        boardRepository.save(board);

        //when, then
        Optional<Board> foundBoard = boardQueryRepository.getDetail(board.getId());
        Assertions.assertThat(foundBoard.isPresent())
                  .isTrue();
      }

    }

  }

  @Nested
  @DisplayName("getSliceBoard 메서드는")
  class DescribeGetSliceBoard {

    @Nested
    @DisplayName("null id값이 전달되면")
    class ContextWithNullId {

      @Test
      @DisplayName("가장 마지막 id부터 역순으로 size 만큼의 Board를 반환한다")
      void ItReturnBoardOfSizesInReverseOrderFromLastId() {
        //given
        int totalBoardCount = 20;
        int sliceSize = 10;
        User user = TestDataGenerator.getRandomUsers(1)
                                     .get(0);
        userRepository.save(user);

        List<Board> boards = TestDataGenerator.getRandomBoards(totalBoardCount, user);
        boardRepository.saveAll(boards);

        //when
        List<Board> sliceOfBoard = boardQueryRepository.getSliceOfBoard(null, sliceSize, null);

        //then
        Assertions.assertThat(sliceOfBoard.size())
                  .isEqualTo(sliceSize);
      }

    }

    @Nested
    @DisplayName("id값이 전달되면")
    class ContextWithId {

      @Test
      @DisplayName("해당 id보다 작은값부터 역순으로 size 만큼의 Board를 반환한다")
      void ItReturnBoardOfSizesInReverseOrderFromId() {
        //given
        int totalBoardCount = 20;
        int sliceSize = 10;
        User user = TestDataGenerator.getRandomUsers(1)
                                     .get(0);
        userRepository.save(user);

        List<Board> boards = TestDataGenerator.getRandomBoards(totalBoardCount, user);
        boardRepository.saveAll(boards);

        long targetBoardId = boards.get(boards.size() - 1)
                                   .getId();

        //when
        List<Board> sliceOfBoard = boardQueryRepository.getSliceOfBoard(targetBoardId, sliceSize, null);

        //then
        Assertions.assertThat(sliceOfBoard.size())
                  .isEqualTo(sliceSize);

        Board firstSelectedBoard = sliceOfBoard.get(0);
        Assertions.assertThat(firstSelectedBoard.getId())
                  .isEqualTo(targetBoardId - 1);
      }

    }

    @Nested
    @DisplayName("keyword 값이 전달되면")
    class ContextWithKeyword {

      @Test
      @DisplayName("해당 keyword가 title에 포함되어 있는 Board를 반환한다")
      void ItReturnBoardThatContainKeywordInTitle() {
        //given
        String keyword = "hello";
        String title = "hello world";

        int totalBoardCount = 20;
        int sliceSize = 10;
        User user = TestDataGenerator.getRandomUsers(1)
                                     .get(0);
        userRepository.save(user);

        Board keywordBoard = TestDataGenerator.getBoard(title, "camping", user);
        List<Board> boards = TestDataGenerator.getRandomBoards(totalBoardCount, user);
        boardRepository.save(keywordBoard);
        boardRepository.saveAll(boards);

        //when
        List<Board> sliceOfBoard = boardQueryRepository.getSliceOfBoard(null, sliceSize, keyword);

        //then
        Board lastBoard = sliceOfBoard.get(0);
        Assertions.assertThat(lastBoard.getTitle())
                  .isEqualTo(title);
      }

      @Test
      @DisplayName("해당 keyword가 description에 포함되어 있는 Board를 반환한다")
      void ItReturnBoardThatContainKeywordInDescription() {
        //given
        String keyword = "hello";
        String description = "hello world";

        int totalBoardCount = 20;
        int sliceSize = 10;
        User user = TestDataGenerator.getRandomUsers(1)
                                     .get(0);
        userRepository.save(user);

        Board keywordBoard = TestDataGenerator.getBoard("camping", description, user);
        List<Board> boards = TestDataGenerator.getRandomBoards(totalBoardCount, user);
        boardRepository.save(keywordBoard);
        boardRepository.saveAll(boards);

        //when
        List<Board> sliceOfBoard = boardQueryRepository.getSliceOfBoard(null, sliceSize, keyword);

        //then
        Board lastBoard = sliceOfBoard.get(0);
        Assertions.assertThat(lastBoard.getDescription())
                  .isEqualTo(description);
      }

    }

  }

}
