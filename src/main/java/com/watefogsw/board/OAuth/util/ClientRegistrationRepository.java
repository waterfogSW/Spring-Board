package com.watefogsw.board.OAuth.util;

public interface ClientRegistrationRepository {

  OAuthClientRegistration findByProviderName(String name);

}
