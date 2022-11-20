package com.watefogsw.board.oauth.userProfile.extractorStrategy;

import java.util.Map;

import com.watefogsw.board.oauth.service.dto.OAuthUserProfile;

public interface OAuthUserProfileExtractor {

  OAuthUserProfile extract(Map<String, Object> attributes);

  String getExtractorName();

}
