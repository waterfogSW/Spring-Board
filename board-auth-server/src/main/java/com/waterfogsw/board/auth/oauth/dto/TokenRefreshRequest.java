package com.waterfogsw.board.auth.oauth.dto;

public record TokenRefreshRequest(
    String accessToken,
    String refreshToken
) {

}
