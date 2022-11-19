package com.watefogsw.board.OAuth.util;

import com.watefogsw.board.OAuth.property.OAuthProperties;

import lombok.Builder;

@Builder
public record OAuthClientRegistration(
    String clientId,
    String clientSecret,
    String redirectUrl,
    String tokenUrl,
    String userInfoUrl
) {

  public static OAuthClientRegistration of(
      OAuthProperties.Client client,
      OAuthProperties.Provider provider
  ) {
    return OAuthClientRegistration.builder()
                                  .clientId(client.clientId())
                                  .clientSecret(client.clientSecret())
                                  .redirectUrl(client.redirectUri())
                                  .tokenUrl(provider.tokenUri())
                                  .userInfoUrl(provider.userInfoUri())
                                  .build();
  }

}
