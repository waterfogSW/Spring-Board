package com.watefogsw.board.oauth.userProfile;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.watefogsw.board.oauth.userProfile.extractorStrategy.OAuthUserProfileExtractor;

@Component
public class OAuthUserProfileExtractorFactory {

  private final Map<String, OAuthUserProfileExtractor> extractors;

  public OAuthUserProfileExtractorFactory(Set<OAuthUserProfileExtractor> extractorSet) {
    extractors = new HashMap<>();
    initExtractors(extractorSet);
  }

  public OAuthUserProfileExtractor getExtractor(String provider) {
    return extractors.get(provider);
  }

  private void initExtractors(Set<OAuthUserProfileExtractor> extractorSet) {
    extractorSet.forEach(extractor -> extractors.put(extractor.getExtractorName(), extractor));
  }

}
