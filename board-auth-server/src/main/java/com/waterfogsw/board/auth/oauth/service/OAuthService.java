package com.waterfogsw.board.auth.oauth.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.waterfogsw.board.auth.jwt.JwtTokenProvider;
import com.waterfogsw.board.auth.oauth.clientRegistration.ClientRegistrationRepository;
import com.waterfogsw.board.auth.oauth.clientRegistration.OAuthClientRegistration;
import com.waterfogsw.board.auth.oauth.dto.LoginResponse;
import com.waterfogsw.board.auth.oauth.dto.OAuthTokenResponse;
import com.waterfogsw.board.auth.oauth.dto.OAuthUserProfile;
import com.waterfogsw.board.auth.oauth.dto.TokenRefreshRequest;
import com.waterfogsw.board.auth.oauth.dto.TokenRefreshResponse;
import com.waterfogsw.board.auth.oauth.userProfile.OAuthUserProfileExtractorFactory;
import com.waterfogsw.board.auth.oauth.userProfile.extractorStrategy.OAuthUserProfileExtractor;
import com.waterfogsw.board.core.user.domain.Role;
import com.waterfogsw.board.core.user.domain.User;
import com.waterfogsw.board.core.user.repository.UserRepository;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthService {

  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final StringRedisTemplate redisTemplate;
  private final ClientRegistrationRepository clientRegistrationRepository;
  private final OAuthAuthorizationServerClient oAuthAuthorizationServerClient;
  private final OAuthUserProfileExtractorFactory oAuthUserProfileExtractorFactory;

  private static final String LOGIN_TOKEN_TYPE = "Bearer";

  public LoginResponse login(
      String provider,
      String code
  ) {
    OAuthClientRegistration registration = clientRegistrationRepository.findByProviderName(provider);

    OAuthTokenResponse tokenResponse = oAuthAuthorizationServerClient.getToken(code, registration);

    OAuthUserProfile userProfile = getUserProfile(provider, tokenResponse, registration);

    User user = getUser(userProfile);

    String accessToken = jwtTokenProvider.createAccessToken(user);

    UUID uuid = UUID.randomUUID();
    String refreshToken = jwtTokenProvider.createRefreshToken(uuid.toString());

    redisTemplate.opsForValue()
                 .set(uuid.toString(), refreshToken);

    return LoginResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .imageUrl(user.getImageUrl())
                        .role(user.getRole())
                        .tokenType(LOGIN_TOKEN_TYPE)
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
  }

  public TokenRefreshResponse refresh(TokenRefreshRequest request) {
    String refreshTokenId = jwtTokenProvider.getPayload(request.refreshToken());
    if (redisTemplate.opsForValue()
                     .get(refreshTokenId) == null) {
      throw new JwtException("Refresh token not found");
    }

    long tokenUserId = Long.parseLong(jwtTokenProvider.getPayload(request.accessToken()));

    User user = userRepository.findById(tokenUserId)
                              .orElseThrow(() -> new RuntimeException("User not found"));

    String accessToken = jwtTokenProvider.createAccessToken(user);

    return TokenRefreshResponse.builder()
                               .id(user.getId())
                               .name(user.getName())
                               .email(user.getEmail())
                               .imageUrl(user.getImageUrl())
                               .role(user.getRole())
                               .tokenType(LOGIN_TOKEN_TYPE)
                               .accessToken(accessToken)
                               .build();
  }

  private OAuthUserProfile getUserProfile(
      String provider,
      OAuthTokenResponse tokenResponse,
      OAuthClientRegistration registration
  ) {
    Map<String, Object> userAttributes = oAuthAuthorizationServerClient.getUserAttributes(registration, tokenResponse);

    OAuthUserProfileExtractor extractor = oAuthUserProfileExtractorFactory.getExtractor(provider);

    return extractor.extract(userAttributes);
  }

  private User getUser(OAuthUserProfile userProfile) {
    return userRepository.findByOauthId(userProfile.oauthId())
                         .map(user -> updateUser(user, userProfile))
                         .orElseGet(() -> createUser(userProfile));
  }

  private User createUser(OAuthUserProfile userProfile) {
    User user = User.builder()
                    .oauthId(userProfile.oauthId())
                    .email(userProfile.email())
                    .name(userProfile.name())
                    .imageUrl(userProfile.imageUrl())
                    .role(Role.USER)
                    .build();
    return userRepository.save(user);
  }

  private User updateUser(
      User user,
      OAuthUserProfile userProfile
  ) {
    return user.update(userProfile.email(), userProfile.name(), userProfile.imageUrl());
  }

}
