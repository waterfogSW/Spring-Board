package com.watefogsw.board.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.watefogsw.board.common.property.JwtProperties;
import com.watefogsw.board.common.property.OAuthProperties;

@Configuration
@EnableConfigurationProperties({
    OAuthProperties.class,
    JwtProperties.class
})
public class PropertyConfig {

}
