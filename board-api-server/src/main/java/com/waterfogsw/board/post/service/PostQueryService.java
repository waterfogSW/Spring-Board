package com.waterfogsw.board.post.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterfogsw.board.core.common.exception.NotFoundException;
import com.waterfogsw.board.core.post.repository.PostQueryRepository;
import com.waterfogsw.board.post.dto.PostGetDetailResponse;
import com.waterfogsw.board.post.dto.PostSliceRequest;
import com.waterfogsw.board.post.dto.PostSliceResponse;

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

  public List<PostSliceResponse> getSlice(PostSliceRequest request) {
    return postQueryRepository.getSlice(request.id(), request.boardId(), request.size(), request.keyword())
                              .stream()
                              .map(PostSliceResponse::from)
                              .toList();
  }

}
