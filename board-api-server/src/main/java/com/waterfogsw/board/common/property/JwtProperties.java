package com.waterfogsw.board.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
    String clientSecret
) {

}
