package com.waterfogsw.board.auth.oauth.controller.dto;

import com.waterfogsw.board.core.user.domain.Role;

import lombok.Builder;

@Builder
public record LoginResponse(
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
