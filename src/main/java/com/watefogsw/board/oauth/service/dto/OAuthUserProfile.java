package com.watefogsw.board.oauth.service.dto;

import lombok.Builder;

@Builder
public record OAuthUserProfile(
    String oauthId,
    String email,
    String name,
    String imageUrl
) {

}
