package com.waterfogsw.board.auth.oauth.clientRegistration;

import com.waterfogsw.board.auth.common.property.OAuthProperties;

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
    return builder().clientId(client.clientId())
                    .clientSecret(client.clientSecret())
                    .redirectUrl(client.redirectUri())
                    .tokenUrl(provider.tokenUri())
                    .userInfoUrl(provider.userInfoUri())
                    .build();
  }

}
