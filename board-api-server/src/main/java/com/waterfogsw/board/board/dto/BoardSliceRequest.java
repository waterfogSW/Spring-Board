package com.waterfogsw.board.board.dto;

import org.springframework.lang.Nullable;

public record BoardSliceRequest(
    @Nullable
    Long id,
    Integer size,
    @Nullable
    String keyword
) {

  public BoardSliceRequest(
      @Nullable
      Long id,
      Integer size,
      @Nullable
      String keyword
  ) {
    this.id = id;
    this.size = size == null ? 10 : size;
    this.keyword = keyword;
  }

}
