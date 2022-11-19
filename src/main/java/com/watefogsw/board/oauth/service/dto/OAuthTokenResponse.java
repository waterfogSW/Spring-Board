package com.watefogsw.board.oauth.service.dto;

public record OAuthTokenResponse(
    String accessToken,
    String scope,
    String tokenType
) {

}
