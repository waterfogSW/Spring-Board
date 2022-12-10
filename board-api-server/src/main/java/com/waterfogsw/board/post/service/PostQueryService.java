package com.waterfogsw.board.post.service;

import org.springframework.stereotype.Service;

import com.waterfogsw.board.common.exception.NotFoundException;
import com.waterfogsw.board.core.post.repository.PostQueryRepository;
import com.waterfogsw.board.post.dto.PostGetDetailResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostQueryService {

  private final PostQueryRepository postQueryRepository;

  public PostGetDetailResponse getDetail(long id) {
    return postQueryRepository.getDetail(id)
                              .map(PostGetDetailResponse::from)
                              .orElseThrow(() -> new NotFoundException("Post not found"));
  }

}
