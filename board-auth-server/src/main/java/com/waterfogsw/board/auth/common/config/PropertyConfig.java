package com.waterfogsw.board.auth.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.waterfogsw.board.auth.common.property.JwtProperties;
import com.waterfogsw.board.auth.common.property.OAuthProperties;
import com.waterfogsw.board.auth.common.property.RedisProperties;

@Configuration
@EnableConfigurationProperties({
    OAuthProperties.class,
    JwtProperties.class,
    RedisProperties.class
})
public class PropertyConfig {

}
