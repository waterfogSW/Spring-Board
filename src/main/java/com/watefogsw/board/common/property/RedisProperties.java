package com.watefogsw.board.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "redis")
public record RedisProperties(
    RedisInfo master,
    RedisInfo slave
) {

  public static record RedisInfo(
      String host,
      int port
  ) {

  }

}
