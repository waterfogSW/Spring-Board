package com.waterfogsw.board.common.auth;

public class AuthenticationContextHolder {

  private static final ThreadLocal<Authentication> authenticationHolder = new ThreadLocal<>();

  public static void clearContext() {
    authenticationHolder.remove();
  }

  public static Authentication getAuthentication() {
    Authentication authentication = authenticationHolder.get();
    if(authentication == null) {
      authentication = createEmptyAuthentication();
      authenticationHolder.set(authentication);
    }
    return authentication;
  }

  public static void setAuthentication(Authentication authentication) {
    authenticationHolder.set(authentication);
  }


  private static Authentication createEmptyAuthentication() {
    return new Authentication(0L, null);
  }

}
