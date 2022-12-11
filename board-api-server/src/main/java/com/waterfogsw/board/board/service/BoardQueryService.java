package com.waterfogsw.board.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterfogsw.board.board.dto.BoardGetDetailResponse;
import com.waterfogsw.board.board.dto.BoardSliceRequest;
import com.waterfogsw.board.board.dto.BoardSliceResponse;
import com.waterfogsw.board.core.common.exception.NotFoundException;
import com.waterfogsw.board.core.board.repository.BoardQueryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardQueryService {

  private final BoardQueryRepository boardQueryRepository;

  public BoardGetDetailResponse getDetail(long id) {
    return boardQueryRepository.getDetail(id)
                               .map(BoardGetDetailResponse::from)
                               .orElseThrow(() -> new NotFoundException("Board not found"));
  }

  public List<BoardSliceResponse> getSliceOfBoard(BoardSliceRequest request) {
    return boardQueryRepository.getSliceOfBoard(request.id(), request.size(), request.keyword())
                               .stream()
                               .map(BoardSliceResponse::from)
                               .toList();
  }

}
