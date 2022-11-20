package com.waterfogsw.board.auth.common.config;

import static io.lettuce.core.ReadFrom.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.waterfogsw.board.auth.common.property.RedisProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfig {

  private final RedisProperties redisProperties;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                                                                        .readFrom(REPLICA_PREFERRED)
                                                                        .build();
    RedisProperties.RedisInfo masterInfo = redisProperties.master();
    RedisProperties.RedisInfo slaveInfo = redisProperties.slave();

    RedisStaticMasterReplicaConfiguration redisConfig =
        new RedisStaticMasterReplicaConfiguration(masterInfo.host(), masterInfo.port());

    redisConfig.addNode(slaveInfo.host(), slaveInfo.port());

    return new LettuceConnectionFactory(redisConfig, clientConfig);
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
