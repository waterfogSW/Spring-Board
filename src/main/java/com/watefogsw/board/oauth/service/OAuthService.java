package com.watefogsw.board.oauth.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.watefogsw.board.jwt.JwtTokenProvider;
import com.watefogsw.board.oauth.clientRegistration.ClientRegistrationRepository;
import com.watefogsw.board.oauth.clientRegistration.OAuthClientRegistration;
import com.watefogsw.board.oauth.service.dto.LoginResponse;
import com.watefogsw.board.oauth.service.dto.OAuthTokenResponse;
import com.watefogsw.board.oauth.service.dto.OAuthUserProfile;
import com.watefogsw.board.oauth.userProfile.OAuthUserProfileExtractorFactory;
import com.watefogsw.board.oauth.userProfile.extractorStrategy.OAuthUserProfileExtractor;
import com.watefogsw.board.user.entity.User;
import com.watefogsw.board.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthService {

  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final ClientRegistrationRepository clientRegistrationRepository;
  private final OAuthAuthorizationServerClient oAuthAuthorizationServerClient;
  private final OAuthUserProfileExtractorFactory oAuthUserProfileExtractorFactory;

  public LoginResponse login(
      String provider,
      String code
  ) {
    OAuthClientRegistration registration =
        clientRegistrationRepository.findByProviderName(provider);

    OAuthTokenResponse tokenResponse = oAuthAuthorizationServerClient.getToken(code, registration);

    OAuthUserProfile userProfile = getUserProfile(provider, tokenResponse, registration);

    User user = getUser(userProfile);

    Long userId = user.getId();
    String accessToken = jwtTokenProvider.createAccessToken(userId.toString());
    String refreshToken = jwtTokenProvider.createRefreshToken();

    //TODO refresh토큰 저장

    return null;
  }

  private OAuthUserProfile getUserProfile(
      String provider,
      OAuthTokenResponse tokenResponse,
      OAuthClientRegistration registration
  ) {
    Map<String, Object> userAttributes =
        oAuthAuthorizationServerClient.getUserAttributes(registration, tokenResponse);

    OAuthUserProfileExtractor extractor =
        oAuthUserProfileExtractorFactory.getExtractor(provider);

    return extractor.extract(userAttributes);
  }

  private User getUser(OAuthUserProfile userProfile) {
    return userRepository.findByOauthId(userProfile.oauthId())
                         .map(u -> u.update(userProfile))
                         .orElseGet(() -> createUser(userProfile));
  }

  private User createUser(OAuthUserProfile oAuthUserProfile) {
    User user = User.from(oAuthUserProfile);
    return userRepository.save(user);
  }

}
