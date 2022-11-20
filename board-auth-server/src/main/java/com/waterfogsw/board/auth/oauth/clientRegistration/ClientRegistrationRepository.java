package com.waterfogsw.board.auth.oauth.clientRegistration;

public interface ClientRegistrationRepository {

  OAuthClientRegistration findByProviderName(String name);

}
