package com.watefogsw.board.oauth.service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.watefogsw.board.oauth.service.dto.OAuthTokenResponse;
import com.watefogsw.board.oauth.clientRegistration.OAuthClientRegistration;

@Component
public class OAuthAuthorizationServerClient {

  public OAuthTokenResponse getToken(
      String code,
      OAuthClientRegistration registration
  ) {
    return WebClient.create()
                    .post()
                    .uri(registration.tokenUrl())
                    .headers(header -> {
                      header.setBasicAuth(registration.clientId(), registration.clientSecret());
                      header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                      header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                      header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                    })
                    .bodyValue(tokenRequest(code, registration))
                    .retrieve()
                    .bodyToMono(OAuthTokenResponse.class)
                    .block();
  }

  public Map<String, Object> getUserAttributes(
      OAuthClientRegistration registration,
      OAuthTokenResponse tokenResponse
  ) {
    return WebClient.create()
                    .get()
                    .uri(registration.userInfoUrl())
                    .headers(header -> header.setBearerAuth(tokenResponse.accessToken()))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                    })
                    .block();
  }

  private MultiValueMap<String, String> tokenRequest(
      String code,
      OAuthClientRegistration registration
  ) {
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("code", code);
    formData.add("grant_type", "authorization_code");
    formData.add("redirect_uri", registration.redirectUrl());
    return formData;
  }

}
