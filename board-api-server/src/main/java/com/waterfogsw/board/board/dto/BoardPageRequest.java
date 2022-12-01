package com.waterfogsw.board.board.dto;

import org.springframework.lang.Nullable;

public record BoardPageRequest(
    @Nullable
    Long id,
    int pageSize
) {

}
