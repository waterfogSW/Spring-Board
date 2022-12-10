package com.waterfogsw.board.post.dto;

import javax.annotation.Nullable;

public record PostSliceRequest(
    @Nullable
    Long id,
    long boardId,
    Integer size,
    @Nullable
    String keyword
) {

  public PostSliceRequest(
      @Nullable
      Long id,
      long boardId,
      Integer size,
      @Nullable
      String keyword
  ) {
    this.id = id;
    this.boardId = boardId;
    this.size = size == null ? 10 : size;
    this.keyword = keyword;
  }

}
