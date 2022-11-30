package com.waterfogsw.board.auth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OAuthTokenResponse(
    @JsonProperty("access_token")
    String accessToken,

    String scope,

    @JsonProperty("token_type")
    String tokenType
) {

}
