package com.waterfogsw.board.board.controller

import NUMBER
import STRING
import com.fasterxml.jackson.databind.ObjectMapper
import com.waterfogsw.board.board.dto.BoardCreateRequest
import com.waterfogsw.board.board.dto.BoardGetDetailResponse
import com.waterfogsw.board.board.service.BoardCommandService
import com.waterfogsw.board.board.service.BoardQueryService
import com.waterfogsw.board.common.auth.Authentication
import com.waterfogsw.board.common.auth.AuthenticationContextHolder
import com.waterfogsw.board.common.auth.AuthenticationTokenResolver
import com.waterfogsw.board.core.user.domain.Role
import com.waterfogsw.board.restdoc.*
import com.waterfogsw.board.user.dto.UserInfo
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockkObject
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.MockBeans
import org.springframework.http.MediaType
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
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

      val titles = listOf(null, "", "\n", "a".repeat(21))
      titles.forEach { title ->
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

    context("설명의 길이가 1~200사이가 아니면") {
      val authentication = Authentication(1L, Role.USER)
      AuthenticationContextHolder.setAuthentication(authentication)

      val descriptions = listOf(null, "", "\n", "a".repeat(201))
      descriptions.forEach { description ->
        val requestBody = BoardCreateRequest("Test", description)
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

  describe("GET : /api/v1/boards/{id}") {
    val url = "/api/v1/boards/{id}"
    context("유효한 요청이 전달되면") {
      val id = 1L
      val response = getTestBoardGetDetailResponse()
      it("200 응답") {
        given(queryService.getDetail(anyLong())).willReturn(response)
        mockMvc
            .get(url, id)
            .andExpect {
              status { isOk() }
            }
            .andDocument("Lookup Board", {
              responseBody(
                "id" type NUMBER means "게시판 번호" isOptional false,
                "title" type STRING means "게시판 제목" isOptional false,
                "description" type STRING means "게시판 제목" isOptional true,
                "creatorInfo.id" type NUMBER means "게시판 생성자 번호" isOptional false,
                "creatorInfo.name" type STRING means "게시판 생성자 이름" isOptional false,
                "creatorInfo.imageUrl" type STRING means "게시판 생성자 이미지 URL" isOptional false,
                "createdAt" type STRING means "게시판 생성일" isOptional false,
              )
            })
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
