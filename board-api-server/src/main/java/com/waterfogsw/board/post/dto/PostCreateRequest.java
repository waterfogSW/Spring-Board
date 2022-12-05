package com.waterfogsw.board.post.dto;

public record PostCreateRequest(
    String title,
    String content,
    long boardId
) {

}
