package com.watefogsw.board.oauth.property;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;

@Getter
@ConfigurationProperties(prefix = "oauth2")
public class OAuthProperties {

  private final Map<String, Client> clients = new HashMap<>();
  private final Map<String, Provider> providers = new HashMap<>();

  public record Client(
      String clientId,
      String clientSecret,
      String redirectUri
  ) {

  }

  public record Provider(
      String tokenUri,
      String userInfoUri,
      String userNameAttribute
  ) {

  }

}
