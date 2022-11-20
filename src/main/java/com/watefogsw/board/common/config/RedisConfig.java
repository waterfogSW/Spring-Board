package com.watefogsw.board.common.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfig {

  private final RedisProperties redisProperties;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
  }

  @Bean
  public StringRedisTemplate stringRedisTemplate() {
    StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
    stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
    stringRedisTemplate.setValueSerializer(new StringRedisSerializer());
    stringRedisTemplate.setConnectionFactory(redisConnectionFactory());
    return stringRedisTemplate;
  }

}
