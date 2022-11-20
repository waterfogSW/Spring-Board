package com.watefogsw.board.oauth.controller.dto;

public record TokenRefreshRequest(
    String accessToken,
    String refreshToken
) {

}
