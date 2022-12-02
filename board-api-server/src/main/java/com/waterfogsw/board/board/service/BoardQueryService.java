package com.waterfogsw.board.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterfogsw.board.board.dto.BoardSearchRequest;
import com.waterfogsw.board.board.dto.BoardSearchResponse;
import com.waterfogsw.board.core.board.repository.BoardQueryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardQueryService {

  private final BoardQueryRepository boardQueryRepository;

  public List<BoardSearchResponse> getSliceOfBoard(BoardSearchRequest request) {
    return boardQueryRepository.getSliceOfBoard(request.id(), request.size(), request.keyword())
                               .stream()
                               .map(BoardSearchResponse::from)
                               .toList();
  }

}
