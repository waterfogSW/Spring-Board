package com.waterfogsw.board.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.waterfogsw.board.common.property.JwtProperties;

@Configuration
@EnableConfigurationProperties({
    JwtProperties.class
})
public class PropertyConfig {

}
