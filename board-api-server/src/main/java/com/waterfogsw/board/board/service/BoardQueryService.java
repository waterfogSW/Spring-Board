package com.waterfogsw.board.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterfogsw.board.board.dto.BoardPageRequest;
import com.waterfogsw.board.board.dto.BoardPageResponse;
import com.waterfogsw.board.core.board.repository.BoardQueryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardQueryService {

  private final BoardQueryRepository boardQueryRepository;

  public List<BoardPageResponse> getPageOfBoard(BoardPageRequest request) {
    return boardQueryRepository.getPageOfBoard(request.id(), request.pageSize())
                               .stream()
                               .map(BoardPageResponse::from)
                               .toList();
  }

}
