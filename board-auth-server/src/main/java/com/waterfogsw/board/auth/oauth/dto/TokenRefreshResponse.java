package com.waterfogsw.board.auth.oauth.dto;

import com.waterfogsw.board.core.user.domain.Role;

import lombok.Builder;

@Builder
public record TokenRefreshResponse(
    long id,
    String email,
    String name,
    String imageUrl,
    Role role,
    String tokenType,
    String accessToken
) {

}
