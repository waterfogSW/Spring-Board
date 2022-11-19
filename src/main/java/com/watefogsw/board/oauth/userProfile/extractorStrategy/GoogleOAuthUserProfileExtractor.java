package com.watefogsw.board.oauth.userProfile.extractorStrategy;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.watefogsw.board.oauth.service.dto.OAuthUserProfile;

@Component
public class GoogleOAuthUserProfileExtractor implements OAuthUserProfileExtractor {

  private static final String PROVIDER_NAME = "google";

  @Override
  public OAuthUserProfile extract(Map<String, Object> attributes) {
    return OAuthUserProfile.builder()
                           .oauthId((String)attributes.get("sub"))
                           .email((String)attributes.get("email"))
                           .name((String)attributes.get("name"))
                           .imageUrl((String)attributes.get("picture"))
                           .build();
  }

  @Override
  public String getExtractorName() {
    return PROVIDER_NAME;
  }

}
