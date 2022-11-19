package com.watefogsw.board.OAuth.config;

import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.watefogsw.board.OAuth.property.OAuthProperties;
import com.watefogsw.board.OAuth.util.ClientRegistrationMemoryRepository;
import com.watefogsw.board.OAuth.util.ClientRegistrationRepository;
import com.watefogsw.board.OAuth.util.OAuthClientRegistration;
import com.watefogsw.board.OAuth.util.OAuthClientRegistrationPropertiesAdapter;

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
