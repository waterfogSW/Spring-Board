package com.waterfogsw.board.board.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waterfogsw.board.board.dto.BoardCreateRequest;
import com.waterfogsw.board.board.service.BoardCommandService;
import com.waterfogsw.board.board.service.BoardQueryService;
import com.waterfogsw.board.core.user.domain.Role;
import com.waterfogsw.board.util.ControllerTest;

@WebMvcTest(BoardRestController.class)
@MockBeans({
    @MockBean(BoardCommandService.class),
    @MockBean(BoardQueryService.class)
})
class BoardRestControllerTest extends ControllerTest {

  @Autowired
  ObjectMapper objectMapper;

  private static final String URL = "/api/v1/boards";

  @Nested
  @DisplayName("POST")
  class DescribePOST {

    @Nested
    @DisplayName("유효한 요청이 전달되면")
    class ContextWithValidRequest {

      @Test
      @DisplayName("201 응답")
      void ItResponse201() throws Exception {
        //given
        setAuthenticationHolder(1L, Role.USER);
        final var requestBody = new BoardCreateRequest("Test", "Test");
        final var jsonBody = objectMapper.writeValueAsString(requestBody);

        //when
        final var request = RestDocumentationRequestBuilders
            .post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBody);

        final var response = mockMvc.perform(request);

        //then
        response.andExpect(status().isCreated())
                .andDo(document("Create Board", preprocessRequest(prettyPrint()), requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING)
                                          .description("게시판 이름"),
                    fieldWithPath("description").type(JsonFieldType.STRING)
                                                .description("게시판 설명")
                )));
      }

    }

    @Nested
    @DisplayName("제목의 길이가 유효하지 않으면")
    class ContextWithInvalidTitleLength {

      @ParameterizedTest
      @ArgumentsSource(TitleString.class)
      @DisplayName("400 응답")
      void ItResponse400(String title) throws Exception {
        //given
        setAuthenticationHolder(1L, Role.USER);
        final var requestBody = new BoardCreateRequest(title, "Test");
        final var jsonBody = objectMapper.writeValueAsString(requestBody);

        //when
        final var request = RestDocumentationRequestBuilders
            .post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBody);

        final var response = mockMvc.perform(request);

        //then
        response.andExpect(status().isBadRequest());
      }

    }

    @Nested
    @DisplayName("설명의 길이가 유효하지 않으면")
    class ContextWithInvalidDescriptionLength {

      @ParameterizedTest
      @ArgumentsSource(DescriptionString.class)
      @DisplayName("400 응답")
      void ItResponse400(String description) throws Exception {
        //given
        setAuthenticationHolder(1L, Role.USER);
        final var requestBody = new BoardCreateRequest("Test", description);
        final var jsonBody = objectMapper.writeValueAsString(requestBody);

        //when
        final var request = RestDocumentationRequestBuilders
            .post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBody);

        final var response = mockMvc.perform(request);

        //then
        response.andExpect(status().isBadRequest());
      }

    }

    static class TitleString implements ArgumentsProvider {

      @Override
      public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
            Arguments.of((String)null),
            Arguments.of(""),
            Arguments.of(" "),
            Arguments.of("a".repeat(21))
        );
      }

    }

    static class DescriptionString implements ArgumentsProvider {

      @Override
      public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
            Arguments.of((String)null),
            Arguments.of(""),
            Arguments.of(" "),
            Arguments.of("a".repeat(201))
        );
      }

    }

  }

}
