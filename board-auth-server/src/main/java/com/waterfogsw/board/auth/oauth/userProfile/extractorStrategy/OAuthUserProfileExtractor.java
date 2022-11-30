package com.waterfogsw.board.auth.oauth.userProfile.extractorStrategy;

import java.util.Map;

import com.waterfogsw.board.auth.oauth.dto.OAuthUserProfile;

public interface OAuthUserProfileExtractor {

  OAuthUserProfile extract(Map<String, Object> attributes);

  String getExtractorName();

}
