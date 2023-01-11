package com.waterfogsw.board.restdoc

import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.snippet.Snippet
import org.springframework.test.web.servlet.ResultActionsDsl

fun ResultActionsDsl.andDocument(identifier: String, vararg snippets: Snippet): ResultActionsDsl {
  return andDo {
    handle(
      document(
        identifier,
        *snippets
      )
    )
  }
}
