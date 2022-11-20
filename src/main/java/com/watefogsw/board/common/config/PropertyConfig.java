package com.watefogsw.board.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.watefogsw.board.common.property.JwtProperties;
import com.watefogsw.board.common.property.OAuthProperties;
import com.watefogsw.board.common.property.RedisProperties;

@Configuration
@EnableConfigurationProperties({
    OAuthProperties.class,
    JwtProperties.class,
    RedisProperties.class
})
public class PropertyConfig {

}
