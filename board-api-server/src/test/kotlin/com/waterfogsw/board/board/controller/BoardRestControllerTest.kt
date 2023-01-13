package com.waterfogsw.board.board.controller

import STRING
import com.fasterxml.jackson.databind.ObjectMapper
import com.waterfogsw.board.board.dto.BoardCreateRequest
import com.waterfogsw.board.board.service.BoardCommandService
import com.waterfogsw.board.board.service.BoardQueryService
import com.waterfogsw.board.common.auth.Authentication
import com.waterfogsw.board.common.auth.AuthenticationContextHolder
import com.waterfogsw.board.common.auth.AuthenticationTokenResolver
import com.waterfogsw.board.core.user.domain.Role
import com.waterfogsw.board.restdoc.andDocument
import com.waterfogsw.board.restdoc.requestBody
import com.waterfogsw.board.restdoc.restDocMockMvcBuild
import com.waterfogsw.board.restdoc.type
import io.kotest.core.spec.style.DescribeSpec
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.MockBeans
import org.springframework.http.MediaType
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.test.web.servlet.post
import org.springframework.web.context.WebApplicationContext

@MockBeans(
  MockBean(BoardCommandService::class),
  MockBean(BoardQueryService::class),
  MockBean(AuthenticationTokenResolver::class)
)
@WebMvcTest(BoardRestController::class)
@ExtendWith(RestDocumentationExtension::class)
class BoardRestControllerTest(
  @Autowired
  private val context: WebApplicationContext,
  @Autowired
  private val mapper: ObjectMapper,
) : DescribeSpec({
  val restDocumentation = ManualRestDocumentation()
  val mockMvc = restDocMockMvcBuild(context, restDocumentation)

  beforeEach { restDocumentation.beforeTest(javaClass, it.name.testName) }
  afterEach { restDocumentation.afterTest() }

  describe("POST : /api/v1/boards") {
    val url = "/api/v1/boards"
    context("유효한 요청이 전달되면") {
      val authentication = Authentication(1L, Role.USER)
      AuthenticationContextHolder.setAuthentication(authentication)

      val requestBody = BoardCreateRequest("Test", "Test")
      val requestJson = mapper.writeValueAsString(requestBody)

      it("201 응답") {
        mockMvc
            .post(url) {
              contentType = MediaType.APPLICATION_JSON
              content = requestJson
            }
            .andExpect {
              status { isCreated() }
            }
            .andDocument("Create Board", {
              requestBody(
                "title" type STRING means "게시판 이름" isOptional false,
                "description" type STRING means "게시판 설명" isOptional true
              )
            })
      }
    }

    context("제목의 길이가 1~20사이가 아니면") {
      val authentication = Authentication(1L, Role.USER)
      AuthenticationContextHolder.setAuthentication(authentication)

      val titleCases = listOf(null, "", "\n", "a".repeat(21))
      titleCases.forEach { title ->
        val requestBody = BoardCreateRequest(title, "Test")
        val requestJson = mapper.writeValueAsString(requestBody)

        it("400 응답") {
          mockMvc
              .post(url) {
                contentType = MediaType.APPLICATION_JSON
                content = requestJson
              }
              .andExpect {
                status { isBadRequest() }
              }
        }
      }
    }
  }
})
