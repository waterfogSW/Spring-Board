package com.waterfogsw.board.auth.oauth.controller.dto;

public record TokenRefreshRequest(
    String accessToken,
    String refreshToken
) {

}
