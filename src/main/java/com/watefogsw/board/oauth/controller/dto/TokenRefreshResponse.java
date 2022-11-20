package com.watefogsw.board.oauth.controller.dto;

import com.watefogsw.board.user.entity.Role;

import lombok.Builder;

@Builder
public record TokenRefreshResponse(
    long id,
    String email,
    String name,
    String imageUrl,
    Role role,
    String tokenType,
    String accessToken,
    String refreshToken
) {

}
