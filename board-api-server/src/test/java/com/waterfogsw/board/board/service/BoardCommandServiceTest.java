package com.waterfogsw.board.board.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.waterfogsw.board.board.dto.BoardCreateRequest;
import com.waterfogsw.board.board.dto.BoardUpdateRequest;
import com.waterfogsw.board.common.exception.AuthenticationException;
import com.waterfogsw.board.core.board.domain.Board;
import com.waterfogsw.board.core.board.repository.BoardRepository;
import com.waterfogsw.board.core.user.domain.User;
import com.waterfogsw.board.core.util.TestDataGenerator;
import com.waterfogsw.board.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class BoardCommandServiceTest {

  @Mock
  BoardRepository boardRepository;

  @Mock
  UserService userService;

  @InjectMocks
  BoardCommandService boardCommandService;

  @Nested
  @DisplayName("create 메서드는")
  class DescribeCreate {

    @Nested
    @DisplayName("request 값이 전달되면")
    class ContextWithRequest {

      @Test
      @DisplayName("board를 만들어 저장한다")
      void ItSaveBoard() {
        //given
        var request = new BoardCreateRequest("membership", "lucky");
        User user = TestDataGenerator.getUser("torie", "torie_cottrillaq@lightbox.mzo");
        given(userService.getCurrentUser()).willReturn(user);

        //when
        boardCommandService.create(request);

        //then
        verify(boardRepository).save(any(Board.class));
      }

    }

  }

  @Nested
  @DisplayName("update 메서드는")
  class DescribeUpdate {

    @Nested
    @DisplayName("게시판 소유자가 현재 유저가 아니면")
    class ContextWithCurrentUserIsNotBoardOwner {

      @Test
      @DisplayName("AuthenticationException 을 발생시킨다")
      void ItThrowsException() {
        //given
        long currentUserId = 1L;
        long boardOwnerId = 2L;

        User currentUser = TestDataGenerator.getUser("panic", "sharea_nicholson2oeu@scale.nbe");
        User boardOwner = TestDataGenerator.getUser("test", "gamalier_shelton5lq@pushing.bqj");
        ReflectionTestUtils.setField(currentUser, "id", currentUserId);
        ReflectionTestUtils.setField(boardOwner, "id", boardOwnerId);

        Board board = TestDataGenerator.getBoard("below", "wordpress", boardOwner);

        given(userService.getCurrentUser()).willReturn(currentUser);
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));

        BoardUpdateRequest request = new BoardUpdateRequest("test", "test");

        //when, then
        assertThatThrownBy(() -> boardCommandService.update(1L, request)).isInstanceOf(AuthenticationException.class);
      }

    }

    @Nested
    @DisplayName("현재 유저가 게시판 소유자이면")
    class ContextWithCurrentUserIsBoardOwner {

      @Test
      @DisplayName("board의 내용을 수정한다")
      void ItUpdateBoard() {
        //given
        String updateTitle = "test";
        String updateDescription = "test";
        long currentUserId = 1L;
        long boardOwnerId = 1L;
        long boardId = 1L;
        BoardUpdateRequest request = new BoardUpdateRequest(updateTitle, updateDescription);

        User currentUser = TestDataGenerator.getUser("panic", "sharea_nicholson2oeu@scale.nbe");
        User boardOwner = TestDataGenerator.getUser("test", "gamalier_shelton5lq@pushing.bqj");
        Board board = TestDataGenerator.getBoard("below", "wordpress", boardOwner);

        ReflectionTestUtils.setField(currentUser, "id", currentUserId);
        ReflectionTestUtils.setField(boardOwner, "id", boardOwnerId);
        ReflectionTestUtils.setField(board, "id", boardId);

        given(userService.getCurrentUser()).willReturn(currentUser);
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));

        //when
        boardCommandService.update(boardId, request);

        //then
        Assertions.assertThat(board.getTitle())
                  .isEqualTo(updateTitle);
        Assertions.assertThat(board.getDescription())
                  .isEqualTo(updateDescription);

      }

    }

  }

}
