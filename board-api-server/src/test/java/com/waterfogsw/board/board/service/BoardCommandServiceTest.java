package com.waterfogsw.board.board.service;

import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.waterfogsw.board.board.dto.BoardCreateRequest;
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

}
