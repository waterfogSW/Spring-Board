package com.watefogsw.board.oauth.userProfile;

import lombok.Builder;

@Builder
public record OAuthUserProfile(
    String email,
    String name,
    String imageUrl,
    String oauthId
) {

}
