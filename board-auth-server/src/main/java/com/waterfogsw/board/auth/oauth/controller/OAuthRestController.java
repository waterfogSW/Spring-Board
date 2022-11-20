package com.waterfogsw.board.auth.oauth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.waterfogsw.board.auth.oauth.controller.dto.LoginResponse;
import com.waterfogsw.board.auth.oauth.controller.dto.TokenRefreshResponse;
import com.waterfogsw.board.auth.oauth.service.OAuthService;
import com.waterfogsw.board.auth.oauth.controller.dto.TokenRefreshRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OAuthRestController {

  private final OAuthService oAuthService;

  @GetMapping("/login/oauth/{provider}")
  @ResponseStatus(HttpStatus.OK)
  public LoginResponse login(
      @PathVariable String provider,
      @RequestParam String code
  ) {
    return oAuthService.login(provider, code);
  }

  @PostMapping("/auth/refresh")
  @ResponseStatus(HttpStatus.OK)
  public TokenRefreshResponse refresh(@RequestBody TokenRefreshRequest request) {
    return oAuthService.refresh(request);
  }


}
