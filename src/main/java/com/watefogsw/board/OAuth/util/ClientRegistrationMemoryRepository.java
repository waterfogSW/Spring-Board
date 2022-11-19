package com.watefogsw.board.OAuth.util;

import java.util.Map;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientRegistrationMemoryRepository implements ClientRegistrationRepository {

  private final Map<String, OAuthClientRegistration> clientRegistrations;

  public OAuthClientRegistration findByProviderName(String name) {
    return clientRegistrations.get(name);
  }

}
