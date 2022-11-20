package com.watefogsw.board.oauth.clientRegistration;

import java.util.HashMap;
import java.util.Map;

import com.watefogsw.board.common.property.OAuthProperties;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuthClientRegistrationPropertiesAdapter {

  public static Map<String, OAuthClientRegistration> getOauthProviders(OAuthProperties properties) {
    Map<String, OAuthClientRegistration> provider = new HashMap<>();
    properties.getClients()
              .forEach((k, v) -> provider.put(k, getOAuthClientRegistration(k, v, properties)));
    return provider;
  }

  private static OAuthClientRegistration getOAuthClientRegistration(
      String key,
      OAuthProperties.Client value,
      OAuthProperties properties
  ) {
    Map<String, OAuthProperties.Provider> providers = properties.getProviders();

    return OAuthClientRegistration.of(value, providers.get(key));
  }

}
