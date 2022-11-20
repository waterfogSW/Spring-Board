package com.waterfogsw.board.auth.oauth.controller.dto;

import lombok.Builder;

@Builder
public record OAuthUserProfile(
    String oauthId,
    String email,
    String name,
    String imageUrl
) {

}
