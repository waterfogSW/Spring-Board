package com.waterfogsw.board.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waterfogsw.board.common.exception.NotFoundException;
import com.waterfogsw.board.core.board.domain.Board;
import com.waterfogsw.board.core.board.repository.BoardRepository;
import com.waterfogsw.board.core.post.entity.Post;
import com.waterfogsw.board.core.post.repository.PostRepository;
import com.waterfogsw.board.core.user.domain.User;
import com.waterfogsw.board.post.dto.PostCreateRequest;
import com.waterfogsw.board.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostCommandService {

  private final PostRepository postRepository;
  private final BoardRepository boardRepository;
  private final UserService userService;

  @Transactional
  public void create(PostCreateRequest request) {
    User user = userService.getCurrentUser();

    Board board = boardRepository.findById(request.boardId())
                                 .orElseThrow(() -> new NotFoundException("Board not found"));

    Post post = Post.builder()
                    .title(request.title())
                    .content(request.content())
                    .writer(user)
                    .board(board)
                    .build();

    postRepository.save(post);
  }

}
