package com.waterfogsw.board.auth.common.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.waterfogsw.board.auth.common.property.OAuthProperties;
import com.waterfogsw.board.auth.oauth.clientRegistration.ClientRegistrationMemoryRepository;
import com.waterfogsw.board.auth.oauth.clientRegistration.ClientRegistrationRepository;
import com.waterfogsw.board.auth.oauth.clientRegistration.OAuthClientRegistration;
import com.waterfogsw.board.auth.oauth.clientRegistration.OAuthClientRegistrationPropertiesAdapter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class OAuthPropertyConfig {

  private final OAuthProperties properties;

  @Bean
  public ClientRegistrationRepository clientRegistrationRepository() {
    Map<String, OAuthClientRegistration> oauthProviders =
        OAuthClientRegistrationPropertiesAdapter.getOauthProviders(properties);

    return new ClientRegistrationMemoryRepository(oauthProviders);
  }

}
