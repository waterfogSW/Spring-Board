package com.watefogsw.board.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
    String accessTokenHeader,
    String refreshTokenHeader,
    String issuer,
    String prefix,
    String clientSecret,
    Long accessTokenExpirySeconds,
    Long refreshTokenExpirySeconds
) {

}
