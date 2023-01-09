package com.waterfogsw.board.util;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.waterfogsw.board.common.auth.Authentication;
import com.waterfogsw.board.common.auth.AuthenticationContextHolder;
import com.waterfogsw.board.common.auth.AuthenticationFilter;
import com.waterfogsw.board.common.auth.AuthenticationTokenResolver;
import com.waterfogsw.board.core.user.domain.Role;

@ExtendWith(MockitoExtension.class)
@ExtendWith(RestDocumentationExtension.class)
public class ControllerTest {

  private static final String ROLE_CLAIM_NAME = "role";
  private static final String AUTH_TYPE = "Bearer";
  protected static final String AUTH_HEADER = "Authorization";

  protected MockMvc mockMvc;

  @MockBean
  AuthenticationTokenResolver authenticationTokenResolver;

  @BeforeEach
  @SuppressWarnings("all")
  void setUp(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation
  ) {
    buildMockMvc(webApplicationContext, restDocumentation);
  }

  protected void setAuthenticationHolder(
      long id,
      Role role
  ) {
    Authentication authentication = new Authentication(id, role);
    AuthenticationContextHolder.setAuthentication(authentication);
  }

  private void buildMockMvc(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation
  ) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                  .addFilter(authenticationFilter())
                                  .apply(documentationConfiguration(restDocumentation))
                                  .build();
  }

  private AuthenticationFilter authenticationFilter() {
    return new AuthenticationFilter(authenticationTokenResolver);
  }

}
