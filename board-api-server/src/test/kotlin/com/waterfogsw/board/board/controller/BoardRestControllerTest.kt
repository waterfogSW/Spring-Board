package com.waterfogsw.board.board.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.waterfogsw.board.board.dto.*
import com.waterfogsw.board.board.service.BoardCommandService
import com.waterfogsw.board.board.service.BoardQueryService
import com.waterfogsw.board.common.auth.*
import com.waterfogsw.board.core.user.domain.Role
import com.waterfogsw.board.user.dto.UserInfo
import com.waterfogsw.board.util.restdoc.*
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockkObject
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.MockBeans
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.request
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDateTime

@MockBeans(
  MockBean(AuthenticationTokenResolver::class)
)
@WebMvcTest(BoardRestController::class)
@ExtendWith(RestDocumentationExtension::class)
class BoardRestControllerTest(
  @MockBean
  private val commandService: BoardCommandService,
  @MockBean
  private val queryService: BoardQueryService,
  @Autowired
  private val context: WebApplicationContext,
  @Autowired
  private val mapper: ObjectMapper,
) : DescribeSpec({
  val restDocumentation = ManualRestDocumentation()
  val mockMvc = restDocMockMvcBuild(context, restDocumentation)

  beforeEach {
    mockkObject(commandService)
    mockkObject(queryService)
    restDocumentation.beforeTest(javaClass, it.name.testName)
  }
  afterEach { restDocumentation.afterTest() }

  describe("POST : /api/v1/boards") {
    val url = "/api/v1/boards"
    context("유효한 요청이 전달 되면") {
      val authentication = Authentication(1L, Role.USER)
      AuthenticationContextHolder.setAuthentication(authentication)

      val requestBody = BoardCreateRequest("Test", "Test")
      val requestJson = mapper.writeValueAsString(requestBody)
      val request = request(HttpMethod.POST, url)
          .contentType(MediaType.APPLICATION_JSON)
          .content(requestJson)

      it("201 응답") {
        mockMvc
            .perform(request)
            .andExpect(status().isCreated)
            .andDocument(
              "Create Board", (
                  requestBody(
                    "title" type STRING means "게시판 이름" isOptional false,
                    "description" type STRING means "게시판 설명" isOptional true
                  ))
            )
      }
    }

    context("제목의 길이가 1~20사이가 아니면") {
      val authentication = Authentication(1L, Role.USER)
      AuthenticationContextHolder.setAuthentication(authentication)

      val titles = listOf(null, "", "\n", "a".repeat(21))
      titles.forEach { title ->
        val requestBody = BoardCreateRequest(title, "Test")
        val requestJson = mapper.writeValueAsString(requestBody)
        val request = request(HttpMethod.POST, url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)

        it("400 응답") {
          mockMvc
              .perform(request)
              .andExpect(status().isBadRequest)
        }
      }
    }

    context("설명의 길이가 1~200사이가 아니면") {
      val authentication = Authentication(1L, Role.USER)
      AuthenticationContextHolder.setAuthentication(authentication)

      val descriptions = listOf(null, "", "\n", "a".repeat(201))
      descriptions.forEach { description ->
        val requestBody = BoardCreateRequest("Test", description)
        val requestJson = mapper.writeValueAsString(requestBody)
        val request = request(HttpMethod.POST, url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)

        it("400 응답") {
          mockMvc
              .perform(request)
              .andExpect(status().isBadRequest)
        }
      }
    }
  }

  describe("GET : /api/v1/boards/{id}") {
    val url = "/api/v1/boards/{id}"
    context("유효한 요청이 전달 되면") {
      val id = 1L
      val request = request(HttpMethod.GET, url, id)
      it("200 응답") {
        val response = getTestBoardGetDetailResponse()
        given(queryService.getDetail(anyLong())).willReturn(response)
        mockMvc
            .perform(request)
            .andExpect(status().isOk)
            .andDocument(
              "Lookup Board",
              pathParameters(
                "id" pathMeans "게시판 번호"
              ),
              responseBody(
                "id" type NUMBER means "게시판 번호" isOptional false,
                "title" type STRING means "게시판 제목" isOptional false,
                "description" type STRING means "게시판 제목" isOptional true,
                "creatorInfo.id" type NUMBER means "게시판 생성자 번호" isOptional false,
                "creatorInfo.name" type STRING means "게시판 생성자 이름" isOptional false,
                "creatorInfo.imageUrl" type STRING means "게시판 생성자 이미지 URL" isOptional false,
                "createdAt" type STRING means "게시판 생성일" isOptional false,
              )
            )
      }
    }
  }

  describe("PUT : /api/v1/boards/{id}") {
    val url = "/api/v1/boards/{id}"
    context("유효한 요청이 전달 되면") {
      val authentication = Authentication(1L, Role.USER)
      AuthenticationContextHolder.setAuthentication(authentication)

      val id = 1L
      val requestBody = BoardUpdateRequest("Test", "Test")
      val requestJson = mapper.writeValueAsString(requestBody)
      val request = request(HttpMethod.PUT, url, id)
          .contentType(MediaType.APPLICATION_JSON)
          .content(requestJson)
      it("200 응답") {
        mockMvc
            .perform(request)
            .andExpect(status().isOk)
            .andDocument(
              "Update Board",
              pathParameters(
                "id" pathMeans "게시판 번호"
              ),
              requestBody(
                "title" type STRING means "게시판 이름" isOptional false,
                "description" type STRING means "게시판 설명" isOptional true
              )
            )
      }
    }
  }

  describe("DELETE : /api/v1/boards/{id}") {
    val url = "/api/v1/boards/{id}"
    context("유효한 요청이 전달 되면") {
      val authentication = Authentication(1L, Role.USER)
      AuthenticationContextHolder.setAuthentication(authentication)

      val id = 1L
      val request = request(HttpMethod.DELETE, url, id)

      it("200 응답") {
        mockMvc
            .perform(request)
            .andExpect(status().isOk)
            .andDocument(
              "Delete Board",
              pathParameters(
                "id" pathMeans "게시판 번호"
              )
            )
      }
    }
  }
})

fun getTestUserInfo(): UserInfo {
  return UserInfo(1L, "Garry", "https://sublimeygmewhw4zeus.iqg")
}

fun getTestBoardGetDetailResponse(): BoardGetDetailResponse {
  return BoardGetDetailResponse(
    1L,
    "firms",
    "Canadian interface avoiding resorts paper manitoba",
    getTestUserInfo(),
    LocalDateTime
        .now()
        .toString()
  )
}
