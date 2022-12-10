package com.waterfogsw.board.core.post.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.waterfogsw.board.core.board.domain.Board;
import com.waterfogsw.board.core.board.repository.BoardRepository;
import com.waterfogsw.board.core.post.entity.Post;
import com.waterfogsw.board.core.user.domain.User;
import com.waterfogsw.board.core.user.repository.UserRepository;
import com.waterfogsw.board.core.util.BaseRepositoryTest;
import com.waterfogsw.board.core.util.TestDataGenerator;

class PostQueryRepositoryTest extends BaseRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  BoardRepository boardRepository;

  @Autowired
  PostRepository postRepository;

  @Autowired
  PostQueryRepository postQueryRepository;

  @AfterEach
  void tearDown() {
    postRepository.deleteAllInBatch();
    boardRepository.deleteAllInBatch();
    userRepository.deleteAllInBatch();
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
        Optional<Post> foundPost = postQueryRepository.getDetail(-1L);

        Assertions.assertThat(foundPost.isEmpty())
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

        Post post = TestDataGenerator.getPost("hello", "world", user, board);
        postRepository.save(post);

        //when, then
        Optional<Post> foundPost = postQueryRepository.getDetail(post.getId());
        Assertions.assertThat(foundPost.isPresent())
                  .isTrue();
      }

    }

  }

  @Nested
  @DisplayName("getSlice 메서드는")
  class DescribeGetSlice {

    @Nested
    @DisplayName("null id값이 전달되면")
    class ContextWithNullId {

      @Test
      @DisplayName("가장 마지막 id부터 역순으로 size 만큼의 Post를 반환한다")
      void ItReturnSizeOfPostInReverseOrderFromLastId() {
        //given
        int sliceSize = 10;
        User user = TestDataGenerator.getRandomUsers(1)
                                     .get(0);
        userRepository.saveAndFlush(user);

        List<Board> boards = TestDataGenerator.getRandomBoards(2, user);
        Board board1 = boards.get(0);
        Board board2 = boards.get(1);
        boardRepository.saveAllAndFlush(boards);

        List<Post> board1Posts = TestDataGenerator.getRandomPosts(10, user, board1);
        List<Post> board2Posts = TestDataGenerator.getRandomPosts(10, user, board2);
        postRepository.saveAllAndFlush(board1Posts);
        postRepository.saveAllAndFlush(board2Posts);

        entityManager.clear();

        //when
        List<Post> slice = postQueryRepository.getSlice(null, board1.getId(), sliceSize, null);

        //then
        Assertions.assertThat(slice.size())
                  .isEqualTo(sliceSize);
      }

    }

    @Nested
    @DisplayName("id값이 전달되면")
    class ContextWithId {

      @Test
      @DisplayName("해당 id보다 작은값부터 역순으로 size 만큼의 Post를 반환한다")
      void ItReturnSizeOfPostInReverseOrderFromId() {
        //given
        int sliceSize = 10;
        User user = TestDataGenerator.getRandomUsers(1)
                                     .get(0);
        userRepository.saveAndFlush(user);

        List<Board> boards = TestDataGenerator.getRandomBoards(2, user);
        Board board1 = boards.get(0);
        Board board2 = boards.get(1);
        boardRepository.saveAllAndFlush(boards);

        List<Post> board1Posts = TestDataGenerator.getRandomPosts(20, user, board1);
        List<Post> board2Posts = TestDataGenerator.getRandomPosts(20, user, board2);
        postRepository.saveAllAndFlush(board1Posts);
        postRepository.saveAllAndFlush(board2Posts);

        long targetPostId = board1Posts.get(board1Posts.size() - 1)
                                       .getId();
        entityManager.clear();

        //when
        List<Post> slice = postQueryRepository.getSlice(targetPostId, board1.getId(), sliceSize, null);

        //then
        Assertions.assertThat(slice.size())
                  .isEqualTo(sliceSize);

        Post firstSelected = slice.get(0);
        Assertions.assertThat(firstSelected.getId())
                  .isEqualTo(targetPostId - 1);
      }

    }

    @Nested
    @DisplayName("keyword 값이 전달되면")
    class ContextWithKeyword {

      @Test
      @DisplayName("해당 keyword가 title에 포함되어 있는 Post 리스트를 반환한다")
      void ItReturnPostsThatContainKeywordInTitle() {
        //given
        String keyword = "hello";
        String title = " hello world ";

        User user = TestDataGenerator.getUser("alisong", "alisong@naver.com");
        userRepository.save(user);

        Board board = TestDataGenerator.getBoard("meet", "direct", user);
        boardRepository.save(board);

        Post post = TestDataGenerator.getPost(title, "world", user, board);
        postRepository.save(post);

        //when
        List<Post> slice = postQueryRepository.getSlice(null, board.getId(), 10, keyword);

        //then
        Post found = slice.get(0);
        Assertions.assertThat(found.getTitle())
                  .isEqualTo(title);
      }

    }

    @Test
    @DisplayName("해당 keyword가 content에 포함되어 있는 Post 리스트를 반환한다")
    void ItReturnPostsThatContainKeywordInTitle() {
      //given
      String keyword = "hello";
      String content = " hello world ";

      User user = TestDataGenerator.getUser("alisong", "alisong@naver.com");
      userRepository.save(user);

      Board board = TestDataGenerator.getBoard("meet", "direct", user);
      boardRepository.save(board);

      Post post = TestDataGenerator.getPost("title", content, user, board);
      postRepository.save(post);

      //when
      List<Post> slice = postQueryRepository.getSlice(null, board.getId(), 10, keyword);

      //then
      Post found = slice.get(0);
      Assertions.assertThat(found.getContent())
                .isEqualTo(content);
    }

  }

}
