package com.watefogsw.board.oauth.property;

import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.watefogsw.board.oauth.clientRegistration.ClientRegistrationMemoryRepository;
import com.watefogsw.board.oauth.clientRegistration.ClientRegistrationRepository;
import com.watefogsw.board.oauth.clientRegistration.OAuthClientRegistration;
import com.watefogsw.board.oauth.clientRegistration.OAuthClientRegistrationPropertiesAdapter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(OAuthProperties.class)
public class OAuthPropertyConfig {

  private final OAuthProperties properties;

  @Bean
  public ClientRegistrationRepository clientRegistrationRepository() {
    Map<String, OAuthClientRegistration> oauthProviders =
        OAuthClientRegistrationPropertiesAdapter.getOauthProviders(properties);

    return new ClientRegistrationMemoryRepository(oauthProviders);
  }

}
