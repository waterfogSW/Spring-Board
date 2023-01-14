package com.waterfogsw.board.util.restdoc

import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.snippet.Snippet
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

fun ResultActions.andDocument(
  identifier: String,
  vararg snippets: Snippet
): ResultActions {
  return andDo(document(identifier, *snippets))
}

fun restDocMockMvcBuild(
  context: WebApplicationContext,
  restDocumentation: RestDocumentationContextProvider
): MockMvc {
  return MockMvcBuilders
      .webAppContextSetup(context)
      .apply<DefaultMockMvcBuilder>(
        MockMvcRestDocumentation
            .documentationConfiguration(restDocumentation)
            .operationPreprocessors()
            .withRequestDefaults(Preprocessors.prettyPrint())
            .withResponseDefaults(Preprocessors.prettyPrint())
      )
      .build()
}
