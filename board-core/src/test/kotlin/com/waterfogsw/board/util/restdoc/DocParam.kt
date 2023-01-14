package com.waterfogsw.board.util.restdoc

import org.springframework.restdocs.request.*

class RestDocParam(
  val descriptor: ParameterDescriptor
)

infix fun String.pathMeans(
  description: String
): RestDocParam {
  return createField(this, description)
}

private fun createField(
  value: String,
  description: String,
  optional: Boolean = true
): RestDocParam {
  val descriptor = RequestDocumentation
      .parameterWithName(value)
      .description(description)

  if (optional) descriptor.optional()

  return RestDocParam(descriptor)
}

fun pathParameters(vararg params: RestDocParam): PathParametersSnippet =
  RequestDocumentation.pathParameters(params.map { it.descriptor })
