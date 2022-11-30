package com.waterfogsw.board.auth.oauth.dto;

import lombok.Builder;

@Builder
public record OAuthUserProfile(
    String oauthId,
    String email,
    String name,
    String imageUrl
) {

}
