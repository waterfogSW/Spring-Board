package com.watefogsw.board.oauth.clientRegistration;

public interface ClientRegistrationRepository {

  OAuthClientRegistration findByProviderName(String name);

}
